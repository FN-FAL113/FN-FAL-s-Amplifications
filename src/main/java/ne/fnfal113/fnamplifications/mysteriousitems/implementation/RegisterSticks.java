package ne.fnfal113.fnamplifications.mysteriousitems.implementation;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnMysteryStickAltar;
import ne.fnfal113.fnamplifications.mysteriousitems.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterSticks {

    public static void setup(SlimefunAddon instance){
        new MysteryStick(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 8), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 6), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 8),
                SlimefunItems.BLANK_RUNE, new ItemStack(Material.STICK), SlimefunItems.BLANK_RUNE,
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 8), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 6), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 8)},
                Material.DIAMOND_SWORD)
                .register(instance);

        new MysteryStick2(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_2, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 8), new ItemStack(Material.LEATHER), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 8),
                SlimefunItems.BLANK_RUNE, new ItemStack(Material.STICK), SlimefunItems.BLANK_RUNE,
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 8), new ItemStack(Material.OAK_WOOD, 2), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 8)},
                Material.DIAMOND_AXE)
                .register(instance);

        new MysteryStick3(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_3, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.LEATHER), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 8), new ItemStack(Material.STICK), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 8),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 6), new ItemStack(Material.LEAD), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 6)},
                Material.BOW)
                .register(instance);

        new MysteryStick4(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_4, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 1), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 16),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 1), FNAmpItems.FN_STICK, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 1),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 16), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 1), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1)},
                Material.DIAMOND_SWORD)
                .register(instance);

        new MysteryStick5(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_5, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 18), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_2, 18),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), FNAmpItems.FN_STICK_2, new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 2),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 12), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 12)},
                Material.DIAMOND_AXE)
                .register(instance);

        new MysteryStick6(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_6, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 1), FNAmpItems.FN_STICK_3, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 1),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 18), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 2), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 18)},
                Material.BOW)
                .register(instance);

        new MysteryStick7(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_7, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),  new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 3), FNAmpItems.FN_STICK_4, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 2), new SlimefunItemStack(SlimefunItems.ENCHANTMENT_RUNE, 3)},
                Material.DIAMOND_SWORD)
                .register(instance);

        new MysteryStick8(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_8, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 3), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), FNAmpItems.FN_STICK_5, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3)},
                Material.DIAMOND_AXE)
                .register(instance);

        new MysteryStick9(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_9, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 2), FNAmpItems.FN_STICK_6, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 3), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1)},
                Material.BOW)
                .register(instance);

        new MysteryStick10(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_10, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2), FNAmpItems.FN_STICK_4, new SlimefunItemStack(SlimefunItems.ENCHANTMENT_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 3), FNAmpItems.FN_STICK_7, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3), FNAmpItems.FN_STICK, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2)},
                Material.DIAMOND_SWORD)
                .register(instance);

        new MysteryStick11(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_11, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FN_STICK_2, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), FNAmpItems.FN_STICK_8,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), FNAmpItems.FN_STICK_5, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 4)},
                Material.DIAMOND_AXE)
                .register(instance);
    }
}
