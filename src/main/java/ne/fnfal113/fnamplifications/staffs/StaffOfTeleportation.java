package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.utils.Keys;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class StaffOfTeleportation extends AbstractStaff {

    public StaffOfTeleportation(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10, Keys.createKey("tpstaff"));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        Vector directional = player.getLocation().getDirection();
        
        ItemStack item = player.getInventory().getItemInMainHand();
        
        Block block = event.getPlayer().getTargetBlockExact(100);

        if(block == null || item.getType() == Material.AIR) return;

        if(!hasPermissionToCast(item.getItemMeta().getDisplayName(), player, block.getLocation())) return;

        ItemMeta meta = item.getItemMeta();

        getStaffTask().updateMeta(item, meta, player);
        player.teleport(block.getLocation().add(0.5, 1, 0.5).setDirection(directional));
    }

}