package ne.fnfal113.fnamplifications.powergenerators;

import ne.fnfal113.fnamplifications.powergenerators.implementation.CustomSolarGen;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import ne.fnfal113.fnamplifications.items.FNAmpItems;

public class FNSolarGenerators {

    public static void setup(SlimefunAddon instance) {
        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER1, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR, SlimefunItems.SOLAR_GENERATOR, SlimefunItems.SOLAR_GENERATOR,
                SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.MEDIUM_CAPACITOR,
                SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.SOLAR_GENERATOR, SlimefunItems.MEDIUM_CAPACITOR},
                256, 0, 4096).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER2, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_2, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.SOLAR_GENERATOR_2,
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BIG_CAPACITOR,
                SlimefunItems.MEDIUM_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.MEDIUM_CAPACITOR},
                512, 0, 6144).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER3, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_3, FNAmpItems.FN_FAL_GENERATOR_TIER2, SlimefunItems.SOLAR_GENERATOR_3,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.POWER_CRYSTAL, FNAmpItems.FN_FAL_GENERATOR_TIER1,
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.BIG_CAPACITOR, SlimefunItems.BIG_CAPACITOR},
                768, 0, 8192).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER4, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.DURALUMIN_INGOT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.DURALUMIN_INGOT,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.LARGE_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1},
                1024, 0, 10240).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER5, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.BIG_CAPACITOR, SlimefunItems.SOLAR_GENERATOR_4,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, FNAmpItems.FN_FAL_GENERATOR_TIER2, FNAmpItems.FN_FAL_GENERATOR_TIER1,
                SlimefunItems.STEEL_INGOT, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.STEEL_INGOT},
                1282, 0, 12288).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER6, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_3, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.SOLAR_GENERATOR_3,
                SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.DAMASCUS_STEEL_INGOT,
                FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.LARGE_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER3},
                1538, 0, 14336).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER7, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.LARGE_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.LARGE_CAPACITOR,
                SlimefunItems.SOLAR_GENERATOR_3, FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.SOLAR_GENERATOR_3},
                1794, 0, 16384).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER8, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REINFORCED_ALLOY_INGOT,
                FNAmpItems.FN_FAL_GENERATOR_TIER4, SlimefunItems.LARGE_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER4},
                2048, 0, 20480).register(instance);
    }
}
