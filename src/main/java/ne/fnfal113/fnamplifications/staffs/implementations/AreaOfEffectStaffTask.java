package ne.fnfal113.fnamplifications.staffs.implementations;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;

public class AreaOfEffectStaffTask {

    @Getter
    private final Player player;
    @Getter
    private final Block targetBlock;
    @Getter
    private final String cloudName;
    @Getter
    private final float radius;
    @Getter
    private final int durationInTicks;
    @Getter
    private final Particle particle;
    @Getter
    private final NamespacedKey storageKey;

    public AreaOfEffectStaffTask(Player player, Block targetBlock, String cloudName, float radius, int durationInTicks, Particle particle, @Nullable NamespacedKey key){
        this.player = player;
        this.targetBlock = targetBlock;
        this.cloudName = cloudName;
        this.radius = radius;
        this.durationInTicks = durationInTicks;
        this.particle = particle;
        this.storageKey = key;
    }

    public void spawnCloud(){
        AreaEffectCloud effectCloud = (AreaEffectCloud) getPlayer().getWorld().spawnEntity(getTargetBlock().getLocation().add(0.5, 1, 0.5), EntityType.AREA_EFFECT_CLOUD);
        effectCloud.setParticle(getParticle());
        effectCloud.setDuration(getDurationInTicks());
        effectCloud.setRadius(getRadius());
        effectCloud.setCustomName(getCloudName());
        effectCloud.setCustomNameVisible(false);
        effectCloud.setReapplicationDelay(0);
        if(getStorageKey() != null){
            effectCloud.getPersistentDataContainer().set(getStorageKey(), PersistentDataType.STRING, player.getName());
        }
        effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 0 , 0, false, false, false), true);

    }
}