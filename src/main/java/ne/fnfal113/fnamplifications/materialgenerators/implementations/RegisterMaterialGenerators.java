package ne.fnfal113.fnamplifications.materialgenerators.implementations;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import org.bukkit.inventory.meta.ItemMeta;

public class RegisterMaterialGenerators {

    private static final ItemStack SHOVEL = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
            new ItemStack(Material.NETHERITE_SHOVEL) : new ItemStack(Material.DIAMOND_SHOVEL);
    private static final ItemStack PICKAXE = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
            new ItemStack(Material.NETHERITE_PICKAXE) : new ItemStack(Material.DIAMOND_PICKAXE);

    public static void setup(SlimefunAddon instance) {
        new CustomGeneratorMultiblock(FNAmpItems.MATERIAL_GENERATORS, FNAmpItems.FMG_GENERATOR_MULTIBLOCK)
                .register(instance);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_CLAY_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        SlimefunItems.CARBON, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.CARBON,
                        new ItemStack(Material.BOWL), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BOWL),
                        SlimefunItems.CARBON, SlimefunItems.GOLD_PAN, SlimefunItems.CARBON})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_CLAY,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.GOLD_PAN, new ItemStack(Material.CLAY), SlimefunItems.GOLD_PAN,
                        new ItemStack(Material.DIAMOND_PICKAXE), FNAmpItems.FMG_GENERATOR_CLAY_BROKEN, new ItemStack(Material.DIAMOND_PICKAXE),
                        new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.FURNACE), new ItemStack(Material.DIAMOND_PICKAXE)}, 32)
                .setItem(Material.CLAY)
                .setMaterialName("&3&lClay")
                .register(instance);

        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)) {
            new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                    FNAmpItems.FMG_GENERATOR_WARPED_BROKEN,
                    FnAssemblyStation.RECIPE_TYPE,
                    new ItemStack[]{
                            SlimefunItems.CARBONADO, new ItemStack(Material.DIAMOND_SHOVEL), SlimefunItems.CARBONADO,
                            PICKAXE.clone(), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.NETHERITE_PICKAXE),
                            SlimefunItems.CARBONADO, SlimefunItems.NETHER_GOLD_PAN, SlimefunItems.CARBONADO})
                    .register(instance);

            new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                    FNAmpItems.FMG_GENERATOR_WARPED,
                    RecipeType.ENHANCED_CRAFTING_TABLE,
                    new ItemStack[]{
                            SHOVEL.clone(), new ItemStack(Material.WARPED_NYLIUM), SHOVEL.clone(),
                            FNAmpItems.FMG_GENERATOR_WARPED_BROKEN, FNAmpItems.FMG_GENERATOR_WARPED_BROKEN, FNAmpItems.FMG_GENERATOR_WARPED_BROKEN,
                            SHOVEL.clone(), new ItemStack(Material.BLAST_FURNACE), SHOVEL.clone()}, 48)
                    .setItem(Material.WARPED_NYLIUM)
                    .setMaterialName("&4&cWarped Nylium")
                    .register(instance);
        }

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_TERRACOTTA_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        SlimefunItems.FERROSILICON, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.FERROSILICON,
                        new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.BUCKET), new ItemStack(Material.DIAMOND_SHOVEL),
                        SlimefunItems.FERROSILICON, new ItemStack(Material.COAL), SlimefunItems.FERROSILICON})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_TERRACOTTA,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.DAMASCUS_STEEL_INGOT, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.DAMASCUS_STEEL_INGOT,
                        FNAmpItems.FMG_GENERATOR_CLAY, FNAmpItems.FMG_GENERATOR_TERRACOTTA_BROKEN, FNAmpItems.FMG_GENERATOR_CLAY,
                        SlimefunItems.DAMASCUS_STEEL_INGOT, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.DAMASCUS_STEEL_INGOT}, 84)
                .setItem(Material.TERRACOTTA)
                .setMaterialName("&4&lTerracotta")
                .register(instance);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_BONE_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.BONE_MEAL),
                        new ItemStack(Material.BONE_BLOCK), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BONE_BLOCK),
                        SlimefunItems.BLISTERING_INGOT_2, new ItemStack(Material.COAL), SlimefunItems.BLISTERING_INGOT_2})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_BONE,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.BLISTERING_INGOT_3, FNAmpItems.FMG_GENERATOR_CLAY, SlimefunItems.BLISTERING_INGOT_3,
                        new ItemStack(Material.BONE), FNAmpItems.FMG_GENERATOR_BONE_BROKEN, new ItemStack(Material.BONE),
                        SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.PROGRAMMABLE_ANDROID}, 256)
                .setItem(Material.BONE)
                .setMaterialName("&f&lBone")
                .register(instance);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_DIAMOND_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        SlimefunItems.SYNTHETIC_DIAMOND, PICKAXE.clone(), SlimefunItems.SYNTHETIC_DIAMOND,
                        new ItemStack(Material.DIAMOND_BLOCK), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.DIAMOND_BLOCK),
                        SlimefunItems.SYNTHETIC_DIAMOND, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.SYNTHETIC_DIAMOND})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_DIAMOND,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.PROGRAMMABLE_ANDROID_MINER, new ItemStack(Material.DIAMOND), SlimefunItems.PROGRAMMABLE_ANDROID_MINER,
                        new ItemStack(Material.DIAMOND), FNAmpItems.FMG_GENERATOR_DIAMOND_BROKEN, new ItemStack(Material.DIAMOND),
                        SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.PROGRAMMABLE_ANDROID}, 192)
                .setItem(Material.DIAMOND)
                .setMaterialName("&b&lDiamond")
                .register(instance);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_EMERALD_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        new ItemStack(Material.EMERALD), new ItemStack(Material.EMERALD_ORE), new ItemStack(Material.EMERALD),
                        new ItemStack(Material.EMERALD_BLOCK), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.EMERALD_BLOCK),
                        SlimefunItems.SYNTHETIC_EMERALD, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.SYNTHETIC_EMERALD})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_EMERALD,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        SlimefunItems.PROGRAMMABLE_ANDROID_MINER, new ItemStack(Material.EMERALD), SlimefunItems.PROGRAMMABLE_ANDROID_MINER,
                        new ItemStack(Material.EMERALD), FNAmpItems.FMG_GENERATOR_EMERALD_BROKEN, new ItemStack(Material.EMERALD),
                        SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.PROGRAMMABLE_ANDROID}, 218)
                .setItem(Material.EMERALD)
                .setMaterialName("&a&lEmerald")
                .register(instance);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_DIRT_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        new ItemStack(Material.DIRT), new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.DIRT),
                        new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.IRON_SHOVEL),
                        SlimefunItems.SOLDER_INGOT, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.SOLDER_INGOT})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_DIRT,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.GOLDEN_SHOVEL), new ItemStack(Material.DIRT), new ItemStack(Material.GOLDEN_SHOVEL),
                        new ItemStack(Material.DIRT), FNAmpItems.FMG_GENERATOR_DIRT_BROKEN, new ItemStack(Material.DIRT),
                        SlimefunItems.MAGNESIUM_INGOT, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.MAGNESIUM_INGOT}, 12)
                .setItem(Material.DIRT)
                .setMaterialName("&6&lDirt")
                .register(instance);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_HONEYCOMB_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        new ItemStack(Material.HONEYCOMB), new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.HONEYCOMB),
                        new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.IRON_SHOVEL),
                        SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.HONEY_BLOCK), SlimefunItems.REINFORCED_PLATE})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_HONEYCOMB,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.HONEYCOMB_BLOCK), FNAmpItems.FMG_GENERATOR_DIRT, new ItemStack(Material.HONEYCOMB_BLOCK),
                        new ItemStack(Material.HONEYCOMB), FNAmpItems.FMG_GENERATOR_HONEYCOMB_BROKEN, new ItemStack(Material.HONEYCOMB),
                        SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.REINFORCED_ALLOY_INGOT}, 48)
                .setItem(Material.HONEYCOMB)
                .setMaterialName("&6&lHoney Comb")
                .register(instance);

        new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_QUARTZ_BROKEN,
                FnAssemblyStation.RECIPE_TYPE,
                new ItemStack[]{
                        new ItemStack(Material.NETHERRACK), new ItemStack(Material.GOLDEN_SHOVEL), new ItemStack(Material.NETHERRACK),
                        new ItemStack(Material.IRON_SHOVEL), new ItemStack(Material.QUARTZ_BLOCK), new ItemStack(Material.IRON_SHOVEL),
                        SlimefunItems.STEEL_PLATE, new ItemStack(Material.NETHERRACK), SlimefunItems.STEEL_PLATE})
                .register(instance);

        new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                FNAmpItems.FMG_GENERATOR_QUARTZ,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.IRON_PICKAXE),
                        new ItemStack(Material.QUARTZ), FNAmpItems.FMG_GENERATOR_QUARTZ_BROKEN, new ItemStack(Material.QUARTZ),
                        SlimefunItems.DAMASCUS_STEEL_INGOT, new ItemStack(Material.BLAST_FURNACE), SlimefunItems.DAMASCUS_STEEL_INGOT}, 28)
                .setItem(Material.QUARTZ)
                .setMaterialName("&f&lQuartz")
                .register(instance);

        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)){
            ItemStack silkTool = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta meta = silkTool.getItemMeta();
            meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
            silkTool.setItemMeta(meta);

            new CustomBrokenGenerator(FNAmpItems.MATERIAL_GENERATORS,
                    FNAmpItems.FMG_GENERATOR_AMETHYST_BROKEN,
                    FnAssemblyStation.RECIPE_TYPE,
                    new ItemStack[]{
                            FNAmpItems.FMG_GENERATOR_QUARTZ, silkTool.clone(), FNAmpItems.FMG_GENERATOR_QUARTZ,
                            silkTool.clone(), new ItemStack(Material.AMETHYST_BLOCK), silkTool.clone(),
                            FNAmpItems.REINFORCED_CASING, FNAmpItems.FMG_GENERATOR_WARPED_BROKEN, FNAmpItems.REINFORCED_CASING})
                    .register(instance);

            new CustomMaterialGenerator(FNAmpItems.MATERIAL_GENERATORS,
                    FNAmpItems.FMG_GENERATOR_AMETHYST,
                    RecipeType.ENHANCED_CRAFTING_TABLE,
                    new ItemStack[]{
                            new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.DIAMOND_PICKAXE),
                            new ItemStack(Material.AMETHYST_CLUSTER), FNAmpItems.FMG_GENERATOR_AMETHYST_BROKEN, new ItemStack(Material.AMETHYST_CLUSTER),
                            new ItemStack(Material.AMETHYST_BLOCK), new ItemStack(Material.BLAST_FURNACE), new ItemStack(Material.AMETHYST_BLOCK)}, 160)
                    .setItem(Material.AMETHYST_CLUSTER)
                    .setMaterialName("&d&lAmethyst Cluster")
                    .register(instance);
        }

    }
}
