package ne.fnfal113.fnamplifications.Gems.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public abstract class AbstractGem extends SlimefunItem {

    public AbstractGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     *
     * @param event the click event for the drag and drop
     * @param player the player who dragged and dropped the gem
     */
    public abstract void onDrag(InventoryClickEvent event, Player player);

    /**
     *
     * @param pdc the persistent data that contains the key and amount
     *            value from the itemstack
     * @param itemStack the itemstack that has the needed pdc data
     * @return the amount of gem inside the itemstack if there are any
     */
    public abstract int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack);

}