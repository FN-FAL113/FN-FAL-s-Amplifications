package ne.fnfal113.fnamplifications.machines;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import ne.fnfal113.fnamplifications.machines.abstracts.CMachine;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.items.FNAmpItems;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

public class ElectricMachineDowngrader extends CMachine implements RecipeDisplayItem {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(FNAmplifications.getInstance(), "fn_fal_downgrader"),
            FNAmpItems.FN_FAL_DOWNGRADER,
            "&fGet metal scraps from downgrading a",
            "&fmachine using Machine Downgrader",
            "&bYields metal scraps at certain chance"
    );

    public ElectricMachineDowngrader(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
    public void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = processor.getOperation(b);

        if (currentOperation != null) {
            if (takeCharge(b.getLocation())) {

                if (!currentOperation.isFinished()) {
                    processor.updateProgressBar(inv, 22, currentOperation);
                    currentOperation.addProgress(1);
                } else {
                    inv.replaceExistingItem(22, new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "));

                    for (ItemStack output : currentOperation.getResults()) {
                        if (ThreadLocalRandom.current().nextInt(100) < 25 && inv.hasViewer()) {
                            inv.pushItem(output.clone(), getOutputSlots());
                            inv.pushItem(new CustomItemStack(FNAmpItems.FN_METAL_SCRAPS.clone(), 1), getOutputSlots());
                        }
                        else{
                            inv.pushItem(output.clone(), getOutputSlots());
                        }
                    }

                    processor.endOperation(b);
                }
            }
        } else {
            MachineRecipe next = findNextRecipe(inv);

            if (next != null) {
                getMachineProcessor().startOperation(b, new CraftingOperation(next));
            }
        }
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_GOLD_PAN_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_GOLD_PAN_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_GOLD_PAN_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_GOLD_PAN, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_SMELTERY_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_SMELTERY, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_INGOT_FACTORY_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_INGOT_FACTORY_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_INGOT_FACTORY_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_INGOT_FACTORY, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_DUST_WASHER_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_DUST_WASHER_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_DUST_WASHER_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_DUST_WASHER, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_ORE_GRINDER_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_ORE_GRINDER_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_ORE_GRINDER_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_ORE_GRINDER, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_FURNACE_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_FURNACE_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_FURNACE_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_FURNACE, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.HEATED_PRESSURE_CHAMBER_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.HEATED_PRESSURE_CHAMBER, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIFIED_CRUCIBLE_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIFIED_CRUCIBLE_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIFIED_CRUCIBLE_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIFIED_CRUCIBLE, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.CARBON_PRESS_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.CARBON_PRESS_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.CARBON_PRESS_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.CARBON_PRESS, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.FREEZER_3, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.FREEZER_2, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.FREEZER_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.FREEZER, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.FOOD_FABRICATOR_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.FOOD_FABRICATOR, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.LAVA_GENERATOR_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.LAVA_GENERATOR, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.COAL_GENERATOR_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.COAL_GENERATOR, 1)});
        registerRecipe(20, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_PRESS_2, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ELECTRIC_PRESS, 1)});
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "ELECTRIC_MACHINE_DOWNGRADER";
    }

    @Override
    public ItemStack getProgressBar() {
        return new SlimefunItemStack(FNAmpItems.DOWNGRADER_PART, 1);
    }
}
