package ne.fnfal113.fnamplifications.items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;

import ne.fnfal113.fnamplifications.multiblocks.FnScrapRecycler;

import org.bukkit.inventory.ItemStack;

public class FnScrapRecipes {

    public static void setup(SlimefunAddon instance){
        new UnplaceableBlock(FNAmpItems.METAL_SCRAP_RECIPES, FNAmpItems.COMPONENT_PART_SCRAP, FnScrapRecycler.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS,
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS,
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS}, FNAmpItems.COMPONENT_PART)
                .register(instance);

        new UnplaceableBlock(FNAmpItems.METAL_SCRAP_RECIPES, FNAmpItems.GEAR_PART_SCRAP, FnScrapRecycler.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS,
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS,
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS}, FNAmpItems.GEAR_PART)
                .register(instance);

        new UnplaceableBlock(FNAmpItems.METAL_SCRAP_RECIPES, FNAmpItems.MACHINE_PART_SCRAP, FnScrapRecycler.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS,
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS,
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS}, FNAmpItems.MACHINE_PART)
                .register(instance);

        new UnplaceableBlock(FNAmpItems.METAL_SCRAP_RECIPES, FNAmpItems.MOTOR_SWITCH_SCRAP, FnScrapRecycler.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS,
                null, FNAmpItems.FN_METAL_SCRAPS, null,
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS}, FNAmpItems.MOTOR_SWITCH)
                .register(instance);

        new UnplaceableBlock(FNAmpItems.METAL_SCRAP_RECIPES, FNAmpItems.THREAD_PART_SCRAP, FnScrapRecycler.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS,
                null, FNAmpItems.FN_METAL_SCRAPS, null,
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS}, FNAmpItems.THREAD_PART)
                .register(instance);

        new UnplaceableBlock(FNAmpItems.METAL_SCRAP_RECIPES, FNAmpItems.POWER_COMPONENT_SCRAP, FnScrapRecycler.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS,
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, null,
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS}, FNAmpItems.POWER_COMPONENT)
                .register(instance);

        new UnplaceableBlock(FNAmpItems.METAL_SCRAP_RECIPES, FNAmpItems.FUNNEL_PART_SCRAP, FnScrapRecycler.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS, FNAmpItems.FN_METAL_SCRAPS,
                null, FNAmpItems.FN_METAL_SCRAPS, null,
                FNAmpItems.FN_METAL_SCRAPS, null, FNAmpItems.FN_METAL_SCRAPS}, FNAmpItems.FUNNEL_PART)
                .register(instance);
    }

}
