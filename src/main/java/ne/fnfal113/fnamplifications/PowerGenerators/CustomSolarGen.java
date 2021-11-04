package ne.fnfal113.fnamplifications.PowerGenerators;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

public class CustomSolarGen extends SlimefunItem implements EnergyNetProvider {
    private final int dayEnergy;
    private final int nightEnergy;
    private final int capacity;

    public CustomSolarGen(ItemGroup itemGroup, int dayEnergy, int nightEnergy, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int capacity) {
        super(itemGroup, item, recipeType, recipe);
        this.dayEnergy = dayEnergy;
        this.nightEnergy = nightEnergy;
        this.capacity = capacity;
    }

    public CustomSolarGen(ItemGroup itemGroup, int dayEnergy, int nightEnergy, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        this(itemGroup, dayEnergy, nightEnergy, item, recipeType, recipe, 0);
    }

    public int getDayEnergy() {
        return this.dayEnergy;
    }

    public int getNightEnergy() {
        return this.nightEnergy;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public final boolean isChargeable() {
        return true;
    }

    public int getGeneratedOutput(Location l, Config data) {
        World world = l.getWorld();
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
        this.addItemHandler(new ItemHandler[]{handler});
    }
}
