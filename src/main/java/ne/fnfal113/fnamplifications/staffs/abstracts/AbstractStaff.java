package ne.fnfal113.fnamplifications.staffs.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class AbstractStaff extends SlimefunItem {

    public AbstractStaff(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     *
     * @return the default lore of the staff
     */
    public abstract List<String> lore();

    /**
     *
     * @param event the interact event specifically used here for right click action
     */
    public abstract void onRightClick(PlayerInteractEvent event);
}