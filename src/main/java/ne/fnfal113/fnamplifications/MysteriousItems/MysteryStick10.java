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
import ne.fnfal113.fnamplifications.Multiblock.FnMysteryStickAltar;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
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
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MysteryStick10 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    @ParametersAreNonnullByDefault
    public MysteryStick10(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "expstickupgradedfinalfn");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "damagefinalfn");
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
        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();
        if(meta == null){
            return;
        }

        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damageAmount = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int damageAll = damageAmount.getOrDefault(key2, PersistentDataType.INTEGER, 0);

        List<String> lore2 = new ArrayList<>();
        meta.addEnchant(Enchantment.SWEEPING_EDGE, 18, true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 20, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 15, true);
        meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 17, true);
        meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 17, true);
        meta.setUnbreakable(true);
        meta.setLore(loreUpdate(lore2, damageAll, xpamount));
        item1.setItemMeta(meta);

        if(!(item1.getType() == Material.DIAMOND_SWORD)) {
            item1.setType(Material.DIAMOND_SWORD);
            player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            player.getWorld().playEffect(player.getLocation().add(0.3, 0.4, 0.45), Effect.ENDER_SIGNAL, 1);
            player.getWorld().spawnParticle(Particle.FLASH, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
            player.getWorld().spawnParticle(Particle.LAVA, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
        }

    }


    public void onSwing(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)){
            return;
        }
        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.DIAMOND_SWORD){
            return;
        }

        if(player.getLevel() >= 25) {
            if(ThreadLocalRandom.current().nextInt(100) < 47) {
                player.setLevel(player.getLevel() - 4);
            }
            if(event.getEntity() instanceof LivingEntity) {
                LivingEntity victim = (LivingEntity) event.getEntity();
                if(ThreadLocalRandom.current().nextInt(100) < 15 && !(victim.hasPotionEffect(PotionEffectType.POISON))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 3, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 13 && !(victim.hasPotionEffect(PotionEffectType.WITHER))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 3, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 13 && !(victim.hasPotionEffect(PotionEffectType.WEAKNESS))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 3, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 6){
                    int playerDefaultHealth = (int) Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
                    if(player.getHealth() < playerDefaultHealth - 2)  {
                        player.setHealth(player.getHealth() + 2);
                        victim.setHealth(victim.getHealth() - 2);
                    } else {
                        player.sendMessage(ChatColor.RED + "Make sure your hearts are not full for Lifesteal to proc!");
                    }
                }
                if(ThreadLocalRandom.current().nextInt(100) < 8){
                    Location loc = victim.getLocation();
                    loc.setYaw(loc.getYaw() + 180);
                    victim.teleport(loc);
                    victim.sendMessage(ChatColor.DARK_RED + "You have been disoriented! your opponent's mysterious stick is deadly");
                }
            } else {
                return;
            }
        }
        else{
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2, false, false));
            player.sendTitle(ChatColor.DARK_RED + "Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 45, 120, 135);
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than 25");
            transformWeapon(player, item);
        }

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();
        if(meta == null){
            return;
        }

        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damage = meta.getPersistentDataContainer();
        int damageamount = damage.getOrDefault(key2, PersistentDataType.INTEGER, 0);
        int get_Damage = (int) event.getDamage() + damageamount;
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        damage.set(key2, PersistentDataType.INTEGER, get_Damage);

        List<String> lore2 = new ArrayList<>();
        meta.setLore(loreUpdate(lore2, get_Damage, xpamount));
        item.setItemMeta(meta);

    }

    public List<String> loreUpdate(List<String> lore2, int get_Damage, int xpamount){
        lore2.add(0,ChatColor.GOLD + "Why is this stick too good");
        lore2.add(1, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
        lore2.add(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
        lore2.add(3, "");
        lore2.add(4, ChatColor.RED + "◢◤◢◤◢◤◢◤| "+ ChatColor.DARK_RED + "" + ChatColor.BOLD + "Effects " + ChatColor.WHITE + "|◥◣◥◣◥◣◥◣");
        lore2.add(5, ChatColor.BLUE +"◆ 15% Chance 5s Poison");
        lore2.add(6, ChatColor.BLUE +"◆ 13% Chance 5s Wither");
        lore2.add(7, ChatColor.BLUE +"◆ 13% Chance 4s Weakness");
        lore2.add(8, ChatColor.BLUE +"◆ 6% Chance ♡ Lifesteal");
        lore2.add(9, ChatColor.BLUE +"◆ 8% Chance 180° rotation");
        lore2.add(10,ChatColor.RED + "◢◤◢◤◢◤◢◤| " + ChatColor.DARK_RED + "  ◢◤◤◥◤◥◥◣   " + ChatColor.WHITE + "|◥◣◥◣◥◣◥◣");
        return lore2;
    }

    public void LevelChange(PlayerLevelChangeEvent event){
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if(event.getOldLevel() > event.getNewLevel()) {
            transformWeapon(p, item);
        }
    }

    public void transformWeapon(Player p, ItemStack item) {
        CustomItemStack item2 = new CustomItemStack(FNAmpItems.FN_STICK_10);

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();
        if(meta == null){
            return;
        }

        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damage = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int damageamount = damage.getOrDefault(key2, PersistentDataType.INTEGER, 0);
        int amount = ++xpamount + 3;
        expUsed.set(key, PersistentDataType.INTEGER, amount);

        List<String> lore = new ArrayList<>();
        meta.setLore(loreUpdate(lore, damageamount, amount));
        item.setItemMeta(meta);

        if (p.getLevel() <= 25) {
            lore.remove(3);
            lore.remove(3);
            lore.remove(3);
            lore.remove(3);
            lore.remove(3);
            lore.remove(3);
            lore.remove(3);
            lore.remove(3);
            lore.set(0, ChatColor.WHITE + "Deadly or creepy stick");
            lore.set(1, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + amount);
            lore.set(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + damageamount);
            meta.setLore(lore);
            meta.removeEnchant(Enchantment.SWEEPING_EDGE);
            meta.removeEnchant(Enchantment.DAMAGE_ALL);
            meta.removeEnchant(Enchantment.FIRE_ASPECT);
            meta.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
            meta.removeEnchant(Enchantment.DAMAGE_UNDEAD);
            item.setItemMeta(meta);
            item.setType(item2.getType());
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
        new MysteryStick10(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_10, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 5), FNAmpItems.FN_STICK_4, new SlimefunItemStack(SlimefunItems.ENCHANTMENT_RUNE, 16),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 16), FNAmpItems.FN_STICK_7, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 16),
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 16), FNAmpItems.FN_STICK, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 5)})
                .register(plugin);
    }
}