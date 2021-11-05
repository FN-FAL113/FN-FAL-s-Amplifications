package ne.fnfal113.fnamplifications.PowerGenerators;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;

public class FNSolarGenerators {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();
    static ReturnConfValue value = new ReturnConfValue();

    public static void setup() {
        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier1Power(), value.tier1PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER1, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_3, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.SOLAR_GENERATOR_3,
                SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.MEDIUM_CAPACITOR,
                SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.MEDIUM_CAPACITOR},
        value.tier1Buffer()).register(plugin);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier2Power(), value.tier2PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER2, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BIG_CAPACITOR,
                SlimefunItems.BIG_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.BIG_CAPACITOR},
        value.tier2Buffer()).register(plugin);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier3Power(), value.tier3PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER3, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER2, SlimefunItems.SOLAR_GENERATOR_4,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.POWER_CRYSTAL, FNAmpItems.FN_FAL_GENERATOR_TIER1,
                SlimefunItems.SOLAR_GENERATOR_3, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.SOLAR_GENERATOR_3},
        value.tier3Buffer()).register(plugin);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier4Power(), value.tier4PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER4, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BIG_CAPACITOR,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.LARGE_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1},
        value.tier4Buffer()).register(plugin);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier5Power(), value.tier5PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER5, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER4, SlimefunItems.SOLAR_GENERATOR_4,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, FNAmpItems.FN_FAL_GENERATOR_TIER2, FNAmpItems.FN_FAL_GENERATOR_TIER1,
                SlimefunItems.LARGE_CAPACITOR, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.LARGE_CAPACITOR},
        value.tier5Buffer()).register(plugin);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier6Power(), value.tier6PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER6, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER5, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.BOOSTED_URANIUM, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BOOSTED_URANIUM,
                FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.LARGE_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER3},
        value.tier6Buffer()).register(plugin);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier7Power(), value.tier7PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER7, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER6, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.LARGE_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.LARGE_CAPACITOR,
                SlimefunItems.CARBONADO_EDGED_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.CARBONADO_EDGED_CAPACITOR},
        value.tier7Buffer()).register(plugin);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, value.tier8Power(), value.tier8PowerNight(), FNAmpItems.FN_FAL_GENERATOR_TIER8, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.FN_FAL_GENERATOR_TIER7, SlimefunItems.SOLAR_GENERATOR_4,
                SlimefunItems.BOOSTED_URANIUM, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BOOSTED_URANIUM,
                SlimefunItems.CARBONADO_EDGED_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.CARBONADO_EDGED_CAPACITOR},
        value.tier8Buffer()).register(plugin);
    }
}
