package ne.fnfal113.fnamplifications.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;

public class ShockwaveTest implements CommandExecutor {

    private final List<Double> cosine = new ArrayList<>();
    private final List<Double> sine = new ArrayList<>();

    private final Map<UUID, Long> playerCooldownMap = new HashMap<>();

    public ShockwaveTest(){
        for (int x = 0; x <= 360; x++) {
            cosine.add(x, Math.cos(x));
            sine.add(x, Math.sin(x));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
    
        if(!player.hasPermission("fnshockwave.test")){
            player.sendMessage("You have no permission to execute this command!");
            return false;
        }

        if(args.length == 0){
            shockwave(player);
        } else if (args[0].equals("repeat")){
            Long cd = playerCooldownMap.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
            int duration = !args[1].isBlank() ? Integer.parseInt(args[1]) : 60;
            
            player.sendMessage("repeating task for " + duration + " seconds");
            
            Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task -> {
                shockwave(player);
                
                if(Utils.cooldownHelper(cd) >= duration || !player.isOnline()){
                    task.cancel();
                }
            }, 1, 20);
        }

        return true;
    }

    public void shockwave(Player player){
        if(Utils.cooldownHelper(playerCooldownMap.getOrDefault(player.getUniqueId(), 15L)) < 5) {
            Long cd = Utils.cooldownHelper(playerCooldownMap.get(player.getUniqueId()));

            player.sendMessage(Utils.colorTranslator("&dShockwave gem in cooldown for " + (5 - cd) + " seconds!"));
            return;
        } else {
            playerCooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        }

        Set<Block> blocks = new HashSet<>();
        double height = 0.1;
       
        for (int i = 0; i < 8; i++) {
            for (int c = 0; c <= 360; c++) {
                double x = i * cosine.get(c);
                double z = i * sine.get(c);
                Block block = player.getLocation().getBlock().getRelative((int) x, -1, (int) z);

                // check block below if applicable to be queued
                if(!blocks.contains(block) && block.getType() != Material.AIR && block.getRelative(BlockFace.UP).getType() == Material.AIR) {
                    blocks.add(block);
                    spawnJumpingBlock(block, height);
                    height += 0.003475;
                }
            }
        }
    }

    public void spawnJumpingBlock(Block blockOnGround, double height){
        Location loc = blockOnGround.getRelative(BlockFace.UP).getLocation();
        FallingBlock block = blockOnGround.getWorld().spawnFallingBlock(loc, blockOnGround.getBlockData());
        
        block.setDropItem(false);
        block.setVelocity(new Vector(0, height, 0));
        block.setMetadata("shockwave_gem", new FixedMetadataValue(FNAmplifications.getInstance(), "ghost_block"));
    }
    
}
