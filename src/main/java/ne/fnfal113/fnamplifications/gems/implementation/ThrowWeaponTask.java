package ne.fnfal113.fnamplifications.gems.implementation;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.compatibility.VersionedMaterial;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;

/**
 * @author FN_FAL113
 */
public class ThrowWeaponTask extends BukkitRunnable {

    private final ArmorStand armorStand;
    
    private final Player player;
    
    private final ItemStack itemStack;
    
    private final boolean rotateWeapon;
    
    private final boolean isTriWeapon;
    
    private final boolean isRetaliated;
    
    private final ReturnWeaponTask returnWeaponTask;

    private Vector vector;

    public ThrowWeaponTask(Player player, ItemStack itemStack, boolean rotateWeapon, boolean isTriWeapon, boolean returnWeapon) {
       this(player, itemStack, rotateWeapon, isTriWeapon, returnWeapon, new Vector(0, 0, 0));
    }

    public ThrowWeaponTask(Player player, ItemStack itemStack, boolean rotateWeapon, boolean isTriWeapon, boolean isRetaliated, Vector vector) {
        this.player = player;
        this.itemStack = itemStack;
        this.rotateWeapon = rotateWeapon;
        this.isTriWeapon = isTriWeapon;
        this.isRetaliated = isRetaliated;
        this.vector = vector;
        this.armorStand = spawnArmorstand(player, itemStack);
        this.returnWeaponTask = new ReturnWeaponTask(getPlayer(), getItemStack(), getArmorStand(), isTriWeapon);
    }

    public ArmorStand spawnArmorstand(Player player, ItemStack itemStack) {
        ArmorStand as = player.getWorld().spawn(player.getLocation().add(0, 0.9, 0), ArmorStand.class);

        as.setArms(true);
        as.setGravity(false);
        as.setVisible(false);
        as.setSmall(true);
        as.setMarker(true);
        as.setCustomNameVisible(false);
        as.setPersistent(false);

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_WITCH_THROW, 1.0F, 1.0F);

        ItemStack weapon = itemStack.clone();

        // sets armor stand arm item and body angle
        if(isTriWeapon()) { // tri-weapon variant
            Objects.requireNonNull(as.getEquipment()).setItemInMainHand(weapon);
            Objects.requireNonNull(as.getEquipment()).setItemInOffHand(weapon);
            Objects.requireNonNull(as.getEquipment()).setHelmet(weapon);
        } else {
            as.setRightArmPose(Utils.setRightArmAngle(as, 270, 0, 0));

            Objects.requireNonNull(as.getEquipment()).setItemInMainHand(weapon);
        }

        return as;
    }

    @Override
    public void run() {
        getArmorStand().teleport(getArmorStand().getLocation().add(getVector()));
        getArmorStand().getWorld().spawnParticle(Particle.ASH, getArmorStand().getLocation(), 8, 0.5, 0.5, 0);

        // rotate floating item by 45 degrees per tick
        if(isRotateWeapon()) {
            getArmorStand().setRightArmPose(Utils.setRightArmAngle(getArmorStand(), 45, 0, 0));
        }

        if(isTriWeapon()) {
            getArmorStand().setHeadPose(Utils.setHeadAngle(getArmorStand(), 18, 30, 18));
        }

        RayTraceResult result = getArmorStand().rayTraceBlocks(0.109);
        List<Entity> entityList = getArmorStand().getNearbyEntities(0.3, 0.3, 0.3);

        // check if the raytrace result has a block within the max distance
        // if it hits a block, the weapon is either returned or dropped
        if(result != null && Objects.requireNonNull(result.getHitBlock()).getType() != VersionedMaterial.SHORT_GRASS
            && !Tag.FLOWERS.isTagged(result.getHitBlock().getType())) {
            if(shouldReturnWeapon(false)) {
                returnWeapon();

                return;
            }

            dropWeapon();
            
            return;
        }

        // check if there are nearby entities around the given bounding box
        // if weapon has retaliate gem, return the weapon after damaging an entity else drop it instead
        // tri-weapon can cut through entities, will not be dropped or returned unless it hits a block or reach max distance
        if(!entityList.isEmpty() && !entityList.contains(getPlayer())) {
            for(int i = 0; i < entityList.size(); i++) { 
                if(entityList.get(i) instanceof Damageable && entityList.get(i).getUniqueId() != getPlayer().getUniqueId()){
                    if(WeaponArmorEnum.SWORDS.isTagged(getItemStack().clone().getType())) { // for throwable swords
                        ((Damageable) entityList.get(i)).damage(13, getPlayer());
                    } else { 
                        ((Damageable) entityList.get(i)).damage(16, getPlayer());
                    }
                }
            }

            if(shouldReturnWeapon(true)) {
                returnWeapon();

                return;
            }

            if(!isTriWeapon()) {
                dropWeapon();

                return;
            }
        }

        // drop the weapon if the distance is greater square-root of 3600 or 60 blocks
        if(getArmorStand().getLocation().distanceSquared(getPlayer().getLocation()) > 3600) {
            getPlayer().sendMessage(Utils.colorTranslator("&eYour weapon has reached the max distance of 60 blocks!"));

            if(shouldReturnWeapon(false)) {
                returnWeapon();

                return;
            }

            dropWeapon();
        }
    }

    public boolean shouldReturnWeapon(boolean entityHit) {
        // weapon does not have retaliate gem
        if(!isRetaliated()) return false;

        // tri-weapon can pass-through entities
        if(isTriWeapon() && entityHit) return false;

        return true;
    }

    public void dropWeapon() {
        this.cancel();

        Item droppedItem = getArmorStand().getWorld().dropItem(getArmorStand().getLocation(), getItemStack().clone());
        Location locInfo = droppedItem.getLocation();

        droppedItem.setOwner(getPlayer().getUniqueId()); // only the player who threw can pick up the weapon
        droppedItem.setGlowing(true);

        getArmorStand().remove();

        getPlayer().sendMessage(Utils.colorTranslator("&6Weapon dropped near at " +
                "x: " + (int) locInfo.getX() + ", " +
                "y: " + (int) locInfo.getY() + ", " +
                "z: " + (int) locInfo.getZ()));
    }

    public void returnWeapon() {
        this.cancel();

        getReturnWeaponTask().runTaskTimer(FNAmplifications.getInstance(), 4L, 1L);
    }

    public void centeredThrow() {
        getArmorStand().teleport(getPlayer().getLocation().add(0,0.9, 0));
    }

    public void resetArmorstandArmPos() {
        getArmorStand().setRightArmPose(new EulerAngle(0, 0, 0));
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isRotateWeapon() {
        return rotateWeapon;
    }

    public boolean isTriWeapon() {
        return isTriWeapon;
    }

    public boolean isRetaliated() {
        return isRetaliated;
    }

    public ReturnWeaponTask getReturnWeaponTask() {
        return returnWeaponTask;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }
}