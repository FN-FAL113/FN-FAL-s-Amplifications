package ne.fnfal113.fnamplifications.Staffs.Listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.Staffs.StaffOfLocomotion;
import ne.fnfal113.fnamplifications.Staffs.StaffOfInvisibility;
import ne.fnfal113.fnamplifications.Staffs.StaffOfTeleportation;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class StaffListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if (stick == null) {
            return;
        }

        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfTeleportation) {
                ((StaffOfTeleportation) stick).onRightClick(e);
            }
        }

        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfInvisibility) {
                if (!p.isInvisible()) {
                    ((StaffOfInvisibility) stick).onRightClick(e);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour invisibility is still active!"));
                }
            }
        }

        if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfLocomotion) {
                ((StaffOfLocomotion) stick).onLeftClick(e);
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
