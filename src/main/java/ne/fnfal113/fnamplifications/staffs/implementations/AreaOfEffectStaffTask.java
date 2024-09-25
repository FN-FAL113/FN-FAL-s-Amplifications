package ne.fnfal113.fnamplifications.staffs.implementations;

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

    private final Player player;

    private final Block targetBlock;

    private final String cloudName;

    private final float radius;

    private final int durationInTicks;

    private final Particle particle;

    private final NamespacedKey storageKey;

    public AreaOfEffectStaffTask(Player player, Block targetBlock, String cloudName, float radius, int durationInTicks, Particle particle, @Nullable NamespacedKey key) {
        this.player = player;
        this.targetBlock = targetBlock;
        this.cloudName = cloudName;
        this.radius = radius;
        this.durationInTicks = durationInTicks;
        this.particle = particle;
        this.storageKey = key;
    }

    public void spawnCloud() {
        AreaEffectCloud effectCloud = (AreaEffectCloud) getPlayer().getWorld().spawnEntity(getTargetBlock().getLocation().add(0.5, 1, 0.5), EntityType.AREA_EFFECT_CLOUD);
        
        effectCloud.setParticle(getParticle());
        effectCloud.setDuration(getDurationInTicks());
        effectCloud.setRadius(getRadius());
        effectCloud.setCustomName(getCloudName());
        effectCloud.setCustomNameVisible(false);
        effectCloud.setReapplicationDelay(0);

        if(getStorageKey() != null) {
            effectCloud.getPersistentDataContainer().set(getStorageKey(), PersistentDataType.STRING, player.getName());
        }

        effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 0 , 0, false, false, false), true);
    }

    public Player getPlayer() {
        return player;
    }

    public Block getTargetBlock() {
        return targetBlock;
    }

    public String getCloudName() {
        return cloudName;
    }

    public float getRadius() {
        return radius;
    }

    public int getDurationInTicks() {
        return durationInTicks;
    }

    public Particle getParticle() {
        return particle;
    }

    public NamespacedKey getStorageKey() {
        return storageKey;
    }
}