package ne.fnfal113.fnamplifications.gems.handlers;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.implementation.UpgradedGem;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

/**
 * Serves as the interface for upgradeable gems
 * and will be used to check the instance of the
 * gem based from the class that implements
 * this interface
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
     * @param slimefunItem the gem that will be used
     * @param currentItem the item to socket
     * @param event the click event to listen for
     * @param player the player who initiated the process for binding
     * @param id the slimefun id of the gem
     */
    default void upgradeGem(SlimefunItem slimefunItem, ItemStack currentItem, InventoryClickEvent event, Player player, String id){
        int currentTier = getTier(Objects.requireNonNull(event.getCursor()), id);

        new UpgradedGem(slimefunItem, currentItem, player).upgradeExistingGem(event, event.getCursor(), currentTier);
    }

    default int getTier(ItemStack itemStack, String id){
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(
                new NamespacedKey(FNAmplifications.getInstance(), id + "_gem_tier"),
                PersistentDataType.INTEGER, 4);
    }

}
