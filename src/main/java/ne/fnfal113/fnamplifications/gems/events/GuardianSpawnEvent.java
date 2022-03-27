package ne.fnfal113.fnamplifications.gems.events;

import lombok.Getter;
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

    @Getter
    private final Player guardianOwner;
    @Getter
    private final Entity damager;
    @Getter
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


}
