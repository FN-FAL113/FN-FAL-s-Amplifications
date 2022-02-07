package ne.fnfal113.fnamplifications.Machines;

import java.util.ArrayList;
import java.util.List;

import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;

import javax.annotation.Nonnull;

public class ElectricRecycler extends AContainer implements RecipeDisplayItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

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

    public static void setup() {
        new ElectricRecycler(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_RECYCLER_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.FUNNEL_PART, FNAmpItems.RECYCLER_PART, FNAmpItems.COMPRESSOR_PART,
                FNAmpItems.THREAD_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.CONDENSER_PART, FNAmpItems.BRASS_PLATING, FNAmpItems.MOTOR_SWITCH
        }).setCapacity(1536).setEnergyConsumption(84).setProcessingSpeed(1).register(plugin);

        new ElectricRecycler(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_RECYCLER_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.FUNNEL_PART, 2), FNAmpItems.RECYCLER_PART, FNAmpItems.COMPRESSOR_PART,
                FNAmpItems.THREAD_PART, FNAmpItems.BASIC_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2),
                FNAmpItems.FN_FAL_RECYCLER_1, FNAmpItems.ALUMINUM_PLATING, FNAmpItems.MOTOR_SWITCH
        }).setCapacity(1536).setEnergyConsumption(168).setProcessingSpeed(2).register(plugin);

        new ElectricRecycler(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_RECYCLER_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.RECYCLER_PART, 3), FNAmpItems.FN_FAL_TRANSFORMER_2, FNAmpItems.COMPRESSOR_PART,
                FNAmpItems.GEAR_PART, FNAmpItems.HIGHTECH_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 3),
                FNAmpItems.FN_FAL_TRANSFORMER_1, FNAmpItems.REINFORCED_CASING, FNAmpItems.FN_FAL_TRANSFORMER_1
        }).setCapacity(1536).setEnergyConsumption(336).setProcessingSpeed(4).register(plugin);
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
