package ne.fnfal113.fnamplifications.powergenerators.implementation;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import ne.fnfal113.fnamplifications.config.ReturnConfValue;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import ne.fnfal113.fnamplifications.powergenerators.implementation.CustomPowerGen;
import ne.fnfal113.fnamplifications.powergenerators.implementation.CustomSolarGen;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import ne.fnfal113.fnamplifications.items.FNAmpItems;

public class RegisterPowerGenerators {

    private static final ReturnConfValue value = new ReturnConfValue();

    public static ItemStack LITEX_ITEMSTACK_TIN = SlimefunItems.TIN_INGOT;
    public static ItemStack LITEX_ITEMSTACK_SOLDER = SlimefunItems.SOLDER_INGOT;
    public static ItemStack LITEX_ITEMSTACK_BRONZE = SlimefunItems.BRONZE_INGOT;
    public static ItemStack LITEX_ITEMSTACK_DURALUMIN = SlimefunItems.DURALUMIN_INGOT;
    public static ItemStack LITEX_ITEMSTACK_BRASS = SlimefunItems.ALUMINUM_BRASS_INGOT;
    public static ItemStack LITEX_ITEMSTACK_CORINTHIAN = SlimefunItems.CORINTHIAN_BRONZE_INGOT;
    public static ItemStack LITEX_ITEMSTACK_STEEL = SlimefunItems.STEEL_INGOT;
    public static ItemStack LITEX_ITEMSTACK_DAMASCUS = SlimefunItems.DAMASCUS_STEEL_INGOT;
    public static ItemStack LITEX_ITEMSTACK_HARDENED = SlimefunItems.HARDENED_METAL_INGOT;
    public static ItemStack LITEX_ITEMSTACK_REINFORCED = SlimefunItems.REINFORCED_ALLOY_INGOT;

    static {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("LiteXpansion") && value.liteXpansionRecipe()) {
            SlimefunItem iridium = SlimefunItem.getById("IRIDIUM");
            SlimefunItem uu_matter = SlimefunItem.getById("UU_MATTER");
            if (iridium != null && uu_matter != null && !iridium.isDisabled() && !uu_matter.isDisabled()) {
                LITEX_ITEMSTACK_TIN = iridium.getItem().clone();
                LITEX_ITEMSTACK_SOLDER = iridium.getItem().clone();
                LITEX_ITEMSTACK_BRONZE = iridium.getItem().clone();
                LITEX_ITEMSTACK_DURALUMIN = iridium.getItem().clone();
                LITEX_ITEMSTACK_BRASS = iridium.getItem().clone();
                LITEX_ITEMSTACK_CORINTHIAN = iridium.getItem().clone();
                LITEX_ITEMSTACK_STEEL = iridium.getItem().clone();
                LITEX_ITEMSTACK_HARDENED = iridium.getItem().clone();
                LITEX_ITEMSTACK_DAMASCUS = iridium.getItem().clone();
                LITEX_ITEMSTACK_REINFORCED = iridium.getItem().clone();
            }
        }
    }

    public static void setup(SlimefunAddon instance) {
        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, new CustomItemStack(LITEX_ITEMSTACK_TIN, 3), FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, FNAmpItems.ALUMINUM_PLATING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }, 236, 138, 20, 100000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R1, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_SOLDER, 3), FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, FNAmpItems.ALUMINUM_PLATING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }, 472, 276, 40, 200000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R2, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_BRONZE, 3), FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, FNAmpItems.ALUMINUM_PLATING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }, 944, 552, 60, 300000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R4, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R3, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_DURALUMIN, 6), FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, FNAmpItems.GOLD_PLATING, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }, 1888, 1104, 120, 400000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R5, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R4, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_BRASS, 6), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, FNAmpItems.GOLD_PLATING, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }, 3064, 1784, 480, 500000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R6, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R5, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_CORINTHIAN, 8), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER2, FNAmpItems.GOLD_PLATING, SlimefunItems.REINFORCED_ALLOY_INGOT
        }, 5224, 3128, 480, 600000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R7, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R6, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_STEEL, 13), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER3, FNAmpItems.BRASS_PLATING, SlimefunItems.BLISTERING_INGOT
        }, 7128, 5142, 960, 700000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R8, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R7, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_DAMASCUS, 13), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                SlimefunItems.BLISTERING_INGOT_2, FNAmpItems.FN_FAL_GENERATOR_TIER4, SlimefunItems.BLISTERING_INGOT_2
        }, 9420, 6752, 1200, 800000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R9, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R8, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_HARDENED, 16), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                SlimefunItems.REINFORCED_ALLOY_INGOT, FNAmpItems.FN_FAL_GENERATOR_TIER5, SlimefunItems.REINFORCED_ALLOY_INGOT
        }, 11384, 7431, 1440, 900000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R10, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R9, FNAmpItems.GEAR_PART,
                SlimefunItems.ENERGIZED_CAPACITOR, new CustomItemStack(LITEX_ITEMSTACK_HARDENED, 16), SlimefunItems.ENERGIZED_CAPACITOR,
                SlimefunItems.URANIUM, FNAmpItems.FN_FAL_GENERATOR_TIER6, SlimefunItems.URANIUM
        }, 13692, 8128, 1520, 1000000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R11, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R10, FNAmpItems.GEAR_PART,
                SlimefunItems.ENERGIZED_CAPACITOR, new CustomItemStack(LITEX_ITEMSTACK_REINFORCED, 20), SlimefunItems.ENERGIZED_CAPACITOR,
                SlimefunItems.NEPTUNIUM, FNAmpItems.FN_FAL_GENERATOR_TIER7, SlimefunItems.NEPTUNIUM
        }, 15984, 9462, 1640, 1500000).register(instance);

        new CustomPowerGen(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R12, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R11, FNAmpItems.GEAR_PART,
                SlimefunItems.ENERGIZED_CAPACITOR, new CustomItemStack(LITEX_ITEMSTACK_REINFORCED, 20), SlimefunItems.ENERGIZED_CAPACITOR,
                FNAmpItems.REINFORCED_CASING, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.BOOSTED_URANIUM
        }, 17421, 10128, 1780, 2000000).register(instance);

        // Solar Generators
        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER1, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_2, SlimefunItems.SOLAR_GENERATOR, SlimefunItems.SOLAR_GENERATOR_2,
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BIG_CAPACITOR,
                SlimefunItems.SOLAR_GENERATOR_2, SlimefunItems.SOLAR_GENERATOR_3, SlimefunItems.SOLAR_GENERATOR_2},
                256, 0, 4096).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER2, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.MEDIUM_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.MEDIUM_CAPACITOR,
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BIG_CAPACITOR,
                SlimefunItems.BIG_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.BIG_CAPACITOR},
                512, 0, 6144).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER3, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.BIG_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.BIG_CAPACITOR,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.POWER_CRYSTAL, FNAmpItems.FN_FAL_GENERATOR_TIER1,
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.BIG_CAPACITOR, SlimefunItems.BIG_CAPACITOR},
                768, 0, 8192).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER4, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SOLAR_GENERATOR_2, FNAmpItems.FN_FAL_GENERATOR_TIER3, SlimefunItems.SOLAR_GENERATOR_2,
                SlimefunItems.DURALUMIN_INGOT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.DURALUMIN_INGOT,
                SlimefunItems.BIG_CAPACITOR,  SlimefunItems.LARGE_CAPACITOR, SlimefunItems.BIG_CAPACITOR},
                1024, 0, 10240).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER5, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.BIG_CAPACITOR, SlimefunItems.BIG_CAPACITOR, SlimefunItems.BIG_CAPACITOR,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, FNAmpItems.FN_FAL_GENERATOR_TIER2, FNAmpItems.FN_FAL_GENERATOR_TIER1,
                SlimefunItems.STEEL_INGOT, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.STEEL_INGOT},
                1282, 0, 12288).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER6, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.STEEL_INGOT, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.STEEL_INGOT,
                SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.DAMASCUS_STEEL_INGOT,
                FNAmpItems.FN_FAL_GENERATOR_TIER2, FNAmpItems.FN_FAL_GENERATOR_TIER3, FNAmpItems.FN_FAL_GENERATOR_TIER2},
                1538, 0, 14336).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER7, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.HARDENED_METAL_INGOT, FNAmpItems.FN_FAL_GENERATOR_TIER5, SlimefunItems.HARDENED_METAL_INGOT,
                SlimefunItems.LARGE_CAPACITOR, SlimefunItems.POWER_CRYSTAL, SlimefunItems.LARGE_CAPACITOR,
                SlimefunItems.DAMASCUS_STEEL_INGOT, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.DAMASCUS_STEEL_INGOT},
                1794, 0, 16384).register(instance);

        new CustomSolarGen(FNAmpItems.SOLAR_GENERATORS, FNAmpItems.FN_FAL_GENERATOR_TIER8, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.BIG_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER6, SlimefunItems.BIG_CAPACITOR,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.LARGE_CAPACITOR, FNAmpItems.FN_FAL_GENERATOR_TIER1, SlimefunItems.LARGE_CAPACITOR},
                2048, 0, 20480).register(instance);
    }

}