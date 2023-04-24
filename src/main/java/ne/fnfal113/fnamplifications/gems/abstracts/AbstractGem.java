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
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.implementation.GemKeysEnum;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public abstract class AbstractGem extends SlimefunItem {

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
        GemKeysEnum.GEM_KEYS.getGemKeyList().add(Keys.createKey(this.getId().toLowerCase()));
    }

    @SneakyThrows
    public void initializeSettings(int defaultChance){
        // only gem with default chance above 0 but must also implement gem upgrade interface
        if(defaultChance != 0) { 
            setConfigChanceValues(defaultChance);
            setConfigWorldSettings();

            Utils.setGemTierLore(this.getItem(), this.getId(),
                    "chance", "%", "&e", "%", 4, "gem-settings");
            this.chance = FNAmplifications.getInstance().getConfigManager().getCustomConfig("gem-settings").getInt(this.getId() + "." + "chance");
        } else {
            setConfigWorldSettings();
        }
    }

    /**
     *
     * @param chance the chance to set in the config file
     */
    public void setConfigChanceValues(int chance) throws IOException {
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "chance", chance, "gem-settings");
    }

    /**
     * This sets config world settings for the gems, enabled by default on all worlds
     */
    public void setConfigWorldSettings() throws IOException {
        for (World world: Bukkit.getWorlds()) {
            FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId() + "." + "world-settings", world.getName() + "_enable", true, "gem-settings");
        }
    }

    /**
     *
     * @param worldName the name of the world the player currently resides
     * @return true if gem is enabled in the current world
     */
    public boolean isEnabledInCurrentWorld(String worldName){
        return FNAmplifications.getInstance().getConfigManager().getCustomConfig("gem-settings").getBoolean(this.getId() + "." + "world-settings" + "." + worldName + "_enable");
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

    public void bindGem(SlimefunItem slimefunGemItem, ItemStack itemStackToSocket, Player player){
        new Gem(slimefunGemItem, itemStackToSocket, player).startSocket();
    }

    /**
     *
     * @param player the player who dragged and dropped the gem
     * @param slimefunItem the slimefun gem in the inventory that is attached to the cursor
     * @param gemItem the itemstack gem in the inventory that is attached to the cursor
     * @param itemStackToSocket the itemstack to socket the gem
     */
    public abstract void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket);

}