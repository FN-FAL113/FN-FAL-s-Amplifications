package ne.fnfal113.fnamplifications.tools.implementation;

import lombok.Getter;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author FN_FAL113
 */
public class ThrowableItemTask extends BukkitRunnable {

    @Getter
    private final Player player;
    @Getter
    private final ItemStack itemStack;
    @Getter
    private final Vector vector;
    @Getter
    private final ArmorStand armorStand;
    @Getter
    private final Consumer<ThrowableItemTask> throwableItemTaskConsumer;

    public ThrowableItemTask(Player player, ItemStack itemStack, Vector vector, Consumer<ThrowableItemTask> throwableItemTaskConsumer){
        this.player = player;
        this.itemStack = itemStack;
        this.vector = vector;
        this.armorStand = spawnArmorstand();
        this.throwableItemTaskConsumer = throwableItemTaskConsumer;
    }

    public ArmorStand spawnArmorstand(){
        return getPlayer().getWorld().spawn(getPlayer().getLocation().add(0, 0.9, 0), ArmorStand.class, armorStand ->{
            armorStand.setArms(true);
            armorStand.setSmall(true);
            armorStand.setMarker(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setCustomNameVisible(false);
            armorStand.setPersistent(false);
            armorStand.setBasePlate(false);

            armorStand.setRightArmPose(Utils.setRightArmAngle(armorStand, 270, 0, 0));
            Objects.requireNonNull(armorStand.getEquipment()).setItemInMainHand(getItemStack().clone());
        });
    }

    @Override
    public void run() {
        getThrowableItemTaskConsumer().accept(this);
    }

    public void dropItemTask(ArmorStand as, ItemStack itemStack){
        itemStack.setAmount(1); // prevent cloning the exact itemstack amount in the main hand

        Item droppedItem = as.getWorld().dropItemNaturally(as.getLocation(), itemStack.clone());
        droppedItem.setGlowing(true);

        as.remove();

        this.cancel();
    }

    public void removeItemTask(ArmorStand as){
        as.remove();

        this.cancel();
    }

}