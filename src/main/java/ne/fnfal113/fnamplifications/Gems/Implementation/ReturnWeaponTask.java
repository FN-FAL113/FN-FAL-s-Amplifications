package ne.fnfal113.fnamplifications.Gems.Implementation;

import lombok.Getter;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ReturnWeaponTask extends BukkitRunnable {

    @Getter
    private final ItemStack itemStack;
    @Getter
    private final ArmorStand armorStand;
    @Getter
    private final Player player;

    public ReturnWeaponTask(ItemStack itemStack, ArmorStand armorStand, Player player){
        this.itemStack = itemStack;
        this.armorStand = armorStand;
        this.player = player;
    }

    @Override
    public void run() {
        Location asLocation = getArmorStand().getLocation();
        Vector asVector = asLocation.toVector();
        Location pLocation = getPlayer().getLocation();
        Vector pVector = pLocation.toVector();

        getArmorStand().teleport(asLocation.subtract(asVector.subtract(pVector).normalize()).setDirection(pLocation.getDirection()));

        if(!getPlayer().isOnline()){
            dropItem(asLocation);

            stopTask();
        }

        if(distanceBetween(asLocation, pLocation) > 150){
            Location dropLoc = dropItem(asLocation);
            getPlayer().sendMessage(Utils.colorTranslator("&cWeapon has not been returned because you're too far!"));
            getPlayer().sendMessage(Utils.colorTranslator("&cit was dropped at: &e" +
                    "x: " + (int) dropLoc.getX() + ", " +
                    "y: " + (int) dropLoc.getY() + ", " +
                    "z: " + (int) dropLoc.getZ()));

            stopTask();
        }

        if(distanceBetween(asLocation, pLocation) < 0.5){
            if(getPlayer().getInventory().firstEmpty() == -1){
                getPlayer().sendMessage(Utils.colorTranslator("&eInventory full! dropped the item instead"));
                dropItem(pLocation);
            } else {
                getPlayer().getInventory().addItem(getItemStack().clone());
            }

            stopTask();
        }
    }

    public Location dropItem(Location location){
        Item droppedItem = getPlayer().getWorld().dropItem(location, getItemStack().clone());
        droppedItem.setOwner(getPlayer().getUniqueId());
        droppedItem.setGlowing(true);

        return droppedItem.getLocation();
    }

    public double distanceBetween(Location asLoc, Location pLoc){
        return asLoc.distance(pLoc);
    }

    public void stopTask(){
        getArmorStand().remove();
        this.cancel();
    }
}
