package ne.fnfal113.fnamplifications.powergenerators;

import javax.annotation.Nonnull;

import dev.j3fftw.extrautils.utils.LoreBuilderDynamic;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.HologramOwner;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;

import lombok.SneakyThrows;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
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
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import ne.fnfal113.fnamplifications.config.ReturnConfValue;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PowahGenerator extends SlimefunItem implements HologramOwner, EnergyNetProvider  {

    private static final ReturnConfValue value = new ReturnConfValue();

    private final Map<Location, Boolean> HOLO_CACHE = new HashMap<>();

    @SneakyThrows
    public PowahGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int dayRate, int nightRate, int output, int storage) {
        super(itemGroup, item, recipeType, recipe);

        setConfigValues(dayRate, nightRate, output, storage);
        setLore(this.getItem());

        addItemHandler(toggleHologram(),
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
        lore.add(Utils.colorTranslator(LoreBuilderDynamic.powerBuffer(FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-storage"))));
        lore.add(Utils.colorTranslator(LoreBuilderDynamic.powerPerTick(FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-dayrate")) + " (Day Rate)"));
        lore.add(Utils.colorTranslator(LoreBuilderDynamic.powerPerTick(FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-nightrate")) + " (Night Rate)"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public void setConfigValues(int dayRate, int nightRate, int output, int storage) throws IOException {
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId() + "-dayrate", dayRate, "power-xpansion-settings");
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId() + "-nightrate", nightRate, "power-xpansion-settings");
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId() + "-output", output, "power-xpansion-settings");
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId() + "-storage", storage, "power-xpansion-settings");
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
        Validate.notNull(l.getWorld());

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
        return FNAmplifications.getInstance().getConfigManager().getValueById(this. getId() + "-storage");
    }

    public int getDayRate(){
        return FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-dayrate");
    }

    public int getNightRate(){
        return FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-nightrate");
    }

    public static ItemStack LITEX_ITEMSTACK_TIN = SlimefunItems.TIN_INGOT;
    public static ItemStack LITEX_ITEMSTACK_SOLDER = SlimefunItems.SOLDER_INGOT;
    public static ItemStack LITEX_ITEMSTACK_BRONZE = SlimefunItems.BRONZE_INGOT;
    public static ItemStack LITEX_ITEMSTACK_DURALUMIN = SlimefunItems.DURALUMIN_INGOT;
    public static ItemStack LITEX_ITEMSTACK_BRASS = SlimefunItems.ALUMINUM_BRASS_INGOT;
    public static ItemStack LITEX_ITEMSTACK_CORINTHIAN = SlimefunItems.CORINTHIAN_BRONZE_INGOT;
    public static ItemStack LITEX_ITEMSTACK_STEEL = SlimefunItems.STEEL_INGOT;
    public static ItemStack LITEX_ITEMSTACK_DAMASCUS = SlimefunItems.DAMASCUS_STEEL_INGOT;
    public static ItemStack LITEX_ITEMSTACK_HARDENED = SlimefunItems.HARDENED_METAL_INGOT;
    public static ItemStack LITEX_ITEMSTACK_REINFORCED = SlimefunItems.REINFORCED_ALLOY_INGOT;

    static {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("LiteXpansion") && value.liteXpansionRecipe()) {
            SlimefunItem iridium = SlimefunItem.getById("IRIDIUM");
            SlimefunItem uu_matter = SlimefunItem.getById("UU_MATTER");
            if (iridium != null && uu_matter != null && !iridium.isDisabled() && !uu_matter.isDisabled()) {
                LITEX_ITEMSTACK_TIN = iridium.getItem().clone();
                LITEX_ITEMSTACK_SOLDER = iridium.getItem().clone();
                LITEX_ITEMSTACK_BRONZE = iridium.getItem().clone();
                LITEX_ITEMSTACK_DURALUMIN = iridium.getItem().clone();
                LITEX_ITEMSTACK_BRASS = iridium.getItem().clone();
                LITEX_ITEMSTACK_CORINTHIAN = iridium.getItem().clone();
                LITEX_ITEMSTACK_STEEL = iridium.getItem().clone();
                LITEX_ITEMSTACK_HARDENED = iridium.getItem().clone();
                LITEX_ITEMSTACK_DAMASCUS = iridium.getItem().clone();
                LITEX_ITEMSTACK_HARDENED = iridium.getItem().clone();
                LITEX_ITEMSTACK_REINFORCED = iridium.getItem().clone();
            }
        }
    }

    public static void setup(SlimefunAddon instance) {
        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, new CustomItemStack(LITEX_ITEMSTACK_TIN, 3), FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, SlimefunItems.SOLAR_GENERATOR_4, FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, FNAmpItems.ALUMINUM_PLATING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }, 236, 138, 20, 100000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R1, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_SOLDER, 6), FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, FNAmpItems.ALUMINUM_PLATING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }, 472, 276, 40, 200000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R2, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_BRONZE, 9), FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, FNAmpItems.ALUMINUM_PLATING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }, 944, 552, 60, 300000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R4, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R3, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.BASIC_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_DURALUMIN, 12), FNAmpItems.BASIC_MACHINE_BLOCK,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, FNAmpItems.GOLD_PLATING, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }, 1888, 1104, 120, 400000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R5, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R4, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_BRASS, 15), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER1, FNAmpItems.GOLD_PLATING, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }, 3064, 1784, 480, 5000000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R6, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R5, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_CORINTHIAN, 18), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER2, FNAmpItems.GOLD_PLATING, SlimefunItems.REINFORCED_ALLOY_INGOT
        }, 5224, 3128, 480, 600000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R7, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R6, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_STEEL, 21), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER3, FNAmpItems.BRASS_PLATING, SlimefunItems.BLISTERING_INGOT
        }, 7128, 5142, 960, 700000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R8, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R7, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_DAMASCUS, 24), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER4, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.BLISTERING_INGOT_2
        }, 9420, 6752, 1200, 800000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R9, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R8, FNAmpItems.GEAR_PART,
                FNAmpItems.HIGHTECH_MACHINE_BLOCK, new CustomItemStack(LITEX_ITEMSTACK_HARDENED, 27), FNAmpItems.HIGHTECH_MACHINE_BLOCK,
                FNAmpItems.FN_FAL_GENERATOR_TIER5, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.REINFORCED_ALLOY_INGOT
        }, 11384, 7431, 1440, 900000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R10, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R9, FNAmpItems.GEAR_PART,
                SlimefunItems.ENERGIZED_CAPACITOR, new CustomItemStack(LITEX_ITEMSTACK_HARDENED, 32), SlimefunItems.ENERGIZED_CAPACITOR,
                FNAmpItems.FN_FAL_GENERATOR_TIER6, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.URANIUM
        }, 13692, 8128, 1520, 1000000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R11, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R10, FNAmpItems.GEAR_PART,
                SlimefunItems.ENERGIZED_CAPACITOR, new CustomItemStack(LITEX_ITEMSTACK_REINFORCED, 36), SlimefunItems.ENERGIZED_CAPACITOR,
                FNAmpItems.FN_FAL_GENERATOR_TIER7, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.NEPTUNIUM
        }, 15984, 9462, 1640, 1500000).register(instance);

        new PowahGenerator(FNAmpItems.POWER_GENERATORS, FNAmpItems.FN_XPANSION_POWER_R12, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.POWER_COMPONENT, FNAmpItems.FN_XPANSION_POWER_R11, FNAmpItems.GEAR_PART,
                SlimefunItems.ENERGIZED_CAPACITOR, new CustomItemStack(LITEX_ITEMSTACK_REINFORCED, 40), SlimefunItems.ENERGIZED_CAPACITOR,
                FNAmpItems.REINFORCED_CASING, FNAmpItems.FN_XPANSION_POWER_R7, SlimefunItems.BOOSTED_URANIUM
        }, 17421, 10128, 1780, 2000000).register(instance);
    }

}