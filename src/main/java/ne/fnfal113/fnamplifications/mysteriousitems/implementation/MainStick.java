package ne.fnfal113.fnamplifications.mysteriousitems.implementation;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.Getter;
import ne.fnfal113.fnamplifications.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
    public final int effectCount;
    @Getter
    public final int requiredLevel;

    @ParametersAreNonnullByDefault
    public MainStick(NamespacedKey key1, NamespacedKey key2, Map<Enchantment, Integer> enchantmentMap, String weaponLore, String stickLore){
        this(key1, key2, enchantmentMap, weaponLore, stickLore, 0, 0);
    }

    @ParametersAreNonnullByDefault
    public MainStick(NamespacedKey key1, NamespacedKey key2, Map<Enchantment, Integer> enchantmentMap, String weaponLore, String stickLore, int effectCount, int levelReq){
        this.storageKey = key1;
        this.storageKey2 = key2;
        this.enchantmentMap = enchantmentMap;
        this.weaponLore = weaponLore;
        this.stickLore = stickLore;
        this.effectCount = effectCount;
        this.requiredLevel = levelReq;
    }

    /**
     *
     * @param pdc the persistent data associated with the item meta
     * @param key the namespaced key or identifier for where the pdc resides
     * @return the value that the pdc contains
     */
    public int getPdc(PersistentDataContainer pdc, NamespacedKey key){
        return pdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    /**
     * transforms the stick to a weapon on right click if the player has the sufficient levels
     * @param e the interact event
     * @param material the material of the item that will be used for transformation
     */
    @ParametersAreNonnullByDefault
    public void onInteract(PlayerInteractEvent e, Material material){
        if(!(e.getPlayer().getLevel() >= getRequiredLevel())) {
            darkenVision(e.getPlayer(), getRequiredLevel());
            return;
        }

        Player player = e.getPlayer();
        ItemStack item1 = player.getInventory().getItemInMainHand();

        ItemMeta meta = item1.getItemMeta();
        List<String> lore2 = meta.getLore();

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        getEnchantmentMap().forEach((Key, Value) -> meta.addEnchant(Key, Value, true));
        meta.setUnbreakable(true);
        meta.setLore(loreUpdate(lore2, getPdc(pdc, getStorageKey2()), getPdc(pdc, getStorageKey()), getWeaponLore()));
        item1.setItemMeta(meta);

        if(!(item1.getType() == material)) {
            item1.setType(material);
            player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            player.getWorld().playEffect(player.getLocation().add(0.3, 0.4, 0.45), Effect.ENDER_SIGNAL, 1);
            player.getWorld().spawnParticle(Particle.FLASH, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
        }
    }

    /**
     * transform the weapon back to a stick if player doesn't have enough exp levels
     * @param p the player involved
     * @param item the weapon used
     * @param slimefunItem the item as an slimefun item instance
     * @param finalLore the final changes to the persistent lore before transforming the weapon
     * @param incrementLevel the value for incrementing the current exp level pdc data
     */
    public void transformWeapon(Player p, ItemStack item, SlimefunItemStack slimefunItem, String finalLore, int incrementLevel, int damageAmount, boolean isLevelDeducted) {
        CustomItemStack item2 = new CustomItemStack(slimefunItem);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        int xpAmount = getPdc(pdc, getStorageKey());
        int damageInflicted =  damageAmount + getPdc(pdc, getStorageKey2());
        pdc.set(getStorageKey2(), PersistentDataType.INTEGER, damageInflicted);

        if(isLevelDeducted) {
            int amount = ++xpAmount + incrementLevel;
            pdc.set(getStorageKey(), PersistentDataType.INTEGER, amount);
            meta.setLore(loreUpdate(lore, damageInflicted, amount, finalLore));
        } else {
            meta.setLore(loreUpdate(lore, damageInflicted, xpAmount, finalLore));
        }

        item.setItemMeta(meta);

        if (p.getLevel() <= getRequiredLevel()) {
            if (lore.size() >= 4) {
                lore.subList(3, lore.size()).clear();
            }
            meta.setLore(lore);
            meta.getEnchants().forEach((enchant, value) -> meta.removeEnchant(enchant));
            item.setItemMeta(meta);
            item.setType(item2.getType());
        }

    }

    public boolean onSwing(ItemStack item, SlimefunItemStack slimefunItemStack, Player player, double damageAmount, int chance, int levelDeduction){
        if(player.getLevel() >= getRequiredLevel()) {
            if (ThreadLocalRandom.current().nextInt(100) < chance) {
                player.setLevel(player.getLevel() - levelDeduction);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.
                        fromLegacyText(Utils.colorTranslator("&d" + levelDeduction + " xp levels has been consumed from you")));
                transformWeapon(player, item, slimefunItemStack, getStickLore(), levelDeduction - 1, (int) damageAmount, true);
                return true;
            }
            transformWeapon(player, item, slimefunItemStack, getStickLore(), levelDeduction - 1, (int) damageAmount, false);
        } else {
            darkenVision(player, getRequiredLevel());
        }
        return false;
    }

    /**
     * updates the item lore with the persistent data changes as the values
     * @param lore2 the lore of the itemstack
     * @param get_Damage the damage amount inflicted to the enemy that is store
     * @param xpamount the amount of xp levels deducted from the player who uses the stick
     * @param lore the default lore of the item except for persistent lore
     * @return the lore that has been updated with the current changes to pdc data
     */
    public List<String> loreUpdate(List<String> lore2, int get_Damage, int xpamount, String lore){
        if(lore2.size() == 1) {
            lore2.remove(0);
            lore2.add(lore);
            lore2.add(ChatColor.YELLOW + "Total Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
            lore2.add(ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
        } else {
            lore2.set(0, lore);
            lore2.set(1, ChatColor.YELLOW + "Total Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
            lore2.set(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
        }

        if(!(lore2.size() >= 4)){
            lore2.add("");
            lore2.add(Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4&lEffects &f|◥◣◥◣◥◣◥◣"));
            lore2.add(ChatColor.BLUE + "◆ "  + getEffectCount() + " Mystery effect/s");
            lore2.add(Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4◢◤◤◥◤◥◤◥◤◥◥◣ &f|◥◣◥◣◥◣◥◣"));
        }

        return lore2;
    }

    @ParametersAreNonnullByDefault
    public void darkenVision(Player player, int level){
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2, false, false));
        player.sendTitle(ChatColor.DARK_RED + "Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 45, 120, 135);
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than " + level);
    }

}