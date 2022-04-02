package ne.fnfal113.fnamplifications.gears.implementation;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.gears.FnBoots;
import ne.fnfal113.fnamplifications.gears.FnChestPlate;
import ne.fnfal113.fnamplifications.gears.FnHelmet;
import ne.fnfal113.fnamplifications.gears.FnLeggings;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterGears {

    private static final ItemStack INGOT = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
            new ItemStack(Material.NETHERITE_INGOT, 2) : new ItemStack(Material.DIAMOND, 2);

    public static void setup(SlimefunAddon instance){
        new FnBoots(FNAmpItems.FN_GEARS, FNAmpItems.FN_GEAR_BOOTS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 5), new ItemStack(Material.IRON_BOOTS), new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 5),
                SlimefunItems.ENCHANTMENT_RUNE, INGOT.clone(), SlimefunItems.ENCHANTMENT_RUNE,
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6), new ItemStack(Material.DIAMOND_BOOTS), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6)})
                .register(instance);

        new FnChestPlate(FNAmpItems.FN_GEARS, FNAmpItems.FN_GEAR_CHESTPLATE, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2), new ItemStack(Material.IRON_CHESTPLATE), new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2),
                SlimefunItems.ENCHANTMENT_RUNE, INGOT.clone(), SlimefunItems.ENCHANTMENT_RUNE,
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 4), new ItemStack(Material.DIAMOND_CHESTPLATE), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 4)})
                .register(instance);

        new FnHelmet(FNAmpItems.FN_GEARS, FNAmpItems.FN_GEAR_HELMET, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 4), new ItemStack(Material.IRON_HELMET), new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 4),
                SlimefunItems.ENCHANTMENT_RUNE, INGOT.clone(), SlimefunItems.ENCHANTMENT_RUNE,
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6), new ItemStack(Material.DIAMOND_HELMET), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6)})
                .register(instance);

        new FnLeggings(FNAmpItems.FN_GEARS, FNAmpItems.FN_GEAR_LEGGINGS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2), new ItemStack(Material.IRON_LEGGINGS), new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2),
                SlimefunItems.ENCHANTMENT_RUNE, INGOT.clone(), SlimefunItems.ENCHANTMENT_RUNE,
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 3), new ItemStack(Material.DIAMOND_LEGGINGS), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 3)})
                .register(instance);
    }
}
