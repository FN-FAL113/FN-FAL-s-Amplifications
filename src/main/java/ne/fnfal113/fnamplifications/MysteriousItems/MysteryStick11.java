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
import java.util.concurrent.ThreadLocalRandom;

public class MysteryStick11 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    @ParametersAreNonnullByDefault
    public MysteryStick11(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "axeexp_xpfinalfn");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "axeexpdamage_damagefinalfn");
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
        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damageAmount = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int damageAll = damageAmount.getOrDefault(key2, PersistentDataType.INTEGER, 0);

        List<String> lore2 = new ArrayList<>();
        meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 20, true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 18, true);
        meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 18, true);
        meta.setUnbreakable(true);
        meta.setLore(loreUpdate(lore2, damageAll, xpamount));
        item1.setItemMeta(meta);

        if(!(item1.getType() == Material.DIAMOND_AXE)) {
            item1.setType(Material.DIAMOND_AXE);
            player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            player.getWorld().playEffect(player.getLocation().add(0.3, 0.4, 0.45), Effect.ENDER_SIGNAL, 1);
            player.getWorld().spawnParticle(Particle.FLASH, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
        }

    }


    public void onSwing(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)){
            return;
        }
        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.DIAMOND_AXE){
            return;
        }

        if(player.getLevel() >= 25)  {
            if(ThreadLocalRandom.current().nextInt(100) < 35) {
                player.setLevel(player.getLevel() - 4);
            }
            if(event.getEntity() instanceof LivingEntity) {
                LivingEntity victim = (LivingEntity) event.getEntity();
                if(ThreadLocalRandom.current().nextInt(100) < 35 && !(victim.hasPotionEffect(PotionEffectType.SLOW))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 40 && !(victim.hasPotionEffect(PotionEffectType.WEAKNESS))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 30 && !(victim.hasPotionEffect(PotionEffectType.HUNGER))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 10){
                    double nX;
                    double nZ;
                    float nang = victim.getLocation().getYaw() + 90;

                    if(nang < 0) nang += 360;

                    nX = Math.cos(Math.toRadians(nang));
                    nZ = Math.sin(Math.toRadians(nang));

                    Location newDamagerLoc = new Location(player.getWorld(), victim.getLocation().getX() - nX,
                            victim.getLocation().getY(), victim.getLocation().getZ() - nZ, victim.getLocation().getYaw(), victim.getLocation().getPitch());
                    player.teleport(newDamagerLoc);
                    victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Behind you awaits danger");
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
        lore2.add(0,ChatColor.GOLD + "Behind your enemies awaits danger");
        lore2.add(1, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
        lore2.add(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
        lore2.add(3, "");
        lore2.add(4, ChatColor.RED + "◢◤◢◤◢◤◢◤| "+ ChatColor.DARK_RED + "" + ChatColor.BOLD + "Effects " + ChatColor.WHITE + "|◥◣◥◣◥◣◥◣");
        lore2.add(5, ChatColor.BLUE +"◆ 35% Chance 5s Slow");
        lore2.add(6, ChatColor.BLUE +"◆ 40% Chance 4s Weakness");
        lore2.add(7, ChatColor.BLUE +"◆ 30% Chance 5s Hunger");
        lore2.add(8, ChatColor.BLUE +"◆ 10% Chance tp behind opponent");
        lore2.add(9, ChatColor.RED + "◢◤◢◤◢◤◢◤| " + ChatColor.DARK_RED + "  ◢◤◤◥◤◥◥◣   " + ChatColor.WHITE + "|◥◣◥◣◥◣◥◣");
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
        CustomItemStack item2 = new CustomItemStack(FNAmpItems.FN_STICK_11);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();
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
            lore.set(0, ChatColor.WHITE + "The stick of the nords");
            lore.set(1, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + amount);
            lore.set(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + damageamount);
            meta.setLore(lore);
            meta.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
            meta.removeEnchant(Enchantment.DAMAGE_ALL);
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
        new MysteryStick11(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_11, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_STICK_2, SlimefunItems.ESSENCE_OF_AFTERLIFE, FNAmpItems.FN_STICK_8,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 32), FNAmpItems.FN_STICK_5, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 32),
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 32), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 32), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 32)})
                .register(plugin);
    }
}
