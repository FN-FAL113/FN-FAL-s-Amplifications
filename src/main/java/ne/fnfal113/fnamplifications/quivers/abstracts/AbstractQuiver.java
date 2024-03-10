package ne.fnfal113.fnamplifications.quivers.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.quivers.implementations.QuiverTask;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

/**
 * Serves as the abstract class for the quivers
 * that implements the main methods
 * @author FN_FAL113
 */
public abstract class AbstractQuiver extends SlimefunItem {

    @Getter
    private final NamespacedKey storedArrowsKey;
    
    @Getter
    private final NamespacedKey randomIdKey;
    
    @Getter
    private final NamespacedKey stateKey;
    
    @Getter
    private final int quiverSize;
    
    @Getter
    private final ItemStack arrowType;

    @Getter
    private final QuiverTask quiverTask;

    public AbstractQuiver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
        NamespacedKey storedArrowKey, NamespacedKey randomIdKey, NamespacedKey stateKey, int quiverSize, ItemStack arrowType) {
        super(itemGroup, item, recipeType, recipe);

        this.storedArrowsKey = storedArrowKey;
        this.randomIdKey = randomIdKey;
        this.stateKey = stateKey;
        this.quiverSize = quiverSize;
        this.arrowType = arrowType;
        this.quiverTask = new QuiverTask(this);
    }

}