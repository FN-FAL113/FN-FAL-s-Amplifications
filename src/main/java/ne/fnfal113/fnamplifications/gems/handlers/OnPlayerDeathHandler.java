package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.event.entity.PlayerDeathEvent;

public interface OnPlayerDeathHandler extends GemHandler {

    /**
     *
     * @param event the event where player death is listened
     */
    void onPlayerDeath(PlayerDeathEvent event);

}