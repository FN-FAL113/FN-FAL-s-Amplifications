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
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class MysteryStick8 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick8(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "axeexp_xpfinal");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "axeexpdamage_damagefinal");
        this.mainStick = new MainStick(getStorageKey(), getStorageKey2(), enchantments(), weaponLore(), stickLore(), effectLore());
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
        enchantments.put(Enchantment.DAMAGE_ARTHROPODS, 12);
        enchantments.put(Enchantment.DAMAGE_ALL, 10);
        enchantments.put(Enchantment.DAMAGE_UNDEAD, 11);
        enchantments.put(Enchantment.KNOCKBACK, 6);

        return enchantments;
    }

    public String weaponLore(){
        return ChatColor.GOLD + "I'm out of words using this";
    }

    public String stickLore(){
        return ChatColor.WHITE + "This stick is kinda heavy";
    }

    public List<String> effectLore(){
        List<String> lore2 = new ArrayList<>();
        lore2.add(0,"");
        lore2.add(1, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4&lEffects &f|◥◣◥◣◥◣◥◣"));
        lore2.add(2, ChatColor.BLUE +"◆ 8% Chance 4s Slow");
        lore2.add(3, ChatColor.BLUE +"◆ 7% Chance 3s Weakness");
        lore2.add(4, ChatColor.BLUE +"◆ 10% Chance 4s Hunger");
        lore2.add(5, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4◢◤◤◥◤◥◥◣ &f|◥◣◥◣◥◣◥◣"));
        return lore2;
    }

    public void interact(PlayerInteractEvent e) {
        mainStick.onInteract(e, Material.DIAMOND_AXE, true);
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

        if(item.getType() != Material.DIAMOND_AXE){
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

        meta.setLore(mainStick.loreUpdate(lore2, get_Damage, xpamount, weaponLore(), true));
        item.setItemMeta(meta);

        if(player.getLevel() >= 20)  {
            if(ThreadLocalRandom.current().nextInt(100) < 30) {
                player.setLevel(player.getLevel() - 3);
            }
            if(event.getEntity() instanceof LivingEntity) {
                LivingEntity victim = (LivingEntity) event.getEntity();
                if(ThreadLocalRandom.current().nextInt(100) < 8 && !(victim.hasPotionEffect(PotionEffectType.SLOW))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 7 && !(victim.hasPotionEffect(PotionEffectType.WEAKNESS))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 10 && !(victim.hasPotionEffect(PotionEffectType.HUNGER))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 80, 1, false, true));
                }
            }
        } else{
           mainStick.darkenVision(player, 20);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_8, 20, stickLore(), 2);
        }

    }

    public void LevelChange(PlayerLevelChangeEvent event){
        mainStick.levelChange(event, FNAmpItems.FN_STICK_8, 20, 2);
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
        new MysteryStick8(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_8, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 10), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4), new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 10),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4), FNAmpItems.FN_STICK_5, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4),
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 10), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 10)})
                .register(plugin);
    }
}