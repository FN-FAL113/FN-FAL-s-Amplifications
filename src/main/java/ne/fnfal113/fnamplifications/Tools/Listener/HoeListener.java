package ne.fnfal113.fnamplifications.Tools.Listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.Tools.Abstracts.AbstractHoe;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class HoeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        boolean rightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK;
        boolean leftClick = event.getAction() == Action.LEFT_CLICK_BLOCK;

        if (event.getHand() == EquipmentSlot.HAND) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (itemStack.getType() == Material.DIAMOND_HOE || itemStack.getType() == Material.NETHERITE_HOE) {
                SlimefunItem hoe = SlimefunItem.getByItem(itemStack);

                if (hoe instanceof AbstractHoe) {
                    Block clickedBlock = event.getClickedBlock();
                    boolean isAgeable = clickedBlock != null && clickedBlock.getBlockData().clone() instanceof Ageable;

                    if (rightClick && clickedBlock != null) {
                        ((AbstractHoe) hoe).onRightClick(player, clickedBlock);
                    }

                    if (leftClick && isAgeable) {
                        ((AbstractHoe) hoe).onLeftClick(player, clickedBlock, itemStack);
                    }

                } // instance of fn item check
            } // main hand item type check
        } // click type check
    }

}