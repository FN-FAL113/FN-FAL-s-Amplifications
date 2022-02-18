package ne.fnfal113.fnamplifications.Gears.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.Gears.FnBoots;
import ne.fnfal113.fnamplifications.Gears.FnChestPlate;
import ne.fnfal113.fnamplifications.Gears.FnHelmet;
import ne.fnfal113.fnamplifications.Gears.FnLeggings;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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

            if(ThreadLocalRandom.current().nextInt(100) < 10) {
                SlimefunItem armor = SlimefunItem.getByItem(p.getInventory().getChestplate());
                if(armor != null) {
                    if (armor instanceof FnChestPlate) {
                        ((FnChestPlate) armor).onHit(event);
                    }
                }
            }

            if(ThreadLocalRandom.current().nextInt(100) < 12) {
                SlimefunItem leggings = SlimefunItem.getByItem(p.getInventory().getLeggings());
                if (leggings != null) {
                    if (leggings instanceof FnLeggings) {
                        ((FnLeggings) leggings).onHit(event);
                    }
                }
            }

            if(ThreadLocalRandom.current().nextInt(100) < 11) {
                SlimefunItem helmet = SlimefunItem.getByItem(p.getInventory().getHelmet());
                if (helmet != null) {
                    if (helmet instanceof FnHelmet) {
                        ((FnHelmet) helmet).onHit(event);
                    }
                }
            }

            if(ThreadLocalRandom.current().nextInt(100) < 12) {
                SlimefunItem boots = SlimefunItem.getByItem(p.getInventory().getBoots());
                if (boots != null) {
                    if (boots instanceof FnBoots) {
                        ((FnBoots) boots).onHit(event);
                    }
                }
            }

        }

        if(event.getDamager() instanceof LivingEntity) {

            if(!(event.getEntity() instanceof Player)) {
                return;
            }

            Player p = (Player) event.getEntity();

            if(ThreadLocalRandom.current().nextInt(100) < 30) {
                SlimefunItem armor = SlimefunItem.getByItem(p.getInventory().getChestplate());
                if(armor != null) {
                    if (armor instanceof FnChestPlate) {
                            ((FnChestPlate) armor).onHit(event);
                    }
                }
            }

            if(ThreadLocalRandom.current().nextInt(100) < 31) {
                SlimefunItem leggings = SlimefunItem.getByItem(p.getInventory().getLeggings());
                if (leggings != null) {
                    if (leggings instanceof FnLeggings) {
                        ((FnLeggings) leggings).onHit(event);
                    }
                }
            }

            if(ThreadLocalRandom.current().nextInt(100) < 34) {
                SlimefunItem helmet = SlimefunItem.getByItem(p.getInventory().getHelmet());
                if (helmet != null) {
                    if (helmet instanceof FnHelmet) {
                        ((FnHelmet) helmet).onHit(event);
                    }
                }
            }

            if(ThreadLocalRandom.current().nextInt(100) < 35) {
                SlimefunItem boots = SlimefunItem.getByItem(p.getInventory().getBoots());
                if (boots != null) {
                    if (boots instanceof FnBoots) {
                        ((FnBoots) boots).onHit(event);
                    }
                }
            }

        }


    }


}
