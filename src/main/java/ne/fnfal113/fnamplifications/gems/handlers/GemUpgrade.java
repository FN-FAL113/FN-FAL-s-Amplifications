package ne.fnfal113.fnamplifications.gems.handlers;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.gems.implementation.UpgradedGem;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

/**
 * Serves as the interface for upgradeable gems
 * and will be used to check the instance of the
 * gem based from the class that implements
 * this interface
 * @author FN-FAL113
 */
public interface GemUpgrade {

    /**
     *
     * @param itemStack the gem as itemstack instead of SlimefunItemStack
     * @param id the id of the gem
     * @return true if gem is an upgraded variant
     */
    default boolean isUpgradeGem(ItemStack itemStack, String id){
        return getTier(itemStack, id) != 4;
    }

    /**
     *
     * @param slimefunGemItem the gem as an SlimefunItem instead of ItemStack
     * @param itemStackToSocket the item to socket
     * @param gemItemStack the gem as an itemstack instead of SlimefunItemStack
     * @param player the player who initiated the process for binding
     */
    default void upgradeGem(SlimefunItem slimefunGemItem, ItemStack itemStackToSocket, ItemStack gemItemStack, Player player){
        int gemTier = getTier(Objects.requireNonNull(gemItemStack), slimefunGemItem.getId());

        new UpgradedGem(slimefunGemItem, itemStackToSocket, player).upgradeExistingGem(gemItemStack, gemTier);
    }

    default int getTier(ItemStack itemStack, String id){
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(
                Keys.createKey(id + "_gem_tier"), PersistentDataType.INTEGER, 4);
    }

}
