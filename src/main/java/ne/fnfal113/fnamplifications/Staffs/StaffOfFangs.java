package ne.fnfal113.fnamplifications.Staffs;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfFangs extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    private final NamespacedKey defaultUsageKey;

    public StaffOfFangs(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "fangsstaff");
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        NamespacedKey key = getStorageKey();
        Block block = event.getPlayer().getTargetBlockExact(50);

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast fangs there!");
            return;
        }

        if(item.getItemMeta() == null){
            return;
        }

        for (int i = 0; i < 10; i++){
            Location loc = block.getLocation().clone().add(0.5, 1, 0.5).setDirection(player.getLocation().clone().getDirection());
            player.getWorld().spawnEntity(loc.add(loc.getDirection().clone().multiply(i).setY(0)), EntityType.EVOKER_FANGS);
        }

        ItemMeta meta = item.getItemMeta();

        updateMeta(item, meta, key, player);

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public void updateMeta(ItemStack item, ItemMeta meta, NamespacedKey key, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(key, PersistentDataType.INTEGER, value.staffOfFangs());
        int decrement = uses_Left - 1;

        List<String> lore = new ArrayList<>();

        if(decrement > 0) {
            max_Uses.set(key, PersistentDataType.INTEGER, decrement);
            lore.add(0, "");
            lore.add(1, ChatColor.LIGHT_PURPLE + "Right click a target block to");
            lore.add(2, ChatColor.LIGHT_PURPLE + "spawn evoker fangs that causes");
            lore.add(3, ChatColor.LIGHT_PURPLE + "damage to entities");
            lore.add(4, "");
            lore.add(5, ChatColor.YELLOW + "Uses left: " + decrement);
            meta.setLore(lore);
            item.setItemMeta(meta);
        } else {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lFangs staff has reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }


    }

    public static void setup(){
        new StaffOfFangs(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_FANGS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.BLANK_RUNE, new ItemStack(Material.BLAZE_POWDER, 14), SlimefunItems.BLANK_RUNE,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2), new ItemStack(Material.BLAZE_ROD), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 7), new ItemStack(Material.ROTTEN_FLESH, 24), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 7)})
                .register(plugin);
    }

}