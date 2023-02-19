package ne.fnfal113.fnamplifications.gems.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractGemUnbinder extends SlimefunItem {

    @Getter
    private final int chance;

    @SneakyThrows
    public AbstractGemUnbinder(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int chance) {
        super(itemGroup, item, recipeType, recipe);

        setConfigValues(chance);
        Utils.setLoreByConfigValue(this.getItem(), this.getId(), "unbind-chance", "%", "&e", "%", "unbind-gem-settings");
        this.chance = FNAmplifications.getInstance().getConfigManager().getCustomConfig("unbind-gem-settings").getInt(this.getId() + "." + "unbind-chance");
    }

    /**
     *
     * @param chance the chance to set in the config file
     */
    public void setConfigValues(int chance) {
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "unbind-chance", chance, "unbind-gem-settings");
    }

    /**
     *
     * @param event the interact event
     * @param player the player who right-clicked
     * @return the gem chance to unbind the gem
     */
    public abstract int onRightClick(PlayerInteractEvent event, Player player);

}