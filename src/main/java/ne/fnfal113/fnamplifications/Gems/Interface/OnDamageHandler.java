package ne.fnfal113.fnamplifications.Gems.Interface;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Serves as the handler for gems that relies
 * on damaging entities
 */
public interface OnDamageHandler {

    /**
     *
     * @param event the click event for the drag and drop
     */
    void onDamage(EntityDamageByEntityEvent event);

}
