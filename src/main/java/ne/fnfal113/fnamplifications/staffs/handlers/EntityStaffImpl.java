package ne.fnfal113.fnamplifications.staffs.handlers;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public interface EntityStaffImpl {

    /**
     *
     * @return the default lore as list of the staff
     */
    List<String> lore();

    /**
     *
     * @param event the player interact at entity event
     */
    void onRightClick(PlayerInteractEntityEvent event);

    /**
     *
     * @param event the player interact event
     */
    void onLeftClick(PlayerInteractEvent event);
}
