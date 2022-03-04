package ne.fnfal113.fnamplifications.Gems.Interface;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public interface OnArrowHitHandler {

    void onArrowHit(ProjectileHitEvent event, Player player, LivingEntity entity);

}
