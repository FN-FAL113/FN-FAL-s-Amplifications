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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class MysteryStick3 extends AbstractStick {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick3(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "bowexp");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "bowdamage");
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
        enchantments.put(Enchantment.ARROW_DAMAGE, 3);
        enchantments.put(Enchantment.ARROW_INFINITE, 1);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "I knew it was something about shooting arrows";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "I feel coordinated when holding this stick";
    }

    @Override
    public void interact(PlayerInteractEvent e) {
        if(e.getPlayer().getLevel() >= 5) {
            mainStick.onInteract(e, Material.BOW, false);
        } else {
            blindPlayer(e.getPlayer(), 5);
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

        if (ThreadLocalRandom.current().nextInt(100) < 24) {
            player.setLevel(player.getLevel() - 1);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.
                    fromLegacyText(Utils.colorTranslator("&d1 xp levels has been consumed from you")));
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

        if(player.getLevel() <= 5) {
            mainStick.darkenVision(player, 5);
            mainStick.transformWeapon(player, item, FNAmpItems.FN_STICK_3, 5, stickLore(), 0);
        }

    }

    @Override
    public void LevelChange(PlayerLevelChangeEvent event){
       mainStick.levelChange(event, FNAmpItems.FN_STICK_3, 5, 0);
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
        new MysteryStick3(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_3, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.LEATHER), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 8), new ItemStack(Material.STICK), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 8),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 6), new ItemStack(Material.LEAD), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 6)})
                .register(plugin);
    }
}