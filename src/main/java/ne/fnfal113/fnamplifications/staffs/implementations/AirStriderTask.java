package ne.fnfal113.fnamplifications.staffs.implementations;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AirStriderTask extends BukkitRunnable {

    @Getter
    private final Map<UUID, List<Block>> blockMap = new HashMap<>();

    @Getter
    private final Map<UUID, Long> cooldown = new HashMap<>();

    @Getter
    private final Player player;

    public AirStriderTask(Player player){
        this.player = player;
        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @Override
    public void run() {
        if(cooldown.containsKey(player.getUniqueId())){
            long diff = (System.currentTimeMillis() - cooldown.get(player.getUniqueId())) / 1000;

            if(diff < 10) { // check if current players time is less than 10 seconds
                if (blockMap.containsKey(player.getUniqueId())) {
                    List<Block> block = blockMap.get(player.getUniqueId());
                    if(block != null) {
                        for (Block blocks : block) {
                            if (blocks != null) {
                                blocks.setType(Material.AIR);
                            }
                        }
                    }

                    blockMap.remove(player.getUniqueId());
                } // remove the last block saved from the hashmap
                List<Block> blockList = new ArrayList<>();
                for(int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        Block newBlock = player.getLocation().getBlock().getRelative(x, -1, z);

                        if (newBlock.getType() == Material.AIR) {
                            blockList.add(newBlock);
                            newBlock.setType(Material.BARRIER);
                            blockMap.put(player.getUniqueId(), blockList);
                        }// check if relative block below along its relative axis the player is air
                    }
                }
            } else { // when cooldown is due remove remaining blocks
                List<Block> block = blockMap.get(player.getUniqueId());
                if(block != null) {
                    for (Block blocks : block) {
                        if (blocks != null) {
                            blocks.setType(Material.AIR);
                        }
                    }
                }
                cooldown.remove(player.getUniqueId());
            }
        } // cooldown map check key exist
    }

}