package ne.fnfal113.fnamplifications.Staffs;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import ne.fnfal113.fnamplifications.Multiblock.FnMysteryStickAltar;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfTeleportation extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;

    protected StaffOfTeleportation(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "tpstaff");
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Vector directional = player.getLocation().getDirection();
        ItemStack item = player.getInventory().getItemInMainHand();
        NamespacedKey key = getStorageKey();
        Block block = event.getPlayer().getTargetBlockExact(100);

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to teleport there!");
            return;
        }

        if(item.getItemMeta() == null){
            return;
        }

        ItemMeta meta = item.getItemMeta();

        updateMeta(item, meta, key, player);
        player.teleport(block.getLocation().add(0.5, 1, 0.5).setDirection(directional));

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

    }

    public void updateMeta(ItemStack item, ItemMeta meta, NamespacedKey key, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(key, PersistentDataType.INTEGER, 10);
        int decrement = uses_Left - 1;

        List<String> lore = new ArrayList<>();

        if(decrement > 0) {
            max_Uses.set(key, PersistentDataType.INTEGER, decrement);
            lore.add(0, "");
            lore.add(1, ChatColor.LIGHT_PURPLE + "Teleport to a target block by");
            lore.add(2, ChatColor.LIGHT_PURPLE + "right clicking it");
            lore.add(3, "");
            lore.add(4, ChatColor.YELLOW + "Uses left: " + decrement);
            meta.setLore(lore);
            item.setItemMeta(meta);
        } else {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lTeleportation staff has reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }


    }

    public static ItemStack ALT_RECIPE = SlimefunItems.COMMON_TALISMAN;

    static {
        SlimefunItem alt_recipe = SlimefunItem.getByItem(SlimefunItems.SCROLL_OF_DIMENSIONAL_TELEPOSITION);
        if(alt_recipe != null && !alt_recipe.isDisabled()){
            ALT_RECIPE = alt_recipe.getItem().clone();
        }
    }


    public static void setup(){
        new StaffOfTeleportation(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_TP, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 5), ALT_RECIPE, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 5),
                SlimefunItems.ENDER_RUNE, new ItemStack(Material.BLAZE_ROD), SlimefunItems.ENDER_RUNE,
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2), SlimefunItems.MAGIC_EYE_OF_ENDER, new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2)})
                .register(plugin);
    }
}
