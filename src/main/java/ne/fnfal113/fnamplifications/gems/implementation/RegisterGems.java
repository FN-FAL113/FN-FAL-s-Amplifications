package ne.fnfal113.fnamplifications.gems.implementation;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.gems.*;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnGemAltar;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterGems {

    public static void setup(SlimefunAddon instance){
        new ArmorImpairGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_ARMOR_IMPAIR, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new ItemStack(Material.FIRE_CHARGE), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3),
                SlimefunItems.COMMON_TALISMAN, new ItemStack(Material.FIRE_CHARGE), SlimefunItems.COMMON_TALISMAN})
                .register(instance);

        new AxeThrowieGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_AXETHROWIE, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3),  SlimefunItems.TALISMAN_WARRIOR,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),  SlimefunItems.TALISMAN_WARRIOR})
                .register(instance);

        new BlindBindGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_BLINDBIND, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.ENCHANTMENT_RUNE, 3), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3),
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.ENCHANTMENT_RUNE, 3), SlimefunItems.COMMON_TALISMAN})
                .register(instance);

        new DamnationGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_DAMNATION, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 4),  SlimefunItems.TALISMAN_KNIGHT,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3),
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.RAINBOW_RUNE, 4),  SlimefunItems.TALISMAN_KNIGHT})
                .register(instance);

        new GuardianGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_GUARDIAN, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_ANGEL, new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), SlimefunItems.TALISMAN_FIRE,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 1),  SlimefunItems.MAGIC_SUGAR})
                .register(instance);

        new HastyGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_HASTY, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_MINER, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_MINER, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.COMMON_TALISMAN})
                .register(instance);

        new ImpostorGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_IMPOSTOR, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4), new SlimefunItemStack(SlimefunItems.COMMON_TALISMAN, 1), new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 4),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 4), new SlimefunItemStack(SlimefunItems.COMMON_TALISMAN, 1), SlimefunItems.WATER_RUNE})
                .register(instance);

        new InfernoGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_INFERNO, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_FIRE, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), SlimefunItems.TALISMAN_FIRE,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_FIRE, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), SlimefunItems.TALISMAN_FIRE})
                .register(instance);

        new PsychokinesisGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_PSYCHOKINESIS, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_TRAVELLER, new SlimefunItemStack(SlimefunItems.ENDER_RUNE, 4), SlimefunItems.TALISMAN_TRAVELLER,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_TRAVELLER, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), SlimefunItems.TALISMAN_TRAVELLER})
                .register(instance);

        new RetaliateGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_RETALIATE, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2),  SlimefunItems.TALISMAN_WHIRLWIND,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_WHIRLWIND, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2),  SlimefunItems.COMMON_TALISMAN})
                .register(instance);

        new TelepathyGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_TELEPATHY, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_MINER, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.TALISMAN_MINER,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.EARTH_RUNE, new ItemStack(Material.IRON_PICKAXE), SlimefunItems.EARTH_RUNE})
                .register(instance);

        new ThornAwayGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_THORN_AWAY, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 3), new ItemStack(Material.BLAZE_POWDER, 8), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 3),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 4), new ItemStack(Material.BLAZE_POWDER, 8), new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 4)})
                .register(instance);

        new ThunderBoltGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_THUNDER, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3), new ItemStack(Material.FIRE_CHARGE), new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3), new ItemStack(Material.FIRE_CHARGE), new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3)})
                .register(instance);

        new TriSwordGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_TRI_SWORD, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_KNIGHT, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 6),  SlimefunItems.TALISMAN_KNIGHT,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3),
                SlimefunItems.TALISMAN_KNIGHT, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4),  SlimefunItems.TALISMAN_KNIGHT})
                .register(instance);
    }
}
