package ne.fnfal113.fnamplifications.staffs.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.Getter;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.implementations.StaffTask;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.logging.Level;

public abstract class AbstractStaff extends SlimefunItem {

    @Getter
    private final NamespacedKey defaultUsageKey;
    @Getter
    private final StaffTask staffTask;

    @SneakyThrows
    public AbstractStaff(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int maxUses, NamespacedKey identifer) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = identifer;
        this.staffTask = new StaffTask(getDefaultUsageKey(), this.getId());
        setConfigValues(maxUses, this.getItem().getType().toString());
        setMaterial();
        Utils.setLoreByConfigValue(this.getItem(), this.getId(), "max-uses", "left", "&e", " left", "staffs-settings");
    }

    public void setMaterial(){
        Material matchMaterial = Material.matchMaterial(FNAmplifications.getInstance().getConfigManager().getCustomConfig("staffs-settings").getString(this.getId() + "." + "staff-material").toUpperCase());

        if(matchMaterial != null){
            this.getItem().setType(matchMaterial);
        } else {
            FNAmplifications.getInstance().getLogger().log(Level.INFO, "Invalid Material ID for " + this.getId() + (". Using BLAZE_ROD as default material."));
        }
    }

    public boolean hasPermissionToCast(String staffName, Player player, Location location){
        if (Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()), location,
                Interaction.INTERACT_BLOCK)) {
            return true;
        }

        player.sendMessage(Utils.colorTranslator("&cYou don't have permission to cast " + staffName + " here!"));
        return false;
    }

    /**
     * this adds the config entries if none exist
     * @param maxUses the max uses value that will be set in the config
     */
    public void setConfigValues(int maxUses, String material) throws IOException {
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "max-uses", maxUses, "staffs-settings");
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "staff-material", material, "staffs-settings");
    }

    /**
     *
     * @param event the interact event specifically used here for right or left click action
     */
    public abstract void onClick(PlayerInteractEvent event);

}