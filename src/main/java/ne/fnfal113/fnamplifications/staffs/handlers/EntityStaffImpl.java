package ne.fnfal113.fnamplifications.staffs.handlers;

import org.bukkit.event.player.PlayerInteractEntityEvent;

public interface EntityStaffImpl {

    /**
     *
     * @param event the player interact at entity event
     */
    void onEntityClick(PlayerInteractEntityEvent event);

}