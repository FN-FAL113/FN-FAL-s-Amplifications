package ne.fnfal113.fnamplifications.Gems.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.io.IOException;
import java.util.List;

public abstract class AbstractGem extends SlimefunItem {

    public AbstractGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    // This constructor is specifically used for gems that has chances to proc
    // It sets and retrieves data from the config and update the lore to reflect the chance value
    @SneakyThrows
    public AbstractGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int chance) {
        super(itemGroup, item, recipeType, recipe);

        setConfigValues(chance);
        Utils.setLore(this.getItem(), this.getId(), "-percent-chance", "%", "&e", "%");
    }

    /**
     *
     * @param chance the chance to set in the config file
     */
    public void setConfigValues(int chance) throws IOException {
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId() + "-percent-chance", chance, "gem-settings");
    }

    /**
     *
     * @param player the thrower of the weapon
     * @return if the player has permission to throw his weapon in the current location
     */
    public boolean hasPermissionToThrow(Player player){
        return Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()), player.getLocation(), Interaction.PLACE_BLOCK);
    }

    /**
     *
     * @param event the click event for the drag and drop
     * @param player the player who dragged and dropped the gem
     */
    public abstract void onDrag(InventoryClickEvent event, Player player);

    /**
     *
     * @param pdc the persistent data that contains the key and amount
     *            value from the itemstack
     * @param itemStack the itemstack that has the needed pdc data
     * @return the amount of gem inside the itemstack if there are any
     */
    public abstract int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack);

}