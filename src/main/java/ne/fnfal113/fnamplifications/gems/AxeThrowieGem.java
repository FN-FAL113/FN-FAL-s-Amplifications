package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.implementation.ThrowableWeapon;
import ne.fnfal113.fnamplifications.gems.implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnRightClickHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("ConstantConditions")
public class AxeThrowieGem extends AbstractGem implements OnRightClickHandler {

    private final ThrowableWeapon throwableWeapon = new ThrowableWeapon();

    public AxeThrowieGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){
        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());

        if(slimefunItem != null && currentItem != null) {
            if (WeaponArmorEnum.AXES.isTagged(currentItem.getType())) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes only"));
            }
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
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(FNAmplifications.getInstance(), "return_weapon");

        throwableWeapon.throwWeapon(player, throwableWeapon.spawnArmorstand(player, itemStack.clone(), false), itemStack.clone(),
                true, false, false, Boolean.parseBoolean(pdc.getOrDefault(key, PersistentDataType.STRING, "false")));

        itemStack.setAmount(0);
    }
}