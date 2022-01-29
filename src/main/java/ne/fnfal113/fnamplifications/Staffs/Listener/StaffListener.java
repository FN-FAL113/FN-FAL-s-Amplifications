package ne.fnfal113.fnamplifications.Staffs.Listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Staffs.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class StaffListener implements Listener {

    private final Map<UUID, Block> blockMap = new HashMap<>();
    private final Map<UUID, Long> cooldown = new HashMap<>();

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
            String playerCaster = event.getEntity().getPersistentDataContainer().get(new NamespacedKey(FNAmplifications.getInstance(), "cloudfn"), PersistentDataType.STRING);
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

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfGravitationalPull) {
                ((StaffOfGravitationalPull) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfStallion) {
                ((StaffOfStallion) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfForce) {
                ((StaffOfForce) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfHealing) {
                ((StaffOfHealing) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfInvulnerability) {
                ((StaffOfInvulnerability) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfExplosion) {
                ((StaffOfExplosion) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfMuster) {
                ((StaffOfMuster) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfAwareness) {
                ((StaffOfAwareness) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfMinerals) {
                ((StaffOfMinerals) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfFangs) {
                ((StaffOfFangs) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfSkulls) {
                ((StaffOfSkulls) stick).onRightClick(e);
            }
        }

        if (actionRight && e.getHand() == EquipmentSlot.HAND) {
            if (stick instanceof StaffOfAirStrider) {
                if(cooldown.containsKey(p.getUniqueId())) {
                    long diff = (System.currentTimeMillis() - cooldown.get(p.getUniqueId())) / 1000;
                    if(diff < 10) {
                        p.sendMessage(ChatColor.GOLD + "in cooldown! please wait: " + ChatColor.YELLOW + (10 - diff)
                               + ChatColor.GOLD + " seconds to use the staff again");
                    }
                } else{
                    if(Slimefun.getProtectionManager().hasPermission
                            (Bukkit.getOfflinePlayer(p.getUniqueId()), p.getLocation(), Interaction.PLACE_BLOCK)) {
                        ((StaffOfAirStrider) stick).onRightClick(e);
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "You can now walk on the air for 10 seconds");
                        cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    } else{
                        p.sendMessage(ChatColor.RED  + "You have no permission to cast air strider on this land claim!");
                    }
                }
            }
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(cooldown.containsKey(player.getUniqueId())){
            long diff = (System.currentTimeMillis() - cooldown.get(player.getUniqueId())) / 1000;
            if(diff < 10) {
                if (blockMap.containsKey(player.getUniqueId())) {
                    Block block = blockMap.get(player.getUniqueId());
                    block.setType(Material.AIR);
                    blockMap.remove(player.getUniqueId());
                } // remove the last block saved from the hashmap
                Block newBlock = player.getLocation().getBlock().getRelative(0, -1, 0);
                if (newBlock.getType() == Material.AIR) {
                    newBlock.setType(Material.BARRIER);
                    blockMap.put(player.getUniqueId(), newBlock);
                }// check if relative block below the player is air}
            } // cooldown check
            else {
                Block block = blockMap.get(player.getUniqueId());
                block.setType(Material.AIR);
                cooldown.remove(player.getUniqueId());
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Air Strider has expired!");
            }
        } // cooldown map key check
    }

    // For Staff of Minerals, removed in favor of written book
    /*@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(InventoryClickEvent event){
        InventoryView inventoryView = event.getView();
        if(inventoryView.getTitle().equals("Staff of Minerals")){
            event.setCancelled(true);
        }
    }*/

    @EventHandler
    public void onPlayerDismount(VehicleExitEvent event){
        if(event.getExited() instanceof Player){
            if(event.getVehicle() instanceof SkeletonHorse){
                SkeletonHorse skeletonHorse = (SkeletonHorse) event.getVehicle();
                if(skeletonHorse.getCustomName() != null && !skeletonHorse.getPersistentDataContainer().isEmpty()) {
                    if (skeletonHorse.getCustomName().equals("FN_SKELETON_HORSE")) {
                        skeletonHorse.remove();
                        skeletonHorse.getPersistentDataContainer().remove(new NamespacedKey(FNAmplifications.getInstance(), "Horsey"));
                    }
                }
            } // instanceof SkeletonHorse
        } // instanceof Player

    }

    @EventHandler
    public void horseInventory(InventoryOpenEvent event){
        if(event.getInventory().getHolder() instanceof SkeletonHorse) {
            if (event.getView().getTitle().equals("FN_SKELETON_HORSE")) {
                event.setCancelled(true);
            } // check view/inventory title
        } // check InventoryHolder

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
