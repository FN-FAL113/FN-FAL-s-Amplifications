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
import org.bukkit.entity.LivingEntity;
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
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.ChatColor.stripColor;

public class MysteryStick9 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    @ParametersAreNonnullByDefault
    public MysteryStick9(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "bowexp_xpfinal");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "bowdamage_damagefinal");
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
        lore.add(ChatColor.GOLD + "I wonder if Elves possess this relic");
        lore.add(ChatColor.YELLOW + "Exp Levels Consumed:");
        lore.add(ChatColor.YELLOW + "Total Damage inflicted:");
        lore.add("");
        lore.add(ChatColor.RED + "◢◤◢◤◢◤◢◤| "+ ChatColor.DARK_RED + "" + ChatColor.BOLD + "Effects " + ChatColor.WHITE + "|◥◣◥◣◥◣◥◣");
        lore.add(ChatColor.BLUE +"◆ 35% Chance 3s Levitation");
        lore.add(ChatColor.BLUE +"◆ 30% Chance 4s Harm");
        lore.add(ChatColor.BLUE +"◆ 20% Chance 3s Blindness");
        lore.add(ChatColor.RED + "◢◤◢◤◢◤◢◤| " + ChatColor.DARK_RED + "  ◢◤◤◥◤◥◥◣   " + ChatColor.WHITE + "|◥◣◥◣◥◣◥◣");
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 25, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 15, true);
        meta.addEnchant(Enchantment.ARROW_FIRE, 15, true);
        meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 15, true);
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
            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
        }

    }

    public void onSwing(EntityDamageByEntityEvent event){
        Arrow arrow = (Arrow) event.getDamager();
        Player player = ((Player) arrow.getShooter());
        ItemStack item = player.getInventory().getItemInMainHand();
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

        if(event.getEntity() instanceof LivingEntity) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            if(ThreadLocalRandom.current().nextInt(100) < 35){
                victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 1, false, true));
            }
            if(ThreadLocalRandom.current().nextInt(100) < 30){
                victim.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 80, 1, false, true));
            }
            if(ThreadLocalRandom.current().nextInt(100) < 20){
                victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1, false, true));
            }
        }
    }

    public void LevelChange(PlayerLevelChangeEvent event){
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        CustomItemStack item2 = new CustomItemStack(FNAmpItems.FN_STICK_9);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = getStorageKey();
        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int amount = ++xpamount + 5;
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
                if (stripColor(line).startsWith("I wonder if Elves possess this relic") && p.getLevel() <= 5) {
                    lore.remove(3);
                    lore.remove(3);
                    lore.remove(3);
                    lore.remove(3);
                    lore.remove(3);
                    lore.remove(3);
                    lore.set(index, ChatColor.WHITE + "You need more mana when using this");
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
        new MysteryStick9(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_9, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
                SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.AIR_RUNE, SlimefunItems.ESSENCE_OF_AFTERLIFE,
                SlimefunItems.FIRE_RUNE, FNAmpItems.FN_STICK_6, SlimefunItems.EARTH_RUNE,
                SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.ENDER_RUNE, SlimefunItems.ESSENCE_OF_AFTERLIFE})
                .register(plugin);
    }
}
