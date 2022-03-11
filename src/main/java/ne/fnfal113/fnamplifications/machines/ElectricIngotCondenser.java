package ne.fnfal113.fnamplifications.machines;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import ne.fnfal113.fnamplifications.items.FNAmpItems;

public class ElectricIngotCondenser extends AContainer implements RecipeDisplayItem {

    public static final ItemStack VERSIONED_ITEMSTACK_COPPER;

    static {
        if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)) {
            VERSIONED_ITEMSTACK_COPPER = new ItemStack(Material.COPPER_INGOT, 2);
        } else {
            VERSIONED_ITEMSTACK_COPPER = new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 2);
        }
    }

    public ElectricIngotCondenser(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 4);

        for (MachineRecipe recipe : recipes) {
            displayRecipes.add(recipe.getInput()[0]);
            displayRecipes.add(recipe.getInput()[1]);
            displayRecipes.add(new CustomItemStack(Material.PINK_STAINED_GLASS_PANE, "&eIndicator", "&fArrow below point towards the output of 2 vertical inputs"));
            displayRecipes.add(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e")));
            displayRecipes.add(new CustomItemStack(Material.PINK_STAINED_GLASS_PANE, "&eIndicator", "&fNext item beside this glass is", "&fa 2 input recipe vertical 1 output horizontal"));
            displayRecipes.add(recipe.getOutput()[0]);
        }

        return displayRecipes;
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_10K, 1), new SlimefunItemStack(SlimefunItems.GOLD_14K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_24K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1), new SlimefunItemStack(SlimefunItems.URANIUM, 9)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 2), new SlimefunItemStack(SlimefunItems.COMPRESSED_CARBON, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.HARDENED_METAL_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 2), new SlimefunItemStack(SlimefunItems.CARBON, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 2)});
        registerRecipe(12, new ItemStack[]{new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 1), VERSIONED_ITEMSTACK_COPPER},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.CORINTHIAN_BRONZE_INGOT, 1)});
        registerRecipe(12, new ItemStack[]{new ItemStack(Material.IRON_INGOT, 1), VERSIONED_ITEMSTACK_COPPER},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.NICKEL_INGOT, 1)});
        registerRecipe(12, new ItemStack[]{new SlimefunItemStack(SlimefunItems.NICKEL_INGOT, 1), VERSIONED_ITEMSTACK_COPPER},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.COBALT_INGOT, 1)});
        registerRecipe(12, new ItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 1), VERSIONED_ITEMSTACK_COPPER},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.DURALUMIN_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 1), new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_BRONZE_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 1), new SlimefunItemStack(SlimefunItems.BRASS_INGOT, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_BRASS_INGOT, 1)});
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "ELECTRIC_INGOT_CONDENSER";
    }

    @Override
    public ItemStack getProgressBar() {
        return new SlimefunItemStack(FNAmpItems.CONDENSER_PART, 1);
    }
}
