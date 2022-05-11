package ne.fnfal113.fnamplifications.quivers.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.quivers.implementations.MainQuiver;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Serves as the abstract class for the quivers that
 * implements the main methods
 * @author FN_FAL113
 */
public abstract class AbstractQuiver extends SlimefunItem {

    @Getter
    private final NamespacedKey storageKey;
    @Getter
    private final NamespacedKey storageKey2;
    @Getter
    private final NamespacedKey storageKey3;
    @Getter
    private final MainQuiver mainQuiver;
    @Getter
    private final int quiverSize;
    @Getter
    private final ItemStack arrowType;


    public AbstractQuiver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          NamespacedKey arrowKey, NamespacedKey arrowIDKey, NamespacedKey quiverStateKey, int quiverSize, ItemStack arrowType) {
        super(itemGroup, item, recipeType, recipe);

        this.storageKey = arrowKey;
        this.storageKey2 = arrowIDKey;
        this.storageKey3 = quiverStateKey;
        this.quiverSize = quiverSize;
        this.arrowType = arrowType;
        this.mainQuiver = new MainQuiver(arrowKey, arrowIDKey, quiverStateKey, getQuiverSize(), getArrowType(), item);
    }

    /**
     * The method for adding arrows into the quiver
     * @param player The player involved in this event
     * @param item The quiver in your inventory
     */
    public void onArrowDeposit(Player player, ItemStack item){
        getMainQuiver().depositArrows(item, item.getItemMeta(), item.getItemMeta().getPersistentDataContainer(), player);
    }

    /**
     * The method for removing arrows from the quiver
     * @param event the interact event
     * @param item the item involved which is the quiver
     */
    public void onArrowWithdraw(PlayerInteractEvent event, ItemStack item){
        getMainQuiver().withdrawArrows(item, item.getItemMeta(), event.getPlayer(), item.getItemMeta().getPersistentDataContainer());
    }

    /**
     * The method for changing the quiver state between opened and closed quiver
     * @param itemStack the item involved when changing the state
     */
    public void onChangeState(ItemStack itemStack){
        getMainQuiver().changeState(itemStack, itemStack.getItemMeta(), itemStack.getItemMeta().getPersistentDataContainer());
    }

    /**
     * The event for deducting arrows from the quiver when
     * shooting a bow
     * @param event - Parameter for the EntityShootBowEvent
     */
    public void onBowShoot(EntityShootBowEvent event, ItemStack itemStack, boolean checkInfinity){
        getMainQuiver().bowShoot(event, itemStack, checkInfinity);
    }

}