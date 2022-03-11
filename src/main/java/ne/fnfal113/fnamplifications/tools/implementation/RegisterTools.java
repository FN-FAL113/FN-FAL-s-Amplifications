package ne.fnfal113.fnamplifications.tools.implementation;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import ne.fnfal113.fnamplifications.tools.FnHoe;
import ne.fnfal113.fnamplifications.tools.FnHoeAutoPlant;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterTools {

    public static void setup(SlimefunAddon instance){
        new FnHoe(FNAmpItems.FN_MISC, FNAmpItems.FN_HOE_5X5, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.CARBON, 4), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3), new ItemStack(Material.DIAMOND_HOE), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6), new SlimefunItemStack(SlimefunItems.CARBON, 4), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6)})
                .register(instance);

        new FnHoeAutoPlant(FNAmpItems.FN_MISC, FNAmpItems.FN_HOE_5X5_AUTO_PLANT, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.CARBON, 6), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 7), new ItemStack(Material.DIAMOND_HOE), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 7),
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 14), new SlimefunItemStack(SlimefunItems.CARBON, 6), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 14)})
                .register(instance);
    }

}
