package ne.fnfal113.fnamplifications.gems.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public class GuardianSpawnEvent extends Event implements Cancellable, Listener {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player guardianOwner;

    private final Entity damager;

    private final Zombie zombieGuardian;

    private boolean isCancelled;

    public GuardianSpawnEvent(Player guardianOwner, Zombie zombieGuardian, Entity damager){
        this.guardianOwner = guardianOwner;
        this.zombieGuardian = zombieGuardian;
        this.damager = damager;
        this.isCancelled = false;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public Player getGuardianOwner() {
        return guardianOwner;
    }

    public Entity getDamager() {
        return damager;
    }

    public Zombie getZombieGuardian() {
        return zombieGuardian;
    }

}
