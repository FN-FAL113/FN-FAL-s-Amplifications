package ne.fnfal113.fnamplifications.Gems.Interface;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * Interface for the gems that has the main the methods
 * for the gem subclasses
 * @author FN_FAL113
 */
public interface GemImpl {

    /**
     *
     * @param event the click event for the drag and drop
     * @param player the player who dragged and dropped the gem
     */
    void onDrag(InventoryClickEvent event, Player player);

    /**
     *
     * @param pdc the persistent data that contains the key and amount
     *            value from the itemstack
     * @param itemStack the itemstack that has the needed pdc data
     */
    int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack);
}
