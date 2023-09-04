package ne.fnfal113.fnamplifications.powergenerators.implementation;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.HologramOwner;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;

import lombok.SneakyThrows;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomPowerGen extends SlimefunItem implements HologramOwner, EnergyNetProvider {

    private final Map<Location, Boolean> HOLO_CACHE = new HashMap<>();

    @SneakyThrows
    public CustomPowerGen(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int dayRate, int nightRate, int output, int storage) {
        super(itemGroup, item, recipeType, recipe);

        setConfigValues(dayRate, nightRate, output, storage);
        setLore(this.getItem());

        addItemHandler(
                toggleHologram(),
                new BlockBreakHandler(false, false) {
                    @Override
                    public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                        if (BlockStorage.getLocationInfo(e.getBlock().getLocation(), "holo_status").startsWith("true")) {
                            removeHologram(e.getBlock());
                        }
                    }
                },
                new BlockPlaceHandler(false) {
                    @Override
                    public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                        BlockStorage.addBlockInfo(e.getBlock(), "holo_status", "true");
                        HOLO_CACHE.put(e.getBlock().getLocation(), true);
                        toggleHologram();
                    }
                });
    }

    public void setLore(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        
        List<String> lore = meta.getLore();
        
        lore.add(Utils.colorTranslator(LoreBuilder.powerBuffer(FNAmplifications.getInstance().getConfigManager().getCustomConfig("power-xpansion-settings").getInt(this.getId() + "." + "storage"))));
        lore.add(Utils.colorTranslator("&8\u21E8 &e\u26A1 &7" + Utils.powerFormat.format(FNAmplifications.getInstance().getConfigManager().getCustomConfig("power-xpansion-settings").getInt(this.getId() + "." + "dayrate")) + " J/t" + " (Day Rate)"));
        lore.add(Utils.colorTranslator("&8\u21E8 &e\u26A1 &7" + Utils.powerFormat.format(FNAmplifications.getInstance().getConfigManager().getCustomConfig("power-xpansion-settings").getInt(this.getId() + "." + "nightrate")) + " J/t" + " (Night Rate)"));
        
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public void setConfigValues(int dayRate, int nightRate, int output, int storage) throws IOException {
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "dayrate", dayRate, "power-xpansion-settings");
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "nightrate", nightRate, "power-xpansion-settings");
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "output", output, "power-xpansion-settings");
        FNAmplifications.getInstance().getConfigManager().initializeConfig(this.getId(), "storage", storage, "power-xpansion-settings");
    }

    @Nonnull
    @Override
    public Vector getHologramOffset(@Nonnull Block b) {
        return new Vector(0.5, 0.7, 0.5);
    }

    public BlockUseHandler toggleHologram(){
        return e -> {
            if(!e.getClickedBlock().isPresent()){
                return;
            }
            Block block = e.getClickedBlock().get();

            String holoStatus = BlockStorage.getLocationInfo(block.getLocation(), "holo_status");
            if(holoStatus == null) {
                BlockStorage.addBlockInfo(block.getLocation(), "holo_status", "true");
                HOLO_CACHE.put(block.getLocation(), true);
                toggleHologram();
            } else {
                if(HOLO_CACHE.get(block.getLocation())){
                    HOLO_CACHE.put(block.getLocation(), false);
                    BlockStorage.addBlockInfo(block.getLocation(), "holo_status", "false");
                    removeHologram(block);
                } else {
                    HOLO_CACHE.put(block.getLocation(), true);
                    BlockStorage.addBlockInfo(block.getLocation(), "holo_status", "true");
                    toggleHologram();
                }
            }
        };
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        final int stored = getCharge(l);

        // for first server boot up, cache location and hologram status per generator
        if(!HOLO_CACHE.containsKey(l)){
            if(BlockStorage.getLocationInfo(l, "holo_status") != null){
                HOLO_CACHE.put(l, Boolean.parseBoolean(BlockStorage.getLocationInfo(l, "holo_status")));
            } else {
                BlockStorage.addBlockInfo(l, "holo_status", "true");
                HOLO_CACHE.put(l, true);
            }
        } else {
            if(HOLO_CACHE.get(l)) {
                String charge = getCharge(l) <= 0 ? Utils.colorTranslator("&8" + getCharge(l)) : Utils.colorTranslator("&a" + getCharge(l));
                updateHologram(l.getBlock(), Utils.colorTranslator("&eStored &a⚡: " + charge));
            }
        }

        return stored < getCapacity() ? getGeneratingAmount(l.getBlock(), l.getWorld()) : 0;
    }

    @Override
    public boolean willExplode(@Nonnull Location l, @Nonnull Config data) {
        return false;
    }

    private int getGeneratingAmount(@Nonnull Block loc, @Nonnull World world) {
        if (world.getEnvironment() == World.Environment.NETHER) {
            return this.getDayRate();
        }
        if (world.getEnvironment() == World.Environment.THE_END) {
            return this.getNightRate();
        }

        if (world.isThundering() || world.hasStorm() || world.getTime() >= 13000
                || loc.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15) {
            return this.getNightRate();
        } else {
            return this.getDayRate();
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int getCapacity() {
        return FNAmplifications.getInstance().getConfigManager().getCustomConfig("power-xpansion-settings").getInt(this.getId() + "." + "storage");
    }

    public int getDayRate(){
        return FNAmplifications.getInstance().getConfigManager().getCustomConfig("power-xpansion-settings").getInt(this.getId() + "." + "dayrate");
    }

    public int getNightRate(){
        return FNAmplifications.getInstance().getConfigManager().getCustomConfig("power-xpansion-settings").getInt(this.getId() + "." + "nightrate");
    }

}