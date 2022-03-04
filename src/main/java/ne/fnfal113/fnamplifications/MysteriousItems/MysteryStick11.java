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
public class MysteryStick11 extends AbstractStick {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick11(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "axeexp_xpfinalfn");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "axeexpdamage_damagefinalfn");
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
        enchantments.put(Enchantment.DAMAGE_ARTHROPODS, 18);
        enchantments.put(Enchantment.DAMAGE_ALL, 16);
        enchantments.put(Enchantment.DAMAGE_UNDEAD, 15);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "Behind your enemies awaits danger";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "The stick of the nords";
    }

    public List<String> effectLore(){
        List<String> lore2 = new ArrayList<>();
        lore2.add(0,"");
        lore2.add(1, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4&lEffects &f|◥◣◥◣◥◣◥◣"));
        lore2.add(2, ChatColor.BLUE +"◆ 15% Chance 5s Slow");
        lore2.add(3, ChatColor.BLUE +"◆ 13% Chance 4s Weakness");
        lore2.add(4, ChatColor.BLUE +"◆ 13% Chance 5s Hunger");
        lore2.add(5, ChatColor.BLUE +"◆ 10% Chance tp behind opponent");
        lore2.add(6, Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4◢◤◤◥◤◥◥◣ &f|◥◣◥◣◥◣◥◣"));
        return lore2;
    }

    @Override
    public void interact(PlayerInteractEvent e) {
        if(e.getPlayer().getLevel() >= 25) {
            mainStick.onInteract(e, Material.DIAMOND_AXE, true);
        } else {
            blindPlayer(e.getPlayer(), 25);
        }
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)){
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

        if(player.getLevel() >= 25)  {
            if(ThreadLocalRandom.current().nextInt(100) < 35) {
                player.setLevel(player.getLevel() - 4);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.
                        fromLegacyText(Utils.colorTranslator("&d4 xp levels has been consumed from you")));
            }
            if(event.getEntity() instanceof LivingEntity) {
                LivingEntity victim = (LivingEntity) event.getEntity();
                if(ThreadLocalRandom.current().nextInt(100) < 15 && !(victim.hasPotionEffect(PotionEffectType.SLOW))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 13 && !(victim.hasPotionEffect(PotionEffectType.WEAKNESS))){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1, false, true));
                }
                if(ThreadLocalRandom.current().nextInt(100) < 13 && !(victim.hasPotionEffect(PotionEffectType.HUNGER))){
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
            }
        } else{
            mainStick.darkenVision(player, 25);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_11, 25, stickLore(), 3);
        }

    }

    @Override
    public void LevelChange(PlayerLevelChangeEvent event){
        mainStick.levelChange(event, FNAmpItems.FN_STICK_11, 25, 3);
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
                FNAmpItems.FN_STICK_2, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 5), FNAmpItems.FN_STICK_8,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 5), FNAmpItems.FN_STICK_5, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 5),
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 16), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 5), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 16)})
                .register(plugin);
    }
}
