package ne.fnfal113.fnamplifications.tools.implementation;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import ne.fnfal113.fnamplifications.tools.AutoLadder;
import ne.fnfal113.fnamplifications.tools.FnHoe;
import ne.fnfal113.fnamplifications.tools.FnHoeAutoPlant;
import ne.fnfal113.fnamplifications.tools.BlockRotator;
import ne.fnfal113.fnamplifications.tools.OrientPearl;
import ne.fnfal113.fnamplifications.tools.ThrowableTorch;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterTools {

    public static void setup(SlimefunAddon instance){
        new FnHoe(FNAmpItems.FN_MISC, FNAmpItems.FN_HOE_5X5, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.CARBON, 3), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4), new ItemStack(Material.DIAMOND_HOE), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),
                new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 6), new SlimefunItemStack(SlimefunItems.CARBON, 4), new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 6)})
                .register(instance);

        new FnHoeAutoPlant(FNAmpItems.FN_MISC, FNAmpItems.FN_HOE_5X5_AUTO_PLANT, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, null, SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4), FNAmpItems.FN_HOE_5X5, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4),
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 8), null, new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 8)})
                .register(instance);

        new BlockRotator(FNAmpItems.FN_MISC, FNAmpItems.FN_BLOCK_ROTATOR, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.CARBON, new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 3), SlimefunItems.CARBON,
                SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.COMPASS), SlimefunItems.ELECTRO_MAGNET,
                new SlimefunItemStack(SlimefunItems.DURALUMIN_INGOT, 2), new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 3), new SlimefunItemStack(SlimefunItems.DURALUMIN_INGOT, 2)})
                .register(instance);

        new AutoLadder(FNAmpItems.FN_MISC, FNAmpItems.FN_AUTO_LADDER, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                null, new ItemStack(Material.LEAD, 2), null,
                new ItemStack(Material.LEAD, 2), new ItemStack(Material.LADDER, 8), new ItemStack(Material.LEAD, 2),
                null, new ItemStack(Material.LEAD, 2), null})
                .register(instance);

        new OrientPearl(FNAmpItems.FN_MISC, FNAmpItems.FN_ORIENT_PEARL, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                null, new ItemStack(Material.BONE), null,
                new ItemStack(Material.BONE), new ItemStack(Material.ENDER_PEARL, 1), new ItemStack(Material.BONE),
                null, new ItemStack(Material.BONE), null})
                .register(instance);

        new ThrowableTorch(FNAmpItems.FN_MISC, FNAmpItems.FN_THROWABLE_TORCH, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                null, new ItemStack(Material.BLAZE_POWDER), null,
                new ItemStack(Material.BLAZE_POWDER), new ItemStack(Material.TORCH, 1), new ItemStack(Material.BLAZE_POWDER),
                null, new ItemStack(Material.BLAZE_POWDER), null})
                .register(instance);

    }

}
