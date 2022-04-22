package ne.fnfal113.fnamplifications.gems.implementation;

import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class ThrowableWeapon {

    private final Map<UUID, Integer> WEAPONS = new HashMap<>();

    public ThrowableWeapon(){}

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

    public void floatThrowItem(Player player, ItemStack itemStack, boolean returnWeapon){ // used by damnation gem
            ArmorStand as = spawnArmorstand(player, itemStack, false);

            player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1.0F, 1.0F);

            int id = Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), () -> {
                int x = ThreadLocalRandom.current().nextInt(3);
                double xFinal = x == 2 ? 1.2 : -1.2;
                int z = ThreadLocalRandom.current().nextInt(3);
                double zFinal = z == 2 ? 1.2 : -1.2;
                as.teleport(player.getLocation().clone().add(xFinal, 0.8, zFinal));
            }, 5L, 12L).getTaskId();

            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                WEAPONS.put(player.getUniqueId(), WEAPONS.get(player.getUniqueId()) - 1);
                Bukkit.getScheduler().cancelTask(id);
                as.setRightArmPose(new EulerAngle(0, 0, 0));
                throwWeapon(player, as, itemStack.clone(), false, false, false, returnWeapon);
            }, 160L);
    }

    public void throwWeapon(Player player, ArmorStand as, ItemStack itemStack, boolean rotateWeapon, boolean cutThrough, boolean isTriWeapon, boolean returnWeapon){
        Vector vector = player.getLocation().add(player.getLocation().getDirection().multiply(9).normalize())
                .subtract(player.getLocation().toVector()).toVector();

        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1.0F, 1.0F);
        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            as.teleport(player.getLocation().add(0,0.9, 0));

            if(isTriWeapon){
                as.setRightArmPose(Utils.setRightArmAngle(as, 0, 348, 0));
                as.setLeftArmPose(Utils.setLeftArmAngle(as, 0, 12, 0));
                as.setHeadPose(Utils.setHeadAngle(as, 98, 32, 97));
            }

            new ThrowWeaponTask(as, player, itemStack, rotateWeapon, cutThrough, isTriWeapon, returnWeapon, vector)
                    .runTaskTimer(FNAmplifications.getInstance(), 0L, 1L);
        }, 1L);
    }

}