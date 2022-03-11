package ne.fnfal113.fnamplifications.staffs.implementations;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import ne.fnfal113.fnamplifications.staffs.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterStaffs {

    public static void setup(SlimefunAddon instance){
        new StaffOfAirStrider(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_AIR_STRIDER, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), new ItemStack(Material.FEATHER, 6),  new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.BLAZE_ROD), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 12), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2)})
                .register(instance);

        new StaffOfAwareness(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_AWARENESS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2), new ItemStack(Material.BLAZE_POWDER, 16), new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 2),
                SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGICAL_GLASS,
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 2), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2)})
                .register(instance);

        new StaffOfConfusion(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_CONFUSION, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.LINGERING_POTION), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8),
                SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGICAL_BOOK_COVER,
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.RAINBOW_RUNE, 3)})
                .register(instance);

        new StaffOfDeepFreeze(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_DEEPFREEZE, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.LINGERING_POTION), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8),
                SlimefunItems.MAGICAL_BOOK_COVER, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGICAL_BOOK_COVER,
                new SlimefunItemStack(SlimefunItems.WATER_RUNE, 3), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3)})
                .register(instance);

        new StaffOfExplosion(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_EXPLOSION, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3), new ItemStack(Material.TNT, 8), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3),
                SlimefunItems.MAGIC_SUGAR, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGIC_SUGAR,
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 12), new ItemStack(Material.GUNPOWDER, 16), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 12)})
                .register(instance);

        new StaffOfFangs(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_FANGS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.BLANK_RUNE, new ItemStack(Material.BLAZE_POWDER, 14), SlimefunItems.BLANK_RUNE,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2), new ItemStack(Material.BLAZE_ROD), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 7), new ItemStack(Material.ROTTEN_FLESH, 24), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 7)})
                .register(instance);

        new StaffOfForce(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_FORCE, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 24), new ItemStack(Material.FEATHER, 12), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 24),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3), new ItemStack(Material.BLAZE_ROD),  new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3),
                new ItemStack(Material.BLAZE_POWDER, 12), SlimefunItems.MAGIC_SUGAR, new ItemStack(Material.BLAZE_POWDER, 12)})
                .register(instance);

        new StaffOfGravitationalPull(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_GRAVITY, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.LINGERING_POTION), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8),
                SlimefunItems.MAGICAL_BOOK_COVER, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGIC_EYE_OF_ENDER,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 4)})
                .register(instance);

        new StaffOfHealing(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_HEALING, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 8), new ItemStack(Material.SPLASH_POTION), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8),
                new SlimefunItemStack(SlimefunItems.BANDAGE, 8), new ItemStack(Material.BLAZE_ROD), new SlimefunItemStack(SlimefunItems.BANDAGE, 8),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1)})
                .register(instance);

        new StaffOfHellFire(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_HELLFIRE, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.LINGERING_POTION), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8),
                SlimefunItems.MAGICAL_BOOK_COVER, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGICAL_BOOK_COVER,
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 3), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3)})
                .register(instance);

        new StaffOfInvisibility(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_INVI, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2), SlimefunItems.MAGICAL_GLASS, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 6),
                SlimefunItems.AIR_RUNE, new ItemStack(Material.BLAZE_ROD), SlimefunItems.AIR_RUNE,
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 6), SlimefunItems.MAGIC_SUGAR,  new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2)})
                .register(instance);

        new StaffOfInvulnerability(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_INVULNERABILITY, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.GOLDEN_APPLE, 3), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGICAL_BOOK_COVER,
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 8)})
                .register(instance);

        new StaffOfLocomotion(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_LOCOMOTION, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.BLANK_RUNE, SlimefunItems.MAGICAL_GLASS, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 6),
                SlimefunItems.AIR_RUNE, new ItemStack(Material.BLAZE_ROD), SlimefunItems.AIR_RUNE,
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 6), SlimefunItems.MAGIC_EYE_OF_ENDER, SlimefunItems.BLANK_RUNE}, 10)
                .register(instance);

        new StaffOfMinerals(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_MINERALS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2), new ItemStack(Material.BLAZE_POWDER, 12), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 14), new ItemStack(Material.BLAZE_ROD), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 14),
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2)})
                .register(instance);

        new StaffOfMuster(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_MUSTER, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 2), new ItemStack(Material.ENDER_PEARL, 16), new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 2),
                SlimefunItems.MAGIC_EYE_OF_ENDER, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGIC_EYE_OF_ENDER,
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 10), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 10)})
                .register(instance);

        new StaffOfSkulls(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_SKULLS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.NECROTIC_SKULL, 2), new ItemStack(Material.WITHER_SKELETON_SKULL, 3),  new SlimefunItemStack(SlimefunItems.NECROTIC_SKULL, 2),
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 6), new ItemStack(Material.BLAZE_ROD), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 6),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.BLAZE_POWDER, 14), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8)})
                .register(instance);

        new StaffOfStallion(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_STALLION, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 32), new ItemStack(Material.SADDLE), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 32),
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.BLAZE_ROD),  SlimefunItems.ESSENCE_OF_AFTERLIFE,
                new ItemStack(Material.BONE, 32), SlimefunItems.MAGIC_SUGAR, new ItemStack(Material.BONE, 32)})
                .register(instance);

        new StaffOfTeleportation(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_TP, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 5), SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 5),
                SlimefunItems.ENDER_RUNE, new ItemStack(Material.BLAZE_ROD), SlimefunItems.ENDER_RUNE,
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2), SlimefunItems.MAGIC_EYE_OF_ENDER, new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2)})
                .register(instance);

        new StaffOfWebs(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_COBWEB, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3), new ItemStack(Material.COBWEB, 6),  new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3),
                new ItemStack(Material.COBWEB, 6), new ItemStack(Material.BLAZE_ROD),  new ItemStack(Material.COBWEB, 6),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 5), new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2)})
                .register(instance);
    }
}
