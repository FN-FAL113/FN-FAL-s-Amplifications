package ne.fnfal113.fnamplifications.gears.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public abstract class AbstractGears extends SlimefunItem {

    public AbstractGears(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        try {
            FNAmplifications.getInstance().getConfigManager()
                    .setBooleanValues(this.getId(), "unbreakable", false, "fn-gear-unbreakable-settings");
            setUnbreakable();
        } catch (IOException e){
            FNAmplifications.getInstance().getLogger()
                    .info("An error has occurred upon setting the armor unbreakable settings! Please report on github!");
            e.printStackTrace();
        }
    }

    public final void setUnbreakable() {
        ItemMeta meta = this.getItem().getItemMeta();
        meta.setUnbreakable(FNAmplifications.getInstance().getConfigManager().getBoolById(this.getId(), "unbreakable"));
        this.getItem().setItemMeta(meta);
    }

    /**
     * This is where player gets the progress by being hit by living entities
     * @param event the entity damage event where progress are added
     */
    public abstract void onHit(EntityDamageByEntityEvent event);

    /**
     * When the armor levels up, add or upgrade any enchants/attributes
     * @param armor the armor that leveled up
     * @param level the new level of the armor
     * @param p the player who wore the armor
     */
    public abstract void upgradeArmor(ItemStack armor, int level, Player p);

}
