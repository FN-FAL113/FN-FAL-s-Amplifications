package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public interface OnBlockBreakHandler {

    /**
     *
     * @param event the block break event and listens if a block is broken
     * @param player the player who broke the block
     */
    void onBlockBreak(BlockBreakEvent event, Player player);


}
