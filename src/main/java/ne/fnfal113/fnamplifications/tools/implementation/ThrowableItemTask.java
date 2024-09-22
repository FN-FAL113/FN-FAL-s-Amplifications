package ne.fnfal113.fnamplifications.tools.implementation;

import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import org.bukkit.util.Consumer;

import java.util.Objects;

/**
 * @author FN_FAL113
 */
public class ThrowableItemTask extends BukkitRunnable {

    private final Player player;

    private final ItemStack itemStack;

    private final Vector vector;

    private final ArmorStand armorStand;

    private final Consumer<ThrowableItemTask> throwableItemTaskConsumer;

    public ThrowableItemTask(Player player, ItemStack itemStack, Vector vector, Consumer<ThrowableItemTask> throwableItemTaskConsumer){
        this.player = player;
        this.itemStack = itemStack.clone(); // don't mutate original itemstack else item amout will be set to 1 when dropping the torch
        this.vector = vector;
        this.armorStand = spawnArmorstand();
        this.throwableItemTaskConsumer = throwableItemTaskConsumer;
    }

    public ArmorStand spawnArmorstand() {
        ArmorStand as = getPlayer().getWorld().spawn(getPlayer().getLocation().add(0, 0.9, 0), ArmorStand.class);

        as.setArms(true);
        as.setSmall(true);
        as.setMarker(true);
        as.setGravity(false);
        as.setVisible(false);
        as.setCustomNameVisible(false);
        as.setPersistent(false);
        as.setBasePlate(false);

        as.setRightArmPose(Utils.setRightArmAngle(as, 270, 0, 0));
        Objects.requireNonNull(as.getEquipment()).setItemInMainHand(getItemStack().clone());

        return as;
    }

    @Override
    public void run() {
       getThrowableItemTaskConsumer().accept(this);
    }

    public void dropTorch() {
        if(getItemStack() != null && getItemStack().getType() != Material.AIR) {
            getItemStack().setAmount(1); // prevent cloning the exact itemstack amount in the main hand

            Item droppedItem = getArmorStand().getWorld().dropItemNaturally(getArmorStand().getLocation(), getItemStack());
            droppedItem.setGlowing(true);
        }

        stopTask();
    }

    public void stopTask() {
        getArmorStand().remove();

        this.cancel();
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    
    public Vector getVector() {
        return vector;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Consumer<ThrowableItemTask> getThrowableItemTaskConsumer() {
        return throwableItemTaskConsumer;
    }
}