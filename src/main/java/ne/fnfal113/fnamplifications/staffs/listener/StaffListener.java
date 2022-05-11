package ne.fnfal113.fnamplifications.staffs.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.staffs.handlers.EntityStaffImpl;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class StaffListener implements Listener {

    @EventHandler
    public void onEffectApply(AreaEffectCloudApplyEvent event){

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
                World world = entity.getWorld();
                int pitch = ThreadLocalRandom.current().nextInt(180 + 1 + 180) - 180;
                int yaw = ThreadLocalRandom.current().nextInt(180 + 1 + 180) - 180;
                Location loc = new Location(world, entity.getLocation().getX(),
                        entity.getLocation().getY(),
                        entity.getLocation().getZ(), pitch, yaw);
                entity.teleport(loc);
            }
        }

        if (Objects.equals(event.getEntity().getCustomName(), "FN_GRAVITY")){
            for(LivingEntity entity : event.getAffectedEntities()){
                entity.setVelocity(entity.getVelocity().clone().add(event.getEntity().getLocation().clone().toVector().subtract(entity.getLocation().clone().toVector()).multiply(0.800)));
            }
        }

        if (Objects.equals(event.getEntity().getCustomName(), "FN_FORCE")){
            for(LivingEntity entity : event.getAffectedEntities()){
               entity.setVelocity(entity.getLocation().clone().getDirection().multiply(8).setY(0));
            }
        }

        if (Objects.equals(event.getEntity().getCustomName(), "FN_BACKWARD_FORCE")){
            for(LivingEntity entity : event.getAffectedEntities()){
                entity.setVelocity(entity.getLocation().clone().getDirection().multiply(-8).setY(0));
            }
        }

        if (Objects.equals(event.getEntity().getCustomName(), "FN_HEALING")){
            String playerCaster = event.getEntity().getPersistentDataContainer().get(Keys.createKey("cloudfn"), PersistentDataType.STRING);
            for(LivingEntity entity : event.getAffectedEntities()){
                boolean health = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null;
                if(entity instanceof Player) {
                    if (entity.getName().equals(playerCaster) && health) {
                        double maxHealth = Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
                        if (entity.getHealth() < maxHealth - 2) {
                            entity.setHealth(entity.getHealth() + 2);
                        }
                    }
                }
            }
        }

        if (Objects.equals(event.getEntity().getCustomName(), "FN_INVULNERABILITY")){
            for(LivingEntity entity : event.getAffectedEntities()){
                entity.setNoDamageTicks(40);
            }
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());
        boolean actionRight = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK);
        boolean isRightHand = e.getHand() == EquipmentSlot.HAND;

        if(!(stick instanceof AbstractStaff)){
            return;
        }

        if(!isRightHand){
            return;
        }

        if (!(stick instanceof EntityStaffImpl) && actionRight) {
            ((AbstractStaff) stick).onClick(e);
        } else if (stick instanceof EntityStaffImpl && !actionRight) {
            ((AbstractStaff) stick).onClick(e);
        }

    }

    @EventHandler
    public void onHorseDismount(VehicleExitEvent event){
        /*
         * This event is used by stallion staff to de-spawn
         * the skeleton horse upon unmount
         */
        if(event.getExited() instanceof Player){
            if(event.getVehicle() instanceof SkeletonHorse){
                SkeletonHorse skeletonHorse = (SkeletonHorse) event.getVehicle();
                if(skeletonHorse.getCustomName() != null && !skeletonHorse.getPersistentDataContainer().isEmpty()) {
                    if (skeletonHorse.getCustomName().equals("FN_SKELETON_HORSE")) {
                        skeletonHorse.remove();
                        skeletonHorse.getPersistentDataContainer().remove(Keys.createKey("Horsey"));
                    }
                }
            } // instanceof SkeletonHorse
        } // instanceof Player

    }

    @EventHandler
    public void onHorseInventoryOpen(InventoryOpenEvent event){
        /*
         * This event is used by stallion staff to prevent
         * opening of horse inventory which holds a saddle
         */
        if(event.getInventory().getHolder() instanceof SkeletonHorse) {
            if (event.getView().getTitle().equals("FN_SKELETON_HORSE")) {
                event.setCancelled(true);
            } // check view/inventory title
        } // check InventoryHolder

    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if (stick instanceof EntityStaffImpl && e.getHand() == EquipmentSlot.HAND) {
            ((EntityStaffImpl) stick).onEntityClick(e);
        } // check if slimefun item reference points to EntityStaff interface (Locomotion Staff)

    }

}