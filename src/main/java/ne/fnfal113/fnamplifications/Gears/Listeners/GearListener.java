package ne.fnfal113.fnamplifications.Gears.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.Gears.Abstracts.AbstractGears;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class GearListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void progressListener(EntityDamageByEntityEvent event){

        if(event.getDamager() instanceof Arrow){
            Arrow arrow = (Arrow) event.getDamager();

            if (!(arrow.getShooter() instanceof LivingEntity)) {
                return;
            }

            if(!(event.getEntity() instanceof Player)) {
                return;
            }

            Player p = (Player) event.getEntity();

            if(ThreadLocalRandom.current().nextInt(100) < 20) {
                for(ItemStack armour : p.getInventory().getArmorContents()) {
                    if (armour != null) {
                        SlimefunItem armor = SlimefunItem.getByItem(armour);
                        if (armor instanceof AbstractGears) {
                            ((AbstractGears) armor).onHit(event);
                        }
                    }
                }
            }
        } // check if damager is an arrow

        if(event.getDamager() instanceof LivingEntity) {
            if(!(event.getEntity() instanceof Player)) {
                return;
            }
            Player p = (Player) event.getEntity();

            if(ThreadLocalRandom.current().nextInt(100) < 40) {
                for(ItemStack armour : p.getInventory().getArmorContents()) {
                    if (armour != null) {
                        SlimefunItem armor = SlimefunItem.getByItem(armour);
                        if (armor instanceof AbstractGears) {
                            ((AbstractGears) armor).onHit(event);
                        }
                    }
                }
            }
        } // check if damager is a living entity

    }

}