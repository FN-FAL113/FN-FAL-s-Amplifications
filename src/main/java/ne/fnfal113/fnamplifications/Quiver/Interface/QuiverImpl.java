package ne.fnfal113.fnamplifications.Quiver.Interface;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
    void defaultLore(List<String> lore);

    /**
     * The event for adding and removing arrows from the quiver
     * @param event - Parameter for PlayerInteractEvent
     */
    void onRightClick(PlayerInteractEvent event);

    /**
     * The event for deducting arrows from the quiver when
     * shooting a bow
     * @param event - Parameter for the EntityShootBowEvent
     */
    void bowShoot(EntityShootBowEvent event);

}
