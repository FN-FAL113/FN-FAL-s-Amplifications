package ne.fnfal113.fnamplifications.quivers;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import ne.fnfal113.fnamplifications.quivers.abstracts.AbstractQuiver;
import ne.fnfal113.fnamplifications.utils.Keys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class UpgradedSpectralQuiver extends AbstractQuiver {

    public UpgradedSpectralQuiver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int quiverSize) {
        super(itemGroup, item, recipeType, recipe, Keys.UPGRADED_SPECTRAL_ARROWS_KEY, Keys.UPGRADED_SPECTRAL_ARROWS_ID_KEY, Keys.UPGRADED_SPECTRAL_QUIVER_STATE_KEY, quiverSize,  new ItemStack(Material.SPECTRAL_ARROW, 1));
    }

}