package ne.fnfal113.fnamplifications.gems.unbinder;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGemUnbinder;
import org.bukkit.inventory.ItemStack;

public class PreciousUnbindGem extends AbstractGemUnbinder {

    public PreciousUnbindGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 70);
    }

}