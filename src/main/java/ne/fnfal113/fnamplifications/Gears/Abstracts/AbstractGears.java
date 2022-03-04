package ne.fnfal113.fnamplifications.Gears.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class AbstractGears extends SlimefunItem {

    public AbstractGears(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     *
     * @return the default lore associated with the gear
     */
    public abstract List<String> defaultLore();

    /**
     *
     * @param event the entity damage event where progress are added
     */
    public abstract void onHit(EntityDamageByEntityEvent event);

    /**
     *
     * @param armor the armor that leveled up
     * @param level the new level of the armor
     * @param p the player who wore the armor
     */
    public abstract void upgradeArmor(ItemStack armor, int level, Player p);

}
