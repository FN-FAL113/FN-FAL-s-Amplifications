package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Serves as the handler for gems that relies
 * on damaging entities
 */
public interface OnDamageHandler {

    /**
     *
     * @param event the entity damage by other entity event
     */
    void onDamage(EntityDamageByEntityEvent event);

}
