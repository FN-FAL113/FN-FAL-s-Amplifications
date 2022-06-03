package ne.fnfal113.fnamplifications.tools.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.tools.OrientPearl;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class OrientPearlListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.AIR && player.getInventory().getItemInOffHand().getType() == Material.AIR){
            return;
        }

        SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());

        if(sfItem instanceof OrientPearl){
            player.getPersistentDataContainer().set(Keys.createKey("orientpearl"), PersistentDataType.INTEGER, 1);
        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(event.isCancelled()){
            return;
        }
        if(event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL){
            return;
        }
        if(!event.getPlayer().isInsideVehicle()){
            return;
        }

        Player player = event.getPlayer();
        Optional<Entity> entity = Optional.ofNullable(player.getVehicle());
        if(!entity.isPresent()){
            return;
        }
        if(player.getPersistentDataContainer().getOrDefault(Keys.createKey("orientpearl"), PersistentDataType.INTEGER, 0) == 0){
            return;
        }

        player.getPersistentDataContainer().set(Keys.createKey("orientpearl"), PersistentDataType.INTEGER, 0);
        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () ->{
            entity.get().teleport(player.getLocation());
            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> entity.get().addPassenger(player), 5L);
        }, 4L);

    }

}
