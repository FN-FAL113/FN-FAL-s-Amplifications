package ne.fnfal113.fnamplifications.tools.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.tools.BlockRotator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class RotatorListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = event.getPlayer();
            if(event.getClickedBlock() == null || event.getHand() != EquipmentSlot.HAND) {
                return;
            }

            SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());

            if(sfItem instanceof BlockRotator){
                event.setCancelled(true);
                ((BlockRotator) sfItem).getBlockRotatorTask().onRightClick(event.getClickedBlock(), player);
            } // instance of Rotator

        } // right click action

    }

}
