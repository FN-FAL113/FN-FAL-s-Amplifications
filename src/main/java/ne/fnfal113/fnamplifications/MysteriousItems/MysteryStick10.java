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
import org.bukkit.attribute.Attribute;
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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class MysteryStick10 extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick10(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "expstickupgradedfinalfn");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "damagefinalfn");
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
        enchantments.put(Enchantment.SWEEPING_EDGE, 18);
        enchantments.put(Enchantment.DAMAGE_ALL, 20);
        enchantments.put(Enchantment.FIRE_ASPECT, 15);
        enchantments.put(Enchantment.DAMAGE_ARTHROPODS, 17);
        enchantments.put(Enchantment.DAMAGE_UNDEAD, 17);

        return enchantments;
    }

    public String weaponLore(){
        return ChatColor.GOLD + "Why is this stick too good";
    }

    public String stickLore(){
        return ChatColor.WHITE + "Deadly or creepy stick";
    }

    public List<String> effectLore(){
        List<String> lore2 = new ArrayList<>();
        lore2.add(0,"");
        lore2.add(1, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4&lEffects &f|◥◣◥◣◥◣◥◣"));
        lore2.add(2, ChatColor.BLUE +"◆ 15% Chance 5s Poison");
        lore2.add(3, ChatColor.BLUE +"◆ 13% Chance 5s Wither");
        lore2.add(4, ChatColor.BLUE +"◆ 13% Chance 4s Weakness");
        lore2.add(5, ChatColor.BLUE +"◆ 6% Chance ♡ Lifesteal");
        lore2.add(6, ChatColor.BLUE +"◆ 8% Chance 180° rotation");
        lore2.add(7, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4◢◤◤◥◤◥◥◣ &f|◥◣◥◣◥◣◥◣"));
        return lore2;
    }

    public void interact(PlayerInteractEvent e) {
        mainStick.onInteract(e, Material.DIAMOND_SWORD, true);
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

        meta.setLore(mainStick.loreUpdate(lore2, get_Damage, xpamount, weaponLore(), true));
        item.setItemMeta(meta);

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
            }
        } else{
            mainStick.darkenVision(player, 25);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_10, 25, stickLore(), 3);
        }

    }

    public void LevelChange(PlayerLevelChangeEvent event){
       mainStick.levelChange(event, FNAmpItems.FN_STICK_10, 25, 3);
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