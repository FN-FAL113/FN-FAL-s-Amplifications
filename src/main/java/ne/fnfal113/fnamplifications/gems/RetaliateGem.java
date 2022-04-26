package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("ConstantConditions")
public class RetaliateGem extends AbstractGem {

    public RetaliateGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onDrag(InventoryClickEvent event, Player player, SlimefunItem slimefunItem, ItemStack currentItem){
        if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType())) {
            if (hasNeededGem(currentItem.getItemMeta().getPersistentDataContainer())) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, true);
            } else {
                player.sendMessage(Utils.colorTranslator("&eWeapon is missing the needed gem in the weapon, please read the lore of the gem!"));
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes and swords only"));
        }
    }

    public boolean hasNeededGem(PersistentDataContainer pdc){
        return pdc.has(Keys.RETURN_DAMNATION_KEY, PersistentDataType.STRING) ||
                pdc.has(Keys.RETURN_TRISWORD_KEY, PersistentDataType.STRING) ||
                pdc.has(Keys.RETURN_AXE_KEY, PersistentDataType.STRING);
    }
}