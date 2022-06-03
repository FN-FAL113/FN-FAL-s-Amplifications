package ne.fnfal113.fnamplifications.gears;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gears.abstracts.AbstractGears;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class FnHelmet extends AbstractGears {

    public FnHelmet(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, Keys.FN_GEAR_HELMET, Keys.FN_GEAR_HELMET_LEVEL, Keys.FN_GEAR_HELMET_FINAL,
                20, 100, 30, 3, EquipmentSlot.HEAD);
    }

}