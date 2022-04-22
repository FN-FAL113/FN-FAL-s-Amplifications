package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public interface OnPlayerDeathHandler extends GemHandler {

    /**
     *
     * @param event the event where player death is listened
     * @param itemStack the item where the gem is bound at
     */
    void onPlayerDeath(PlayerDeathEvent event, ItemStack itemStack);

}