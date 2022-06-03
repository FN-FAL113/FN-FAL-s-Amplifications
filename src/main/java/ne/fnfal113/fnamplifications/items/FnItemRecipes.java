package ne.fnfal113.fnamplifications.items;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import ne.fnfal113.fnamplifications.config.ReturnConfValue;
import ne.fnfal113.fnamplifications.machines.ElectricMachineDowngrader;
import ne.fnfal113.fnamplifications.multiblocks.FnGemAltar;
import ne.fnfal113.fnamplifications.multiblocks.FnMagicAltar;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;

public class FnItemRecipes {

    private static final ReturnConfValue value = new ReturnConfValue();

    public static final ItemStack VERSIONED_ITEMSTACK_COPPER;
    public static final ItemStack VERSIONED_ITEMSTACK_COPPER_BATTERY;
    public static final ItemStack VERSIONED_ITEMSTACK_COPPER_BLOCK_INGOT;
    public static final ItemStack VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRON;
    public static final ItemStack VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRONBLOCK;
    public static final ItemStack VERSIONED_ITEMSTACK_CALCITE_IRONINGOT;
    public static final ItemStack VERSIONED_ITEMSTACK_ROD_BATTERY;
    public static final ItemStack VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON;

    static {
        if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17) && value.latestMcVersionRecipe()) {
            VERSIONED_ITEMSTACK_COPPER = new ItemStack(Material.COPPER_INGOT);
            VERSIONED_ITEMSTACK_COPPER_BATTERY = new ItemStack(Material.COPPER_INGOT);
            VERSIONED_ITEMSTACK_COPPER_BLOCK_INGOT = new ItemStack(Material.COPPER_BLOCK);
            VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRON = new ItemStack(Material.AMETHYST_SHARD);
            VERSIONED_ITEMSTACK_CALCITE_IRONINGOT = new ItemStack(Material.CALCITE);
            VERSIONED_ITEMSTACK_ROD_BATTERY = new ItemStack(Material.LIGHTNING_ROD);
            VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON = new ItemStack(Material.AMETHYST_CLUSTER);
            VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRONBLOCK = new ItemStack(Material.AMETHYST_SHARD);
        } else {
            VERSIONED_ITEMSTACK_COPPER = SlimefunItems.COPPER_INGOT;
            VERSIONED_ITEMSTACK_COPPER_BATTERY = SlimefunItems.BATTERY;
            VERSIONED_ITEMSTACK_COPPER_BLOCK_INGOT = SlimefunItems.COPPER_INGOT;
            VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRON = new ItemStack(Material.IRON_NUGGET);
            VERSIONED_ITEMSTACK_CALCITE_IRONINGOT = new ItemStack(Material.IRON_INGOT);
            VERSIONED_ITEMSTACK_ROD_BATTERY = SlimefunItems.BATTERY;
            VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON = new ItemStack(Material.IRON_INGOT);
            VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRONBLOCK = new ItemStack(Material.IRON_BLOCK);
        }
    }

    public static void setup(SlimefunAddon instance) {
        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.MACHINE_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.BRONZE_INGOT, SlimefunItems.GOLD_4K, SlimefunItems.BRONZE_INGOT,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.ADVANCED_CIRCUIT_BOARD,
                SlimefunItems.COPPER_WIRE, VERSIONED_ITEMSTACK_ROD_BATTERY, SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.COMPONENT_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                VERSIONED_ITEMSTACK_COPPER, new ItemStack(Material.GOLD_INGOT), VERSIONED_ITEMSTACK_COPPER,
                SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.MAGNET, SlimefunItems.BASIC_CIRCUIT_BOARD,
                SlimefunItems.COPPER_WIRE, VERSIONED_ITEMSTACK_ROD_BATTERY, SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.MOTOR_SWITCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.COPPER_WIRE, SlimefunItems.ALUMINUM_BRASS_INGOT, SlimefunItems.COPPER_WIRE,
                new ItemStack(Material.LEVER), new ItemStack(Material.REDSTONE), new ItemStack(Material.LEVER),
                SlimefunItems.COPPER_WIRE, VERSIONED_ITEMSTACK_ROD_BATTERY, SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.GEAR_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.COPPER_WIRE, SlimefunItems.CARGO_MOTOR, SlimefunItems.COPPER_WIRE,
                new ItemStack(Material.REDSTONE), SlimefunItems.CHAIN, new ItemStack(Material.REDSTONE),
                SlimefunItems.COPPER_WIRE, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.THREAD_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.OAK_PLANKS), new ItemStack(Material.STICK), new ItemStack(Material.OAK_PLANKS),
                SlimefunItems.COPPER_WIRE, new ItemStack(Material.STICK), SlimefunItems.COPPER_WIRE,
                SlimefunItems.COPPER_WIRE, new ItemStack(Material.STICK), SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.COMPRESSOR_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), new ItemStack(Material.NETHER_BRICKS), new ItemStack(Material.PISTON),
                FNAmpItems.THREAD_PART, FNAmpItems.GEAR_PART, FNAmpItems.THREAD_PART,
                SlimefunItems.COPPER_WIRE, new ItemStack(Material.NETHER_BRICKS), SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.CONDENSER_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), FNAmpItems.COMPONENT_PART, new ItemStack(Material.PISTON),
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.GEAR_PART, FNAmpItems.COMPRESSOR_PART,
                SlimefunItems.COPPER_WIRE, FNAmpItems.COMPONENT_PART, SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.RECYCLER_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), FNAmpItems.COMPONENT_PART, new ItemStack(Material.PISTON),
                null, FNAmpItems.GEAR_PART, null,
                FNAmpItems.CONDENSER_PART, FNAmpItems.COMPRESSOR_PART, FNAmpItems.CONDENSER_PART})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.DOWNGRADER_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), FNAmpItems.THREAD_PART, new ItemStack(Material.PISTON),
                FNAmpItems.RECYCLER_PART, FNAmpItems.GEAR_PART, FNAmpItems.RECYCLER_PART,
                null, FNAmpItems.COMPRESSOR_PART, null})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.FUNNEL_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.LEAD_INGOT, VERSIONED_ITEMSTACK_CALCITE_IRONINGOT, SlimefunItems.LEAD_INGOT,
                FNAmpItems.THREAD_PART, new ItemStack(Material.BARREL), FNAmpItems.THREAD_PART,
                SlimefunItems.COPPER_WIRE, VERSIONED_ITEMSTACK_CALCITE_IRONINGOT, SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.POWER_COMPONENT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.ELECTRIC_MOTOR, VERSIONED_ITEMSTACK_COPPER_BATTERY, SlimefunItems.ELECTRIC_MOTOR,
                VERSIONED_ITEMSTACK_COPPER, SlimefunItems.POWER_CRYSTAL, VERSIONED_ITEMSTACK_COPPER,
                SlimefunItems.COPPER_WIRE, VERSIONED_ITEMSTACK_ROD_BATTERY, SlimefunItems.COPPER_WIRE})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.BRASS_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                VERSIONED_ITEMSTACK_COPPER, VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRON, VERSIONED_ITEMSTACK_COPPER,
                SlimefunItems.BRASS_INGOT, VERSIONED_ITEMSTACK_COPPER_BLOCK_INGOT, SlimefunItems.BRASS_INGOT,
                SlimefunItems.BRONZE_INGOT, VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRON, SlimefunItems.BRONZE_INGOT})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.REINFORCED_CASING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.REINFORCED_PLATE, VERSIONED_ITEMSTACK_COPPER, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.IRON_BLOCK), SlimefunItems.BRASS_INGOT,
                SlimefunItems.BRONZE_INGOT, VERSIONED_ITEMSTACK_COPPER, SlimefunItems.BRONZE_INGOT})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.DIAMOND_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.COBALT_INGOT, VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON, SlimefunItems.COBALT_INGOT,
                new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND_BLOCK), new ItemStack(Material.DIAMOND),
                SlimefunItems.BRONZE_INGOT, VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON, SlimefunItems.BRONZE_INGOT})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.ALUMINUM_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.DURALUMIN_INGOT, VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON, SlimefunItems.DURALUMIN_INGOT,
                SlimefunItems.ALUMINUM_INGOT, VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON, SlimefunItems.ALUMINUM_INGOT,
                SlimefunItems.BRONZE_INGOT, VERSIONED_ITEMSTACK_AMETHYSTCLUSTER_IRON, SlimefunItems.BRONZE_INGOT})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.GOLD_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.GOLD_8K, new ItemStack(Material.GOLD_NUGGET), SlimefunItems.GOLD_8K,
                SlimefunItems.ALUMINUM_INGOT, new ItemStack(Material.GOLD_BLOCK), SlimefunItems.ALUMINUM_INGOT,
                SlimefunItems.BRONZE_INGOT, new ItemStack(Material.GOLD_NUGGET), SlimefunItems.BRONZE_INGOT})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.BASIC_MACHINE_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                FNAmpItems.GEAR_PART, VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRONBLOCK, FNAmpItems.GEAR_PART,
                FNAmpItems.BRASS_PLATING, FNAmpItems.MACHINE_PART, FNAmpItems.BRASS_PLATING,
                FNAmpItems.COMPONENT_PART, VERSIONED_ITEMSTACK_AMETHYSTSHARD_IRONBLOCK, FNAmpItems.COMPONENT_PART})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.HIGHTECH_MACHINE_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                FNAmpItems.GEAR_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.GEAR_PART,
                FNAmpItems.REINFORCED_CASING, FNAmpItems.MACHINE_PART, FNAmpItems.BRASS_PLATING,
                FNAmpItems.COMPONENT_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.COMPONENT_PART})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.FN_METAL_SCRAPS, ElectricMachineDowngrader.RECIPE_TYPE, new ItemStack[]{
                null, null, null,
                null, null, null,
                null, null, null})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.UNBIND_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1), new ItemStack(Material.BLAZE_POWDER, 1), new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 2), null, new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 2),
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.ROTTEN_FLESH, 1), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.SPIRAL_FIRE_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 2), new ItemStack(Material.GUNPOWDER, 3), new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1),
                null, new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), null,
                new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 2), new ItemStack(Material.FLINT_AND_STEEL, 1), new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.SPIRIT_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2), new ItemStack(Material.ROTTEN_FLESH, 2), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2),
                new ItemStack(Material.BLAZE_POWDER, 1), new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 1), new ItemStack(Material.BLAZE_POWDER, 1),
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2), new ItemStack(Material.WITHER_SKELETON_SKULL, 3), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.PESTILENCE_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.URANIUM, 1), new ItemStack(Material.NETHER_WART, 6), new SlimefunItemStack(SlimefunItems.URANIUM, 1),
                new ItemStack(Material.ROTTEN_FLESH, 4), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.ROTTEN_FLESH, 4),
                new SlimefunItemStack(SlimefunItems.URANIUM, 1), new ItemStack(Material.QUARTZ, 3), new SlimefunItemStack(SlimefunItems.URANIUM, 1)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.SPARKLING_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.SILVER_INGOT, 2), null, new SlimefunItemStack(SlimefunItems.SILVER_INGOT, 2),
                new ItemStack(Material.BLAZE_ROD, 2), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.BLAZE_ROD, 2),
                new SlimefunItemStack(SlimefunItems.SILVER_INGOT, 2), null, new SlimefunItemStack(SlimefunItems.SILVER_INGOT, 2)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.CLOUD_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 2), new ItemStack(Material.BLAZE_POWDER, 3), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 2),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 2), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 1), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 2),
                new SlimefunItemStack(SlimefunItems.MAGNESIUM_DUST, 4), new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 1), new SlimefunItemStack(SlimefunItems.MAGNESIUM_DUST, 4)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.ICE_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new ItemStack(Material.ICE, 4), null, new ItemStack(Material.ICE, 4),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 2), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 2),
                new ItemStack(Material.ICE, 4), null, new ItemStack(Material.ICE, 4)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.POWER_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new ItemStack(Material.REDSTONE, 1), new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1), new ItemStack(Material.REDSTONE, 1),
                new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1),
                new ItemStack(Material.NETHER_STAR, 1), new ItemStack(Material.REDSTONE, 1), new ItemStack(Material.NETHER_STAR, 1)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.LINGER_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new ItemStack(Material.NETHER_WART, 5), new SlimefunItemStack(SlimefunItems.TIN_DUST, 1), new ItemStack(Material.NETHER_WART, 5),
                new ItemStack(Material.GLOWSTONE_DUST, 5), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.GLOWSTONE_DUST, 5),
                new ItemStack(Material.FERMENTED_SPIDER_EYE, 1), new SlimefunItemStack(SlimefunItems.ZINC_DUST, 1), new ItemStack(Material.FERMENTED_SPIDER_EYE, 1)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.AGILITY_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new ItemStack(Material.SUGAR, 2), new SlimefunItemStack(SlimefunItems.PORK_JERKY, 1), new ItemStack(Material.SUGAR, 2),
                new ItemStack(Material.NETHER_WART, 4), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.NETHER_WART, 4),
                new ItemStack(Material.SUGAR, 2), new SlimefunItemStack(SlimefunItems.BEEF_JERKY, 1), new ItemStack(Material.SUGAR, 2)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.INTELLECT_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                new ItemStack(Material.BLAZE_POWDER, 3), null, new ItemStack(Material.BLAZE_POWDER, 3),
                new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 1), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 1),
                new ItemStack(Material.SUGAR, 3), null, new ItemStack(Material.SUGAR, 3)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.HEART_RUNE, FnMagicAltar.RECIPE_TYPE, new ItemStack[]{
                null, new ItemStack(Material.NETHER_WART, 2), null,
                new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 1), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 1),
                new ItemStack(Material.GLISTERING_MELON_SLICE, 1), new ItemStack(Material.NETHER_WART, 2), new ItemStack(Material.GLISTERING_MELON_SLICE, 1)})
                .register(instance);

        new UnplaceableBlock(FNAmpItems.MAGICAL_ITEMS, FNAmpItems.FN_GEM_FINE_JASPER_CRAFTING, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                new ItemStack(Material.IRON_ORE, 2), new ItemStack(Material.DIAMOND_ORE, 2),  new ItemStack(Material.IRON_ORE),
                null, FNAmpItems.POWER_RUNE, null,
                new ItemStack(Material.GOLD_ORE, 2), new ItemStack(Material.EMERALD_ORE, 2), new ItemStack(Material.GOLD_ORE, 2)})
                .register(instance);

    }
}
