package ne.fnfal113.fnamplifications.Tools.Listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Tools.FnHoe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.Set;

public class HoeListener implements Listener {

    private final Set<Material> dirtBlocks = EnumSet.of(
            Material.DIRT,
            Material.DIRT_PATH,
            Material.GRASS_BLOCK
    );

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        boolean rightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK;
        boolean leftClick = event.getAction() == Action.LEFT_CLICK_BLOCK;

        if(event.getHand() == EquipmentSlot.HAND){
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if(itemStack.getType() == Material.DIAMOND_HOE){
                SlimefunItem hoe = SlimefunItem.getByItem(itemStack);

                if(hoe instanceof FnHoe){
                    Block clickedBlock = event.getClickedBlock();
                    boolean checkBlock = clickedBlock != null &&
                            ((clickedBlock.getType() == Material.GRASS_BLOCK) ||
                                    (clickedBlock.getType() == Material.DIRT) ||
                                    (clickedBlock.getType() == Material.DIRT_PATH));
                    boolean checkBlockLeftClick = clickedBlock != null;
                    float yaw = player.getLocation().getYaw();
                    int x = 0, z = 0, i = 0, j = 0;

                    if(yaw > -135 && yaw < -45){
                        x = 0;
                        i = 5;
                        z = -2;
                        j = 3;
                    } // positive x
                    else if(yaw < 45 && yaw > -45){
                        x = -2;
                        i = 3;
                        j = 5;
                    } // positive z
                    else if(yaw < 135 && yaw > 45){
                        x = -4;
                        i = 1;
                        z = -2;
                        j = 3;
                    } // negative X
                    else if((yaw < -135 && yaw > -180) || (yaw > 135 && yaw < 180)){
                        x = -2;
                        i = 3;
                        z = -4;
                        j = 1;
                    } // negative z

                    if(checkBlock && rightClick){
                        for (int a = x; a < i; a++) {
                            for (int b = z; b < j; b++) {
                                int finalA = a;
                                int finalB = b;
                                Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                                    if (dirtBlocks.contains(clickedBlock.getRelative(finalA, 0, finalB).getType())){
                                        if(Slimefun.getProtectionManager().hasPermission
                                                (Bukkit.getOfflinePlayer(player.getUniqueId()), clickedBlock.getRelative(finalA, 0, finalB), Interaction.INTERACT_BLOCK)){

                                            clickedBlock.getRelative(finalA, 0, finalB).setType(Material.FARMLAND);
                                        } // perm check
                                    } // check the necessary block
                                }, 2L);

                            } // z axis loop
                        } // x axis loop

                    }

                    if(leftClick && checkBlockLeftClick && clickedBlock.getBlockData() instanceof Ageable){
                        for (int a = x; a < i; a++) {
                            for (int b = z; b < j; b++) {
                                int finalA = a;
                                int finalB = b;
                                Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                                    if (clickedBlock.getRelative(finalA, 0, finalB).getBlockData() instanceof Ageable){
                                        if(Slimefun.getProtectionManager().hasPermission
                                                (Bukkit.getOfflinePlayer(player.getUniqueId()), clickedBlock.getRelative(finalA, 0, finalB), Interaction.BREAK_BLOCK)){

                                            clickedBlock.getRelative(finalA, 0, finalB).getLocation().
                                                    setDirection(player.getLocation().getDirection()).getBlock().breakNaturally();
                                        } // perm check
                                    } // check if block is a crop
                                }, 2L);

                            } // z axis loop
                        } // x axis loop
                    }

                } // instance of fn item check
            } // main hand item type check
        } // click type check

    }


}
