package ne.fnfal113.fnamplifications.tools.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.tools.ThrowableTorch;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ThrowableItemListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getHand() != EquipmentSlot.HAND){
            return;
        }

        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        if(event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR){
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getType() == Material.AIR){
            return;
        }

        Optional<SlimefunItem> throwableItem = Optional.ofNullable(SlimefunItem.getByItem(itemStack));

        throwableItem.ifPresent(slimefunItem -> {
            if(slimefunItem instanceof ThrowableTorch){
                ((ThrowableTorch) slimefunItem).onClick(player, itemStack);
            }
        });
    }

}