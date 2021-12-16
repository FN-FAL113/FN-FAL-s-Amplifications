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

public class MysteryStick2 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    @ParametersAreNonnullByDefault
    public MysteryStick2(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "axeexp");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "axeexpdamage");
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
        meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 3, true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 3, true);
        meta.setUnbreakable(true);
        meta.setLore(loreUpdate(lore2, damageAll, xpamount));
        item1.setItemMeta(meta);

        if(!(item1.getType() == Material.DIAMOND_AXE)) {
            item1.setType(Material.DIAMOND_AXE);
            player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            player.getWorld().playEffect(player.getLocation().add(0.3, 0.4, 0.45), Effect.ENDER_SIGNAL, 1);
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

        if(player.getLevel() >= 5)  {
            if(ThreadLocalRandom.current().nextInt(100) < 20) {
                player.setLevel(player.getLevel() - 1);
            }
        }
        else{
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2, false, false));
            player.sendTitle(ChatColor.DARK_RED + "Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 45, 120, 135);
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than 5");
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
        lore2.add(0,ChatColor.GOLD + "Another stick of wrecking");
        lore2.add(1, ChatColor.YELLOW + "Exp Levels Consumed: " + ChatColor.WHITE + xpamount);
        lore2.add(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + get_Damage);
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
        CustomItemStack item2 = new CustomItemStack(FNAmpItems.FN_STICK_2);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();
        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damage = meta.getPersistentDataContainer();
        int xpamount = expUsed.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int damageamount = damage.getOrDefault(key2, PersistentDataType.INTEGER, 0);
        int amount = ++xpamount;
        expUsed.set(key, PersistentDataType.INTEGER, amount);

        List<String> lore = new ArrayList<>();
        meta.setLore(loreUpdate(lore, damageamount, amount));
        item.setItemMeta(meta);

        if (p.getLevel() <= 5) {
            lore.set(0, ChatColor.WHITE + "Another stick of no matter what is it");
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
        new MysteryStick2(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_2, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 5), new ItemStack(Material.LEATHER), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 4),
                SlimefunItems.BLANK_RUNE, new ItemStack(Material.STICK), SlimefunItems.BLANK_RUNE,
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 4), new ItemStack(Material.LEATHER), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 5)})
                .register(plugin);
    }
}