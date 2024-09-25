package ne.fnfal113.fnamplifications.staffs.implementations;

import org.bukkit.Location;
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

    private final Map<UUID, List<Block>> blockMap = new HashMap<>();

    private final Map<UUID, Location> playerLocationMap = new HashMap<>();

    private final Player player;

    private boolean isDone = false;

    @Override
    public void run() {
        if(!player.isOnline()) {
            return;
        }
        
        // remove the previous blocks saved in the hashmap value
        if (blockMap.get(getPlayer().getUniqueId()) != null) { 
            List<Block> block = blockMap.get(getPlayer().getUniqueId());
            
            if(block != null) {
                for (Block blocks : block) {
                    if (blocks != null) {
                        blocks.setType(Material.AIR);
                    }
                }
            }

            blockMap.remove(getPlayer().getUniqueId());
        } 

        List<Block> blockList = new ArrayList<>();
        playerLocationMap.put(getPlayer().getUniqueId(), getPlayer().getLocation());
        
        for(int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                Block newBlock = getPlayer().getLocation().getBlock().getRelative(x, -1, z);

                if (newBlock.getType() == Material.AIR) {
                    blockList.add(newBlock);
                    
                    newBlock.setType(Material.BARRIER);

                    blockMap.put(getPlayer().getUniqueId(), blockList);
                }// check if relative block below the player is air
            }
        }
        
       
        if(isDone()) {
            List<Block> blocks = blockMap.get(getPlayer().getUniqueId());
            
            if(blocks != null) {
                for (Block block : blocks) {
                    block.setType(Material.AIR);
                }
            }

            this.cancel();
        }

    }

    public Map<UUID, List<Block>> getBlockMap() {
        return blockMap;
    }

    public Map<UUID, Location> getPlayerLocationMap() {
        return playerLocationMap;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isDone() {
        return isDone;
    }

    public AirStriderTask(Player player) {
        this.player = player;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

}