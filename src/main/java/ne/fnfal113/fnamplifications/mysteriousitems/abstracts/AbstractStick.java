package ne.fnfal113.fnamplifications.mysteriousitems.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import lombok.Getter;
import ne.fnfal113.fnamplifications.mysteriousitems.implementation.StickTask;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class AbstractStick extends SlimefunItem implements NotPlaceable {

    @Getter
    private final StickTask stickTask;

    public AbstractStick(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         NamespacedKey levelKey, NamespacedKey damageKey, int effectCount, int levelReq) {
        super(itemGroup, item, recipeType, recipe);

        this.stickTask = new StickTask(levelKey, damageKey, enchantments(), weaponLore(), stickLore(), effectCount, levelReq);
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
     * @return the weapon material of the stick
     */
    public abstract Material getStickMaterial();

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