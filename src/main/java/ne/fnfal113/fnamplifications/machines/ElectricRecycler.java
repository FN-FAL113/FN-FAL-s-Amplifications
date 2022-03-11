package ne.fnfal113.fnamplifications.machines;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import ne.fnfal113.fnamplifications.items.FNAmpItems;

import javax.annotation.Nonnull;

public class ElectricRecycler extends AContainer implements RecipeDisplayItem {

    public ElectricRecycler(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 4);

        for (MachineRecipe recipe : recipes) {
            displayRecipes.add(recipe.getInput()[0]);
            displayRecipes.add(recipe.getOutput()[0]);
        }

        return displayRecipes;
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.URANIUM, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.SMALL_URANIUM, 4)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.NEPTUNIUM, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.URANIUM, 48)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BOOSTED_URANIUM, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.NEPTUNIUM, 2)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_24K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_22K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_22K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_20K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_20K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_18K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_18K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_16K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_16K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_14K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_14K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_12K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_12K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_10K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_10K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_8K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_8K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_6K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_6K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_4K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_4K, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_DUST, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_2, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_1, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ENDER_LUMP_2, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ENDER_LUMP_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 1)});
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "ELECTRIC_RECYCLER";
    }

    @Override
    public ItemStack getProgressBar() {
        return new SlimefunItemStack(FNAmpItems.RECYCLER_PART, 1);
    }
}
