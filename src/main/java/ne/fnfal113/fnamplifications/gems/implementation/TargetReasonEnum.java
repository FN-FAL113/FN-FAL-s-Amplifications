package ne.fnfal113.fnamplifications.gems.implementation;

import org.bukkit.event.entity.EntityTargetEvent;

/**
 * Enum that contains the needed target reasons 
 * for the guardian to work properly
 */
public enum TargetReasonEnum {

    PLAYER_TARGET(EntityTargetEvent.TargetReason.CLOSEST_PLAYER, EntityTargetEvent.TargetReason.RANDOM_TARGET,
                  EntityTargetEvent.TargetReason.FORGOT_TARGET, EntityTargetEvent.TargetReason.COLLISION,
                  EntityTargetEvent.TargetReason.CLOSEST_ENTITY, EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY,
                  EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER, EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET),
    ZOMBIE_TARGET(EntityTargetEvent.TargetReason.values())
    ;

    private final EntityTargetEvent.TargetReason[] targetReasons;

    TargetReasonEnum(EntityTargetEvent.TargetReason... targetReasons) {
        this.targetReasons = targetReasons;
    }

    public boolean isTagged(EntityTargetEvent.TargetReason targetReason) {
        for(EntityTargetEvent.TargetReason reason : getTargetReasons()) {
            if(reason == targetReason) return true;
        }
        
        return false;
    }

    public EntityTargetEvent.TargetReason[] getTargetReasons() {
        return targetReasons;
    }

}