package ne.fnfal113.fnamplifications.gears.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.gears.abstracts.AbstractGears;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class GearListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){

        if(event.getDamager() instanceof Arrow){
            Arrow arrow = (Arrow) event.getDamager();

            if (!(arrow.getShooter() instanceof LivingEntity)) {
                return;
            }

            if(!(event.getEntity() instanceof Player)) {
                return;
            }

            Player p = (Player) event.getEntity();

            if(ThreadLocalRandom.current().nextInt(100) < 12) {
                for(ItemStack armour : p.getInventory().getArmorContents()) {
                    if (armour != null) {
                        SlimefunItem armor = SlimefunItem.getByItem(armour);
                        if (armor instanceof AbstractGears) {
                            if(!event.isCancelled()) {
                                ((AbstractGears) armor).onHit(event);
                            }
                        } // instance of Gear
                    } // armor is not null
                } // loop armor contents
            }
        } // check if damager is an arrow

        if(event.getDamager() instanceof LivingEntity) {
            if(!(event.getEntity() instanceof Player)) {
                return;
            }
            Player p = (Player) event.getEntity();

            if(ThreadLocalRandom.current().nextInt(100) < 12) {
                for(ItemStack armour : p.getInventory().getArmorContents()) {
                    if (armour != null) {
                        SlimefunItem armor = SlimefunItem.getByItem(armour);
                        if (armor instanceof AbstractGears) {
                            if(!event.isCancelled()) {
                                ((AbstractGears) armor).onHit(event);
                            }
                        } // instance of Gear
                    } // armor is not null
                } // loop armor contents
            }
        } // check if damager is a living entity

    }

}