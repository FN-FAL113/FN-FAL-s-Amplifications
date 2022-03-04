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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class MysteryStick6 extends AbstractStick {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick6(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "bowexp_xp");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "bowdamage_damage");
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

    @Override
    public Map<Enchantment, Integer> enchantments(){
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.ARROW_DAMAGE, 8);
        enchantments.put(Enchantment.ARROW_INFINITE, 1);
        enchantments.put(Enchantment.ARROW_FIRE, 6);
        enchantments.put(Enchantment.ARROW_KNOCKBACK, 5);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "Make them take an arrow to the knee";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "May the force and accuracy be with you";
    }

    @Override
    public void interact(PlayerInteractEvent e) {
        if(e.getPlayer().getLevel() >= 15) {
            mainStick.onInteract(e, Material.BOW, false);
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

        if (ThreadLocalRandom.current().nextInt(100) < 33) {
            player.setLevel(player.getLevel() - 2);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.
                    fromLegacyText(Utils.colorTranslator("&d2 xp levels has been consumed from you")));
        }

        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer expUsed = meta.getPersistentDataContainer();
        PersistentDataContainer damage = meta.getPersistentDataContainer();
        int damageamount = damage.getOrDefault(getStorageKey2(), PersistentDataType.INTEGER, 0);
        int get_Damage = (int) event.getDamage() + damageamount;
        int xpamount = expUsed.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, 0);
        damage.set(getStorageKey2(), PersistentDataType.INTEGER, get_Damage);

        List<String> lore2 = meta.getLore();
        meta.setLore(mainStick.loreUpdate(lore2, get_Damage, xpamount, weaponLore(), false));
        item.setItemMeta(meta);

        if(player.getLevel() <= 15) {
            mainStick.darkenVision(player, 15);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_6, 15, stickLore(), 1);
        }

    }

    @Override
    public void LevelChange(PlayerLevelChangeEvent event){
        mainStick.levelChange(event, FNAmpItems.FN_STICK_6, 15, 1);
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
        new MysteryStick6(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_6, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 4), FNAmpItems.FN_STICK_3, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 24), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 3), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 24)})
                .register(plugin);
    }
}
