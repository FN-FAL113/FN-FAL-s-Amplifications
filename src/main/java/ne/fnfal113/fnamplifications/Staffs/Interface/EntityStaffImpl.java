package ne.fnfal113.fnamplifications.Staffs.Interface;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public interface EntityStaffImpl {

    /**
     *
     * @return the default lore as list for the itemstack
     */
    List<String> lore();


    /**
     *
     * @param event the interact event for right-clicking entities
     */
    void onRightClick(PlayerInteractEntityEvent event);

    /**
     *
     * @param event the interact event for the left click action
     */
    void onLeftClick(PlayerInteractEvent event);
}
