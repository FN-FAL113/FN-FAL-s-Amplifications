package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public interface OnArrowHitHandler extends GemHandler {

    /**
     *
     * @param event the projectile event, used by gems made for bows
     * @param player the player who shot the projectile or arrow
     * @param entity  the entity that got hit by the projectile
     */
    void onArrowHit(ProjectileHitEvent event, Player player, LivingEntity entity);

}
