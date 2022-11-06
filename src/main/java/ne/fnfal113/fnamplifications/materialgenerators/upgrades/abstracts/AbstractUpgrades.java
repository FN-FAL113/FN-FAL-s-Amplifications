package ne.fnfal113.fnamplifications.materialgenerators.upgrades.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.materialgenerators.implementations.CustomMaterialGenerator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Superclass for item upgrade type for FN Material Generators
 * @author fnfal113
 */
public abstract class AbstractUpgrades extends SlimefunItem {

    @Getter
    private final String upgradeMessage;

    public AbstractUpgrades(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, String upgradeMessage) {
        super(itemGroup, item, recipeType, recipe);

        this.upgradeMessage = upgradeMessage;
    }

    /**
     *
     * @return true if the upgrade was successful, false if not
     */
    public abstract boolean upgradeMaterialGenerator(Block sfBlock, Player player, CustomMaterialGenerator matGen);

}
