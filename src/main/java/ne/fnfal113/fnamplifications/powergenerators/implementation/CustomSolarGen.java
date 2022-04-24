package ne.fnfal113.fnamplifications.powergenerators.implementation;

import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.List;

public class CustomSolarGen extends SlimefunItem implements EnergyNetProvider {

    private final int dayEnergy;
    private final int nightEnergy;
    private final int capacity;

    @SneakyThrows
    public CustomSolarGen(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int dayEnergy, int nightEnergy, int capacity) {
        super(itemGroup, item, recipeType, recipe);
        this.dayEnergy = dayEnergy;
        this.nightEnergy = nightEnergy;
        this.capacity = capacity;

        setConfigValues(dayEnergy, capacity);
        setLore(this.getItem());
    }

    public CustomSolarGen(ItemGroup itemGroup, int dayEnergy, int nightEnergy, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        this(itemGroup, item, recipeType, recipe, dayEnergy, nightEnergy, 0);
    }

    public void setLore(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(Utils.colorTranslator(LoreBuilder.powerBuffer(FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "capacity"))));
        lore.add(Utils.colorTranslator(LoreBuilder.powerPerSecond(FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "dayEnergy"))));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public void setConfigValues(int dayEnergy, int capacity) throws IOException {
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId(),"dayEnergy", dayEnergy, "solar-generator-settings");
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId(), "capacity", capacity, "solar-generator-settings");
    }

    public int getDayEnergy() {
        return FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "dayEnergy");
    }

    public int getCapacity() {
        return FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "capacity");
    }

    public int getNightEnergy() {
        return 0;
    }

    public final boolean isChargeable() {
        return true;
    }

    public int getGeneratedOutput(Location l, Config data) {
        World world = l.getWorld();
        Validate.notNull(world);
        if (world.getEnvironment() != World.Environment.NORMAL) {
            return 0;
        } else {
            boolean isDaytime = this.isDaytime(world);
            if (!isDaytime && this.getNightEnergy() < 1) {
                return 0;
            } else if (world.isChunkLoaded(l.getBlockX() >> 4, l.getBlockZ() >> 4) && l.getBlock().getLightFromSky() >= 15) {
                return isDaytime ? this.getDayEnergy() : this.getNightEnergy();
            } else {
                return 0;
            }
        }
    }

    private boolean isDaytime(World world) {
        long time = world.getTime();
        return !world.hasStorm() && !world.isThundering() && (time < 12300L || time > 23850L);
    }

    public void preRegister() {
        super.preRegister();
        BlockUseHandler handler = PlayerRightClickEvent::cancel;
        this.addItemHandler(handler);
    }
}
