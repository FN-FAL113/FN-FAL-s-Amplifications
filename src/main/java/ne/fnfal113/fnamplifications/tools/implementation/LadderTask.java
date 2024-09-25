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
import java.util.concurrent.atomic.AtomicBoolean;

public class LadderTask {

    public LadderTask() {}

    private void cancelLadderPlace(Block relativeBlock, Block autoLadder) {
        if(autoLadder.getType() == Material.AIR) {
            relativeBlock.setType(Material.AIR);
        }
    }

    public void doPlaceTask(PlayerInteractEvent event, Block clickedBlock, BlockFace blockFace) {
        AtomicReference<BlockData> ladderData = new AtomicReference<>();

        if(clickedBlock.getType() == Material.AIR) return;

        // We get the placed ladder block data for later use when
        // adding the other ladders to set their block data (rotation, etc)
        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () ->
            ladderData.set(clickedBlock.getRelative(blockFace).getBlockData())
        , 3L);

        AtomicInteger i = new AtomicInteger(0);
        AtomicBoolean shouldPlaceAbove = new AtomicBoolean(true);
        AtomicBoolean shouldPlaceBelow = new AtomicBoolean(true);

        Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task -> {
            Block relativeBottom = clickedBlock.getRelative(0, i.get(), 0);
            Block relativeUp = clickedBlock.getRelative(0, Math.abs(i.get()), 0);
            
            boolean isAbovePlaceable = relativeUp.getType() != Material.AIR && relativeUp.getRelative(blockFace).getType() == Material.AIR;
            boolean isBottomPlaceable = relativeBottom.getType() != Material.AIR && relativeBottom.getRelative(blockFace).getType() == Material.AIR;
            
            if(i.get() != 0) {
                if(isAbovePlaceable && shouldPlaceAbove.get()) {
                    relativeUp.getRelative(blockFace).setType(Material.LADDER);
                } else if(shouldPlaceAbove.get()) {
                    shouldPlaceAbove.set(false);
                }

                if(isBottomPlaceable && shouldPlaceBelow.get()) {
                    relativeBottom.getRelative(blockFace).setType(Material.LADDER);
                } else if(shouldPlaceBelow.get()) {
                    shouldPlaceBelow.set(false);
                }

                // a delayed task to set the block data (rotation, etc) of other ladders using the placed auto ladder
                Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), task2 -> {
                    if(isAbovePlaceable && shouldPlaceAbove.get()) {
                        relativeUp.getRelative(event.getBlockFace()).setBlockData(ladderData.get());
                       
                        cancelLadderPlace(relativeUp.getRelative(blockFace), clickedBlock.getRelative(blockFace));

                        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_LADDER_PLACE, 1.0F, 1.0F);
                    }

                    if(isBottomPlaceable && shouldPlaceBelow.get()) {
                        relativeBottom.getRelative(blockFace).setBlockData(ladderData.get());
                        
                        cancelLadderPlace(relativeBottom.getRelative(event.getBlockFace()), clickedBlock.getRelative(blockFace));

                        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_LADDER_PLACE, 1.0F, 1.0F);
                    }                    
                }, 3L);
            }

            if(i.get() == -8) {
                task.cancel();

                return;
            }

            i.getAndDecrement();
        }, 0L, 10L);
    }

    public void doBreakTask(BlockBreakEvent event, Block sfBlock, SlimefunItem sfItem) {
        Player player = event.getPlayer();
        
        boolean skipRelativeAboveLadder = false;
        boolean skipRelativeBottomLadder = false;

        // remove any ladder relative (above/below) the auto ladder block
        for (int i = 0; i >= -8; i--) {
            // skip sfBlock
            if(i != 0) {
                if (!skipRelativeBottomLadder && sfBlock.getRelative(0, i, 0).getType() == Material.LADDER) {
                    sfBlock.getRelative(0, i, 0).setType(Material.AIR);
                } else {
                     // skip any other relative blocks when this is fired
                    skipRelativeBottomLadder = true;
                }

                if (!skipRelativeAboveLadder && sfBlock.getRelative(0, Math.abs(i), 0).getType() == Material.LADDER) {
                    sfBlock.getRelative(0, Math.abs(i), 0).setType(Material.AIR);
                } else { 
                    // skip any other relative blocks when this is fired
                    skipRelativeAboveLadder = true;
                }
            }
        }

        // finally remove auto ladder block
        BlockStorage.clearBlockInfo(sfBlock);
        sfBlock.setType(Material.AIR);

        if(player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), sfItem.getItem().clone());
            
            return;
        }

        player.getInventory().addItem(sfItem.getItem().clone());
    }

}
