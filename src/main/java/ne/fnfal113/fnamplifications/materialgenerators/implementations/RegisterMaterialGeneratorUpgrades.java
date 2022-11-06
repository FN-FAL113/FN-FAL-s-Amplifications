package ne.fnfal113.fnamplifications.materialgenerators.implementations;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.materialgenerators.upgrades.FastProduce;
import ne.fnfal113.fnamplifications.materialgenerators.upgrades.RepairItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterMaterialGeneratorUpgrades {

    public static void setup(SlimefunAddon instance) {
        new RepairItem(
                FNAmpItems.MATERIAL_GENERATORS_UPGRADES,
                FNAmpItems.FN_MAT_GEN_UPGRADES_REPAIR_ITEM,
                RecipeType.COMPRESSOR,
                new ItemStack[]{
                        SlimefunItems.SYNTHETIC_SAPPHIRE, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.SYNTHETIC_SAPPHIRE,
                        SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.BLISTERING_INGOT, SlimefunItems.HARDENED_METAL_INGOT,
                        SlimefunItems.SYNTHETIC_SAPPHIRE, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.SYNTHETIC_SAPPHIRE}
        ).register(instance);

        new FastProduce(
                FNAmpItems.MATERIAL_GENERATORS_UPGRADES,
                FNAmpItems.FN_MAT_GEN_UPGRADES_FAST_PRODUCE,
                RecipeType.COMPRESSOR,
                new ItemStack[]{
                        new ItemStack(Material.DIAMOND_SHOVEL), SlimefunItems.HARDENED_METAL_INGOT, new ItemStack(Material.DIAMOND_SHOVEL),
                        SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.BLISTERING_INGOT, SlimefunItems.HARDENED_METAL_INGOT,
                        new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.HARDENED_METAL_INGOT, new ItemStack(Material.DIAMOND_PICKAXE)}
        ).register(instance);
    }
}
