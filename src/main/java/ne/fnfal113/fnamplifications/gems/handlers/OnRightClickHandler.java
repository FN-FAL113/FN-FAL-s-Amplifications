package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.entity.Player;

public interface OnRightClickHandler extends GemHandler {

    /**
     *
     * @param player the player who right-clicked
     */
    void onRightClick(Player player);

}
