package ne.fnfal113.fnamplifications.tools.implementation;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LadderTask {

    public LadderTask() {}

    private void cancelLadderPlace(Block relativeBlock, Block autoLadder){
        if(autoLadder.getType() == Material.AIR){
            relativeBlock.setType(Material.AIR);
        }
    }

    public void doPlaceTask(PlayerInteractEvent event, Block clickedBlock, BlockFace blockFace){
        AtomicReference<BlockData> ladderData = new AtomicReference<>();

        if(clickedBlock.getType() == Material.AIR) {
            return;
        }

        // We get the placed ladder block data for later use when
        // adding the other ladders to set their block data (rotation, etc)
        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () ->
                ladderData.set(clickedBlock.getRelative(blockFace).getBlockData()), 3L);

        AtomicInteger i = new AtomicInteger(0);

        Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task ->{
            Block relativeBottom = clickedBlock.getRelative(0, i.get(), 0);
            Block relativeUp = clickedBlock.getRelative(0, Math.abs(i.get()), 0);
            boolean isAbovePlaceable = relativeUp.getType() != Material.AIR && relativeUp.getRelative(blockFace).getType() == Material.AIR;
            boolean isBottomPlaceable = relativeBottom.getType() != Material.AIR && relativeBottom.getRelative(blockFace).getType() == Material.AIR;

            if(i.get() != 0) {
                if (isAbovePlaceable) {
                    relativeUp.getRelative(blockFace).setType(Material.LADDER);
                }
                if (isBottomPlaceable) {
                    relativeBottom.getRelative(blockFace).setType(Material.LADDER);
                }

                // a delayed task to set the block data (rotation, etc) using the placed auto ladder
                // of the ladder that will be added or below the auto ladder
                Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                    if (isAbovePlaceable) {
                        relativeUp.getRelative(event.getBlockFace()).setBlockData(ladderData.get());
                        cancelLadderPlace(relativeUp.getRelative(blockFace), clickedBlock.getRelative(blockFace));
                    }
                    if (isBottomPlaceable) {
                        relativeBottom.getRelative(blockFace).setBlockData(ladderData.get());
                        cancelLadderPlace(relativeBottom.getRelative(event.getBlockFace()), clickedBlock.getRelative(blockFace));
                    }

                    event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_LADDER_PLACE, 1.0F, 1.0F);
                }, 3L);
            }

            if(i.get() == -8){
                task.cancel();
                return;
            }

            i.getAndDecrement();
        }, 0L, 10L);
    }

    public void doBreakTask(BlockBreakEvent event, Block sfBlock, SlimefunItem sfItem){
        Player player = event.getPlayer();
        int emptySlot = player.getInventory().firstEmpty();
        boolean skipRelativeAboveLadder = false;
        boolean skipRelativeBottomLadder = false;

        // remove any ladder between (up/bottom) the auto ladder block
        for (int i = 0; i >= -8; i--) {
            if(i != 0) { // skip the sfBlock
                if (!skipRelativeBottomLadder && sfBlock.getRelative(0, i, 0).getType() == Material.LADDER) {
                    sfBlock.getRelative(0, i, 0).setType(Material.AIR);
                } else { // skip any other relative blocks when this is fired
                    skipRelativeBottomLadder = true;
                }
                if (!skipRelativeAboveLadder && sfBlock.getRelative(0, Math.abs(i), 0).getType() == Material.LADDER) {
                    sfBlock.getRelative(0, Math.abs(i), 0).setType(Material.AIR);
                } else { // skip any other relative blocks when this is fired
                    skipRelativeAboveLadder = true;
                }
            }
        }

        // finally remove auto ladder block
        BlockStorage.clearBlockInfo(sfBlock);
        sfBlock.setType(Material.AIR);

        if(emptySlot == -1){
            player.getWorld().dropItemNaturally(player.getLocation(), sfItem.getItem().clone());
            return;
        }
        player.getInventory().addItem(sfItem.getItem().clone());

    }

}
