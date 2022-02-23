package ne.fnfal113.fnamplifications.MysteriousItems;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class MainStick {

    @Getter
    public final NamespacedKey storageKey;
    @Getter
    public final NamespacedKey storageKey2;
    @Getter
    public final Map<Enchantment, Integer> enchantmentMap;
    @Getter
    public final String weaponLore;
    @Getter
    public final String stickLore;
    @Getter
    public final List<String> effects;

    public MainStick(NamespacedKey key1, NamespacedKey key2, Map<Enchantment, Integer> enchantmentMap, String weaponLore, String stickLore){
        this(key1, key2, enchantmentMap, weaponLore, stickLore, null);
    }

    public MainStick(NamespacedKey key1, NamespacedKey key2, Map<Enchantment, Integer> enchantmentMap, String weaponLore, String stickLore, List<String> effects){
        this.storageKey = key1;
        this.storageKey2 = key2;
        this.enchantmentMap = enchantmentMap;
        this.weaponLore = weaponLore;
        this.stickLore = stickLore;
        this.effects = effects;
    }

    public void onInteract(PlayerInteractEvent e, Material material, boolean withEffects){
        Player player = e.getPlayer();
        ItemStack item1 = player.getInventory().getItemInMainHand();

        ItemMeta meta = item1.getItemMeta();
        List<String> lore2 = meta.getLore();

        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();

        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damageAmount = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int damageAll = damageAmount.getOrDefault(key2, PersistentDataType.INTEGER, 0);

        getEnchantmentMap().forEach((Key, Value) -> meta.addEnchant(Key, Value, true));
        meta.setUnbreakable(true);
        meta.setLore(loreUpdate(lore2, damageAll, xpamount, weaponLore, withEffects));
        item1.setItemMeta(meta);

        if(!(item1.getType() == material)) {
            item1.setType(material);
            player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            player.getWorld().playEffect(player.getLocation().add(0.3, 0.4, 0.45), Effect.ENDER_SIGNAL, 1);
            player.getWorld().spawnParticle(Particle.FLASH, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
        }
    }

    public void levelChange(PlayerLevelChangeEvent event, SlimefunItemStack slimefunItem, int level, int incrementLevel){
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if(event.getOldLevel() > event.getNewLevel() && p.getLevel() > level) {
            transformWeapon(p, item, slimefunItem, level, weaponLore, incrementLevel);
        }
    }

    public void transformWeapon(Player p, ItemStack item, SlimefunItemStack slimefunItem, int level, String finalLore, int incrementLevel) {
        CustomItemStack item2 = new CustomItemStack(slimefunItem);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damage = meta.getPersistentDataContainer();
        int xpAmount = expUsed.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, 0);
        int damageAmount = damage.getOrDefault(getStorageKey2(), PersistentDataType.INTEGER, 0);
        int amount = ++xpAmount + incrementLevel;


        expUsed.set(getStorageKey(), PersistentDataType.INTEGER, amount);
        meta.setLore(loreUpdate(lore, damageAmount, amount, finalLore, false));
        item.setItemMeta(meta);

        if (p.getLevel() <= level) {
            if (lore.size() >= 4) {
                lore.subList(3, lore.size()).clear();
            }
            meta.setLore(lore);
            getEnchantmentMap().forEach((Key, Value) -> meta.removeEnchant(Key));
            item.setItemMeta(meta);
            item.setType(item2.getType());
        }

    }

    public List<String> loreUpdate(List<String> lore2, int get_Damage, int xpamount, String lore, boolean withEffects){
        if(lore2.size() == 1) {
            lore2.remove(0);
            lore2.add(lore);
            lore2.add(ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
            lore2.add(ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
        } else{
            lore2.set(0,lore);
            lore2.set(1,ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
            lore2.set(2,ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
        }

        if(withEffects){
            if(!(lore2.size() >= 4)){
                lore2.addAll(getEffects());
            }
        }

        return lore2;
    }

    public void darkenVision(Player player,int level){
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2, false, false));
        player.sendTitle(ChatColor.DARK_RED + "Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 45, 120, 135);
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than " + level);
    }

}
