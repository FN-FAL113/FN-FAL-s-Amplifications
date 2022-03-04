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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class MysteryStick9 extends AbstractStick {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick9(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "bowexp_xpfinal");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "bowdamage_damagefinal");
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
        enchantments.put(Enchantment.ARROW_DAMAGE, 13);
        enchantments.put(Enchantment.ARROW_INFINITE, 1);
        enchantments.put(Enchantment.ARROW_FIRE, 10);
        enchantments.put(Enchantment.ARROW_KNOCKBACK, 7);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "I wonder if Elves possess this relic";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "You need more mana when using this";
    }

    public List<String> effectLore(){
        List<String> lore2 = new ArrayList<>();
        lore2.add(0,"");
        lore2.add(1, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4&lEffects &f|◥◣◥◣◥◣◥◣"));
        lore2.add(2, ChatColor.BLUE +"◆ 9% Chance 3s Levitation");
        lore2.add(3, ChatColor.BLUE +"◆ 8% Chance 4s Harm");
        lore2.add(4, ChatColor.BLUE +"◆ 8% Chance 3s Blindness");
        lore2.add(5, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4◢◤◤◥◤◥◥◣ &f|◥◣◥◣◥◣◥◣"));
        return lore2;
    }

    @Override
    public void interact(PlayerInteractEvent e) {
        if(e.getPlayer().getLevel() >= 15) {
            mainStick.onInteract(e, Material.BOW, true);
        } else {
            blindPlayer(e.getPlayer(), 15);
        }
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event){
        Arrow arrow = (Arrow) event.getDamager();
        Player player = ((Player) arrow.getShooter());
        if(player == null){
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() != Material.BOW) {
            return;
        }

        if (ThreadLocalRandom.current().nextInt(100) < 40) {
            player.setLevel(player.getLevel() - 3);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.
                    fromLegacyText(Utils.colorTranslator("&d3 xp levels has been consumed from you")));
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

        if(event.getEntity() instanceof LivingEntity) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            if(ThreadLocalRandom.current().nextInt(100) < 9 && !(victim.hasPotionEffect(PotionEffectType.LEVITATION))){
                victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 1, false, true));
            }
            if(ThreadLocalRandom.current().nextInt(100) < 8 && !(victim.hasPotionEffect(PotionEffectType.HARM))){
                victim.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 80, 1, false, true));
            }
            if(ThreadLocalRandom.current().nextInt(100) < 8 && !(victim.hasPotionEffect(PotionEffectType.BLINDNESS))){
                victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1, false, true));
            }
        }

        if(player.getLevel() <= 20) {
            mainStick.darkenVision(player, 20);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_9, 20, stickLore(), 2);
        }
    }

    @Override
    public void LevelChange(PlayerLevelChangeEvent event){
        mainStick.levelChange(event, FNAmpItems.FN_STICK_9, 20, 2);
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
        new MysteryStick9(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_9, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 10), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 8), FNAmpItems.FN_STICK_6, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 8),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 10), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 4)})
                .register(plugin);
    }
}