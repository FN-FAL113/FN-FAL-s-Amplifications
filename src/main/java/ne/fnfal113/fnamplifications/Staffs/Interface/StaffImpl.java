package ne.fnfal113.fnamplifications.Staffs.Interface;

import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public interface StaffImpl {

    /**
     *
     * @return the default lore as list for the itemstack
     */
    List<String> lore();

    /**
     *
     * @param event the interact event for every right click action
     */
    void onRightClick(PlayerInteractEvent event);

}
