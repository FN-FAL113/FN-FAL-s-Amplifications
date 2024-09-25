package ne.fnfal113.fnamplifications.tools;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import ne.fnfal113.fnamplifications.tools.implementation.BlockRotatorTask;

import org.bukkit.inventory.ItemStack;

public class BlockRotator extends SlimefunItem {

    private final BlockRotatorTask blockRotatorTask = new BlockRotatorTask();

    public BlockRotator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public BlockRotatorTask getBlockRotatorTask() {
        return blockRotatorTask;
    }

}