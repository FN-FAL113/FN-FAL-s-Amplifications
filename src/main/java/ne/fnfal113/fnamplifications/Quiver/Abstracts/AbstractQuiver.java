package ne.fnfal113.fnamplifications.Quiver.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Serves as the abstract class for the quivers that
 * implements the main methods they invoke
 */
public abstract class AbstractQuiver extends SlimefunItem {

    public AbstractQuiver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     * Gets the default lore and add them to the list
     * for reusability
     * @return the default lore as list
     */
    public abstract List<String> defaultLore();

    /**
     * The method for adding arrows into the quiver
     * @param player The player involved in this event
     * @param item The quiver from your inventory
     */
    public abstract void onRightClick(Player player, ItemStack item);

    /**
     * The method for removing arrows from the quiver
     * @param event the interact event
     * @param item the item involved which is the quiver
     */
    public abstract void withdraw(PlayerInteractEvent event, ItemStack item);

    /**
     * The method for changing the quiver state between opened and closed quiver
     * @param itemStack the item involved when changing the state
     */
    public abstract void changeState(ItemStack itemStack);

    /**
     * The event for deducting arrows from the quiver when
     * shooting a bow
     * @param event - Parameter for the EntityShootBowEvent
     */
    public abstract void bowShoot(EntityShootBowEvent event, ItemStack itemStack);

    /**
     *
     * @return the quiver max arrow size
     */
    public abstract int getQuiverSize();

    public abstract NamespacedKey getStorageKey();

    public abstract NamespacedKey getStorageKey2();

    public abstract NamespacedKey getStorageKey3();

}