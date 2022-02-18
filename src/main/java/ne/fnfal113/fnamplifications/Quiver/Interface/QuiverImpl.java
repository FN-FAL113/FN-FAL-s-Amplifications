package ne.fnfal113.fnamplifications.Quiver.Interface;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Serves as the main interface for the quivers that
 * implements the main methods that they use
 */
public interface QuiverImpl {

    /**
     * Gets the default lore and add them to the list
     * for reusability
     */
    List<String> defaultLore();

    /**
     * The method for adding arrows into the quiver
     * @param player The player involved in this event
     * @param item The quiver from your inventory
     */
    void onRightClick(Player player, ItemStack item);

    /**
     * The method for removing arrows from the quiver
     * @param event the interact event
     * @param item the item involved which is the quiver
     */
    void withdraw(PlayerInteractEvent event, ItemStack item);

    /**
     * The method for changing the quiver state between opened and closed quiver
     * @param itemStack the item involved when changing the state
     */
    void changeState(ItemStack itemStack);

    /**
     * The event for deducting arrows from the quiver when
     * shooting a bow
     * @param event - Parameter for the EntityShootBowEvent
     */
    void bowShoot(EntityShootBowEvent event, ItemStack itemStack);

}
