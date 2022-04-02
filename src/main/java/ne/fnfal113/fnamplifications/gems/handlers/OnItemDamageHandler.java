package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.event.player.PlayerItemDamageEvent;

public interface OnItemDamageHandler extends GemHandler{

    /**
     *
     * @param event The durability change event
     */
    void onDurabilityChange(PlayerItemDamageEvent event);
}
