package ne.fnfal113.fnamplifications.Gears.Implementation;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface GearsImpl {

    /**
     *
     * @return the default lore as list
     */
    List<String> defaultLore();

    /**
     *
     * @param event the event for the damaging entity by another entity
     */
    void onHit(EntityDamageByEntityEvent event);

    /**
     *
     * @param armor the armor that levelled up
     * @param level the new level integer value
     * @param p the player that wore the armor
     */
    void upgradeArmor(ItemStack armor, int level, Player p);

}