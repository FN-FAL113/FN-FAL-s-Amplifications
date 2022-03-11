package ne.fnfal113.fnamplifications.mysteriousitems.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class AbstractStick extends SlimefunItem {

    public AbstractStick(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     *
     * @return the assigned enchantments as key along with its level a value
     */
    public abstract Map<Enchantment, Integer> enchantments();

    /**
     *
     * @return the lore when the weapon is summoned
     */
    public abstract String weaponLore();

    /**
     *
     * @return the default lore of the stick
     */
    public abstract String stickLore();

    /**
     *
     * @param e the interact event specifically uses right-click action
     *          to summon the weapon
     */
    public abstract void interact(PlayerInteractEvent e);

    /**
     *
     * @param event the event for damaging another entity and
     *              has a chance to consume levels from the player
     */
    public abstract void onSwing(EntityDamageByEntityEvent event);

    @Override
    public boolean isEnchantable() {
        return false;
    }

    @Override
    public boolean isDisenchantable() {
        return false;
    }

    @Override
    public boolean isUseableInWorkbench() {
        return false;
    }

}