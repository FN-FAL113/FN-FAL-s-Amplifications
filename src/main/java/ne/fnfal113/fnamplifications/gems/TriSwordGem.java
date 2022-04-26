package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.OnRightClickHandler;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.gems.implementation.ThrowableWeapon;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("ConstantConditions")
public class TriSwordGem extends AbstractGem implements OnRightClickHandler {

    private final ThrowableWeapon throwableWeapon = new ThrowableWeapon();

    public TriSwordGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player, SlimefunItem slimefunItem, ItemStack currentItem){
        if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType())) {
            new Gem(slimefunItem, currentItem, player).onDrag(event, false);
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on swords only"));
        }
    }

    @Override
    public void onRightClick(Player player){
        if(player.isSneaking()){
            return;
        }
        if(!hasPermissionToThrow(player)){
            player.sendMessage(Utils.colorTranslator("&c&l[FNAmpli" + "&b&lfications] > " + "&eYou don't have the permission to throw here!"));
            return;
        } // check if player has permission to build on the current location

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        try{
            String pdcValue = pdc.getOrDefault(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "false");

            // creates throw a task from this object instance
            throwableWeapon.throwWeapon(player, throwableWeapon.spawnArmorstand(player, itemStack.clone(), true), itemStack.clone(),
                    false, true, true, pdcValue.equalsIgnoreCase("true"));
        } catch (IllegalArgumentException e){
            pdc.set(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "true");
            itemStack.setItemMeta(meta);
            return;
        }

        itemStack.setAmount(0);
    }

}