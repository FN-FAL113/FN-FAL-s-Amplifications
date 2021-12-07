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
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ElectricBlockBreaker extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public static final Map<Location, BlockBreakerCache> CACHE_MAP = new HashMap<>();

    private static final SlimefunAddon PLUGIN = FNAmplifications.getInstance();
    private static final ReturnConfValue VALUE = new ReturnConfValue();
    public static final int CHANGE_MODE = 0;
    public static final int ON_OFF = 8;
    private static final ItemStack VERSIONED_AMETHYST;

    private static final CustomItemStack NOT_OPERATING = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
        "&cNot Operating...",
        "&ePlace a block facing the dispenser!"
    );

    private static final CustomItemStack NO_POWER = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
        "&cNo Power!",
        "&ePower it up first!"
    );

    private static final CustomItemStack NOT_RUNNING = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,
        "&cNot Running",
        "&eToggle it on first"
    );

    private static final CustomItemStack BREAK_BLOCK_NATURALLY = new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
        "&d&lMode:",
        "&eBreak block naturally (No Silk Touch)",
        "Click to change"
    );

    private static final CustomItemStack DROP_BLOCK_NATURALLY = new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE,
        "&d&lMode:",
        "&eDrop block naturally (Silk Touch)",
        "Click to change"
    );

    private static final CustomItemStack TOGGLED_ON = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE,
        "&d&lToggle:",
        "&eEnabled (Running)",
        "Click to change"
    );

    private static final CustomItemStack TOGGLED_OFF = new CustomItemStack(Material.WHITE_STAINED_GLASS_PANE,
        "&d&lToggle:",
        "&eDisabled (Not Running)",
        "Click to change"
    );

    public static final ItemStack DUMMY_PICK = new ItemStack(Material.DIAMOND_PICKAXE);
    public static final ItemStack DUMMY_SILK_PICK = new ItemStack(Material.DIAMOND_PICKAXE);

    private static final Set<Material> ILLEGAL = EnumSet.of(
        Material.BEDROCK,
        Material.END_PORTAL_FRAME,
        Material.FROSTED_ICE,
        Material.BARRIER,
        Material.END_GATEWAY
    );

    static {
        // Amethyst
        if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)) {
            VERSIONED_AMETHYST = new ItemStack(Material.BUDDING_AMETHYST);
        } else {
            VERSIONED_AMETHYST = new ItemStack(Material.BEDROCK);
        }

        // Silk
        ItemMeta meta = DUMMY_SILK_PICK.getItemMeta();
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        DUMMY_SILK_PICK.setItemMeta(meta);

        // Illegal Materials
        ILLEGAL.addAll(Tag.BEDS.getValues());
        ILLEGAL.addAll(Tag.DOORS.getValues());
    }

    private int energyCapacity = -1;
    private int energyConsumedPerTick = -1;

    private int rate = 2;

    @ParametersAreNonnullByDefault
    public ElectricBlockBreaker(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemHandler(
            new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return true;
                }

                @Override
                @ParametersAreNonnullByDefault
                public void tick(Block block, SlimefunItem slimefunItem, Config data) {
                    onTick(block);
                }
            },
            new BlockPlaceHandler(false) {
                @Override
                public void onPlayerPlace(@Nonnull BlockPlaceEvent event) {
                    BlockStorage.addBlockInfo(event.getBlock(), "owner", event.getPlayer().getUniqueId().toString());
                }
            }
        );

        createPreset(this, getInventoryTitle(), blockMenuPreset -> {
            for (int i = 0; i < 9; i++) {
                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            blockMenuPreset.addItem(4, NO_POWER);
        });
    }

    public static void setup() {
        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_1, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
            FNAmpItems.GEAR_PART, FNAmpItems.COMPONENT_PART, FNAmpItems.GEAR_PART,
            new ItemStack(Material.IRON_PICKAXE), FNAmpItems.BASIC_MACHINE_BLOCK, new ItemStack(Material.IRON_PICKAXE),
            FNAmpItems.ALUMINUM_PLATING, FNAmpItems.POWER_COMPONENT, FNAmpItems.ALUMINUM_PLATING})
            .setRate(VALUE.blockBreaker1Ticks())
            .setCapacity(512)
            .setEnergyConsumption(32)
            .register(PLUGIN);

        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_2, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
            new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), FNAmpItems.COMPONENT_PART, new SlimefunItemStack(FNAmpItems.GEAR_PART, 2),
            new ItemStack(Material.DIAMOND_PICKAXE), new SlimefunItemStack(FNAmpItems.BASIC_MACHINE_BLOCK, 2), new ItemStack(Material.DIAMOND_PICKAXE),
            FNAmpItems.BRASS_PLATING, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2), FNAmpItems.BRASS_PLATING})
            .setRate(VALUE.blockBreaker2Ticks())
            .setCapacity(1024)
            .setEnergyConsumption(64)
            .register(PLUGIN);

        new ElectricBlockBreaker(FNAmpItems.MACHINES, FNAmpItems.FN_BLOCK_BREAKER_3, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
            new SlimefunItemStack(FNAmpItems.GEAR_PART, 3), FNAmpItems.COMPONENT_PART, new SlimefunItemStack(FNAmpItems.GEAR_PART, 3),
            new ItemStack(Material.NETHERITE_PICKAXE), FNAmpItems.HIGHTECH_MACHINE_BLOCK, new ItemStack(Material.NETHERITE_PICKAXE),
            FNAmpItems.REINFORCED_CASING, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2), FNAmpItems.REINFORCED_CASING})
            .setRate(VALUE.blockBreaker3Ticks())
            .setCapacity(2048)
            .setEnergyConsumption(128)
            .register(PLUGIN);
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {
            @Override
            public void init() {
                for (int i = 0; i < 9; i++) {
                    addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return ElectricBlockBreaker.this.canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[]{0};
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                String breakMode = BlockStorage.getLocationInfo(b.getLocation(), "breakBlockNaturally");
                String isRunning = BlockStorage.getLocationInfo(b.getLocation(), "toggled_On");
                String owner = BlockStorage.getLocationInfo(b.getLocation(), "owner");

                // Mode
                boolean currentMode = false;
                if (breakMode != null) {
                    currentMode = Boolean.parseBoolean(breakMode);
                }

                menu.replaceExistingItem(CHANGE_MODE, currentMode ? BREAK_BLOCK_NATURALLY : DROP_BLOCK_NATURALLY);
                menu.addMenuClickHandler(CHANGE_MODE, (p, slot, item, action) -> {
                    toggleMode(menu);
                    return false;
                });

                // isRunning
                boolean isOn = false;
                if (isRunning != null) {
                    isOn = Boolean.parseBoolean(isRunning);
                }

                menu.replaceExistingItem(ON_OFF, isOn ? TOGGLED_ON : TOGGLED_OFF);
                menu.addMenuClickHandler(ON_OFF, (p, slot, item, action) -> {
                    toggleOnOrOff(menu);
                    return false;
                });

                // owner
                UUID ownerUUID = null;
                if (owner != null) {
                    ownerUUID = UUID.fromString(owner);
                }

                BlockBreakerCache cache = new BlockBreakerCache(0, currentMode, isOn, ownerUUID);
                CACHE_MAP.put(menu.getLocation(), cache);

            }
        };
    }

    public void onTick(@Nonnull Block b) {
        BlockMenu invMenu = BlockStorage.getInventory(b);
        if (!(b.getBlockData() instanceof Dispenser)) {
            return;
        }
        Dispenser dispenser = (Dispenser) b.getBlockData();
        Block targetBlock = b.getRelative(dispenser.getFacing());
        World targetLocation = targetBlock.getWorld();


        BlockBreakerCache cache = CACHE_MAP.get(b.getLocation());

        if (getCharge(b.getLocation()) > 0) {
            invMenu.replaceExistingItem(4, NOT_RUNNING);
            if (cache.isOn) {
                invMenu.replaceExistingItem(4, NOT_OPERATING);


                if (targetBlock.getType().isSolid() && !ILLEGAL.contains(targetBlock.getType()) && !(BlockStorage.hasBlockInfo(targetBlock))) {

                    if (!Slimefun.getProtectionManager().hasPermission(
                        Bukkit.getOfflinePlayer(cache.owner),
                        targetBlock,
                        Interaction.BREAK_BLOCK)
                    ) {
                        return;
                    }

                    int progress = cache.progress;

                    if (invMenu.hasViewer()) {
                        invMenu.replaceExistingItem(4, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aOperating!",
                            "", "&bRate: " + this.rate + " Block per tick", "&2Breaking block at rate: " + progress
                            + "/" + this.rate));
                    }

                    if (progress >= this.rate) {
                        progress = 0;
                        if (cache.breakNaturally) {
                            targetBlock.breakNaturally(DUMMY_PICK);
                        } else {
                            ItemStack vanilla = new ItemStack(targetBlock.getType());
                            if (vanilla.isSimilar(VERSIONED_AMETHYST)) {
                                targetBlock.setType(Material.AIR);
                            } else {
                                targetBlock.breakNaturally(DUMMY_SILK_PICK);
                            }
                        }
                        targetLocation.playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
                        b.getWorld().spawnParticle(Particle.SMOKE_LARGE, b.getLocation().add(1, 1, 1), 2, 0.1, 0.1, 0.1);
                    } else {
                        progress++;
                        targetLocation.playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_STONE_HIT, 1, 1);
                    }
                    cache.progress = progress;
                    CACHE_MAP.put(invMenu.getLocation(), cache);
                    takeCharge(b.getLocation());
                }
            }
        }
    }

    public void toggleMode(BlockMenu blockMenu) {
        final Location location = blockMenu.getLocation();
        final BlockBreakerCache cache = CACHE_MAP.get(location);

        cache.breakNaturally = !cache.breakNaturally;
        BlockStorage.addBlockInfo(location, "breakBlockNaturally", String.valueOf(cache.breakNaturally));
        blockMenu.replaceExistingItem(CHANGE_MODE, cache.breakNaturally ? BREAK_BLOCK_NATURALLY : DROP_BLOCK_NATURALLY);
        CACHE_MAP.put(location, cache);
    }

    public void toggleOnOrOff(BlockMenu blockMenu) {
        final Location location = blockMenu.getLocation();
        final BlockBreakerCache cache = CACHE_MAP.get(location);

        cache.isOn = !cache.isOn;
        BlockStorage.addBlockInfo(location, "toggled_On", String.valueOf(cache.isOn));
        blockMenu.replaceExistingItem(ON_OFF, cache.isOn ? TOGGLED_ON : TOGGLED_OFF);
        CACHE_MAP.put(location, cache);
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

public static class BlockBreakerCache {
    private int progress;
    private boolean breakNaturally;
    private boolean isOn;
    private final UUID owner;

    public BlockBreakerCache(int progress, boolean breakNaturally, boolean isOn, @Nullable UUID owner) {
        this.progress = progress;
        this.breakNaturally = breakNaturally;
        this.isOn = isOn;
        this.owner = owner;
    }
}
}
