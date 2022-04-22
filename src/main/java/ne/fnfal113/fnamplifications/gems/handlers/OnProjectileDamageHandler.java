package ne.fnfal113.fnamplifications.gems.handlers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface OnProjectileDamageHandler extends GemHandler {

    /**
     *
     * @param event the entity damage by other entity event
     * @param shooter the player who shot the projectile
     * @param entity the entity who got shot by the projectile
     * @param projectile the projectile that were shot
     * @param itemStack the item where the gem is bound to
     */
    void onProjectileDamage(EntityDamageByEntityEvent event, Player shooter, LivingEntity entity, Projectile projectile, ItemStack itemStack);
}
