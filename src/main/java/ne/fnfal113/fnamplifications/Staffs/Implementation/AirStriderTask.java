package ne.fnfal113.fnamplifications.Staffs.Implementation;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AirStriderTask extends BukkitRunnable {

    @Getter
    private final Map<UUID, Block> blockMap = new HashMap<>();

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
            if(diff < 10) {
                if (blockMap.containsKey(player.getUniqueId())) {
                    Block block = blockMap.get(player.getUniqueId());
                    if(block != null) {
                        block.setType(Material.AIR);
                    }
                    blockMap.remove(player.getUniqueId());
                } // remove the last block saved from the hashmap
                Block newBlock = player.getLocation().getBlock().getRelative(0, -1, 0);
                if (newBlock.getType() == Material.AIR) {
                    newBlock.setType(Material.BARRIER);
                    blockMap.put(player.getUniqueId(), newBlock);
                }// check if relative block below the player is air
            } // cooldown check
            else {
                Block block = blockMap.get(player.getUniqueId());
                if(block != null) {
                    block.setType(Material.AIR);
                }
                cooldown.remove(player.getUniqueId());
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Air Strider has expired!");
            }
        } // cooldown map key check
    }

}