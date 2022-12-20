package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public interface OnBlockBreakHandler extends GemHandler{

    /**
     *
     * @param event the block break event and listens if a block is broken
     * @param player the player who broke the block
     * @param itemStack the item where the gem is bound at
     */
    void onBlockBreak(BlockBreakEvent event, Player player, ItemStack itemStack);

}
