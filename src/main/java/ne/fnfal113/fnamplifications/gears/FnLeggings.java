package ne.fnfal113.fnamplifications.gears;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gears.abstracts.AbstractGears;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class FnLeggings extends AbstractGears {

    public FnLeggings(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, Keys.FN_GEAR_LEGGINGS, Keys.FN_GEAR_LEGGINGS_LEVEL, Keys.FN_GEAR_LEGGINGS_FINAL,
                30, 105, 25, 3, EquipmentSlot.LEGS);
    }

}