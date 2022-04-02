package ne.fnfal113.fnamplifications.staffs.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public abstract class AbstractStaff extends SlimefunItem {

    @SneakyThrows
    public AbstractStaff(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int maxUses) {
        super(itemGroup, item, recipeType, recipe);

        setConfigValues(maxUses);
        Utils.setLore(this.getItem(), this.getId(), "-max-uses", "left", "&e", " left");
    }

    /**
     * this adds the config entries if none exist
     * @param maxUses the max uses value that will be set in the config
     */
    public void setConfigValues(int maxUses) throws IOException {
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId() + "-max-uses",  maxUses, "staffs-settings");
    }

    /**
     *
     * @param event the interact event specifically used here for right or left click action
     */
    public abstract void onClick(PlayerInteractEvent event);
}