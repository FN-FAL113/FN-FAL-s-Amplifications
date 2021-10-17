package ne.fnfal113.fnamplifications.PowerGenerators;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.extrautils.interfaces.InventoryBlock;
import dev.j3fftw.litexpansion.extrautils.utils.Utils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PowahGenerator extends SlimefunItem implements InventoryBlock, EnergyNetProvider {

    static ReturnConfValue value = new ReturnConfValue();

    private static final int PROGRESS_SLOT = 4;
    private static final int INFO_SLOT = 9;
    private static final int CREDIT_SLOT = 17;
    private static final int FN_1 = 11;
    private static final int FN_2 = 12;
    private static final int FN_3 = 13;
    private static final int FN_4 = 14;
    private static final int FN_5 = 15;
    private static final CustomItemStack generatingItem = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&cNot Generating..."
    );
    private static final CustomItemStack addonBy = new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
            "&d&lAddon by FN_FAL113"
    );
    private static final CustomItemStack creditsTo = new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
            "&eCredits to Jeff(LiteXpansion) and Walshy(SF dev) for letting me use their utils"
    );

    private static final CustomItemStack F = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "8442b066e0e5e09a6e6bb9989cc27451f2bd78fb0dc72108aa940fc9db1c24e1"))
    );

    private static final CustomItemStack N = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "e713d2601e35242d35018cece3b34c61bf5001f5dbd7463a4c5587ac365b3d1f"))
    );

    private static final CustomItemStack F2 = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "8442b066e0e5e09a6e6bb9989cc27451f2bd78fb0dc72108aa940fc9db1c24e1"))
    );

    private static final CustomItemStack A = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "a517b4829b83192bd72711277a8efc4196711e4180c22b3e2b8166bea1a9de19"))
    );

    private static final CustomItemStack L = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "206bc417e3c06b22735d539f9c6c8fd7c1efd19236e2c381534051d9d6bee804"))
    );

    public static final int FN_POWERGEN_RANK_I_DAYRATE = value.rank1dayrate();
    public static final int FN_POWERGEN_RANK_I_NIGHTRATE = value.rank1nightrate();
    public static final int FN_POWERGEN_RANK_I_OUTPUT = value.rank1output();
    public static final int FN_POWERGEN_RANK_I_STORAGE = value.rank1capacity();
    public static final int FN_POWERGEN_RANK_II_DAYRATE = value.rank2dayrate();
    public static final int FN_POWERGEN_RANK_II_NIGHTRATE = value.rank2nightrate();
    public static final int FN_POWERGEN_RANK_II_OUTPUT = value.rank2output();
    public static final int FN_POWERGEN_RANK_II_STORAGE = value.rank2capacity();
    public static final int FN_POWERGEN_RANK_III_DAYRATE = value.rank3dayrate();
    public static final int FN_POWERGEN_RANK_III_NIGHTRATE = value.rank3nightrate();
    public static final int FN_POWERGEN_RANK_III_OUTPUT = value.rank3output();
    public static final int FN_POWERGEN_RANK_III_STORAGE = value.rank3capacity();
    public static final int FN_POWERGEN_RANK_IV_DAYRATE = value.rank4dayrate();
    public static final int FN_POWERGEN_RANK_IV_NIGHTRATE = value.rank4nightrate();
    public static final int FN_POWERGEN_RANK_IV_OUTPUT = value.rank4output();
    public static final int FN_POWERGEN_RANK_IV_STORAGE = value.rank4capacity();
    public static final int FN_POWERGEN_RANK_V_DAYRATE = value.rank5dayrate();
    public static final int FN_POWERGEN_RANK_V_NIGHTRATE = value.rank5nightrate();
    public static final int FN_POWERGEN_RANK_V_OUTPUT = value.rank5output();
    public static final int FN_POWERGEN_RANK_V_STORAGE = value.rank5capacity();
    public static final int FN_POWERGEN_RANK_VI_DAYRATE = value.rank6dayrate();
    public static final int FN_POWERGEN_RANK_VI_NIGHTRATE = value.rank6nightrate();
    public static final int FN_POWERGEN_RANK_VI_OUTPUT = value.rank6output();
    public static final int FN_POWERGEN_RANK_VI_STORAGE = value.rank6capacity();
    public static final int FN_POWERGEN_RANK_VII_DAYRATE = value.rank7dayrate();
    public static final int FN_POWERGEN_RANK_VII_NIGHTRATE = value.rank7nightrate();
    public static final int FN_POWERGEN_RANK_VII_OUTPUT = value.rank7output();
    public static final int FN_POWERGEN_RANK_VII_STORAGE = value.rank7capacity();
    public static final int FN_POWERGEN_RANK_VIII_DAYRATE = value.rank8dayrate();
    public static final int FN_POWERGEN_RANK_VIII_NIGHTRATE = value.rank8nightrate();
    public static final int FN_POWERGEN_RANK_VIII_OUTPUT = value.rank8output();
    public static final int FN_POWERGEN_RANK_VIII_STORAGE = value.rank8capacity();
    public static final int FN_POWERGEN_RANK_IX_DAYRATE = value.rank9dayrate();
    public static final int FN_POWERGEN_RANK_IX_NIGHTRATE = value.rank9nightrate();
    public static final int FN_POWERGEN_RANK_IX_OUTPUT = value.rank9output();
    public static final int FN_POWERGEN_RANK_IX_STORAGE = value.rank9capacity();
    public static final int FN_POWERGEN_RANK_X_DAYRATE = value.rank10dayrate();
    public static final int FN_POWERGEN_RANK_X_NIGHTRATE = value.rank10nightrate();
    public static final int FN_POWERGEN_RANK_X_OUTPUT = value.rank10output();
    public static final int FN_POWERGEN_RANK_X_STORAGE = value.rank10capacity();
    public static final int FN_POWERGEN_RANK_XI_DAYRATE = value.rank11dayrate();
    public static final int FN_POWERGEN_RANK_XI_NIGHTRATE = value.rank11nightrate();
    public static final int FN_POWERGEN_RANK_XI_OUTPUT = value.rank11output();
    public static final int FN_POWERGEN_RANK_XI_STORAGE = value.rank11capacity();
    public static final int FN_POWERGEN_RANK_XII_DAYRATE = value.rank12dayrate();
    public static final int FN_POWERGEN_RANK_XII_NIGHTRATE = value.rank12nightrate();
    public static final int FN_POWERGEN_RANK_XII_OUTPUT = value.rank12output();
    public static final int FN_POWERGEN_RANK_XII_STORAGE = value.rank12capacity();
    private final Type type;


    public PowahGenerator(Type type) {
        super(FNAmpItems.POWER_GENERATORS, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;


        createPreset(this, type.getItem().getItemMetaSnapshot().getDisplayName().orElse("&eFN Power Xpansion"),
                blockMenuPreset -> {
                    for (int i = 0; i < 18; i++) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(PROGRESS_SLOT, generatingItem);
                    blockMenuPreset.addItem(INFO_SLOT, addonBy);
                    blockMenuPreset.addItem(CREDIT_SLOT, creditsTo);
                    blockMenuPreset.addItem(FN_1, F);
                    blockMenuPreset.addItem(FN_2, N);
                    blockMenuPreset.addItem(FN_3, F2);
                    blockMenuPreset.addItem(FN_4, A);
                    blockMenuPreset.addItem(FN_5, L);
                });
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) {
            return 0;
        }

        final int stored = getCharge(l);
        final boolean canGenerate = stored < getCapacity();
        Validate.notNull(l.getWorld());
        final int rate = canGenerate ? getGeneratingAmount(inv.getBlock(), l.getWorld()) : 0;

        String generationType = "&4Unknown";

        if (l.getWorld().getEnvironment() == World.Environment.NETHER) {
            generationType = "&cNether &e(Day)";
        } else if (l.getWorld().getEnvironment() == World.Environment.THE_END) {
            generationType = "&5End &8(Night)";
        } else if (rate == this.type.getDayGenerationRate()) {
            generationType = "&aOverworld &e(Day)";
        } else if (rate == this.type.getNightGenerationRate()) {
            generationType = "&aOverworld &8(Night)";
        }

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
            inv.replaceExistingItem(PROGRESS_SLOT,
                    canGenerate ? new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aGenerating",
                            "", "&bRate Type: " + generationType,
                            "&7Generating Power at &6" + Utils.powerFormatAndFadeDecimals(Utils.perTickToPerSecond(rate)) + " J/s " +
                                    "&8(" + rate + " J/t)",
                            "", "&7Stored Power: &6" + Utils.powerFormatAndFadeDecimals((double) stored + rate) + " J"
                    )
                            : new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&cNot Generating",
                            "", "&7Generator has reached maximum capacity.",
                            "", "&7Stored Power: &6" + Utils.powerFormatAndFadeDecimals(stored) + " J")
            );
        }

        return rate;
    }

    @Override
    public boolean willExplode(@Nonnull Location l, @Nonnull Config data) {
        return false;
    }

    private int getGeneratingAmount(@Nonnull Block loc, @Nonnull World world) {
        if (world.getEnvironment() == World.Environment.NETHER) {
            return this.type.getDayGenerationRate();
        }
        if (world.getEnvironment() == World.Environment.THE_END) {
            return this.type.getNightGenerationRate();
        }


        if (world.isThundering() || world.hasStorm() || world.getTime() >= 13000
                || loc.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15
        ) {
            return this.type.getNightGenerationRate();
        } else {
            return this.type.getDayGenerationRate();
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int getCapacity() {
        return this.type.getStorage();
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        RANK1(FNAmpItems.FN_XPANSION_POWER_R1 , FN_POWERGEN_RANK_I_DAYRATE, FN_POWERGEN_RANK_I_NIGHTRATE, FN_POWERGEN_RANK_I_OUTPUT,
                FN_POWERGEN_RANK_I_STORAGE, new ItemStack[] {
                Items.IRIDIUM, Items.REINFORCED_GLASS, Items.IRIDIUM,
                Items.MACHINE_BLOCK, SlimefunItems.SOLAR_GENERATOR_4, Items.MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, Items.IRON_ITEM_CASING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }),

        RANK2(FNAmpItems.FN_XPANSION_POWER_R2, FN_POWERGEN_RANK_II_DAYRATE, FN_POWERGEN_RANK_II_NIGHTRATE, FN_POWERGEN_RANK_II_OUTPUT,
                FN_POWERGEN_RANK_II_STORAGE, new ItemStack[] {
                Items.IRIDIUM, FNAmpItems.FN_XPANSION_POWER_R1, Items.IRIDIUM,
                Items.MACHINE_BLOCK, Items.REINFORCED_GLASS, Items.MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, Items.IRON_ITEM_CASING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }),

        RANK3(FNAmpItems.FN_XPANSION_POWER_R3, FN_POWERGEN_RANK_III_DAYRATE, FN_POWERGEN_RANK_III_NIGHTRATE, FN_POWERGEN_RANK_III_OUTPUT,
                FN_POWERGEN_RANK_III_STORAGE, new ItemStack[] {
                Items.IRIDIUM, FNAmpItems.FN_XPANSION_POWER_R2, Items.IRIDIUM,
                Items.MACHINE_BLOCK, Items.REINFORCED_GLASS, Items.MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, Items.IRON_ITEM_CASING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }),

        RANK4(FNAmpItems.FN_XPANSION_POWER_R4, FN_POWERGEN_RANK_IV_DAYRATE, FN_POWERGEN_RANK_IV_NIGHTRATE, FN_POWERGEN_RANK_IV_OUTPUT,
                FN_POWERGEN_RANK_IV_STORAGE, new ItemStack[] {
                Items.IRIDIUM, FNAmpItems.FN_XPANSION_POWER_R3, Items.IRIDIUM,
                Items.MACHINE_BLOCK, Items.REINFORCED_GLASS, Items.MACHINE_BLOCK,
                SlimefunItems.BASIC_CIRCUIT_BOARD, Items.GOLD_ITEM_CASING, SlimefunItems.BASIC_CIRCUIT_BOARD
        }),

        RANK5(FNAmpItems.FN_XPANSION_POWER_R5, FN_POWERGEN_RANK_V_DAYRATE, FN_POWERGEN_RANK_V_NIGHTRATE, FN_POWERGEN_RANK_V_OUTPUT,
                FN_POWERGEN_RANK_V_STORAGE, new ItemStack[] {
                Items.IRIDIUM, FNAmpItems.FN_XPANSION_POWER_R4, Items.IRIDIUM,
                Items.MACHINE_BLOCK, Items.RE_BATTERY, Items.MACHINE_BLOCK,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.TIN_ITEM_CASING, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        RANK6(FNAmpItems.FN_XPANSION_POWER_R6, FN_POWERGEN_RANK_VI_DAYRATE, FN_POWERGEN_RANK_VI_NIGHTRATE, FN_POWERGEN_RANK_VI_OUTPUT,
                FN_POWERGEN_RANK_VI_STORAGE, new ItemStack[] {
                Items.IRIDIUM_PLATE, FNAmpItems.FN_XPANSION_POWER_R5, Items.IRIDIUM_PLATE,
                Items.ADVANCED_MACHINE_BLOCK, Items.ADVANCED_ALLOY, Items.ADVANCED_MACHINE_BLOCK,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.COPPER_CABLE, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        RANK7(FNAmpItems.FN_XPANSION_POWER_R7, FN_POWERGEN_RANK_VII_DAYRATE, FN_POWERGEN_RANK_VII_NIGHTRATE, FN_POWERGEN_RANK_VII_OUTPUT,
                FN_POWERGEN_RANK_VII_STORAGE, new ItemStack[] {
                Items.IRIDIUM_PLATE, FNAmpItems.FN_XPANSION_POWER_R6, Items.IRIDIUM_PLATE,
                Items.ADVANCED_MACHINE_BLOCK, Items.ADVANCED_ALLOY, Items.ADVANCED_MACHINE_BLOCK,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.TIN_CABLE, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        RANK8(FNAmpItems.FN_XPANSION_POWER_R8, FN_POWERGEN_RANK_VIII_DAYRATE, FN_POWERGEN_RANK_VIII_NIGHTRATE, FN_POWERGEN_RANK_VIII_OUTPUT,
                FN_POWERGEN_RANK_VIII_STORAGE, new ItemStack[] {
                Items.IRIDIUM_PLATE, FNAmpItems.FN_XPANSION_POWER_R7, Items.IRIDIUM_PLATE,
                Items.ADVANCED_MACHINE_BLOCK, Items.THORIUM_PLATE, Items.ADVANCED_MACHINE_BLOCK,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        RANK9(FNAmpItems.FN_XPANSION_POWER_R9, FN_POWERGEN_RANK_IX_DAYRATE, FN_POWERGEN_RANK_IX_NIGHTRATE, FN_POWERGEN_RANK_IX_OUTPUT,
                FN_POWERGEN_RANK_IX_STORAGE, new ItemStack[] {
                Items.IRIDIUM_PLATE, FNAmpItems.FN_XPANSION_POWER_R8, Items.IRIDIUM_PLATE,
                Items.ADVANCED_MACHINE_BLOCK, Items.LAPOTRON_CRYSTAL, Items.ADVANCED_MACHINE_BLOCK,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        RANK10(FNAmpItems.FN_XPANSION_POWER_R10, FN_POWERGEN_RANK_X_DAYRATE, FN_POWERGEN_RANK_X_NIGHTRATE, FN_POWERGEN_RANK_X_OUTPUT,
                FN_POWERGEN_RANK_X_STORAGE, new ItemStack[] {
                Items.IRIDIUM_PLATE, FNAmpItems.FN_XPANSION_POWER_R9, Items.IRIDIUM_PLATE,
                Items.MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT, Items.LAPOTRON_CRYSTAL, Items.MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        RANK11(FNAmpItems.FN_XPANSION_POWER_R11, FN_POWERGEN_RANK_XI_DAYRATE, FN_POWERGEN_RANK_XI_NIGHTRATE, FN_POWERGEN_RANK_XI_OUTPUT,
                FN_POWERGEN_RANK_XI_STORAGE, new ItemStack[] {
                Items.IRIDIUM_PLATE, FNAmpItems.FN_XPANSION_POWER_R10, Items.IRIDIUM_PLATE,
                Items.MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT, Items.LAPOTRON_CRYSTAL, Items.MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, FNAmpItems.FN_XPANSION_POWER_R5, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        RANK12(FNAmpItems.FN_XPANSION_POWER_R12, FN_POWERGEN_RANK_XII_DAYRATE, FN_POWERGEN_RANK_XII_NIGHTRATE, FN_POWERGEN_RANK_XII_OUTPUT,
                FN_POWERGEN_RANK_XII_STORAGE, new ItemStack[] {
                Items.IRIDIUM_PLATE, FNAmpItems.FN_XPANSION_POWER_R11, Items.IRIDIUM_PLATE,
                Items.MULTI_FUNCTIONAL_STORAGE_UNIT, Items.LAPOTRON_CRYSTAL, Items.MULTI_FUNCTIONAL_STORAGE_UNIT,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, FNAmpItems.FN_XPANSION_POWER_R7, SlimefunItems.ADVANCED_CIRCUIT_BOARD
        }),

        ;

        @Nonnull
        private final SlimefunItemStack item;
        private final int dayGenerationRate;
        private final int nightGenerationRate;
        private final int output;
        private final int storage;

        @Nonnull
        private final ItemStack[] recipe;
    }

}
