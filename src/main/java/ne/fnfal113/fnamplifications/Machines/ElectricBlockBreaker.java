package ne.fnfal113.fnamplifications.Machines;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemState;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;

import org.apache.commons.lang.Validate;

import org.bukkit.*;

import org.bukkit.block.Block;

import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ElectricBlockBreaker extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    public static final int CHANGE_MODE = 0;

    public static final int ON_OFF = 8;

    private static final Map<BlockPosition, Integer> breakerProgress = new HashMap<>();

    protected static final Map<Location, Boolean> mode = new HashMap<>();

    protected static final Map<Location, Boolean> toggleOnOff = new HashMap<>();

    private static final ItemStack VERSIONED_AMETHYST;

    static {
        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)){
            VERSIONED_AMETHYST = new ItemStack(Material.BUDDING_AMETHYST);
        } else {
            VERSIONED_AMETHYST = new ItemStack(Material.BEDROCK);
        }
    }

    private static final CustomItemStack notOperating = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&cNot Operating...",
            "&ePlace a block facing the dispenser!"
    );

    private static final CustomItemStack noPower = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&cNo Power!",
            "&ePower it up first!"
    );

    private static final CustomItemStack notRunning = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,
            "&cNot Running",
            "&eToggle it on first"
    );

    private static final CustomItemStack breakBlockNaturally = new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
            "&d&lMode:",
            "&eBreak block naturally (No Silk Touch)",
            "Click to change"
    );

    private static final CustomItemStack dropBlockNaturally = new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE,
            "&d&lMode:",
            "&eDrop block naturally (Silk Touch)",
            "Click to change"
    );

    private static final CustomItemStack toggled_On = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE,
            "&d&lToggle:",
            "&eEnabled (Running)",
            "Click to change"
    );

    private static final CustomItemStack toggled_Off = new CustomItemStack(Material.WHITE_STAINED_GLASS_PANE,
            "&d&lToggle:",
            "&eDisabled (Not Running)",
            "Click to change"
    );

    private int energyCapacity = -1;
    private int energyConsumedPerTick = -1;

    private int rate = 2;

    @ParametersAreNonnullByDefault
    public ElectricBlockBreaker(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        createPreset(this, getInventoryTitle(), blockMenuPreset -> {
            for (int i = 0; i < 9; i++) {
                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            blockMenuPreset.addItem(4, noPower);
        });
    }

    public static void setup(){
        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                FNAmpItems.GEAR_PART, FNAmpItems.COMPONENT_PART, FNAmpItems.GEAR_PART,
                new ItemStack(Material.IRON_PICKAXE), FNAmpItems.BASIC_MACHINE_BLOCK, new ItemStack(Material.IRON_PICKAXE),
                FNAmpItems.ALUMINUM_PLATING, FNAmpItems.POWER_COMPONENT, FNAmpItems.ALUMINUM_PLATING})
                .setRate(value.blockBreaker1Ticks())
                .setCapacity(512)
                .setEnergyConsumption(32)
                .register(plugin);

        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), FNAmpItems.COMPONENT_PART, new SlimefunItemStack(FNAmpItems.GEAR_PART, 2),
                new ItemStack(Material.DIAMOND_PICKAXE), new SlimefunItemStack(FNAmpItems.BASIC_MACHINE_BLOCK, 2), new ItemStack(Material.DIAMOND_PICKAXE),
                FNAmpItems.BRASS_PLATING, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2), FNAmpItems.BRASS_PLATING})
                .setRate(value.blockBreaker2Ticks())
                .setCapacity(1024)
                .setEnergyConsumption(64)
                .register(plugin);

        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 3), FNAmpItems.COMPONENT_PART, new SlimefunItemStack(FNAmpItems.GEAR_PART, 3),
                new ItemStack(Material.NETHERITE_PICKAXE), FNAmpItems.HIGHTECH_MACHINE_BLOCK, new ItemStack(Material.NETHERITE_PICKAXE),
                FNAmpItems.REINFORCED_CASING, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2), FNAmpItems.REINFORCED_CASING})
                .setRate(value.blockBreaker3Ticks())
                .setCapacity(2048)
                .setEnergyConsumption(128)
                .register(plugin);
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            @ParametersAreNonnullByDefault
            public void tick(Block b, SlimefunItem sf, Config data) {
                ElectricBlockBreaker.this.tick(b);
            }

            @Override
            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(@Nonnull Block b) {
        BlockMenu invMenu = BlockStorage.getInventory(b);
        Block targetBlock = b.getRelative(((Dispenser) b.getState().getBlockData()).getFacing());
        World location = targetBlock.getWorld();
        onNewInstance(invMenu, b);
        onNewInstanceToggle(invMenu, b);

        ItemStack stack2 = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack stack = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        stack.setItemMeta(meta);

        if(invMenu == null){
            return;
        }

        if(getCharge(b.getLocation()) > 0) {
            invMenu.replaceExistingItem(4, notRunning);
            if (toggleOnOff.get(b.getLocation())) {
                invMenu.replaceExistingItem(4, notOperating);
                if (!targetBlock.getType().equals(Material.AIR)) {
                    if (targetBlock.getType().isBlock() && targetBlock.getType().isSolid() && !isBed(targetBlock) && !isDoor(targetBlock) && !ILLEGAL.contains(targetBlock.getType())) {
                        final BlockPosition pos = new BlockPosition(b);
                        int progress = breakerProgress.getOrDefault(pos, 0);

                        if (invMenu.toInventory() != null && !invMenu.toInventory().getViewers().isEmpty()) {
                            invMenu.replaceExistingItem(4, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aOperating!",
                                    "", "&bRate: " + this.rate + " Block per tick", "&2Breaking block at rate: " + progress
                                    + "/" + this.rate));
                        }

                        if (progress >= this.rate) {
                            progress = 0;
                            if (mode.get(b.getLocation())) {
                                if (BlockStorage.hasBlockInfo(targetBlock) || BlockStorage.hasInventory(targetBlock)) {
                                    location.dropItemNaturally(targetBlock.getLocation(), BlockStorage.retrieve(targetBlock));
                                    targetBlock.setType(Material.AIR);
                                } else {
                                    targetBlock.breakNaturally(stack2);
                                }
                            } else {
                                if (BlockStorage.hasBlockInfo(targetBlock) || BlockStorage.hasInventory(targetBlock)) {
                                    location.dropItemNaturally(targetBlock.getLocation(), BlockStorage.retrieve(targetBlock));
                                    targetBlock.setType(Material.AIR);
                                } else {
                                    ItemStack vanilla = new ItemStack(targetBlock.getType());
                                    if (vanilla.isSimilar(VERSIONED_AMETHYST)) {
                                        targetBlock.setType(Material.AIR);
                                    } else {
                                        targetBlock.breakNaturally(stack);
                                    }
                                }
                            }
                            location.playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
                            b.getWorld().spawnParticle(Particle.SMOKE_LARGE, b.getLocation().add(1, 1, 1), 2, 0.1, 0.1, 0.1);
                        } else {
                            progress++;
                            location.playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_STONE_HIT, 1, 1);
                        }
                        breakerProgress.put(pos, progress);
                        takeCharge(b.getLocation());
                    }
                }
            }
        }
    }

    private static final Set<Material> ILLEGAL = EnumSet.of(
            Material.BEDROCK, Material.END_PORTAL_FRAME, Material.FROSTED_ICE,
            Material.BARRIER, Material.END_GATEWAY
    );

    private boolean isBed(Block targetBlock) {
        return targetBlock.getBlockData() instanceof org.bukkit.block.data.type.Bed;
    }

    private boolean isDoor(Block targetBlock) {
        return targetBlock.getBlockData() instanceof org.bukkit.block.data.type.Door;
    }

    public void onNewInstance(BlockMenu menu, Block b) {
        String breakMode = BlockStorage.getLocationInfo(b.getLocation(), "breakBlockNaturally");
        boolean currentMode = false;
        if (breakMode != null) {
            currentMode = Boolean.parseBoolean(breakMode);
        }
        mode.put(b.getLocation(), currentMode);

        menu.replaceExistingItem(CHANGE_MODE, currentMode ? breakBlockNaturally : dropBlockNaturally);
        menu.addMenuClickHandler(CHANGE_MODE, (p, slot, item, action) -> {
            toggleMode(menu, b);
            return false;
        });
    }

    public void toggleMode(BlockMenu blockMenu, Block b) {
        boolean change = !mode.get(b.getLocation());
        BlockStorage.addBlockInfo(b, "breakBlockNaturally", String.valueOf(change));
        mode.put(b.getLocation(), change);
        ItemStack itemStack = change ? breakBlockNaturally : dropBlockNaturally;
        blockMenu.replaceExistingItem(CHANGE_MODE, itemStack);
    }

    public void onNewInstanceToggle(BlockMenu menu, Block b) {
        String isRunning = BlockStorage.getLocationInfo(b.getLocation(), "toggled_On");
        boolean onOrOff = false;
        if (isRunning != null) {
            onOrOff = Boolean.parseBoolean(isRunning);
        }
        toggleOnOff.put(b.getLocation(), onOrOff);

        menu.replaceExistingItem(ON_OFF, onOrOff ? toggled_On : toggled_Off);
        menu.addMenuClickHandler(ON_OFF, (p, slot, item, action) -> {
            toggleOnOrOff(menu, b);
            return false;
        });
    }

    public void toggleOnOrOff(BlockMenu blockMenu, Block b) {
        boolean toggleBlock = !toggleOnOff.get(b.getLocation());
        BlockStorage.addBlockInfo(b, "toggled_On", String.valueOf(toggleBlock));
        toggleOnOff.put(b.getLocation(), toggleBlock);
        ItemStack itemStack2 = toggleBlock ? toggled_On : toggled_Off;
        blockMenu.replaceExistingItem(ON_OFF, itemStack2);
    }

    public final ElectricBlockBreaker setRate(int rateTicks) {
        this.rate = Math.max(rateTicks, 1);
        return this;
    }


    @Nonnull
    public String getInventoryTitle() {
        return getItemName();
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    public int getEnergyConsumption() {
        return energyConsumedPerTick;
    }

    public final ElectricBlockBreaker setEnergyConsumption(int energyConsumption) {
        Validate.isTrue(energyConsumption > 0, "The energy consumption must be greater than zero!");
        Validate.isTrue(energyCapacity > 0, "You must specify the capacity before you can set the consumption amount.");
        Validate.isTrue(energyConsumption <= energyCapacity, "The energy consumption cannot be higher than the capacity (" + energyCapacity + ')');

        this.energyConsumedPerTick = energyConsumption;
        return this;
    }

    @Override
    public int getCapacity() {
        return energyCapacity;
    }

    public final ElectricBlockBreaker setCapacity(int capacity) {
        Validate.isTrue(capacity > 0, "The capacity must be greater than zero!");

        if (getState() == ItemState.UNREGISTERED) {
            this.energyCapacity = capacity;
            return this;
        } else {
            throw new IllegalStateException("You cannot modify the capacity after the Item was registered.");
        }
    }

    public boolean takeCharge(@Nonnull Location l) {
        Validate.notNull(l, "Can't attempt to take charge from a null location!");

        if (isChargeable()) {
            int charge = getCharge(l);

            if (charge < getEnergyConsumption()) {
                return false;
            }

            setCharge(l, charge - getEnergyConsumption());
            return true;
        } else {
            return true;
        }
    }
}
