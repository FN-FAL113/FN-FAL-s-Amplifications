package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Serves as the handler for gems that relies
 * on damaging entities
 */
public interface OnDamageHandler extends GemHandler {

    /**
     *
     * @param event the entity damage by other entity event
     * @param itemStack the item where the gem is bound at
     */
    void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack);

}
