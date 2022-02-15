package ne.fnfal113.fnamplifications.Gems.Implementation;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class ThrowWeaponTask {

    private final Map<UUID, Integer> WEAPONS = new HashMap<>();

    public ThrowWeaponTask(){

    }

    public ArmorStand spawnArmorstand(Player player, ItemStack itemStack, boolean isTriSword){

        return player.getWorld().spawn(player.getLocation().add(0, 0.9, 0), ArmorStand.class, armorStand ->{
            armorStand.setArms(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            armorStand.setMarker(true);
            armorStand.setCustomNameVisible(false);
            armorStand.setPersistent(false);
            if(!isTriSword) {
                armorStand.setRightArmPose(Utils.setRightArmAngle(armorStand, 270, 0, 0));
                Objects.requireNonNull(armorStand.getEquipment()).setItemInMainHand(itemStack.clone());
            } else{
                armorStand.setRightArmPose(Utils.setRightArmAngle(armorStand, 0, 0, 0));
                Objects.requireNonNull(armorStand.getEquipment()).setItemInMainHand(itemStack.clone());
                Objects.requireNonNull(armorStand.getEquipment()).setItemInOffHand(itemStack.clone());
                Objects.requireNonNull(armorStand.getEquipment()).setHelmet(itemStack.clone());
            }
        });
    }

    public boolean isBelow4Weapons(Player player){
        if(!WEAPONS.containsKey(player.getUniqueId())){
            WEAPONS.put(player.getUniqueId(), 0);
        }

        if(WEAPONS.get(player.getUniqueId()) < 4) {
            WEAPONS.put(player.getUniqueId(), WEAPONS.get(player.getUniqueId()) + 1);
            return true;
        } else{
            player.sendMessage(Utils.colorTranslator("&eLimit reached! You can only have 4 weapons simultaneously"));
            return false;
        }
    }

    public void floatThrowItem(Player player, ItemStack itemStack){
            ArmorStand as = spawnArmorstand(player, itemStack, false);

            int id = Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), () -> {
                int x = ThreadLocalRandom.current().nextInt(3);
                int xFinal = x < 1 ? -2 : 2;
                int z = ThreadLocalRandom.current().nextInt(3);
                int zFinal = z < 1 ? -2 : 2;
                as.teleport(player.getLocation().clone().add(xFinal, 0.8, zFinal));
            }, 5L, 12L).getTaskId();

            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                WEAPONS.put(player.getUniqueId(), WEAPONS.get(player.getUniqueId()) - 1);
                Bukkit.getScheduler().cancelTask(id);
                as.setRightArmPose(new EulerAngle(0, 0, 0));
                throwWeapon(player, as, itemStack.clone(), false, false, false);
            }, 160L);
    }


    public void throwWeapon(Player player, ArmorStand as, ItemStack itemStack, boolean rotateWeapon, boolean cutThrough, boolean isTriWeapon){
        Vector vector = player.getLocation().add(player.getLocation().getDirection().multiply(9).normalize())
                .subtract(player.getLocation().toVector()).toVector();


        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            as.teleport(player.getLocation().add(0,0.9, 0));

            if(isTriWeapon){
                as.setRightArmPose(Utils.setRightArmAngle(as, 0, 348, 0));
                as.setLeftArmPose(Utils.setLeftArmAngle(as, 0, 12, 0));
                as.setHeadPose(Utils.setHeadAngle(as, 98, 32, 97));
            }

            Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task -> {
                as.teleport(as.getLocation().add(vector));
                as.getWorld().spawnParticle(Particle.DRIPPING_OBSIDIAN_TEAR, as.getLocation().add(0,0, 0.2), 2);
                if(rotateWeapon) {
                    as.setRightArmPose(Utils.setRightArmAngle(as, 45, 0, 0));
                }
                RayTraceResult result = as.rayTraceBlocks(0.105);
                List<Entity> entityList = as.getNearbyEntities(0.3, 0.3, 0.3);

                if(result != null && Objects.requireNonNull(result.getHitBlock()).getType() != Material.GRASS &&
                        !Tag.FLOWERS.isTagged(result.getHitBlock().getType())){
                    player.sendMessage(weaponTask(as, player, task, itemStack.clone()));
                    return;
                }
                if(!entityList.isEmpty() && !entityList.contains(player)){
                    for(int i = 0; i < entityList.size(); i++){
                        if(entityList.get(i) instanceof Damageable && entityList.get(i).getUniqueId() != player.getUniqueId()){
                            if(Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()), entityList.get(i).getLocation(), Interaction.BREAK_BLOCK)) {
                                if(WeaponArmorEnum.SWORDS.isTagged(itemStack.clone().getType())) {
                                    ((Damageable) entityList.get(i)).damage(ThreadLocalRandom.current().nextInt(100) < 35 ? 6 : 3);
                                } else {
                                    ((Damageable) entityList.get(i)).damage(ThreadLocalRandom.current().nextInt(100) < 35 ? 8 : 4);
                                }
                            }
                        }
                    }
                    if(!cutThrough) {
                        weaponTask(as, player, task, itemStack.clone());
                        return;
                    }
                }
                if(as.getLocation().distanceSquared(player.getLocation()) > 3600){
                    player.sendMessage(Utils.colorTranslator("&eYour weapon has reached the max distance, " + weaponTask(as, player, task, itemStack.clone())));

                }
            }, 0L, 1L);
        }, 1L);

    }

    public String weaponTask(ArmorStand as, Player player, BukkitTask task, ItemStack itemStack){
        Item droppedItem = as.getWorld().dropItem(as.getLocation(), itemStack);
        Location locInfo = droppedItem.getLocation();
        droppedItem.setOwner(player.getUniqueId());
        droppedItem.setGlowing(true);
        as.remove();
        task.cancel();

        return Utils.colorTranslator("&eWeapon dropped near at " +
                "x: " + (int) locInfo.getX() + ", " +
                "y: " + (int) locInfo.getY() + ", " +
                "z: " + (int) locInfo.getZ());
    }

}
