package ne.fnfal113.fnamplifications.Tools;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FnHoeAutoPlant extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    public FnHoeAutoPlant(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static void setup(){
        new FnHoeAutoPlant(FNAmpItems.FN_MISC, FNAmpItems.FN_HOE_5X5_AUTO_PLANT, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.CARBON, 6), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 7), new ItemStack(Material.DIAMOND_HOE), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 7),
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 14), new SlimefunItemStack(SlimefunItems.CARBON, 6), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 14)})
                .register(plugin);
    }
}