package ne.fnfal113.fnamplifications.gems.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.Getter;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.handlers.GemHandler;
import ne.fnfal113.fnamplifications.gems.implementation.GemKeysEnum;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public abstract class AbstractGem extends SlimefunItem implements GemHandler {

    @Getter
    private int chance;

    public AbstractGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        this(itemGroup, item, recipeType, recipe, 0);
    }

    // This constructor is specifically used for gems that has chances to proc
    // It sets and retrieves data from the config and update the lore to reflect the chance value
    public AbstractGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int defaultChance) {
        super(itemGroup, item, recipeType, recipe);

        initializeSettings(defaultChance);
        GemKeysEnum.GEM_KEYS_ENUM.getGEM_KEYS().add(Keys.createKey(this.getId().toLowerCase()));
    }

    @SneakyThrows
    public void initializeSettings(int defaultChance){
        if(defaultChance != 0) {
            setConfigChanceValues(defaultChance);
            setConfigWorldSettings();
            Utils.upgradeGemLore(this.getItem(), this.getItem().getItemMeta(), this.getId(),
                    "chance", "%", "&e", "%", 4);
            this.chance = FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "chance");
        } else {
            setConfigWorldSettings();
        }
    }

    /**
     *
     * @param chance the chance to set in the config file
     */
    public void setConfigChanceValues(int chance) throws IOException {
        FNAmplifications.getInstance().getConfigManager().setConfigIntegerValues(this.getId(), "chance", chance, "gem-settings", true);
    }

    /**
     * This sets config world settings for the gems, enabled by default on all worlds
     */
    public void setConfigWorldSettings() throws IOException {
        for (World world: Bukkit.getWorlds()) {
            FNAmplifications.getInstance().getConfigManager().setConfigBooleanValues(this.getId() + "." + "world-settings", world.getName() + "_enable", true, "gem-settings", true);
        }
    }

    /**
     *
     * @param worldName the name of the world the player currently resides
     * @param gemID the slimefun gem identifier
     * @return true if gem is enabled in the current world
     */
    public boolean isEnabledInCurrentWorld(String gemID, String worldName){
        return FNAmplifications.getInstance().getConfigManager().getBoolById(gemID + "." + "world-settings", worldName + "_enable");
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
        return Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()), player.getLocation(), Interaction.INTERACT_BLOCK);
    }

    /**
     *
     * @param event the click event where drag and drop is being listened
     * @param player the player who dragged and dropped the gem
     * @param slimefunItem the gem in the inventory that were clicked by the cursor
     * @param currentItem the current item in the inventory the gem was dragged and drop to
     */
    public abstract void onDrag(InventoryClickEvent event, Player player, SlimefunItem slimefunItem, ItemStack currentItem);

}