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
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfWebs extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    private final NamespacedKey defaultUsageKey;

    public StaffOfWebs(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "webstaff");
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

        if(event.getHand() != EquipmentSlot.HAND){
            return;
        }

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast cobwebs there!");
            return;
        }

        if(item.getItemMeta() == null){
            return;
        }

        ItemMeta meta = item.getItemMeta();

        updateMeta(item, meta, key, player);

        float yaw = player.getLocation().getYaw();
        int x = 0, z = 0, i = 0, j = 0;
        boolean isX = true;

        if(yaw > -135 && yaw < -45){
            z = -1;
            j = 1;
            isX = false;
        } // positive x
        else if(yaw < 45 && yaw > -45){
            x = -1;
            i = 1;
        } // positive z
        else if(yaw < 135 && yaw > 45){
            z = -1;
            j = 1;
            isX = false;
        } // negative X
        else if((yaw < -135 && yaw > -180) || (yaw > 135 && yaw < 180)){
            x = -1;
            i = 1;
        } // negative z

        Location loc = block.getLocation().clone();

        if(isX){
            for(int y = 1; y <= 3; y++){
                for(int a = x; a <= i; a++){
                    if(loc.getBlock().getRelative(a, y, 0).getType() == Material.AIR){
                        loc.getBlock().getRelative(a, y, 0).setType(Material.COBWEB);
                    }
                }
            }
        } else {
            for(int y = 1; y <= 3; y++){
                for(int b = z; b <= j; b++){
                    if(loc.getBlock().getRelative(0, y, b).getType() == Material.AIR) {
                        loc.getBlock().getRelative(0, y, b).setType(Material.COBWEB);
                    }
                }
            }
        }

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public void updateMeta(ItemStack item, ItemMeta meta, NamespacedKey key, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(key, PersistentDataType.INTEGER, value.staffOfCobWebs());
        int decrement = uses_Left - 1;

        List<String> lore = new ArrayList<>();

        if(decrement > 0) {
            max_Uses.set(key, PersistentDataType.INTEGER, decrement);
            lore.add(0, "");
            lore.add(1, ChatColor.LIGHT_PURPLE + "Right click a target block");
            lore.add(2, ChatColor.LIGHT_PURPLE + "to spawn a wall of cobwebs");
            lore.add(3, "");
            lore.add(4, ChatColor.YELLOW + "Uses left: " + decrement);
            meta.setLore(lore);
            item.setItemMeta(meta);
        } else {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lCobweb staff has reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }


    }

    public static void setup(){
        new StaffOfWebs(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_COBWEB, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3), new ItemStack(Material.COBWEB, 6),  new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3),
                new ItemStack(Material.COBWEB, 6), new ItemStack(Material.BLAZE_ROD),  new ItemStack(Material.COBWEB, 6),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 5), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2)})
                .register(plugin);
    }
}
