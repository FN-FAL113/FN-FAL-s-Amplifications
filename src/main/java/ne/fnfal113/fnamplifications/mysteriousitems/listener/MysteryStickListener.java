package ne.fnfal113.fnamplifications.mysteriousitems.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.mysteriousitems.abstracts.AbstractStick;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MysteryStickListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand().getType() == Material.AIR){
            return;
        }
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if(stick instanceof AbstractStick && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(checkStick(stick)) {
                AbstractStick abstractStick = (AbstractStick) stick;
                abstractStick.getStickTask().onInteract(e, abstractStick.getStickMaterial());
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if(e.getCause() == EntityDamageEvent.DamageCause.THORNS){
            return;
        }

        if(!(e.getEntity() instanceof LivingEntity)) {
            return;
        }

        if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();

            if (!(arrow.getShooter() instanceof Player)) {
                return;
            }
            Player player = ((Player) arrow.getShooter());

            if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
                return;
            }
            SlimefunItem stickBow = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());

            if(stickBow instanceof AbstractStick) {
                ((AbstractStick) stickBow).onSwing(e);
            }
        }

        if(e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return;
            }
            SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

            if (stick instanceof AbstractStick) {
                ((AbstractStick) stick).onSwing(e);
            }
        }

    }

    public boolean checkStick(SlimefunItem stick){
        return !(stick.getItem().getType() == Material.DIAMOND_SWORD) && !(stick.getItem().getType() == Material.DIAMOND_AXE) &&
                !(stick.getItem().getType() == Material.BOW);
    }

}