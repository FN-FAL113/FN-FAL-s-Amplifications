package ne.fnfal113.fnamplifications.staffs.implementations;

import lombok.Getter;
import lombok.Setter;
import ne.fnfal113.fnamplifications.FNAmplifications;

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

    @Getter
    private final Map<UUID, List<Block>> blockMap = new HashMap<>();

    @Getter
    private final Map<UUID, Location> playerLocationMap = new HashMap<>();

    @Getter
    private final Player player;

    @Getter
    @Setter
    private boolean isDone = false;

    public AirStriderTask(Player player){
        this.player = player;
    }

    @Override
    public void run() {
        if(!player.isOnline()){
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
                }// check if relative block below along its relative axis the player is air
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

}