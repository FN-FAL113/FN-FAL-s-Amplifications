package ne.fnfal113.fnamplifications.gems.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.handlers.GemHandler;
import ne.fnfal113.fnamplifications.gems.implementation.GemKeysEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public abstract class AbstractGem extends SlimefunItem implements GemHandler {

    public AbstractGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        this(itemGroup, item, recipeType, recipe, 0);
    }

    // This constructor is specifically used for gems that has chances to proc
    // It sets and retrieves data from the config and update the lore to reflect the chance value
    @SneakyThrows
    public AbstractGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int chance) {
        super(itemGroup, item, recipeType, recipe);

        if(chance != 0) {
            setConfigValues(chance);
            Utils.upgradeGemLore(this.getItem(), this.getItem().getItemMeta(), this.getId(),
                    "-percent-chance", "%", "&e", "%", 4);
        }
        GemKeysEnum.GEM_KEYS_ENUM.getGEM_KEYS().add(new NamespacedKey(FNAmplifications.getInstance(), this.getId().toLowerCase()));

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
     * @param player the player who owns the gem
     * @param gemName the name of the gem
     */
    public void sendGemMessage(Player player, String gemName){
        player.sendMessage(Utils.colorTranslator("&6" + gemName + " has taken effect!"));
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
     * @param event the click event where drag and drop is being listened
     * @param player the player who dragged and dropped the gem
     */
    public abstract void onDrag(InventoryClickEvent event, Player player);

}