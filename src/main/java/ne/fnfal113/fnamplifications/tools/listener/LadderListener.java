package ne.fnfal113.fnamplifications.tools.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.tools.AutoLadder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LadderListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }
        if(event.getClickedBlock() == null){
            return;
        }
        if(!event.getClickedBlock().getType().isSolid()){
            return;
        }
        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.LADDER){
            return;
        }

        Player player = event.getPlayer();

        SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());

        if(!(sfItem instanceof AutoLadder)){
            return;
        }

        ((AutoLadder) sfItem).getLadderTask().doPlaceTask(event, event.getClickedBlock(), event.getBlockFace());
    }

    @EventHandler
    public void onLadderPlace(BlockBreakEvent event){
        Block block = event.getBlock();

        for (int i = -8; i <= 8; i++) {
            if(block.getType() == Material.LADDER) {
                if (block.getRelative(0, i, 0).getType() == Material.LADDER) {
                    Block relativeBlock = block.getRelative(0, i, 0);
                    SlimefunItem sfItem = BlockStorage.check(relativeBlock);

                    if (sfItem instanceof AutoLadder) {
                        ((AutoLadder) sfItem).getLadderTask().doBreakTask(event, relativeBlock, sfItem);
                        return;
                    }
                }
            } else {
                for (BlockFace blockFace : BlockLadderFaces.BLOCK_LADDER_FACES.getBlockFaces()) {
                    if (block.getRelative(blockFace).getType() == Material.LADDER) {
                        Block relativeFaceBlock = block.getRelative(blockFace);

                        if (relativeFaceBlock.getRelative(0, i, 0).getType() == Material.LADDER) {
                            Block relativeBlock = relativeFaceBlock.getRelative(0, i, 0);
                            SlimefunItem sfItem = BlockStorage.check(relativeBlock);

                            if (sfItem instanceof AutoLadder) {
                                ((AutoLadder) sfItem).getLadderTask().doBreakTask(event, relativeBlock, sfItem);
                            }
                        }
                    }
                }
            }

        } // loop

    }

}

enum BlockLadderFaces{

    BLOCK_LADDER_FACES(BlockFace.WEST, BlockFace.EAST, BlockFace.SOUTH, BlockFace.NORTH);

    @Getter
    private final List<BlockFace> blockFaces = new ArrayList<>();

    BlockLadderFaces(BlockFace... faces) {
        this.blockFaces.addAll(Arrays.asList(faces));
    }
}
