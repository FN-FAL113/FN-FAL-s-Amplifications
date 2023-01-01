package ne.fnfal113.fnamplifications.items;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;

import dev.j3fftw.extrautils.utils.LoreBuilderDynamic;

import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FNAmpItems {

    private FNAmpItems() {}

    private static final ItemStack STICK = new ItemStack(Material.STICK);

    private static final ItemStack ARMOR = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
            new ItemStack(Material.NETHERITE_CHESTPLATE) : new ItemStack(Material.DIAMOND_CHESTPLATE);

    static{
        ItemMeta meta = STICK.getItemMeta();
        if(meta != null) {
            meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            STICK.setItemMeta(meta);
        }

        ItemMeta armorMeta = ARMOR.getItemMeta();
        if(armorMeta != null) {
            armorMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
            armorMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            ARMOR.setItemMeta(armorMeta);
        }

    }

    // start of Item Groups
    public static final NestedItemGroup FN_MAIN_GROUP = new NestedItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_ITEMS"),
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "115b670a704ba080a730acf246da64c413901644847edd671c3da9a97441ddc5")),
            "&e&lFN &b&lAmpli&c&lfications"));

    public static final SubItemGroup POWER_GENERATORS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "POWER_GENERATORS"),
            FN_MAIN_GROUP,
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "ecb316f7a227a8c59d58ae0dd6768fe4fa546d55b9cfdd56cfe40b6586d81c24")),
            "&eFN Power Xpansion"));

    public static final SubItemGroup MATERIAL_GENERATORS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "MATERIAL_GENERATORS"),
            FN_MAIN_GROUP,
            new CustomItemStack(Material.EMERALD_BLOCK,
            "&eFN Material Generators"));

    public static final SubItemGroup MATERIAL_GENERATORS_UPGRADES = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "MATERIAL_GENERATORS_UPGRADES"),
            FN_MAIN_GROUP,
            new CustomItemStack(Material.GOLD_BLOCK,
                    "&eFN Material Generators Upgrades"));

    public static final SubItemGroup SOLAR_GENERATORS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "SOLAR_GENERATORS"),
            FN_MAIN_GROUP,
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "e7f4c00356d1addb85b45ba5352992d3ecc0c9d11feb9041482f8531fd27d014")),
            "&eFN Solar Generators"));

    public static final SubItemGroup MACHINES = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "MACHINERY"),
            FN_MAIN_GROUP,
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "4a7d54ca45a398c364cebbffb5390ce5e0345e0c7bc5e863acabf57d1342c4bd")),
            "&eFN Machinery"));

    public static final SubItemGroup ITEMS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "MACHINERY_ITEMS"),
            FN_MAIN_GROUP,
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "8245a1c3e8d7c3d59d05e3634b04af4cbf8d11b70e2a40e2e6364386db49e737")),
            "&eFN Machinery Items"));

    public static final SubItemGroup MAGICAL_ITEMS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "MAGICAL_ITEMS"),
            FN_MAIN_GROUP,
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "72114a80357463fe2f59e397aab9fc66d482a65d524f8870d21c724c18ecf757")),
                    "&eFN Magical Items"));

    public static final SubItemGroup MULTIBLOCK = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "MULTIBLOCK"),
            FN_MAIN_GROUP,
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "550c3306bb11522fec380ea248eb6a5b180f4a7ab8843d635d14d4a778d6351")),
            "&eFN Multiblocks"));

    public static final SubItemGroup METAL_SCRAP_RECIPES = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "METAL_SCRAP_RECIPES"),
            FN_MAIN_GROUP,
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "3ff0bee591e5f0000ef16f966b949adcb5c2f409a14ccfc5b91222fd925045db")),
            "&eMetal Scrap Recipes"));

    public static final SubItemGroup MYSTERY_STICKS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "MYSTERY_STICKS"),
            FN_MAIN_GROUP,
            new CustomItemStack(STICK,
                    "&eFN Mystery PVP/PVE Sticks"));

    public static final SubItemGroup FN_GEARS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_GEARS"),
            FN_MAIN_GROUP,
            new CustomItemStack(ARMOR,
                    "&eFN Gears of Friction"));

    public static final SubItemGroup FN_STAFFS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_STAFFS"),
            FN_MAIN_GROUP,
            new CustomItemStack(Material.BLAZE_ROD,
                    "&eFN Staffs"));

    public static final SubItemGroup FN_MISC = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_MISC"),
            FN_MAIN_GROUP,
            new CustomItemStack(Material.CHEST,
                    "&eFN Miscellaneous"));

    public static final SubItemGroup FN_GEMS = new SubItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_GEMS"),
            FN_MAIN_GROUP,
            new CustomItemStack(Material.EMERALD,
                    "&eFN Gems"));

    public static final ItemGroup FN_AMPLIFICATIONS = new ItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_AMPLIFICATIONS"),
            new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "115b670a704ba080a730acf246da64c413901644847edd671c3da9a97441ddc5")),
            "&e&lFN &b&lAmpli&c&lfications"));

    // start of FN itemstacks
    public static final SlimefunItemStack FN_XPANSION_POWER_R1 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R1",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "33165e254de7863459343543f933481f0dcf8d4730a23433b9a7ff0d3ff79c5a")),
            "&d&lFN Power Xpansion R1",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R2 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R2",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "5b78e55e3ea6b3682bd7cead43eb1c91b2527a81aa2894f095801f6ee47a3")),
            "&d&lFN Power Xpansion R2",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R3 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R3",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "45ff60d863d1cf76742f275e2ac5749dd0a978a231d3c51e816132c75aef608a")),
            "&d&lFN Power Xpansion R3",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R4 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R4",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "efc05af0e88c6fb10b4c8c8b81b7aa658e64649724cb73bb9bb0f25f28bd")),
            "&d&lFN Power Xpansion R4",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R5 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R5",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "8f14f3179b86f69b3efa7472dacaeb2339f6290d2d817362793348abd98e021")),
            "&d&lFN Power Xpansion R5",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R6 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R6",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "1d5a09884cb83ef5c908dddd385f246fefdee221712c010177f54376da238fdd")),
            "&d&lFN Power Xpansion R6",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R7 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R7",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "f7e541dfb4ba1f7dc28b548e347abbdc987ebe0e61c49fa87111ef1b2dcb2218")),
            "&d&lFN Power Xpansion R7",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R8 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R8",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "de31efefdd9551af8a4349d3d21e5ec8f37e53c801eb25b14279d6a89fe0c01e")),
            "&d&lFN Power Xpansion R8",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R9 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R9",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "6db32b15d7f32704ed626fa52d06fb2b4071d336fdbfe61e6e41c669d6e37f47")),
            "&d&lFN Power Xpansion R9",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R10 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R10",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "7b7c9b6a23f21cca2b362b85b36dece3d8389e363014defe5b92ff6ee64f1ae")),
            "&d&lFN Power Xpansion R10",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R11 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R11",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "7f9f356f5fe7d1bc92cddfaeba3ee773ac9df1cc4d1c2f8fe5f47013032c551d")),
            "&d&lFN Power Xpansion R11",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R12 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R12",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "3464874df452c1d717eddd0fb3b848202ad15571245af6fade2ecf514f3c80bb")),
            "&d&lFN Power Xpansion R12",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            ""
    );

    public static final SlimefunItemStack FMG_GENERATOR_MULTIBLOCK = new SlimefunItemStack(
            "FMG_GENERATOR_MULTIBLOCK",
            Material.BEDROCK,
            "&9Generator multiblock",
            "",
            "&dBuild any of this addon's",
            "&dgenerators like this.",
            "&aThey will only output to a chest placed above",
            "&athe actual generators."
    );

    public static final SlimefunItemStack FMG_GENERATOR_CLAY_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_CLAY_BROKEN",
            Material.CLAY,
            "&3FN Clay Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static SlimefunItemStack FMG_GENERATOR_CLAY = new SlimefunItemStack(
            "FMG_GENERATOR_CLAY",
            Material.CLAY,
            "&3FN Clay Generator",
            "&6Generates clay at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_TERRACOTTA_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_TERRACOTTA_BROKEN",
            Material.TERRACOTTA,
            "&4FN Terracotta Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_TERRACOTTA = new SlimefunItemStack(
            "FMG_GENERATOR_TERRACOTTA",
            Material.TERRACOTTA,
            "&4FN Terracotta Generator",
            "&6Generates terracotta at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_BONE_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_BONE_BROKEN",
            Material.BONE_BLOCK,
            "&fFN Bone Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_BONE = new SlimefunItemStack(
            "FMG_GENERATOR_BONE",
            Material.BONE_BLOCK,
            "&fFN Bone Generator",
            "&6Generates bone at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_DIAMOND_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_DIAMOND_BROKEN",
            Material.DIAMOND_BLOCK,
            "&bFN Diamond Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_DIAMOND = new SlimefunItemStack(
            "FMG_GENERATOR_DIAMOND",
            Material.DIAMOND_BLOCK,
            "&bFN Diamond Generator",
            "&6Generates diamond at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_EMERALD_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_EMERALD_BROKEN",
            Material.EMERALD_BLOCK,
            "&aFN Emerald Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_EMERALD = new SlimefunItemStack(
            "FMG_GENERATOR_EMERALD",
            Material.EMERALD_BLOCK,
            "&aFN Emerald Generator",
            "&6Generates emerald at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_DIRT_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_DIRT_BROKEN",
            Material.DIRT,
            "&fFN Dirt Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_DIRT = new SlimefunItemStack(
            "FMG_GENERATOR_DIRT",
            Material.DIRT,
            "&fFN Dirt Generator",
            "&6Generates dirt at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_HONEYCOMB_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_HONEYCOMB_BROKEN",
            Material.HONEYCOMB_BLOCK,
            "&6FN Honey Comb Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_HONEYCOMB = new SlimefunItemStack(
            "FMG_GENERATOR_HONEYCOMB",
            Material.HONEYCOMB_BLOCK,
            "&6FN Honey Comb Generator",
            "&6Generates honey comb at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_QUARTZ_BROKEN = new SlimefunItemStack(
            "FMG_GENERATOR_QUARTZ_BROKEN",
            Material.QUARTZ_BLOCK,
            "&fFN Quartz Generator &8(Broken)",
            "&8Needs to be repaired",
            "",
            "&d&oFN Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_QUARTZ = new SlimefunItemStack(
            "FMG_GENERATOR_QUARTZ",
            Material.QUARTZ_BLOCK,
            "&fFN Quartz Generator",
            "&6Generates quartz at a certain rate",
            "&6Right click the block for info",
            "",
            "&6Tickrate: " + "&6ticks",
            "",
            "&cWarning:",
            "&fBreaking the block will drop",
            "&fthe broken type",
            "",
            "&d&oFN Material Generators"
    );

    public static SlimefunItemStack FMG_GENERATOR_WARPED_BROKEN;
    public static SlimefunItemStack FMG_GENERATOR_WARPED;
    public static SlimefunItemStack FMG_GENERATOR_AMETHYST_BROKEN;
    public static SlimefunItemStack FMG_GENERATOR_AMETHYST;

    static {
        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)) {
            FMG_GENERATOR_AMETHYST_BROKEN = new SlimefunItemStack(
                    "FMG_GENERATOR_AMETHYST_BROKEN",
                    Material.AMETHYST_BLOCK,
                    "&dFN Amethyst Cluster Generator &8(Broken)",
                    "&8Needs to be repaired",
                    "",
                    "&d&oFN Material Generators"
            );

            FMG_GENERATOR_AMETHYST = new SlimefunItemStack(
                    "FMG_GENERATOR_AMETHYST",
                    Material.AMETHYST_BLOCK,
                    "&dFN Amethyst Cluster Generator",
                    "&6Generates amethyst cluster at a certain rate",
                    "&6Right click the block for info",
                    "",
                    "&6Tickrate: " + "&6ticks",
                    "",
                    "&cWarning:",
                    "&fBreaking the block will drop",
                    "&fthe broken type",
                    "",
                    "&d&oFN Material Generators"
            );
        }

        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)){
            FMG_GENERATOR_WARPED_BROKEN = new SlimefunItemStack(
                    "FMG_GENERATOR_WARPED_BROKEN",
                    Material.WARPED_NYLIUM,
                    "&4FN Warped Nylium Generator &8(Broken)",
                    "&8Needs to be repaired",
                    "",
                    "&d&oFN Material Generators"
            );

           FMG_GENERATOR_WARPED = new SlimefunItemStack(
                    "FMG_GENERATOR_WARPED",
                    Material.WARPED_NYLIUM,
                    "&4FN Warped Nylium Generator",
                    "&6Generates warped nylium at a certain rate",
                    "&6Right click the block for info",
                    "",
                    "&6Tickrate: " + "&6ticks",
                    "",
                    "&cWarning:",
                    "&fBreaking the block will drop",
                    "&fthe broken type",
                    "",
                    "&d&oFN Material Generators"
            );
        }
    }

    public static final SlimefunItemStack FN_MAT_GEN_UPGRADES_REPAIR_ITEM = new SlimefunItemStack(
            "FN_MAT_GEN_UPGRADES_REPAIR_ITEM",
            Material.BLACK_DYE,
            "&fFN Mat Gen Repair Item",
            "&6Repairs or add durability",
            "",
            "&e+20 durability",
            "",
            "&aRight click on a material generator",
            "&d&oFN Material Generators Upgrades"
    );

    public static final SlimefunItemStack FN_MAT_GEN_UPGRADES_FAST_PRODUCE = new SlimefunItemStack(
            "FN_MAT_GEN_UPGRADES_FAST_PRODUCE",
            Material.YELLOW_DYE,
            "&fFN Mat Gen Fast Produce",
            "&6Faster production",
            "",
            "&e+1.75x Mat gen speed for 30 minutes",
            "",
            "&aRight click on a material generator",
            "&d&oFN Material Generators Upgrades"
    );

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER1 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER1",
            "6d2822cedb3abd579d6dfa2966c1433c3c36cb9732e2c23ec0cc81daedd4403b",
            "&dFN Solar Generator Tier I",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER2 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER2",
            "35dd37f729fc88133e314a552204c0fa2c0168428b353f957bf15ff24b7707e0",
            "&dFN Solar Generator Tier II",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER3 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER3",
            "25ddf6af2d6271d8fdfadbdc54faaad5a68d7b8ac20e163883fc38d76336ea6",
            "&dFN Solar Generator Tier III",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER4 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER4",
            "e7f4c00356d1addb85b45ba5352992d3ecc0c9d11feb9041482f8531fd27d014",
            "&dFN Solar Generator Tier IV",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER5 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER5",
            "afdd9e588d2461d2d3d058cb3e0af2b3a3367607aa14d124ed92a833f25fb112",
            "&dFN Solar Generator Tier V",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER6 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER6",
            "224ad26209fa02f559ef6aa863ee9ba8f3bef0a02f1e9cff8fdc09196402fb6f",
            "&dFN Solar Generator Tier VI",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER7 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER7",
            "c4fe135c311f7086edcc5e6dbc4ef4b23f819fddaa42f827dac46e3574de2287",
            "&dFN Solar Generator Tier VII",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER8 = new SlimefunItemStack(
            "FN_FAL_GENERATOR_TIER8",
            "240775c3ad75763613f32f04986881bbe4eee4366d0c57f17f7c7514e2d0a77d",
            "&dFN Solar Generator Tier VIII",
            "", "&eA solar generator for those who",
            "&eneeds power during day only",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR));

    public static final SlimefunItemStack FN_FAL_TRANSFORMER_1 = new SlimefunItemStack(
            "FN_FAL_TRANSFORMER_1",
            Material.BLUE_GLAZED_TERRACOTTA,
            "&dFN Electric Transformer I",
            "", "&eA machinery that can transform",
            "&evarious items to other forms",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(256));

    public static final SlimefunItemStack FN_FAL_TRANSFORMER_2 = new SlimefunItemStack(
            "FN_FAL_TRANSFORMER_2",
            Material.BLUE_GLAZED_TERRACOTTA, "&dFN Electric Transformer II",
            "", "&eA machinery that can transform",
            "&evarious items to other forms",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(2),
            LoreBuilder.powerPerSecond(384));

    public static final SlimefunItemStack FN_FAL_TRANSFORMER_3 = new SlimefunItemStack(
            "FN_FAL_TRANSFORMER_3",
            Material.BLUE_GLAZED_TERRACOTTA,
            "&dFN Electric Transformer III",
            "", "&eA machinery that can transform",
            "&evarious items to other forms",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(4),
            LoreBuilder.powerPerSecond(768));

    public static final SlimefunItemStack FN_FAL_COMPRESSOR_1 = new SlimefunItemStack(
            "FN_FAL_COMPRESSOR_1",
            Material.GREEN_GLAZED_TERRACOTTA,
            "&dFN Electric Compressor I",
            "", "&eA machinery that can compress",
            "&eitems to form a biological item",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(128));

    public static final SlimefunItemStack FN_FAL_COMPRESSOR_2 = new SlimefunItemStack(
            "FN_FAL_COMPRESSOR_2",
            Material.GREEN_GLAZED_TERRACOTTA,
            "&dFN Electric Compressor II",
            "", "&eA machinery that can compress",
            "&eitems to form a biological item",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(2),
            LoreBuilder.powerPerSecond(256));

    public static final SlimefunItemStack FN_FAL_COMPRESSOR_3 = new SlimefunItemStack(
            "FN_FAL_COMPRESSOR_3",
            Material.GREEN_GLAZED_TERRACOTTA,
            "&dFN Electric Compressor III",
            "", "&eA machinery that can compress",
            "&eitems to form a biological item",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(4),
            LoreBuilder.powerPerSecond(512));

    public static final SlimefunItemStack FN_FAL_CONDENSER_1 = new SlimefunItemStack(
            "FN_FAL_CONDENSER_1",
            Material.YELLOW_STAINED_GLASS,
            "&dFN Electric Ingot Condenser I",
            "", "&eA machinery that can condense two",
            "&eingots or items to one biological ingot",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(256));

    public static final SlimefunItemStack FN_FAL_CONDENSER_2 = new SlimefunItemStack(
            "FN_FAL_CONDENSER_2",
            Material.YELLOW_STAINED_GLASS,
            "&dFN Electric Ingot Condenser II",
            "", "&eA machinery that can condense two",
            "&eingots or items to one biological ingot",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(2),
            LoreBuilder.powerPerSecond(384));

    public static final SlimefunItemStack FN_FAL_CONDENSER_3 = new SlimefunItemStack(
            "FN_FAL_CONDENSER_3",
            Material.YELLOW_STAINED_GLASS,
            "&dFN Electric Ingot Condenser III",
            "", "&eA machinery that can condense two",
            "&eingots or items to one biological ingot",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(4),
            LoreBuilder.powerPerSecond(768));

    public static final SlimefunItemStack FN_FAL_RECYCLER_1 = new SlimefunItemStack(
            "FN_FAL_RECYCLER_1",
            Material.PURPLE_STAINED_GLASS,
            "&dFN Electric Recycler I",
            "", "&eA machinery that can recycle",
            "&eitems to that of in lower form",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(168));

    public static final SlimefunItemStack FN_FAL_RECYCLER_2 = new SlimefunItemStack(
            "FN_FAL_RECYCLER_2",
            Material.PURPLE_STAINED_GLASS,
            "&dFN Electric Recycler II",
            "", "&eA machinery that can recycle",
            "&eitems to that of in lower form",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(2),
            LoreBuilder.powerPerSecond(336));

    public static final SlimefunItemStack FN_FAL_RECYCLER_3 = new SlimefunItemStack(
            "FN_FAL_RECYCLER_3",
            Material.PURPLE_STAINED_GLASS,
            "&dFN Electric Recycler III",
            "", "&eA machinery that can recycle",
            "&eitems to that of in lower form",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(4),
            LoreBuilder.powerPerSecond(672));

    public static final SlimefunItemStack FN_FAL_DOWNGRADER = new SlimefunItemStack(
            "FN_FAL_DOWNGRADER",
            Material.WHITE_GLAZED_TERRACOTTA,
            "&dFN Electric Machine Downgrader",
            "", "&eA machinery that can downgrade SF",
            "&emachines, great machine for mistakenly",
            "&ecrafted machines or want to downgrade",
            "&eextra machines",
            "",
            "&e- Machine gui must be opened in order",
            "  &efor scraps to drop by chance",
            "&e- 25% chance to get 1 scrap/downgrade",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(4),
            LoreBuilder.powerPerSecond(1500));

    public static final SlimefunItemStack MACHINE_PART = new SlimefunItemStack(
            "FN_MACHINERY_MACHINE_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("6ddd4a12da1cc2c9f9d6cd49fc778e3a11f3757de6dd312d70a0d47885189c0")),
            "&dMachine Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack MACHINE_PART_SCRAP = new SlimefunItemStack(
            "FN_MACHINERY_MACHINE_PART_SCRAP",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("6ddd4a12da1cc2c9f9d6cd49fc778e3a11f3757de6dd312d70a0d47885189c0")),
            "&dMachine Part",
            "&fCraft using metal scraps");

    public static final SlimefunItemStack COMPONENT_PART = new SlimefunItemStack(
            "FN_MACHINERY_COMPONENT_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("74657e89167b17ed581e87ce4215ce8d47145ab34038202d5ccefb0a9bd0d8f4")),
            "&dComponent Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack COMPONENT_PART_SCRAP = new SlimefunItemStack(
            "FN_MACHINERY_COMPONENT_PART_SCRAP",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("74657e89167b17ed581e87ce4215ce8d47145ab34038202d5ccefb0a9bd0d8f4")),
            "&dComponent Part",
            "&fCraft using metal scraps");

    public static final SlimefunItemStack MOTOR_SWITCH = new SlimefunItemStack(
            "FN_MACHINERY_MOTOR_SWITCH",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("48748ce96cedbfecaa463966d8fb1ac83c408feea89bd60d76d6024d3befe")),
            "&dMotor Switch",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack MOTOR_SWITCH_SCRAP = new SlimefunItemStack(
            "FN_MACHINERY_MOTOR_SWITCH_SCRAP",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("48748ce96cedbfecaa463966d8fb1ac83c408feea89bd60d76d6024d3befe")),
            "&dMotor Switch",
            "&fCraft using metal scraps");

    public static final SlimefunItemStack POWER_COMPONENT = new SlimefunItemStack(
            "FN_MACHINERY_POWER_COMPONENT",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("25ba8456e92f0790222c19c06f61180a195af1008569ed352b93a3c6d9ec7a98")),
            "&dPower Component",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack POWER_COMPONENT_SCRAP = new SlimefunItemStack(
            "FN_MACHINERY_POWER_COMPONENT_SCRAP",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("25ba8456e92f0790222c19c06f61180a195af1008569ed352b93a3c6d9ec7a98")),
            "&dPower Component",
            "&fCraft using metal scraps");

    public static final SlimefunItemStack GEAR_PART = new SlimefunItemStack(
            "FN_MACHINERY_GEAR_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("faf0c95ceba34c7fe6d33404feb87b4184ccce143978622c1647feaed2b63274")),
            "&dGear Part",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack GEAR_PART_SCRAP = new SlimefunItemStack(
            "FN_MACHINERY_GEAR_PART_SCRAP",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("faf0c95ceba34c7fe6d33404feb87b4184ccce143978622c1647feaed2b63274")),
            "&dGear Part",
            "&fCraft using metal scraps");

    public static final SlimefunItemStack THREAD_PART = new SlimefunItemStack(
            "FN_MACHINERY_THREAD_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("87ec65d6649ac1bf7b282575cef299f8601e51d8418d6e546e4fc71b218f7")),
            "&dThread Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack THREAD_PART_SCRAP = new SlimefunItemStack(
            "FN_MACHINERY_THREAD_PART_SCRAP",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("87ec65d6649ac1bf7b282575cef299f8601e51d8418d6e546e4fc71b218f7")),
            "&dThread Part",
            "&fCraft using metal scraps");

    public static final SlimefunItemStack COMPRESSOR_PART = new SlimefunItemStack(
            "FN_MACHINERY_COMPRESSOR_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("d3898f7c444223b7a91a8f038e224222fef8960cfbef94836b014a06ea4cba63")),
            "&dCompressor Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack CONDENSER_PART = new SlimefunItemStack(
            "FN_MACHINERY_CONDENSER_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("63be652b2e1b93ed8e93b427de455d446582e6c8d929f8fc96ac488a8f7f53")),
            "&dCondenser Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack RECYCLER_PART = new SlimefunItemStack(
            "FN_MACHINERY_RECYCLER_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("4817fc8e16b8e994efb908b71acd1d1352ca5aefa09fecc9339ebea450d83fb8")),
            "&dRecycler Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack DOWNGRADER_PART = new SlimefunItemStack(
            "FN_MACHINERY_DOWNGRADER_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("a44c5ce2eb643f8671c667e8802c9317ad8cc6af680d4ef671d8c0c63277900a")),
            "&dDowngrader Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack FUNNEL_PART = new SlimefunItemStack(
            "FN_MACHINERY_FUNNEL_PART",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("bb2abd66939f4cb7257a88cf52fbc6fdceec1433ec2a6ef16d62e34f6238781")),
            "&dFunnel Part",
            "&fEssential Part for FN Machinery",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack FUNNEL_PART_SCRAP = new SlimefunItemStack(
            "FN_MACHINERY_FUNNEL_PART_SCRAP",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("bb2abd66939f4cb7257a88cf52fbc6fdceec1433ec2a6ef16d62e34f6238781")),
            "&dFunnel Part",
            "&fCraft using metal scraps");

    public static final SlimefunItemStack DIAMOND_PLATING = new SlimefunItemStack(
            "FN_MACHINERY_DIAMOND_PLATING",
            Material.LIGHT_BLUE_CARPET,
            "&dDiamond Plating",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack ALUMINUM_PLATING = new SlimefunItemStack(
            "FN_MACHINERY_ALUMINUM_PLATING",
            Material.WHITE_CARPET,
            "&dAluminum Plating",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack GOLD_PLATING = new SlimefunItemStack(
            "FN_MACHINERY_GOLD_PLATING",
            Material.YELLOW_CARPET,
            "&dGold Plating",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack BRASS_PLATING = new SlimefunItemStack(
            "FN_MACHINERY_BRASS_PLATING",
            Material.BROWN_CARPET,
            "&dBrass Plating",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack REINFORCED_CASING = new SlimefunItemStack(
            "FN_MACHINERY_REINFORCED_CASING",
            Material.LIGHT_GRAY_CARPET,
            "&dReinforced Casing",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack UNBIND_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_UNBIND_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("4f01ec6331a3bc30a8204ec56398d08ca38788556bca9b81d776f6238d567367")),
            "&6Unbind Rune",
            "&fA magical rune from psijic time",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack SPIRAL_FIRE_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_SPIRAL_FIRE_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("8301aa86cafd4b2d732a9b4894cfcfc65edc828e8571b45dbf0a3ba96575cccf")),
            "&cSpiral Fire Rune",
            "&fA heart full of fire is fire",
            "&fthat spirals within this rune",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack SPIRIT_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_SPIRIT_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("c738b8af8d7ce1a26dc6d40180b3589403e11ef36a66d7c4590037732829542e")),
            "&fSpirit Rune",
            "&fSpirit thy come for",
            "&fI redeem thy soul",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack PESTILENCE_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_PESTILENCE_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("a8c4811395fbf7f620f05cc3175cef1515aaf775ba04a01045027f0693a90147")),
            "&aPestilence Rune",
            "&fA rune from the black death",
            "&fperiod that holds a deadly sin",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack SPARKLING_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_SPARKLING_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("f1e2428cb359988f4c4ff0e61de21385c62269de19a69762d773223b75dd1666")),
            "&eSparkling Rune",
            "&fToo bright and too flashy",
            "&ffor this rune is too deadly",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack CLOUD_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_CLOUD_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("2273740d454de962484712f9835e35119b37ab867fa6982d5cc1f333c2334e59")),
            "&3Cloud Rune",
            "&fUp in the skies is where",
            "&fthe clouds lies",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack ICE_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_ICE_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("cdc57c75adf39ec6f0e0916049dd9671e98a8a1e600104e84e645c988950bd7")),
            "&bIce Rune",
            "&fThe rune full of chill",
            "&fbut can you stay still",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack POWER_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_POWER_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("3b481c31dc683bdcb7d375a7c5db7ac7adf9e9fe8b6c04a64931613e29fe470e")),
            "&9Power Rune",
            "&fThe power from this rune",
            "&fcan do dangerous things",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack LINGER_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_LINGER_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("2c4a65c689b2d36409100a60c2ab8d3d0a67ce94eea3c1f7ac974fd893568b5d")),
            "&2Linger Rune",
            "&fIt lingers from top to",
            "&fbottom and soon the middle",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack AGILITY_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_AGILITY_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("35f4861aa5b22ee28a90e75dab45d221efd14c0b1ecc8ee998fb67e43bb8f3de")),
            "&eAgility Rune",
            "&fFaster means better but",
            "&fdoes it matter",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack INTELLECT_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_INTELLECT_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("977c1fc93216e96d435cf962e1173de8d1a249b644894d72676eba732fcd56e7")),
            "&9Intellect Rune",
            "&fVery useful rune in the",
            "&ftime of desperation",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack HEART_RUNE = new SlimefunItemStack(
            "FN_MAGICAL_ITEMS_HEART_RUNE",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("2c1c179ad51955f1522c48ea9931f09c162741b45e22e9d3feb682c7e5ed8274")),
            "&dHeart Rune",
            "&fRejuvenate through this rune",
            "&fas you sense the aura inside",
            "",
            "&d&oFN Magical Items");

    public static final SlimefunItemStack BASIC_MACHINE_BLOCK = new SlimefunItemStack(
            "FN_MACHINERY_BASIC_MACHINE_BLOCK",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("883dd0f90df05fe6a09aaccaf54bc043e455e1c865bda1fd272e3f47fb9bb910")),
            "&dBasic Machine Block",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack HIGHTECH_MACHINE_BLOCK = new SlimefunItemStack(
            "FN_MACHINERY_HIGHTECH_MACHINE_BLOCK",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("5a7b3b16e5d0c4cfd21c4eb9133e969aad7cc7303ccdf317512e26a4879b51")),
            "&dHigh Tech Machine Block",
            "&fEssential Part for FN Machinery"
            , "&fand Power Xpansion",
            "",
            "&d&oFN Machinery Items");

    public static final SlimefunItemStack FN_ASSEMBLY_STATION = new SlimefunItemStack(
            "FN_ASSEMBLY_STATION",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("583150f055935058634576185977ffba49ff4679faa03ad0008feaa0161865b3")),
            "&dFN Assembly Station"
    );

    public static final SlimefunItemStack FN_SCRAP_RECYCLER = new SlimefunItemStack(
            "FN_SCRAP_RECYCLER",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("656634b556caf5382de65038a10e4d79c7c18695048599df74f9c67c1e1e8736")),
            "&dFN Scrap Recycler"
    );

    public static final SlimefunItemStack FN_METAL_SCRAPS = new SlimefunItemStack(
            "FN_METAL_SCRAPS",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("3ff0bee591e5f0000ef16f966b949adcb5c2f409a14ccfc5b91222fd925045db")),
            "&dMetal Scrap",
            "&fLeftover from downgrading machines"
            , "&fcan be used to craft FN Machinery Items",
            "",
            "&d&oFN Machinery Items"
    );

    public static final SlimefunItemStack FN_BLOCK_BREAKER_1 = new SlimefunItemStack(
            "FN_BLOCK_BREAKER_I",
            Material.DISPENSER,
            "&4Electric Block Breaker I",
            "",
            "&6Place block in front",
            "&6of the dispenser to break",
            "&d(Vanilla)",
            "",
            "&eRate: " + "&eticks",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.AVERAGE, MachineType.MACHINE),
            LoreBuilder.powerBuffer(512),
            LoreBuilderDynamic.powerPerTick(32)
    );

    public static final SlimefunItemStack FN_BLOCK_BREAKER_2 = new SlimefunItemStack(
            "FN_BLOCK_BREAKER_II",
            Material.DISPENSER,
            "&4Electric Block Breaker II",
            "",
            "&6Place block in front",
            "&6of the dispenser to break",
            "&d(Vanilla)",
            "",
            "&eRate: " + "&eticks",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            LoreBuilder.powerBuffer(1024),
            LoreBuilderDynamic.powerPerTick(64)
    );

    public static final SlimefunItemStack FN_BLOCK_BREAKER_3 = new SlimefunItemStack(
            "FN_BLOCK_BREAKER_III",
            Material.DISPENSER,
            "&4Electric Block Breaker III",
            "",
            "&6Place block in front",
            "&6of the dispenser to break",
            "&d(Vanilla)",
            "",
            "&eRate: " + "&eticks",
            "",
            "&d&oFN Machinery",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.powerBuffer(2048),
            LoreBuilderDynamic.powerPerTick(128)
    );

    public static final SlimefunItemStack FN_STICK = new SlimefunItemStack(
            "FN_MYSTERY_STICK_1",
            Material.STICK,
            "&cMysterious Stick",
            "&fI wonder what this stick does"
    );


    public static final SlimefunItemStack FN_STICK_2 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_2",
            Material.STICK,
            "&cMysterious Stick II",
            "&fAnother stick of no matter what is it"
    );

    public static final SlimefunItemStack FN_STICK_3 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_3",
            Material.STICK,
            "&cMysterious Stick III",
            "&fI feel coordinated when holding this stick"
    );

    public static final SlimefunItemStack FN_STICK_4 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_4",
            Material.STICK,
            "&cMysterious Stick IV",
            "&fDid I use this before or maybe not"
    );

    public static final SlimefunItemStack FN_STICK_5 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_5",
            Material.STICK,
            "&cMysterious Stick V",
            "&fI know you are tired of this stick thing"
    );

    public static final SlimefunItemStack FN_STICK_6 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_6",
            Material.STICK,
            "&cMysterious Stick VI",
            "&fMay the force and accuracy be with you"
    );

    public static final SlimefunItemStack FN_STICK_7 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_7",
            Material.STICK,
            "&cMysterious Stick VII",
            "&fThe aura on this stick is mesmerizing"
    );

    public static final SlimefunItemStack FN_STICK_8 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_8",
            Material.STICK,
            "&cMysterious Stick VIII",
            "&fThis stick is kinda heavy"
    );

    public static final SlimefunItemStack FN_STICK_9 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_9",
            Material.STICK,
            "&cMysterious Stick IX",
            "&fYou need more mana when using this"
    );

    public static final SlimefunItemStack FN_STICK_10 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_10",
            Material.STICK,
            "&cMysterious Stick X",
            "&fDeadly or creepy stick"
    );

    public static final SlimefunItemStack FN_STICK_11 = new SlimefunItemStack(
            "FN_MYSTERY_STICK_11",
            Material.STICK,
            "&cMysterious Stick XI",
            "&fThe stick of the nords"
    );

    public static final SlimefunItemStack FN_STICK_ALTAR = new SlimefunItemStack(
            "FN_STICK_ALTAR",
            Material.ENCHANTING_TABLE,
            "&dFN Mystery Stick Altar"
    );

    public static final SlimefunItemStack FN_GEM_ALTAR = new SlimefunItemStack(
            "FN_GEM_ALTAR",
            Material.SMITHING_TABLE,
            "&dFN Gem Altar"
    );

    public static final SlimefunItemStack FN_GEM_UPGRADER = new SlimefunItemStack(
            "FN_GEM_UPGRADER",
            Material.SMITHING_TABLE,
            "&dFN Gem Upgrader"
    );

    public static final SlimefunItemStack FN_GEM_DOWNGRADER = new SlimefunItemStack(
            "FN_GEM_DOWNGRADER",
            Material.STONECUTTER,
            "&dFN Gem Downgrader"
    );

    public static final SlimefunItemStack FN_MAGIC_ALTAR = new SlimefunItemStack(
            "FN_MAGIC_ALTAR",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("e34930125767c2e34ac939ec94a2aa4e79c381ee336760695c6c874cf12")),
            "&dFN Magic Altar"
    );

    public static final SlimefunItemStack FN_GEAR_HELMET = new SlimefunItemStack(
            "FN_GEAR_HELMET",
            Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
                    Material.NETHERITE_HELMET : Material.DIAMOND_HELMET,
            "&cFN's Field Tested Helmet",
            "&c◬◬◬◬◬◬| &d&lLore &6|◬◬◬◬◬◬",
            "",
            "&fWear this helmet in the name of FN and",
            "&fevery battle should make it become more",
            "&fstronger with bonus attributes and enchants",
            "",
            "&c◈◈◈◈◈◈| &d&lStats &6|◈◈◈◈◈◈",
            ChatColor.YELLOW + "Helmet Level: 0",
            ChatColor.YELLOW + "Progress:",
            ChatColor.GRAY + "[" + "■■■■■■■■■■" + ChatColor.GRAY + "]"

    );

    public static final SlimefunItemStack FN_GEAR_CHESTPLATE = new SlimefunItemStack(
            "FN_GEAR_CHESTPLATE",
            Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
                    Material.NETHERITE_CHESTPLATE : Material.DIAMOND_CHESTPLATE,
            "&cFN's Battle Scarred Chestplate",
            "&c◬◬◬◬◬◬| &d&lLore &6|◬◬◬◬◬◬",
            "",
            "&fThe armor from the past brought to life",
            "&fonce again. It becomes more powerful during",
            "&ftimes of war and conflict",
            "",
            "&c◈◈◈◈◈◈| &d&lStats &6|◈◈◈◈◈◈",
            ChatColor.YELLOW + "Armor Level: 0",
            ChatColor.YELLOW + "Progress:",
            ChatColor.GRAY + "[" + "■■■■■■■■■■" + ChatColor.GRAY + "]"

    );

    public static final SlimefunItemStack FN_GEAR_LEGGINGS = new SlimefunItemStack(
            "FN_GEAR_LEGGINGS",
            Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
                    Material.NETHERITE_LEGGINGS: Material.DIAMOND_LEGGINGS,
            "&cFN's Chausses of Eminence",
            "&c◬◬◬◬◬◬| &d&lLore &6|◬◬◬◬◬◬",
            "",
            "&fGlorious leggings worn by FN during war",
            "&fand was glorified on every victory against",
            "&fhis foes",
            "",
            "&c◈◈◈◈◈◈| &d&lStats &6|◈◈◈◈◈◈",
            ChatColor.YELLOW + "Leggings Level: 0",
            ChatColor.YELLOW + "Progress:",
            ChatColor.GRAY + "[" + "■■■■■■■■■■" + ChatColor.GRAY + "]"

    );

    public static final SlimefunItemStack FN_GEAR_BOOTS = new SlimefunItemStack(
            "FN_GEAR_BOOTS",
            Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16) ?
                    Material.NETHERITE_BOOTS : Material.DIAMOND_BOOTS,
            "&cFN's Expedition Combat Boots",
            "&c◬◬◬◬◬◬| &d&lLore &6|◬◬◬◬◬◬",
            "",
            "&fSoldiers from FN's army only wants to posses",
            "&fthis historical boots but it was kept",
            "&fhidden under the hands of the zion people",
            "",
            "&c◈◈◈◈◈◈| &d&lStats &6|◈◈◈◈◈◈",
            ChatColor.YELLOW + "Boots Level: 0",
            ChatColor.YELLOW + "Progress:",
            ChatColor.GRAY + "[" + "■■■■■■■■■■" + ChatColor.GRAY + "]"

    );

    public static final SlimefunItemStack FN_STAFF_TP = new SlimefunItemStack(
            "FN_STAFF_TP",
            Material.BLAZE_ROD,
            "&cStaff of Teleportation",
            "",
            "&dTeleport to a target block by",
            "&dright clicking it",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_INVI = new SlimefunItemStack(
            "FN_STAFF_INVI",
            Material.BLAZE_ROD,
            "&cStaff of Invisibility",
            "",
            "&d6 seconds of invisibility",
            "&deven your armor and name",
            "&dare hidden",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_LOCOMOTION = new SlimefunItemStack(
            "FN_STAFF_LOCOMOTION",
            Material.BLAZE_ROD,
            "&cStaff of Locomotion",
            "",
            "&dMove entities to a target location by right",
            "&dclicking to select and left click to move",
            "",
            "&eUses: " + "&eleft",
            "&eEntity stored: none"
    );

    public static final SlimefunItemStack FN_STAFF_HELLFIRE = new SlimefunItemStack(
            "FN_STAFF_HELLFIRE",
            Material.BLAZE_ROD,
            "&cStaff of Hellfire",
            "",
            "&dSpawn an area of effect cloud",
            "&dwhere entities are set on fire",
            "&dif inside the radius for 8 seconds",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_DEEPFREEZE = new SlimefunItemStack(
            "FN_STAFF_DEEPFREEZE",
            Material.BLAZE_ROD,
            "&cStaff of Deep-Freeze",
            "",
            "&dSpawn an area of effect cloud where",
            "&dentities are being slowed by the freezing",
            "&dcold if inside the radius for 8 seconds",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_CONFUSION = new SlimefunItemStack(
            "FN_STAFF_CONFUSION",
            Material.BLAZE_ROD,
            "&cStaff of Confusion",
            "",
            "&dSpawn an area of effect cloud where",
            "&dentities are confused of their direction",
            "&dif inside the radius for 8 seconds",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_GRAVITY = new SlimefunItemStack(
            "FN_STAFF_GRAVITY",
            Material.BLAZE_ROD,
            "&cStaff of Gravitational Pull",
            "",
            "&dSpawn an area of effect cloud where",
            "&dentities are being pulled by the gravitational",
            "&dforce if inside the radius for 8 seconds",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_STALLION = new SlimefunItemStack(
            "FN_STAFF_STALLION",
            Material.BLAZE_ROD,
            "&cStaff of Stallion",
            "",
            "&dSpawns a skeleton horse that is",
            "&drideable until passenger dismount",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_FORCE = new SlimefunItemStack(
            "FN_STAFF_FORCE",
            Material.BLAZE_ROD,
            "&cStaff of Force",
            "",
            "&dRight click to spawn a cloud of effect",
            "&dthat gives a force push forward or",
            "&dshift-right-click to spawn a different cloud",
            "&dof effect that gives a backward force",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_HEALING = new SlimefunItemStack(
            "FN_STAFF_HEALING",
            Material.BLAZE_ROD,
            "&cStaff of Healing",
            "",
            "&dRight click to spawn a cloud of effect",
            "&dthat heals the caster only if inside the",
            "&dradius for 8 seconds",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_INVULNERABILITY = new SlimefunItemStack(
            "FN_STAFF_INVULNERABILITY",
            Material.BLAZE_ROD,
            "&cStaff of Invulnerability",
            "",
            "&dRight click to spawn a cloud of effect",
            "&dthat protects entities inside the radius",
            "&dfrom damage for 8 seconds",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_EXPLOSION = new SlimefunItemStack(
            "FN_STAFF_EXPLOSION",
            Material.BLAZE_ROD,
            "&cStaff of Explosion",
            "",
            "&dRight click a target block to",
            "&dyield an explosion causing damage",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_MUSTER = new SlimefunItemStack(
            "FN_STAFF_MUSTER",
            Material.BLAZE_ROD,
            "&cStaff of Muster",
            "",
            "&dRight click a target block to teleport",
            "&dnearby entities that are on ground",
            "&dwithin 50 block radius",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_AWARENESS = new SlimefunItemStack(
            "FN_STAFF_AWARENESS",
            Material.BLAZE_ROD,
            "&cStaff of Awareness",
            "",
            "&dRight click to receive information",
            "&dregarding the nearest players around",
            "&d50 block radius",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_MINERALS = new SlimefunItemStack(
            "FN_STAFF_MINERALS",
            Material.BLAZE_ROD,
            "&cStaff of Minerals",
            "",
            "&dRight click to receive mythical",
            "&dinformation that awaits upon using",
            "&dthe staff",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_FANGS = new SlimefunItemStack(
            "FN_STAFF_FANGS",
            Material.BLAZE_ROD,
            "&cStaff of Fangs",
            "",
            "&dRight click a target block to",
            "&dspawn evoker fangs that causes",
            "&ddamage to entities",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_SKULLS = new SlimefunItemStack(
            "FN_STAFF_SKULLS",
            Material.BLAZE_ROD,
            "&cStaff of Skulls",
            "",
            "&dRight click to launch",
            "&dwither skull projectiles",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_AIR_STRIDER = new SlimefunItemStack(
            "FN_STAFF_AIR_STRIDER",
            Material.BLAZE_ROD,
            "&cStaff of Air Strider",
            "",
            "&dRight click to gain the ability to",
            "&dwalk on the air for 10 seconds",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_STAFF_COBWEB = new SlimefunItemStack(
            "FN_STAFF_COBWEB",
            Material.BLAZE_ROD,
            "&cStaff of Cobweb",
            "",
            "&dRight click a target block",
            "&dto spawn a wall of cobwebs",
            "",
            "&eUses: " + "&eleft"
    );

    public static final SlimefunItemStack FN_QUIVER = new SlimefunItemStack(
            "FN_QUIVER",
            Material.LEATHER,
            "&bBow Quiver (Normal)",
            "",
            "&dStore inside the quiver",
            "&dby right clicking arrows or",
            "&dshift click quiver to withdraw",
            "",
            "&eClick to change state",
            "&eSize: 192 Arrows",
            "&eArrows: " + "&f0",
            "&eState: Closed (No Arrows)"
    );

    public static final SlimefunItemStack FN_SPECTRAL_QUIVER = new SlimefunItemStack(
            "FN_SPECTRAL_QUIVER",
            Material.LEATHER,
            "&aBow Quiver (Spectral)",
            "",
            "&dStore inside the quiver by",
            "&dright clicking spectral arrows or",
            "&dshift click quiver to withdraw",
            "",
            "&eClick to close/open",
            "&eSize: 192 Spectral Arrows",
            "&eArrows: " + "&f0",
            "&eState: Closed (No Arrows)"
    );

    public static final SlimefunItemStack FN_UPGRADED_QUIVER = new SlimefunItemStack(
            "FN_UPGRADED_QUIVER",
            Material.LEATHER,
            "&6Upgraded Bow Quiver (Normal)",
            "",
            "&dStore inside the quiver",
            "&dby right clicking arrows or",
            "&dshift click to withdraw",
            "",
            "&eClick to change state",
            "&eSize: 288 Arrows",
            "&eArrows: " + "&f0",
            "&eState: Closed (No Arrows)"
    );

    public static final SlimefunItemStack FN_UPGRADED_SPECTRAL_QUIVER = new SlimefunItemStack(
            "FN_UPGRADED_SPECTRAL_QUIVER",
            Material.LEATHER,
            "&cUpgraded Bow Quiver (Spectral)",
            "",
            "&dStore inside the quiver",
            "&dby right clicking spectral arrows or",
            "&dshift click quiver to withdraw",
            "",
            "&eClick to change state",
            "&eSize: 288 Arrows",
            "&eArrows: " + "&f0",
            "&eState: Closed (No Arrows)"
    );

    public static final SlimefunItemStack FN_HOE_5X5 = new SlimefunItemStack(
            "FN_HOE_5X5",
            Material.DIAMOND_HOE,
            "&cFN's Hoe",
            "",
            "&dCan soil/till land and harvest",
            "&dcrops in a 5x5 square area"
    );

    public static final SlimefunItemStack FN_HOE_5X5_AUTO_PLANT = new SlimefunItemStack(
            "FN_HOE_5X5_AUTO_PLANT",
            Material.DIAMOND_HOE,
            "&cFN's Reseeding Hoe",
            "",
            "&dCan soil/till land and harvest",
            "&dcrops in a 5x5 square area",
            "",
            "&dAuto plants if the crops are",
            "&dharvested at maximum age"
    );

    public static final SlimefunItemStack FN_GEM_INFERNO = new SlimefunItemStack(
            "FN_GEM_INFERNO",
            Material.EMERALD,
            "&cInferno Gem",
            "",
            "&eA chance to set enemies in a",
            "&e7 block radius on fire",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a sword",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_ARMOR_IMPAIR = new SlimefunItemStack(
            "FN_GEM_ARMOR_IMPAIR",
            Material.EMERALD,
            "&cArmor Impair Gem",
            "",
            "&eA chance to deal extra durability",
            "&edamage to all enemy armor at proc",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_TELEPATHY = new SlimefunItemStack(
            "FN_GEM_TELEPATHY",
            Material.EMERALD,
            "&cTelepathy Gem",
            "",
            "&eAutomatically places blocks broken by",
            "&ethe tool in the player's inventory",
            "",
            "&dDrag and drop on a tool",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_THUNDER = new SlimefunItemStack(
            "FN_GEM_THUNDER",
            Material.EMERALD,
            "&cThunderbolt Gem",
            "",
            "&eA chance to strike",
            "&elightning to enemies",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_HASTY = new SlimefunItemStack(
            "FN_GEM_HASTY",
            Material.EMERALD,
            "&cHasty Gem",
            "",
            "&eA chance to receive 4 seconds",
            "&eof haste when mining ores",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a tool",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_THORN_AWAY = new SlimefunItemStack(
            "FN_GEM_THORN_AWAY",
            Material.EMERALD,
            "&cThorn Away Gem",
            "",
            "&eA chance to negate",
            "&eall thorn damage",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a chestplate",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_IMPOSTOR = new SlimefunItemStack(
            "FN_GEM_IMPOSTOR",
            Material.EMERALD,
            "&cImpostor Gem",
            "",
            "&eA chance to teleport behind",
            "&ethe player that is hitting you",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a helmet",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_PSYCHOKINESIS = new SlimefunItemStack(
            "FN_GEM_PSYCHOKINESIS",
            Material.EMERALD,
            "&cPsychokinesis Gem",
            "",
            "&eA chance to move enemies towards",
            "&eyou when got hit by your arrow",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a bow",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_AXETHROWIE = new SlimefunItemStack(
            "FN_GEM_AXETHROWIE",
            Material.EMERALD,
            "&cAxe throwie Gem",
            "",
            "&eGives you the ability to throw axes",
            "&ethat can hit entities and damage them",
            "",
            "&dDrag and drop on an axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_TRI_SWORD = new SlimefunItemStack(
            "FN_GEM_TRI_SWORD",
            Material.EMERALD,
            "&cTri-Sword Gem",
            "",
            "&eGives you the ability to throw your",
            "&esword with additional 2 swords around it that",
            "&ecan cut through entities and damage them",
            "",
            "&dDrag and drop on an sword",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_DAMNATION = new SlimefunItemStack(
            "FN_GEM_DAMNATION",
            Material.EMERALD,
            "&cDamnation Gem",
            "",
            "&eShift Right click the weapon that has this gem",
            "&ethe weapon is ready to serve you in battle and",
            "&ewill attack entities in the direction of your",
            "&ecrosshair after 8 seconds",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_RETALIATE = new SlimefunItemStack(
            "FN_GEM_RETALIATE",
            Material.EMERALD,
            "&cRetaliate Gem",
            "",
            "&eAllows your weapons to return back to",
            "&eyou after throwing it despite the distance.",
            "&eThis gem requires throwable weapon gem",
            "&efrom which one below:",
            "&e- Damnation Gem",
            "&e- Tri-Sword Gem",
            "&e- Axe Throwie Gem",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_BLINDBIND = new SlimefunItemStack(
            "FN_GEM_BLINDBIND",
            Material.EMERALD,
            "&cBlind bind Gem",
            "",
            "&eA chance to blind enemies for",
            "&e4 seconds when got hit by your arrow",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a bow",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_GUARDIAN = new SlimefunItemStack(
            "FN_GEM_GUARDIAN",
            Material.EMERALD,
            "&cGuardian Gem",
            "",
            "&eA chance to spawn a guardian that will",
            "&efight for you until death, the armor that",
            "&ehas this gem must be worn in order for",
            "&ethe guardian to protect you from enemies",
            "&ewhen you are being attacked.",
            "&f- Unequipped armor will remove any active guardians",
            "&f- Guardians don't attack other guardians and",
            "  &fcan only attack the owner of that guardian",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a chestplate",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_DISRUPTED = new SlimefunItemStack(
            "FN_GEM_DISRUPTED",
            Material.EMERALD,
            "&cDisrupted Gem",
            "",
            "&eA chance to prevent your enemy",
            "&eguardian from spawning",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a sword",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_ARROW_AVERT = new SlimefunItemStack(
            "FN_GEM_ARROW_AVERT",
            Material.EMERALD,
            "&cArrow Avert Gem",
            "",
            "&eA chance to prevent arrow shot by a player",
            "&efrom hitting you, arrow is averted taking",
            "&eno damage when you get hit",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_DEBERSERK = new SlimefunItemStack(
            "FN_GEM_DEBERSERK",
            Material.EMERALD,
            "&cDeberserk Gem",
            "",
            "&eA chance to decrease damage",
            "&etaken from axes by 30%",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_PARRY = new SlimefunItemStack(
            "FN_GEM_PARRY",
            Material.EMERALD,
            "&cParry Gem",
            "",
            "&eA chance to decrease damage",
            "&etaken from swords by 25%",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_FLAWLESS_UNBIND = new SlimefunItemStack(
            "FN_GEM_FLAWLESS_UNBIND",
            Material.DIAMOND,
            "&bFlawless Unbind Gem",
            "",
            "&e% chance to remove any of the",
            "&eselected gem from an item",
            "",
            "&f&lInstruction",
            "&dRight hand: &cUnbind Gem",
            "&dLeft hand: &cWeapon/Armor/Tools",
            "&dAction: &cRight click"
    );

    public static final SlimefunItemStack FN_GEM_PRECIOUS_UNBIND = new SlimefunItemStack(
            "FN_GEM_PRECIOUS_UNBIND",
            Material.DIAMOND,
            "&dPrecious Unbind Gem",
            "",
            "&e% chance to remove any of the",
            "&eselected gem from an item",
            "",
            "&f&lInstruction",
            "&dRight hand: &cUnbind Gem",
            "&dLeft hand: &cWeapon/Armor/Tools",
            "&dAction: &cRight click"
    );

    public static final SlimefunItemStack FN_GEM_BLEMISHED_UNBIND = new SlimefunItemStack(
            "FN_GEM_BLEMISHED_UNBIND",
            Material.DIAMOND,
            "&aBlemished Unbind Gem",
            "",
            "&e% chance to remove any of the",
            "&eselected gem from an item",
            "",
            "&f&lInstruction",
            "&dRight hand: &cUnbind Gem",
            "&dLeft hand: &cWeapon/Armor/Tools",
            "&dAction: &cRight click"
    );

    public static final SlimefunItemStack FN_GEM_DAMAGED_UNBIND = new SlimefunItemStack(
            "FN_GEM_DAMAGED_UNBIND",
            Material.DIAMOND,
            "&6Damaged Unbind Gem",
            "",
            "&e% chance to remove any of the",
            "&eselected gem from an item",
            "",
            "&f&lInstruction",
            "&dRight hand: &cUnbind Gem",
            "&dLeft hand: &cWeapon/Armor/Tools",
            "&dAction: &cRight click"
    );

    public static final SlimefunItemStack FN_GEM_ADAMANTINE = new SlimefunItemStack(
            "FN_GEM_ADAMANTINE",
            Material.EMERALD,
            "&cAdamantine Gem",
            "",
            "&eA chance to prevent weapon or tools",
            "&efrom having durability damage",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on weapon or",
            "&dtools to bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_STOUT = new SlimefunItemStack(
            "FN_GEM_STOUT",
            Material.EMERALD,
            "&cStout Gem",
            "",
            "&eA chance to prevent armor from",
            "&etaking any durability damage",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_AWAKEN = new SlimefunItemStack(
            "FN_GEM_AWAKEN",
            Material.EMERALD,
            "&cAwaken Gem",
            "",
            "&eA chance to instant resurrect the",
            "&eplayer upon death at same location",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a helmet",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_AVENGE = new SlimefunItemStack(
            "FN_GEM_AVENGE",
            Material.EMERALD,
            "&cAvenge Gem",
            "",
            "&eA chance to spawn primed creeper",
            "&eupon player death at same location",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a boots",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_ACHILLES_HEEL = new SlimefunItemStack(
            "FN_GEM_ACHILLES_HEEL",
            Material.EMERALD,
            "&cAchilles Heel Gem",
            "",
            "&eA chance to deal 2x damage when",
            "&ethe arrow hits the achilles heel",
            "&eof an entity or player",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a bow or crossbow",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_DISARMOR = new SlimefunItemStack(
            "FN_GEM_DISARMOR",
            Material.EMERALD,
            "&cDisarmor Gem",
            "",
            "&eA chance of unequipping a random",
            "&earmor piece from your enemy",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_DISARM = new SlimefunItemStack(
            "FN_GEM_DISARM",
            Material.EMERALD,
            "&cDisarm Gem",
            "",
            "&eA chance of unequipping the weapon",
            "&ethat your enemy is holding",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_SMOKE_CRIMINAL = new SlimefunItemStack(
            "FN_GEM_SMOKE_CRIMINAL",
            Material.EMERALD,
            "&cSmoke Criminal Gem",
            "",
            "&eA chance to become invulnerable for",
            "&e5 seconds when health is below 30%",
            "&ewhile being protected by the smoke",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on a boots",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_LIFESTEAL = new SlimefunItemStack(
            "FN_GEM_LIFESTEAL",
            Material.EMERALD,
            "&cLifesteal Gem",
            "",
            "&eA chance to steal 1 heart from",
            "&eyour enemy as long as your",
            "&ehealth is below your max health",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_ATROPHY = new SlimefunItemStack(
            "FN_GEM_ATROPHY",
            Material.EMERALD,
            "&cAtrophy Gem",
            "",
            "&eA chance to deal wither effect",
            "&eat your enemies that lasts for",
            "&e4 seconds",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Level 1 wither effect",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Level 2 wither effect",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Level 3 wither effect",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Level 4 wither effect",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_BANE = new SlimefunItemStack(
            "FN_GEM_BANE",
            Material.EMERALD,
            "&cBane Gem",
            "",
            "&eA chance to deal poison effect",
            "&eat your enemies that lasts for",
            "&e4 seconds",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Level 1 poison effect",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Level 2 poison effect",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Level 3 poison effect",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Level 4 poison effect",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_SEDATE = new SlimefunItemStack(
            "FN_GEM_SEDATE",
            Material.EMERALD,
            "&cSedate Gem",
            "",
            "&eA chance to deal slow effect",
            "&eat your enemies that lasts for",
            "&e4 seconds",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Level 1 slow effect",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Level 2 slow effect",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Level 3 slow effect",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Level 4 slow effect",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_DECREPIT = new SlimefunItemStack(
            "FN_GEM_DECREPIT",
            Material.EMERALD,
            "&cDecrepit Gem",
            "",
            "&eA chance to deal weakness effect",
            "&eat your enemies that lasts for",
            "&e4 seconds",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Level 1 weakness effect",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Level 2 weakness effect",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Level 3 weakness effect",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Level 4 weakness effect",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_DECEPTION = new SlimefunItemStack(
            "FN_GEM_DECEPTION",
            Material.EMERALD,
            "&cDeception Gem",
            "",
            "&eA chance to deal blindness effect",
            "&eat your enemies that lasts for",
            "&e4 seconds",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Level 1 blindness effect",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Level 2 blindness effect",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Level 3 blindness effect",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Level 4 blindness effect",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_CELERITY = new SlimefunItemStack(
            "FN_GEM_CELERITY",
            Material.EMERALD,
            "&cCelerity Gem",
            "",
            "&eA chance to deal swiftness effect",
            "&eat your enemies that lasts for",
            "&e4 seconds",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Level 1 swiftness effect",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Level 2 swiftness effect",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Level 3 swiftness effect",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Level 4 swiftness effect",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_LOOT = new SlimefunItemStack(
            "FN_GEM_LOOT",
            Material.EMERALD,
            "&cLoot Gem",
            "",
            "&eA chance to steal money from your",
            "&eenemy worth 4$ of currency. Make",
            "&eyour enemies lose money!",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6⨳ Tier II: " + "&e%",
            "&6⨳ Tier III: " + "&e%",
            "&6⨳ Tier IV: " + "&e%",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_SHOCKWAVE = new SlimefunItemStack(
            "FN_GEM_SHOCKWAVE",
            Material.EMERALD,
            "&cShockwave Gem",
            "",
            "&eA chance to deal shockwave damage",
            "&eto all enemies in a 8 block radius",
            "&eand bounce them off the ground!",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Damage: &f3",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Damage: &f6",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Damage: &f9",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Damage: &f12",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on any armor type",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_BERSERK = new SlimefunItemStack(
            "FN_GEM_BERSERK",
            Material.EMERALD,
            "&cBerserk Gem",
            "",
            "&eA chance to deal more damage against",
            "&eenemies when your health is below 30%.",
            "&eHigher tier gem, much deadly!",
            "",
            "&c◢◤◢◤◢◤ Tiers &6◥◣◥◣◥◣",
            "&6⨳ Tier I: " + "&e%",
            "&6  - Damage: &f+6% base damage",
            "&6⨳ Tier II: " + "&e%",
            "&6  - Damage: &f+12% base damage",
            "&6⨳ Tier III: " + "&e%",
            "&6  - Damage: &f+18% base damage",
            "&6⨳ Tier IV: " + "&e%",
            "&6  - Damage: &f+30% base damage",
            "&c◢◤◢◤◢◤◢◤◢◤◢&6◣◥◣◥◣◥◣◥◣◥◣",
            "",
            "&dDrag and drop on sword or axe",
            "&dto bind this gem"
    );

    public static final SlimefunItemStack FN_GEM_UPGRADES_DISPLAY_ITEM = new SlimefunItemStack(
            "FN_GEM_UPGRADES_DISPLAY_ITEM",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("d3623521c8111ad29e9dcf7acc56085a9ab07da732d1518976aee61d0b3e3bd6")),
            "&bGem Tier Upgrades",
            "",
            "&dClick this item to know how to upgrade",
            "&dgems and what recipe are needed in the",
            "&dFN Gem Upgrader Multiblock"
    );

    public static final SlimefunItemStack FN_GEM_DOWNGRADES_DISPLAY_ITEM = new SlimefunItemStack(
            "FN_GEM_DOWNGRADE_DISPLAY_ITEM",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("36161daa3589ec9c8187459ac36fd4dd2646c040678d3bfacb72a2210c6c801c")),
            "&bGem Tier Downgrades",
            "",
            "&dClick this item to know how to downgrade",
            "&dgems and what recipe are needed in the",
            "&dFN Gem Downgrader Multiblock"
    );

    public static final SlimefunItemStack FN_GEM_FINE_JASPER_CRAFTING = new SlimefunItemStack(
            "FN_GEM_FINE_JASPER_CRAFTING",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode("aac15f6fcf2ce963ef4ca71f1a8685adb97eb769e1d11194cbbd2e964a88978c")),
            "&bFine Jasper Gemstone",
            "",
            "&fA shiny gem needed for upgrading",
            "&fthose fn gems that you've been using",
            "&fsince then"
    );

    public static final SlimefunItemStack FN_BLOCK_ROTATOR = new SlimefunItemStack(
            "FN_BLOCK_ROTATOR",
            Material.CLOCK,
            "&cFN's Block Rotator",
            "",
            "&dRight click to rotate orientable or",
            "&ddirectional blocks. &f(Terracotta, Chains, etc)",
            "",
            "&dShift right click to flip slab, bisected",
            "&dor orientable blocks &f(Slabs, stairs, etc)"
    );

    public static final SlimefunItemStack FN_AUTO_LADDER = new SlimefunItemStack(
            "FN_AUTO_LADDER",
            Material.LADDER,
            "&cFN's Auto Ladder",
            "",
            "&dAutomatically places a ladder from top to",
            "&dbottom with a max distance of 8 blocks"
    );

    public static final SlimefunItemStack FN_ORIENT_PEARL = new SlimefunItemStack(
            "FN_ORIENT_PEARL",
            Material.ENDER_PEARL,
            "&cFN's Orient Pearl",
            "",
            "&dAn ender pearl that teleports",
            "&dthe player together with the",
            "&dmounted entity (Horse, etc)"
    );

    public static final SlimefunItemStack FN_THROWABLE_TORCH = new SlimefunItemStack(
            "FN_THROWABLE_TORCH",
            Material.TORCH,
            "&cFN's Throwable Torch",
            "",
            "&dRight click to throw in the target",
            "&ddirection and it will be placed if",
            "&da block got hit. Entities who got hit",
            "&dwill be set on fire for 4 seconds"
    );

    public static final SlimefunItemStack FN_JUKEBOX_I = new SlimefunItemStack(
            "FN_JUKEBOX_I",
            Material.JUKEBOX, "&5F&dN &fJ&bu&ek&ce&5b&do&4x I",
            "", "&eA jukebox that can store and play",
            "&emusic disc, it can loop through",
            "&eentire disc slots and autoplay",
            "&ea new disc after one has finished",
            "",
            "&dSlot amount: &d&o3 music disc slots",
            "",
            LoreBuilder.powerBuffer(512),
            LoreBuilder.powerPerSecond(3));

    public static final SlimefunItemStack FN_JUKEBOX_II = new SlimefunItemStack(
            "FN_JUKEBOX_II",
            Material.JUKEBOX, "&5F&dN &fJ&bu&ek&ce&5b&do&4x II",
            "", "&eA jukebox that can store and play",
            "&emusic disc, it can loop through",
            "&eentire disc slots and autoplay",
            "&ea new disc after one has finished",
            "",
            "&dSlot amount: &d&o7 music disc slots",
            "",
            LoreBuilder.powerBuffer(768),
            LoreBuilder.powerPerSecond(8));

    public static final SlimefunItemStack FN_JUKEBOX_III = new SlimefunItemStack(
            "FN_JUKEBOX_III",
            Material.JUKEBOX, "&5F&dN &fJ&bu&ek&ce&5b&do&4x III",
            "", "&eA jukebox that can store and play",
            "&emusic disc, it can loop through",
            "&eentire disc slots and autoplay",
            "&ea new disc after one has finished",
            "",
            "&dSlot amount: &d&o12 music disc slots",
            "",
            LoreBuilder.powerBuffer(1024),
            LoreBuilder.powerPerSecond(16));

}