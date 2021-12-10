package ne.fnfal113.fnamplifications.MysteriousItems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.stripColor;

public class MysteryStick6 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    @ParametersAreNonnullByDefault
    public MysteryStick6(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "bowexp_xp");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "bowdamage_damage");
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    protected @Nonnull
    NamespacedKey getStorageKey2() {
        return defaultUsageKey2;
    }

    public void interact(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item1 = player.getInventory().getItemInMainHand();

        ItemMeta meta = item1.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Make them take an arrow to the knee");
        lore.add(ChatColor.YELLOW + "Exp Levels Consumed:");
        lore.add(ChatColor.YELLOW + "Total Damage inflicted:");
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 20, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
        meta.addEnchant(Enchantment.ARROW_FIRE, 10, true);
        meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 10, true);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        item1.setItemMeta(meta);
        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();
        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damageAmount = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int damageAll = damageAmount.getOrDefault(key2, PersistentDataType.INTEGER, 0);

        List<String> lore2 = meta.getLore();
        if (lore2 != null && !lore2.isEmpty()) {
            for (int index = 0; index < lore2.size(); index++) {
                String line = lore2.get(index);
                if (stripColor(line).startsWith("Exp Levels Consumed:")) {
                    lore2.set(index, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
                    meta.setLore(lore2);
                    item1.setItemMeta(meta);
                }
                if (stripColor(line).startsWith("Total Damage inflicted:")) {
                    lore2.set(index, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + damageAll);
                    meta.setLore(lore2);
                    item1.setItemMeta(meta);
                }
            }
        }

        if(!(item1.getType() == Material.BOW)) {
            item1.setType(Material.BOW);
            player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            player.getWorld().playEffect(player.getLocation().add(0.3, 0.4, 0.45), Effect.ENDER_SIGNAL, 1);
            player.getWorld().spawnParticle(Particle.FLASH, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
        }

    }

    public void onSwing(EntityDamageByEntityEvent event){
        Arrow arrow = (Arrow) event.getDamager();
        Player player = ((Player) arrow.getShooter());
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.BOW) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key2 = getStorageKey2();
        PersistentDataContainer damage = meta.getPersistentDataContainer();
        int damageamount = damage.getOrDefault(key2, PersistentDataType.INTEGER, 0);
        int get_Damage = (int) event.getDamage() + damageamount;
        damage.set(key2, PersistentDataType.INTEGER, get_Damage);
        List<String> lore2 = meta.getLore();
        if (lore2 != null && !lore2.isEmpty()) {
            for (int index = 0; index < lore2.size(); index++) {
                String line = lore2.get(index);
                if (stripColor(line).startsWith("Total Damage inflicted:")) {
                    lore2.set(index, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
                    meta.setLore(lore2);
                    item.setItemMeta(meta);
                }
            }
        }

        if(player.getLevel() <= 15) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2, false, false));
            player.sendTitle(ChatColor.DARK_RED + "Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 45, 120, 135);
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than 15");
            transformWeapon(player, item);
        }

    }

    public void LevelChange(PlayerLevelChangeEvent event){
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if(event.getOldLevel() > event.getNewLevel() && p.getLevel() > 15) {
            transformWeapon(p, item);
        }
    }

    public void transformWeapon(Player p, ItemStack item) {
        CustomItemStack item2 = new CustomItemStack(FNAmpItems.FN_STICK_6);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = getStorageKey();
        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int amount = ++xpamount + 4;
        expUsed.set(key, PersistentDataType.INTEGER, amount);
        List<String> lore = meta.getLore();

        if (lore != null && !lore.isEmpty()) {
            for (int index = 0; index < lore.size(); index++) {
                String line = lore.get(index);
                if (stripColor(line).startsWith("Exp Levels Consumed:")) {
                    lore.set(index, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + amount);
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
                if (stripColor(line).startsWith("Make them take an arrow to the knee") && p.getLevel() <= 15) {
                    lore.set(index, ChatColor.WHITE + "May the force and accuracy be with you");
                    lore.set(index+1, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + amount);
                    meta.setLore(lore);
                    meta.removeEnchant(Enchantment.ARROW_DAMAGE);
                    meta.removeEnchant(Enchantment.ARROW_INFINITE);
                    meta.removeEnchant(Enchantment.ARROW_FIRE);
                    meta.removeEnchant(Enchantment.ARROW_KNOCKBACK);
                    item.setItemMeta(meta);
                    item.setType(item2.getType());
                }
            }
        }
    }

    @Override
    public boolean isEnchantable() {
        return false;
    }

    @Override
    public boolean isDisenchantable() {
        return false;
    }

    @Override
    public boolean isUseableInWorkbench() {
        return false;
    }

    public static void setup(){
        new MysteryStick6(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_6, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
                SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.AIR_RUNE, SlimefunItems.ESSENCE_OF_AFTERLIFE,
                SlimefunItems.FIRE_RUNE, FNAmpItems.FN_STICK_3, SlimefunItems.EARTH_RUNE,
                SlimefunItems.ENDER_LUMP_3, SlimefunItems.ENDER_RUNE, SlimefunItems.MAGIC_LUMP_3})
                .register(plugin);
    }
}
