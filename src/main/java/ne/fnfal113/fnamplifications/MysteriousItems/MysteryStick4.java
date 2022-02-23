package ne.fnfal113.fnamplifications.MysteriousItems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnMysteryStickAltar;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class MysteryStick4 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick4(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "expstickupgraded");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "damage");
        this.mainStick = new MainStick(getStorageKey(), getStorageKey2(), enchantments(), weaponLore(), stickLore());
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    protected @Nonnull
    NamespacedKey getStorageKey2() {
        return defaultUsageKey2;
    }

    public Map<Enchantment, Integer> enchantments(){
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SWEEPING_EDGE, 6);
        enchantments.put(Enchantment.DAMAGE_ALL, 7);
        enchantments.put(Enchantment.FIRE_ASPECT, 4);
        enchantments.put(Enchantment.DAMAGE_ARTHROPODS, 5);
        enchantments.put(Enchantment.DAMAGE_UNDEAD, 6);

        return enchantments;
    }

    public String weaponLore(){
        return ChatColor.GOLD + "It was indeed a magical improvement";
    }

    public String stickLore(){
        return ChatColor.WHITE + "Did I use this before or maybe not";
    }

    public void interact(PlayerInteractEvent e) {
        mainStick.onInteract(e, Material.DIAMOND_SWORD, false);
    }

    public void onSwing(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)){
            return;
        }
        if(event.getCause() == EntityDamageEvent.DamageCause.THORNS){
            return;
        }
        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.DIAMOND_SWORD){
            return;
        }

        ItemMeta meta = item.getItemMeta();
        List<String> lore2 = meta.getLore();

        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damage = meta.getPersistentDataContainer();
        int damageamount = damage.getOrDefault(getStorageKey2(), PersistentDataType.INTEGER, 0);
        int get_Damage = (int) event.getDamage() + damageamount;
        int xpamount = expUsed.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, 0);
        damage.set(getStorageKey2(), PersistentDataType.INTEGER, get_Damage);

        meta.setLore(mainStick.loreUpdate(lore2, get_Damage, xpamount, weaponLore(), false));
        item.setItemMeta(meta);

        if(player.getLevel() >= 15)  {
            if(ThreadLocalRandom.current().nextInt(100) < 30) {
                player.setLevel(player.getLevel() - 2);
            }
        } else{
            mainStick.darkenVision(player, 15);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_4, 15, stickLore(), 1);
        }

    }

    public void LevelChange(PlayerLevelChangeEvent event){
       mainStick.levelChange(event, FNAmpItems.FN_STICK_4, 15, 1);
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
        new MysteryStick4(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_4, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 16),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 4), FNAmpItems.FN_STICK, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 4),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 16), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 4), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1)})
                .register(plugin);
    }
}
