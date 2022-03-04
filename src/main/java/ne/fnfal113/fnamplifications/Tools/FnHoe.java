package ne.fnfal113.fnamplifications.Tools;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import ne.fnfal113.fnamplifications.Tools.Abstracts.AbstractHoe;
import ne.fnfal113.fnamplifications.Tools.Implementation.HoeTask;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FnHoe extends AbstractHoe implements NotPlaceable {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

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

    @Override
    public boolean useVanillaBlockBreaking(){
        return true;
    }

    public static void setup(){
        new FnHoe(FNAmpItems.FN_MISC, FNAmpItems.FN_HOE_5X5, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.CARBON, 4), SlimefunItems.COMMON_TALISMAN,
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3), new ItemStack(Material.DIAMOND_HOE), new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6), new SlimefunItemStack(SlimefunItems.CARBON, 4), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 6)})
                .register(plugin);
    }
}