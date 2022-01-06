package ne.fnfal113.fnamplifications.Staffs.Listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.Staffs.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;
import java.util.Random;

public class StaffListener implements Listener {

    @EventHandler
    public void onApply(AreaEffectCloudApplyEvent event){

        if (Objects.equals(event.getEntity().getCustomName(), "FN_HELL_FIRE")){
            for(LivingEntity entity : event.getAffectedEntities()){
                entity.setFireTicks(20);
            }
        }

        if (Objects.equals(event.getEntity().getCustomName(), "FN_DEEP_FREEZE")){
            for(LivingEntity entity : event.getAffectedEntities()){
                entity.setFreezeTicks(205);
            }
        }

        if (Objects.equals(event.getEntity().getCustomName(), "FN_CONFUSION")){
            for(LivingEntity entity : event.getAffectedEntities()){
                Random random = new Random();
                World world = entity.getWorld();
                int pitch = random.nextInt(180 + 1 + 180) - 180;
                int yaw = random.nextInt(180 + 1 + 180) - 180;
                Location loc = new Location(world, entity.getLocation().getX(),
                        entity.getLocation().getY(),
                        entity.getLocation().getZ(), pitch, yaw);
                entity.teleport(loc);
            }
        }

    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());
        boolean actionRight = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK);
        boolean actionLeft = (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK);

        if (stick == null) {
            return;
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfTeleportation) {
                ((StaffOfTeleportation) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfInvisibility) {
                if (!p.isInvisible()) {
                    ((StaffOfInvisibility) stick).onRightClick(e);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour invisibility is still active!"));
                }
            }
        }

        if (actionLeft && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfLocomotion) {
                ((StaffOfLocomotion) stick).onLeftClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfHellFire) {
                ((StaffOfHellFire) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfDeepFreeze) {
                ((StaffOfDeepFreeze) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfConfusion) {
                ((StaffOfConfusion) stick).onRightClick(e);
            }
        }

    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if (stick == null) {
            return;
        }

        if (stick instanceof StaffOfLocomotion && e.getHand() == EquipmentSlot.HAND) {
            if (e.getRightClicked() instanceof LivingEntity && !(e.getRightClicked() instanceof Player)) {
                ((StaffOfLocomotion) stick).onRightClick(e);
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou right clicked an invalid entity"));
            }
        }

    }

}
