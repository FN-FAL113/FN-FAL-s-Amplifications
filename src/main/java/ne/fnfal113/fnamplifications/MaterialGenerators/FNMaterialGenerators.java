package ne.fnfal113.fnamplifications.MaterialGenerators;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.MaterialGenerators.Methods.CustomBrokenGenerator;
import ne.fnfal113.fnamplifications.MaterialGenerators.Methods.CustomGeneratorMultiblock;
import ne.fnfal113.fnamplifications.MaterialGenerators.Methods.CustomMaterialGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FNMaterialGenerators {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();
    static ReturnConfValue value = new ReturnConfValue();

    public static void setup() {

        new CustomGeneratorMultiblock(FNAmpItems.MATERIAL_GENERATORS, FNAmpItems.FMG_GENERATOR_MULTIBLOCK).register(plugin);


        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_CLAY_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        SlimefunItems.CARBON, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.CARBON,
                        new ItemStack(Material.BOWL), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BOWL),
                        SlimefunItems.CARBON, SlimefunItems.GOLD_PAN, SlimefunItems.CARBON
                }).register(plugin);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_CLAY,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.GOLD_PAN, new ItemStack(Material.CLAY, 16), SlimefunItems.GOLD_PAN,
                        FNAmpItems.FMG_GENERATOR_CLAY_BROKEN, new ItemStack(Material.DIAMOND_PICKAXE), FNAmpItems.FMG_GENERATOR_CLAY_BROKEN,
                        new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.FURNACE), new ItemStack(Material.DIAMOND_PICKAXE)
                })
                .setItem(Material.CLAY)
                .setRate(value.clayTickrate())
                .register(plugin);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_WARPED_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        SlimefunItems.CARBONADO, new ItemStack(Material.DIAMOND_SHOVEL), SlimefunItems.CARBONADO,
                        new ItemStack(Material.NETHERITE_PICKAXE), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.NETHERITE_PICKAXE),
                        SlimefunItems.CARBONADO, SlimefunItems.NETHER_GOLD_PAN, SlimefunItems.CARBONADO
                }).register(plugin);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_WARPED1,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.NETHERITE_SHOVEL), new ItemStack(Material.WARPED_NYLIUM, 32), new ItemStack(Material.NETHERITE_SHOVEL),
                        FNAmpItems.FMG_GENERATOR_WARPED_BROKEN, new ItemStack(Material.NETHERITE_PICKAXE), FNAmpItems.FMG_GENERATOR_WARPED_BROKEN,
                        FNAmpItems.FMG_GENERATOR_WARPED_BROKEN, new ItemStack(Material.BLAST_FURNACE), FNAmpItems.FMG_GENERATOR_WARPED_BROKEN
                })
                .setItem(Material.WARPED_NYLIUM)
                .setRate(value.warpedTickrate())
                .register(plugin);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_TERRACOTTA_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        SlimefunItems.FERROSILICON, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.FERROSILICON,
                        new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.BUCKET), new ItemStack(Material.DIAMOND_SHOVEL),
                        SlimefunItems.FERROSILICON, new ItemStack(Material.COAL, 32), SlimefunItems.FERROSILICON
                }).register(plugin);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_TERRACOTTA,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.DAMASCUS_STEEL_INGOT, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.DAMASCUS_STEEL_INGOT,
                        FNAmpItems.FMG_GENERATOR_FNFAL_CLAY, new ItemStack(Material.TERRACOTTA, 32), FNAmpItems.FMG_GENERATOR_FNFAL_CLAY,
                        FNAmpItems.FMG_GENERATOR_TERRACOTTA_BROKEN, new ItemStack(Material.BLAST_FURNACE), FNAmpItems.FMG_GENERATOR_TERRACOTTA_BROKEN
                })
                .setItem(Material.TERRACOTTA)
                .setRate(value.terracottaTickrate())
                .register(plugin);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_BONE_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        new ItemStack(Material.BONE_MEAL, 32), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.BONE_MEAL, 32),
                        new ItemStack(Material.BONE_BLOCK, 6), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BONE_BLOCK, 6),
                        SlimefunItems.BLISTERING_INGOT_2, new ItemStack(Material.COAL, 32), SlimefunItems.BLISTERING_INGOT_2
                }).register(plugin);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_BONE,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.BLISTERING_INGOT_3, FNAmpItems.FMG_GENERATOR_FNFAL_CLAY, SlimefunItems.BLISTERING_INGOT_3,
                        FNAmpItems.FMG_GENERATOR_BONE_BROKEN, new ItemStack(Material.BONE_BLOCK, 32), FNAmpItems.FMG_GENERATOR_BONE_BROKEN,
                        SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.PROGRAMMABLE_ANDROID
                })
                .setItem(Material.BONE)
                .setRate(value.boneTickrate())
                .register(plugin);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_DIAMOND_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        new ItemStack(Material.DIAMOND, 16), new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.DIAMOND, 16),
                        new ItemStack(Material.DIAMOND_BLOCK), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.DIAMOND_BLOCK),
                        SlimefunItems.SYNTHETIC_DIAMOND, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.SYNTHETIC_DIAMOND
                }).register(plugin);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_DIAMOND,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.PROGRAMMABLE_ANDROID_MINER, FNAmpItems.FMG_GENERATOR_DIAMOND_BROKEN, SlimefunItems.PROGRAMMABLE_ANDROID_MINER,
                        FNAmpItems.FMG_GENERATOR_DIAMOND_BROKEN, new ItemStack(Material.DIAMOND_BLOCK, 48), FNAmpItems.FMG_GENERATOR_DIAMOND_BROKEN,
                        SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.PROGRAMMABLE_ANDROID
                })
                .setItem(Material.DIAMOND)
                .setRate(value.diamondTickrate())
                .register(plugin);;

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_EMERALD_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        new ItemStack(Material.EMERALD, 32), new ItemStack(Material.EMERALD_ORE, 6), new ItemStack(Material.EMERALD, 32),
                        new ItemStack(Material.EMERALD_BLOCK), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.EMERALD_BLOCK),
                        SlimefunItems.SYNTHETIC_EMERALD, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.SYNTHETIC_EMERALD
                }).register(plugin);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_EMERALD,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.PROGRAMMABLE_ANDROID_MINER, FNAmpItems.FMG_GENERATOR_FNFAL_DIAMOND, SlimefunItems.PROGRAMMABLE_ANDROID_MINER,
                        FNAmpItems.FMG_GENERATOR_EMERALD_BROKEN, new ItemStack(Material.EMERALD_BLOCK, 48), FNAmpItems.FMG_GENERATOR_EMERALD_BROKEN,
                        SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.PROGRAMMABLE_ANDROID
                })
                .setItem(Material.EMERALD)
                .setRate(value.emeraldTickrate())
                .register(plugin);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_DIRT_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        new ItemStack(Material.DIRT, 16), new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.DIRT, 16),
                        new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.IRON_SHOVEL),
                        SlimefunItems.SOLDER_INGOT, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.SOLDER_INGOT
                }).register(plugin);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_DIRT,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.GOLDEN_SHOVEL), FNAmpItems.FMG_GENERATOR_FNFAL_CLAY, new ItemStack(Material.DIAMOND_SHOVEL),
                        FNAmpItems.FMG_GENERATOR_DIRT_BROKEN, new ItemStack(Material.DIRT, 64), FNAmpItems.FMG_GENERATOR_DIRT_BROKEN,
                        SlimefunItems.MAGNESIUM_INGOT, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.MAGNESIUM_INGOT
                })
                .setItem(Material.DIRT)
                .setRate(value.dirtTickrate())
                .register(plugin);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_HONEYCOMB_BROKEN,
                RecipeType.GRIND_STONE,
                new ItemStack[] {
                        new ItemStack(Material.HONEYCOMB, 36), new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.HONEYCOMB, 36),
                        new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.IRON_SHOVEL),
                        new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 16), new ItemStack(Material.HONEY_BLOCK), new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 16)
                }).register(plugin);


        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_FNFAL_HONEYCOMB,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.HONEYCOMB_BLOCK, 16), FNAmpItems.FMG_GENERATOR_FNFAL_DIRT, new ItemStack(Material.HONEYCOMB_BLOCK, 16),
                        FNAmpItems.FMG_GENERATOR_HONEYCOMB_BROKEN, new ItemStack(Material.HONEYCOMB, 32), FNAmpItems.FMG_GENERATOR_HONEYCOMB_BROKEN,
                        new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 12), new ItemStack(Material.BLAST_FURNACE), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 12)
                })
                .setItem(Material.HONEYCOMB)
                .setRate(value.honeycombTickrate())
                .register(plugin);

    }

}
