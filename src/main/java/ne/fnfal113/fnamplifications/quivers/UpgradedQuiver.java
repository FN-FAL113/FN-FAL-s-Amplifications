package ne.fnfal113.fnamplifications.quivers;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.quivers.abstracts.AbstractQuiver;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.inventory.ItemStack;

public class UpgradedQuiver extends AbstractQuiver {



    public UpgradedQuiver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int quiverSize, ItemStack arrowType) {
        super(itemGroup, item, recipeType, recipe, Keys.UPGRADED_ARROWS_KEY, Keys.UPGRADED_ARROWS_ID_KEY, Keys.UPGRADED_QUIVER_STATE_KEY,
                quiverSize, arrowType);
    }


}