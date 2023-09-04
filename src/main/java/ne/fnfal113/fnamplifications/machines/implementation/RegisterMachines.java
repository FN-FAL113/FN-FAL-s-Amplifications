package ne.fnfal113.fnamplifications.machines.implementation;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.machines.*;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterMachines {

    public static void setup(SlimefunAddon instance){
        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.GEAR_PART, FNAmpItems.COMPONENT_PART, FNAmpItems.GEAR_PART,
                new ItemStack(Material.DIAMOND_PICKAXE), FNAmpItems.BASIC_MACHINE_BLOCK, new ItemStack(Material.IRON_PICKAXE),
                FNAmpItems.ALUMINUM_PLATING, FNAmpItems.POWER_COMPONENT, FNAmpItems.ALUMINUM_PLATING}, 12)
                .setCapacity(512)
                .setEnergyConsumption(32)
                .register(instance);

        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), FNAmpItems.COMPONENT_PART, new SlimefunItemStack(FNAmpItems.GEAR_PART, 2),
                FNAmpItems.FN_BLOCK_BREAKER_1, new SlimefunItemStack(FNAmpItems.BASIC_MACHINE_BLOCK, 2), new ItemStack(Material.DIAMOND_PICKAXE),
                FNAmpItems.BRASS_PLATING, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2), FNAmpItems.BRASS_PLATING}, 6)
                .setCapacity(1024)
                .setEnergyConsumption(64)
                .register(instance);

        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 3), FNAmpItems.COMPONENT_PART, new SlimefunItemStack(FNAmpItems.GEAR_PART, 3),
                FNAmpItems.FN_BLOCK_BREAKER_1, FNAmpItems.HIGHTECH_MACHINE_BLOCK, FNAmpItems.FN_BLOCK_BREAKER_2,
                FNAmpItems.REINFORCED_CASING, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2), FNAmpItems.REINFORCED_CASING}, 2)
                .setCapacity(2048)
                .setEnergyConsumption(128)
                .register(instance);

        new ElectricCompressor(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_COMPRESSOR_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.THREAD_PART, FNAmpItems.MOTOR_SWITCH,
                FNAmpItems.GEAR_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.GEAR_PART,
                FNAmpItems.CONDENSER_PART, FNAmpItems.ALUMINUM_PLATING, FNAmpItems.POWER_COMPONENT})
                .setCapacity(1536).setEnergyConsumption(64).setProcessingSpeed(1).register(instance);

        new ElectricCompressor(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_COMPRESSOR_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.COMPRESSOR_PART, 2), new SlimefunItemStack(FNAmpItems.THREAD_PART, 2), FNAmpItems.MOTOR_SWITCH,
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 4), FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.FN_FAL_COMPRESSOR_1,
                FNAmpItems.CONDENSER_PART, FNAmpItems.DIAMOND_PLATING, FNAmpItems.POWER_COMPONENT})
                .setCapacity(1536).setEnergyConsumption(128).setProcessingSpeed(2).register(instance);

        new ElectricCompressor(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_COMPRESSOR_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.COMPRESSOR_PART, 3), new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 2), FNAmpItems.MOTOR_SWITCH,
                FNAmpItems.FN_FAL_COMPRESSOR_2, FNAmpItems.HIGHTECH_MACHINE_BLOCK, FNAmpItems.FN_FAL_COMPRESSOR_2,
                FNAmpItems.CONDENSER_PART, FNAmpItems.REINFORCED_CASING, FNAmpItems.POWER_COMPONENT})
                .setCapacity(1536).setEnergyConsumption(256).setProcessingSpeed(4).register(instance);

        new ElectricIngotCondenser(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_CONDENSER_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.MOTOR_SWITCH, FNAmpItems.THREAD_PART, FNAmpItems.FUNNEL_PART,
                FNAmpItems.GEAR_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.GOLD_PLATING, FNAmpItems.COMPRESSOR_PART})
                .setCapacity(1536).setEnergyConsumption(128).setProcessingSpeed(1).register(instance);

        new ElectricIngotCondenser(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_CONDENSER_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.MOTOR_SWITCH, FNAmpItems.FN_FAL_CONDENSER_1, FNAmpItems.FUNNEL_PART,
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), FNAmpItems.BASIC_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2),
                FNAmpItems.THREAD_PART, FNAmpItems.DIAMOND_PLATING, FNAmpItems.COMPONENT_PART})
                .setCapacity(1536).setEnergyConsumption(192).setProcessingSpeed(2).register(instance);

        new ElectricIngotCondenser(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_CONDENSER_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_FAL_CONDENSER_1, FNAmpItems.FN_FAL_CONDENSER_1, FNAmpItems.FN_FAL_CONDENSER_1,
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 3), FNAmpItems.HIGHTECH_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 3),
                FNAmpItems.THREAD_PART, FNAmpItems.REINFORCED_CASING, FNAmpItems.COMPONENT_PART})
                .setCapacity(1536).setEnergyConsumption(384).setProcessingSpeed(4).register(instance);

        new ElectricMachineDowngrader(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_DOWNGRADER, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.DOWNGRADER_PART, FNAmpItems.RECYCLER_PART,
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), FNAmpItems.HIGHTECH_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2),
                FNAmpItems.CONDENSER_PART, FNAmpItems.REINFORCED_CASING, FNAmpItems.MOTOR_SWITCH})
                .setCapacity(4024).setEnergyConsumption(750).setProcessingSpeed(2).register(instance);

        new ElectricRecycler(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_RECYCLER_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FUNNEL_PART, FNAmpItems.RECYCLER_PART, FNAmpItems.COMPRESSOR_PART,
                FNAmpItems.THREAD_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.CONDENSER_PART, FNAmpItems.BRASS_PLATING, FNAmpItems.MOTOR_SWITCH})
                .setCapacity(1536).setEnergyConsumption(84).setProcessingSpeed(1).register(instance);

        new ElectricRecycler(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_RECYCLER_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.FUNNEL_PART, 2), FNAmpItems.RECYCLER_PART, FNAmpItems.COMPRESSOR_PART,
                FNAmpItems.THREAD_PART, FNAmpItems.BASIC_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2),
                FNAmpItems.FN_FAL_RECYCLER_1, FNAmpItems.ALUMINUM_PLATING, FNAmpItems.MOTOR_SWITCH})
                .setCapacity(1536).setEnergyConsumption(168).setProcessingSpeed(2).register(instance);

        new ElectricRecycler(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_RECYCLER_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.RECYCLER_PART, 3), FNAmpItems.FN_FAL_RECYCLER_1, FNAmpItems.COMPRESSOR_PART,
                FNAmpItems.GEAR_PART, FNAmpItems.HIGHTECH_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 3),
                FNAmpItems.FN_FAL_RECYCLER_1, FNAmpItems.REINFORCED_CASING, FNAmpItems.FN_FAL_RECYCLER_1})
                .setCapacity(1536).setEnergyConsumption(336).setProcessingSpeed(4).register(instance);

        new ElectricTransformer(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_TRANSFORMER_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FUNNEL_PART, FNAmpItems.THREAD_PART, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.GEAR_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.GEAR_PART,
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.BRASS_PLATING, FNAmpItems.CONDENSER_PART})
                .setCapacity(1536).setEnergyConsumption(128).setProcessingSpeed(1).register(instance);

        new ElectricTransformer(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_TRANSFORMER_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FUNNEL_PART, FNAmpItems.FN_FAL_TRANSFORMER_1, FNAmpItems.POWER_COMPONENT,
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), FNAmpItems.BASIC_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.GEAR_PART, 2),
                FNAmpItems.THREAD_PART, FNAmpItems.ALUMINUM_PLATING, FNAmpItems.CONDENSER_PART})
                .setCapacity(1536).setEnergyConsumption(192).setProcessingSpeed(2).register(instance);

        new ElectricTransformer(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_TRANSFORMER_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.FUNNEL_PART, 2), FNAmpItems.FN_FAL_TRANSFORMER_2, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.FN_FAL_TRANSFORMER_1, FNAmpItems.HIGHTECH_MACHINE_BLOCK, FNAmpItems.FN_FAL_TRANSFORMER_1,
                FNAmpItems.COMPONENT_PART, FNAmpItems.REINFORCED_CASING, new SlimefunItemStack(FNAmpItems.CONDENSER_PART, 2)})
                .setCapacity(1536).setEnergyConsumption(384).setProcessingSpeed(4).register(instance);

        new ElectricJukebox(FNAmpItems.MACHINES, FNAmpItems.FN_JUKEBOX_I, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.BRASS_PLATING, FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.COPPER_INGOT, new ItemStack(Material.JUKEBOX), SlimefunItems.COPPER_INGOT,
                FNAmpItems.COMPONENT_PART, FNAmpItems.BRASS_PLATING, FNAmpItems.COMPONENT_PART},
                21, 23, 0 ,0, false)
                .setCapacity(512).setEnergyConsumption(3).register(instance);

        new ElectricJukebox(FNAmpItems.MACHINES, FNAmpItems.FN_JUKEBOX_II, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.ALUMINUM_PLATING, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.ALUMINUM_PLATING,
                SlimefunItems.ALUMINUM_INGOT, FNAmpItems.FN_JUKEBOX_I, SlimefunItems.ALUMINUM_INGOT,
                FNAmpItems.COMPONENT_PART, FNAmpItems.COMPONENT_PART, FNAmpItems.COMPONENT_PART},
                19, 25, 0, 0, false)
                .setCapacity(768).setEnergyConsumption(8).register(instance);

        new ElectricJukebox(FNAmpItems.MACHINES, FNAmpItems.FN_JUKEBOX_III, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.COMPONENT_PART, FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.BRASS_INGOT, FNAmpItems.FN_JUKEBOX_II, SlimefunItems.BRASS_INGOT,
                FNAmpItems.COMPONENT_PART, FNAmpItems.REINFORCED_CASING, FNAmpItems.COMPONENT_PART},
                19, 25, 29, 33, true)
                .setCapacity(1024).setEnergyConsumption(16).register(instance);
    }
}
