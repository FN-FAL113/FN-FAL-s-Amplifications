package ne.fnfal113.fnamplifications.Items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FnItemRecipes {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();


    public static void setup(){

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.MACHINE_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.BRONZE_INGOT, SlimefunItems.GOLD_4K, SlimefunItems.BRONZE_INGOT,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.ADVANCED_CIRCUIT_BOARD,
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 16), SlimefunItems.BATTERY, new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 16)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.COMPONENT_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.COPPER_INGOT, new ItemStack(Material.GOLD_INGOT, 3), SlimefunItems.COPPER_INGOT,
                SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.MAGNET, SlimefunItems.BASIC_CIRCUIT_BOARD,
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4), SlimefunItems.BATTERY, new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.MOTOR_SWITCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ELECTRIC_MOTOR, 3), SlimefunItems.ALUMINUM_BRASS_INGOT, new SlimefunItemStack(SlimefunItems.ELECTRIC_MOTOR, 3),
                new ItemStack(Material.LEVER), new ItemStack(Material.REDSTONE, 8), new ItemStack(Material.LEVER),
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 6), SlimefunItems.BATTERY, new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 6)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.POWER_COMPONENT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ELECTRIC_MOTOR, 8), new SlimefunItemStack(SlimefunItems.BATTERY, 2), new SlimefunItemStack(SlimefunItems.ELECTRIC_MOTOR, 8),
                new ItemStack(Material.COPPER_INGOT, 4), SlimefunItems.POWER_CRYSTAL, new ItemStack(Material.COPPER_INGOT, 4),
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 8), SlimefunItems.BATTERY, new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 8)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.GEAR_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ELECTRIC_MOTOR, 8), new SlimefunItemStack(SlimefunItems.CARGO_MOTOR, 4), new SlimefunItemStack(SlimefunItems.ELECTRIC_MOTOR, 8),
                new ItemStack(Material.REDSTONE, 16), new SlimefunItemStack(SlimefunItems.CHAIN, 8), new ItemStack(Material.REDSTONE, 16),
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 16), new SlimefunItemStack(FNAmpItems.MOTOR_SWITCH, 4), new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 16)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.THREAD_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.OAK_PLANKS, 4), new ItemStack(Material.STICK, 4), new ItemStack(Material.OAK_PLANKS, 4),
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4), new ItemStack(Material.STICK, 4), new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4),
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4), new ItemStack(Material.STICK, 4), new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.COMPRESSOR_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), new ItemStack(Material.NETHER_BRICKS, 8), new ItemStack(Material.PISTON),
                FNAmpItems.THREAD_PART, FNAmpItems.GEAR_PART, FNAmpItems.THREAD_PART,
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4), new ItemStack(Material.NETHER_BRICKS, 8), new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.CONDENSER_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), FNAmpItems.COMPONENT_PART, new ItemStack(Material.PISTON),
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.GEAR_PART, FNAmpItems.COMPRESSOR_PART,
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 12), FNAmpItems.COMPONENT_PART, new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 12)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.RECYCLER_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), FNAmpItems.COMPONENT_PART, new ItemStack(Material.PISTON),
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.GEAR_PART, FNAmpItems.COMPRESSOR_PART,
                FNAmpItems.CONDENSER_PART, FNAmpItems.COMPONENT_PART, FNAmpItems.CONDENSER_PART})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.DOWNGRADER_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PISTON), FNAmpItems.THREAD_PART, new ItemStack(Material.PISTON),
                FNAmpItems.RECYCLER_PART, FNAmpItems.GEAR_PART, FNAmpItems.RECYCLER_PART,
                FNAmpItems.CONDENSER_PART, FNAmpItems.COMPRESSOR_PART, FNAmpItems.CONDENSER_PART})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.FUNNEL_PART, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.LEAD_INGOT, 8), new ItemStack(Material.IRON_INGOT, 16), new SlimefunItemStack(SlimefunItems.LEAD_INGOT, 8),
                FNAmpItems.THREAD_PART, new ItemStack(Material.BARREL, 1), FNAmpItems.THREAD_PART,
                new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4), new ItemStack(Material.IRON_INGOT, 16), new SlimefunItemStack(SlimefunItems.COPPER_WIRE, 4)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.DIAMOND_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.COBALT_INGOT, 8), new ItemStack(Material.IRON_INGOT, 16), new SlimefunItemStack(SlimefunItems.COBALT_INGOT, 8),
                new ItemStack(Material.DIAMOND, 16), new ItemStack(Material.DIAMOND_BLOCK, 1), new ItemStack(Material.DIAMOND, 16),
                new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8), new ItemStack(Material.IRON_INGOT, 16), new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.ALUMINUM_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.DURALUMIN_INGOT, 8), new ItemStack(Material.IRON_INGOT, 16), new SlimefunItemStack(SlimefunItems.DURALUMIN_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 4), new ItemStack(Material.IRON_INGOT, 16), new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 4),
                new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8), new ItemStack(Material.IRON_INGOT, 16), new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.GOLD_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.GOLD_8K, 8), new ItemStack(Material.GOLD_NUGGET, 2), new SlimefunItemStack(SlimefunItems.GOLD_8K, 8),
                new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 4), new ItemStack(Material.GOLD_BLOCK, 1), new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 4),
                new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8), new ItemStack(Material.GOLD_NUGGET, 2), new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.BRASS_PLATING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 8), new ItemStack(Material.IRON_NUGGET, 2), new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.BRASS_INGOT, 4), new ItemStack(Material.COPPER_BLOCK, 1), new SlimefunItemStack(SlimefunItems.BRASS_INGOT, 4),
                new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8), new ItemStack(Material.IRON_NUGGET, 2), new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.REINFORCED_CASING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2), new ItemStack(Material.COPPER_INGOT, 6), new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2),
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 4), new ItemStack(Material.IRON_BLOCK, 1), new SlimefunItemStack(SlimefunItems.BRASS_INGOT, 4),
                new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8), new ItemStack(Material.COPPER_INGOT, 6), new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 8)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.BASIC_MACHINE_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), new ItemStack(Material.IRON_BLOCK, 6), new SlimefunItemStack(FNAmpItems.GEAR_PART, 2),
                new SlimefunItemStack(FNAmpItems.BRASS_PLATING, 4), new SlimefunItemStack(FNAmpItems.MACHINE_PART, 4), new SlimefunItemStack(FNAmpItems.BRASS_PLATING, 4),
                new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 8), new ItemStack(Material.IRON_BLOCK, 6), new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 8)})
                .register(plugin);

        new UnplaceableBlock(FNAmpItems.ITEMS, FNAmpItems.HIGHTECH_MACHINE_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 6), new SlimefunItemStack(FNAmpItems.BASIC_MACHINE_BLOCK, 3), new SlimefunItemStack(FNAmpItems.GEAR_PART, 6),
                new SlimefunItemStack(FNAmpItems.REINFORCED_CASING, 12), new SlimefunItemStack(FNAmpItems.MACHINE_PART, 16), new SlimefunItemStack(FNAmpItems.BRASS_PLATING, 12),
                new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 16), new SlimefunItemStack(FNAmpItems.BASIC_MACHINE_BLOCK, 3), new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 16)})
                .register(plugin);

    }

}
