package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.implementation.ThrowWeaponTask;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnRightClickHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class AxeThrowieGem extends AbstractGem implements OnRightClickHandler {

    public AxeThrowieGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if (WeaponArmorEnum.AXES.isTagged(itemStackToSocket.getType())) {
            bindGem(slimefunGemItem, itemStackToSocket, player);
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes only"));
        }
    }

    @Override
    public void onRightClick(Player player) {
        if(player.isSneaking()) {
            return;
        }

        if(!hasPermissionToThrow(player)) {
            player.sendMessage(Utils.colorTranslator("&eYou don't have the permission to throw here! (Needs block interaction flag enabled)"));
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

        try{
            String pdcValue = pdc.getOrDefault(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "false");
            Vector vector = player.getLocation().add(player.getLocation().getDirection().multiply(9).normalize())
                    .subtract(player.getLocation().toVector()).toVector();

            // creates throw a task from this object instance
            ThrowWeaponTask throwWeaponTask = new ThrowWeaponTask(player, itemStack.clone(), true, false, pdcValue.equalsIgnoreCase("true"), vector);

            throwWeaponTask.runTaskTimer(FNAmplifications.getInstance(), 0L, 1L);
        } catch (IllegalArgumentException e){
            pdc.set(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "true");
            itemStack.setItemMeta(meta);

            return;
        }

        itemStack.setAmount(0);
    }
}