package ne.fnfal113.fnamplifications.MysteriousItems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnMysteryStickAltar;
import ne.fnfal113.fnamplifications.MysteriousItems.Abstracts.AbstractStick;
import ne.fnfal113.fnamplifications.Utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class MysteryStick7 extends AbstractStick {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick7(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "expstickupgradedfinal");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "damagefinal");
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

    @Override
    public Map<Enchantment, Integer> enchantments(){
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SWEEPING_EDGE, 12);
        enchantments.put(Enchantment.DAMAGE_ALL, 10);
        enchantments.put(Enchantment.FIRE_ASPECT, 10);
        enchantments.put(Enchantment.DAMAGE_ARTHROPODS, 11);
        enchantments.put(Enchantment.DAMAGE_UNDEAD, 10);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "Blood will spit across the land";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "May the force and accuracy be with you";
    }

    public List<String> effectLore(){
        List<String> lore2 = new ArrayList<>();
        lore2.add(0,"");
        lore2.add(1, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4&lEffects &f|◥◣◥◣◥◣◥◣"));
        lore2.add(2, ChatColor.BLUE +"◆ 7% Chance 3s Poison");
        lore2.add(3, ChatColor.BLUE +"◆ 8% Chance 4s Wither");
        lore2.add(4, ChatColor.BLUE +"◆ 10% Chance 4s Weakness");
        lore2.add(5, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4◢◤◤◥◤◥◥◣ &f|◥◣◥◣◥◣◥◣"));
        return lore2;
    }

    @Override
    public void interact(PlayerInteractEvent e) {
        if(e.getPlayer().getLevel() >= 20) {
            mainStick.onInteract(e, Material.DIAMOND_SWORD, true);
        } else {
            blindPlayer(e.getPlayer(), 20);
        }
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)){
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

        if(player.getLevel() >= 20)  {
            if(ThreadLocalRandom.current().nextInt(100) < 40) {
                player.setLevel(player.getLevel() - 3);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.
                        fromLegacyText(Utils.colorTranslator("&d3 xp levels has been consumed from you")));
            }
            if(event.getEntity() instanceof LivingEntity) {
                LivingEntity victim = (LivingEntity) event.getEntity();
                if(ThreadLocalRandom.current().nextInt(100) < 7 && !(victim.hasPotionEffect(PotionEffectType.POISON))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 8 && !(victim.hasPotionEffect(PotionEffectType.WITHER))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 10 && !(victim.hasPotionEffect(PotionEffectType.WEAKNESS))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1, false, true));
                }
            }
        } else{
            mainStick.darkenVision(player, 20);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_7, 20, stickLore(), 2);
        }

    }

    @Override
    public void LevelChange(PlayerLevelChangeEvent event){
        mainStick.levelChange(event, FNAmpItems.FN_STICK_7, 20, 2);
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
        new MysteryStick7(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_7, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 5),  new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 8), FNAmpItems.FN_STICK_4, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 8),
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 8), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 5), new SlimefunItemStack(SlimefunItems.ENCHANTMENT_RUNE, 8)})
                .register(plugin);
    }
}