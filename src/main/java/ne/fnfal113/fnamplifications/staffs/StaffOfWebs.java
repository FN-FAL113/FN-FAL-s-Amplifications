package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffOfWebs extends AbstractStaff {

    public StaffOfWebs(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10, Keys.createKey("webstaff"));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getPlayer().getTargetBlockExact(50);

        if(event.getHand() != EquipmentSlot.HAND){
            return;
        }

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!hasPermissionToCast(item.getItemMeta().getDisplayName(), player, block.getLocation())) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        getStaffTask().updateMeta(item, meta, player);

        int x = 0, z = 0, i = 0, j = 0;
        boolean isX = true;
        if(player.getFacing() == BlockFace.EAST){
            z = -1;
            j = 1;
            isX = false;
        } // positive x
        else if(player.getFacing() == BlockFace.SOUTH){
            x = -1;
            i = 1;
        } // positive z
        else if(player.getFacing() == BlockFace.WEST){
            z = -1;
            j = 1;
            isX = false;
        } // negative X
        else if(player.getFacing() == BlockFace.NORTH){
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

    }

}