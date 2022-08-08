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
     * @return true if gem is an upgraded variant otherwise gem is not an upgraded gem
     */
    default boolean isUpgradeGem(ItemStack itemStack, String id){
        return getTier(itemStack, id) != 4;
    }

    /**
     *
     * @param slimefunGemItem the gem as an SlimefunItem instead of ItemStack
     * @param currentItem the item to socket
     * @param gem the gem as an itemstack instead of SlimefunItemStack
     * @param player the player who initiated the process for binding
     * @param id the slimefun id of the gem
     */
    default void upgradeGem(SlimefunItem slimefunGemItem, ItemStack currentItem, ItemStack gem, Player player, String id){
        int currentTier = getTier(Objects.requireNonNull(gem), id);

        new UpgradedGem(slimefunGemItem, currentItem, player).upgradeExistingGem(gem, currentTier);
    }

    default int getTier(ItemStack itemStack, String id){
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(
                Keys.createKey(id + "_gem_tier"), PersistentDataType.INTEGER, 4);
    }

}
