package ne.fnfal113.fnamplifications.tools;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import ne.fnfal113.fnamplifications.tools.abstracts.AbstractHoe;
import ne.fnfal113.fnamplifications.tools.implementation.HoeTask;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FnHoe extends AbstractHoe implements NotPlaceable {

    private final HoeTask hoeTask = new HoeTask();

    public FnHoe(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onRightClick(Player player, Block clickedBlock){
        hoeTask.tillLand(player, clickedBlock);
    }

    @Override
    public void onLeftClick(Player player, Block clickedBlock, ItemStack itemStack){
        hoeTask.harvest(player, clickedBlock, itemStack, false);
    }

}