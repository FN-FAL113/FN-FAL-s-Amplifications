package ne.fnfal113.fnamplifications.quivers.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.quivers.implementations.QuiverTask;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

/**
 * Serves as the abstract class for the quivers
 * that implements the main methods
 * @author FN_FAL113
 */
public abstract class AbstractQuiver extends SlimefunItem {

    private final NamespacedKey storedArrowsKey;
    
    private final NamespacedKey randomIdKey;
    
    private final NamespacedKey stateKey;
    
    private final int quiverSize;
    
    private final ItemStack arrowType;

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

    public NamespacedKey getStoredArrowsKey() {
        return storedArrowsKey;
    }

    public NamespacedKey getRandomIdKey() {
        return randomIdKey;
    }

    public NamespacedKey getStateKey() {
        return stateKey;
    }

    public int getQuiverSize() {
        return quiverSize;
    }

    public ItemStack getArrowType() {
        return arrowType;
    }

    public QuiverTask getQuiverTask() {
        return quiverTask;
    }

}