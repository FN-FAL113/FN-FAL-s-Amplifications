package ne.fnfal113.fnamplifications.Items;

import dev.j3fftw.litexpansion.extrautils.utils.LoreBuilderDynamic;
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
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.PowerGenerators.PowahGenerator;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;


public class FNAmpItems {

    public static final NestedItemGroup FN_ITEMS = new NestedItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_ITEMS"), new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "115b670a704ba080a730acf246da64c413901644847edd671c3da9a97441ddc5")),
            "&e&lFN_FAL113's &b&lAmpli&c&lfications") );
    public static final SubItemGroup POWER_GENERATORS = new SubItemGroup(new NamespacedKey(FNAmplifications.getInstance(), "POWER_GENERATORS"), FN_ITEMS, new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "ecb316f7a227a8c59d58ae0dd6768fe4fa546d55b9cfdd56cfe40b6586d81c24")), "&eFN_Fal's Power Xpansion"));
    public static final SubItemGroup MATERIAL_GENERATORS = new SubItemGroup(new NamespacedKey(FNAmplifications.getInstance(), "MATERIAL_GENERATORS"), FN_ITEMS, new CustomItemStack(Material.EMERALD_BLOCK, "&eFN_FAL'S Material Generators"));
    public static final SubItemGroup SOLAR_GENERATORS = new SubItemGroup(new NamespacedKey(FNAmplifications.getInstance(), "SOLAR_GENERATORS"), FN_ITEMS, new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "e7f4c00356d1addb85b45ba5352992d3ecc0c9d11feb9041482f8531fd27d014")), "&eFN_FAL'S Solar Generators"));

    public static final ItemGroup FN_AMPLIFICATIONS = new ItemGroup(
            new NamespacedKey(FNAmplifications.getInstance(), "FN_AMPLIFICATIONS"), new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "115b670a704ba080a730acf246da64c413901644847edd671c3da9a97441ddc5")),
            "&e&lFN_FAL113's &b&lAmpli&c&lfications") );

    static ReturnConfValue value = new ReturnConfValue();


    public static final SlimefunItemStack FN_XPANSION_POWER_R1 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R1",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "33165e254de7863459343543f933481f0dcf8d4730a23433b9a7ff0d3ff79c5a")),
            "&d&lFN Power Xpansion R1",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_I_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_I_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_I_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R2 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R2",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "5b78e55e3ea6b3682bd7cead43eb1c91b2527a81aa2894f095801f6ee47a3")),
            "&d&lFN Power Xpansion R2",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_II_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_II_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_II_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R3 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R3",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "45ff60d863d1cf76742f275e2ac5749dd0a978a231d3c51e816132c75aef608a")),
            "&d&lFN Power Xpansion R3",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_III_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_III_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_III_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R4 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R4",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "efc05af0e88c6fb10b4c8c8b81b7aa658e64649724cb73bb9bb0f25f28bd")),
            "&d&lFN Power Xpansion R4",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_IV_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_IV_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_IV_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R5 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R5",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "8f14f3179b86f69b3efa7472dacaeb2339f6290d2d817362793348abd98e021")),
            "&d&lFN Power Xpansion R5",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_V_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_V_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_V_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R6 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R6",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "1d5a09884cb83ef5c908dddd385f246fefdee221712c010177f54376da238fdd")),
            "&d&lFN Power Xpansion R6",
            "&9This works at Night!",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_VI_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_VI_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_VI_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R7 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R7",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "f7e541dfb4ba1f7dc28b548e347abbdc987ebe0e61c49fa87111ef1b2dcb2218")),
            "&d&lFN Power Xpansion R7",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_VII_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_VII_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_VII_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R8 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R8",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "de31efefdd9551af8a4349d3d21e5ec8f37e53c801eb25b14279d6a89fe0c01e")),
            "&d&lFN Power Xpansion R8",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_VIII_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_VIII_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_VIII_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R9 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R9",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "6db32b15d7f32704ed626fa52d06fb2b4071d336fdbfe61e6e41c669d6e37f47")),
            "&d&lFN Power Xpansion R9",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_IX_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_IX_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_IX_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R10 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R10",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "56a7d2195ff7674bbb12e2f7578a2a63c54a980e64744450ac6656e05a790499")),
            "&d&lFN Power Xpansion R10",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_X_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_X_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_X_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R11 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R11",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "7f9f356f5fe7d1bc92cddfaeba3ee773ac9df1cc4d1c2f8fe5f47013032c551d")),
            "&d&lFN Power Xpansion R11",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_XI_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_XI_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_XI_NIGHTRATE) + " (Night Rate)"
    );

    public static final SlimefunItemStack FN_XPANSION_POWER_R12 = new SlimefunItemStack(
            "FN_POWER_XPANSION_POWER_R12",
            PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "3464874df452c1d717eddd0fb3b848202ad15571245af6fade2ecf514f3c80bb")),
            "&d&lFN Power Xpansion R12",
            "&9This works at Night!",
            "&9Place and right click",
            "&9the block for power info",
            "",
            LoreBuilderDynamic.powerBuffer(PowahGenerator.FN_POWERGEN_RANK_XII_STORAGE),
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_XII_DAYRATE) + " (Day Rate)",
            LoreBuilderDynamic.powerPerTick(PowahGenerator.FN_POWERGEN_RANK_XII_NIGHTRATE) + " (Night Rate)"
    );

    private FNAmpItems() {};


    public static final SlimefunItemStack FMG_GENERATOR_MULTIBLOCK = new SlimefunItemStack(
            "FMG_GENERATOR_MULTIBLOCK",
            Material.BEDROCK,
            "&9Generator multiblock",
            "",
            "&dBuild any of this addon's",
            "&dgenerators like this.",
            "&aThey will only output to a chest",
            "&adirectly above it."
    );

    public static SlimefunItemStack FMG_GENERATOR_FNFAL_CLAY = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_CLAY",
            Material.CLAY,
            "&3FN_FAL's Clay Generator",
            "&6Generates Clay at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_FNFAL_WARPED1 = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_WARPED1",
            Material.WARPED_NYLIUM,
            "&4FN_FAL's Warped Nylium Generator",
            "&6Generates Warped Nylium at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_FNFAL_TERRACOTTA = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_TERRACOTTA",
            Material.TERRACOTTA,
            "&4FN_FAL's Terracotta Generator",
            "&6Generates Terracotta at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_FNFAL_BONE = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_BONE",
            Material.BONE_BLOCK,
            "&fFN_FAL's Bone Generator",
            "&6Generates Bone at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_FNFAL_DIAMOND = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_DIAMOND",
            Material.DIAMOND_BLOCK,
            "&bFN_FAL's Diamond Generator",
            "&6Generates Diamond at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_FNFAL_EMERALD = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_EMERALD",
            Material.EMERALD_BLOCK,
            "&aFN_FAL's Emerald Generator",
            "&6Generates Emerald at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_FNFAL_DIRT = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_DIRT",
            Material.DIRT,
            "&fFN_FAL's Dirt Generator",
            "&6Generates Dirt at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );

    public static final SlimefunItemStack FMG_GENERATOR_FNFAL_HONEYCOMB = new SlimefunItemStack(
            "FMG_GENERATOR_FNFAL_HONEYCOMB",
            Material.HONEYCOMB_BLOCK,
            "&6FN_FAL's Honey Comb Generator",
            "&6Generates Honey Comb at a certain rate",
            "",
            "&5&oFN_Fal's Material Generators"
    );


    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER1 = new
        SlimefunItemStack("FN_FAL_GENERATOR_TIER1",
            "6d2822cedb3abd579d6dfa2966c1433c3c36cb9732e2c23ec0cc81daedd4403b",
            "&dFN Solar Generator Tier I",
            "", "&eA solar generator for those who needs power",
            value.tier1Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier1Buffer()),
            LoreBuilder.powerPerSecond(value.tier1Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier1PowerNight() * 2) + " (Night)");

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER2 = new
            SlimefunItemStack("FN_FAL_GENERATOR_TIER2",
            "35dd37f729fc88133e314a552204c0fa2c0168428b353f957bf15ff24b7707e0",
            "&dFN Solar Generator Tier II",
            "", "&eA solar generator for those who needs power",
            value.tier2Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier2Buffer()),
            LoreBuilder.powerPerSecond(value.tier2Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier2PowerNight() * 2) + " (Night)");


    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER3 = new
            SlimefunItemStack("FN_FAL_GENERATOR_TIER3",
            "25ddf6af2d6271d8fdfadbdc54faaad5a68d7b8ac20e163883fc38d76336ea6",
            "&dFN Solar Generator Tier III",
            "", "&eA solar generator for those who needs power",
            value.tier3Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier3Buffer()),
            LoreBuilder.powerPerSecond(value.tier3Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier3PowerNight() * 2) + " (Night)");

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER4 = new
            SlimefunItemStack("FN_FAL_GENERATOR_TIER4",
            "e7f4c00356d1addb85b45ba5352992d3ecc0c9d11feb9041482f8531fd27d014",
            "&dFN Solar Generator Tier IV",
            "", "&eA solar generator for those who needs power",
            value.tier4Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier4Buffer()),
            LoreBuilder.powerPerSecond(value.tier4Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier4PowerNight() * 2) + " (Night)");

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER5 = new
            SlimefunItemStack("FN_FAL_GENERATOR_TIER5",
            "afdd9e588d2461d2d3d058cb3e0af2b3a3367607aa14d124ed92a833f25fb112",
            "&dFN Solar Generator Tier V",
            "", "&eA solar generator for those who needs power",
            value.tier5Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier5Buffer()),
            LoreBuilder.powerPerSecond(value.tier5Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier5PowerNight() * 2) + " (Night)");

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER6 = new
            SlimefunItemStack("FN_FAL_GENERATOR_TIER6",
            "224ad26209fa02f559ef6aa863ee9ba8f3bef0a02f1e9cff8fdc09196402fb6f",
            "&dFN Solar Generator Tier VI",
            "", "&eA solar generator for those who needs power",
            value.tier6Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier6Buffer()),
            LoreBuilder.powerPerSecond(value.tier6Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier6PowerNight() * 2) + " (Night)");

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER7 = new
            SlimefunItemStack("FN_FAL_GENERATOR_TIER7",
            "c4fe135c311f7086edcc5e6dbc4ef4b23f819fddaa42f827dac46e3574de2287",
            "&dFN Solar Generator Tier VII",
            "", "&eA solar generator for those who needs power",
            value.tier7Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier7Buffer()),
            LoreBuilder.powerPerSecond(value.tier7Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier7PowerNight() * 2) + " (Night)");

    public static final SlimefunItemStack FN_FAL_GENERATOR_TIER8 = new
            SlimefunItemStack("FN_FAL_GENERATOR_TIER8",
            "240775c3ad75763613f32f04986881bbe4eee4366d0c57f17f7c7514e2d0a77d",
            "&dFN Solar Generator Tier VIII",
            "", "&eA solar generator for those who needs power",
            value.tier8Lore() , "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(value.tier8Buffer()),
            LoreBuilder.powerPerSecond(value.tier8Power() * 2) + " (Day)",
            LoreBuilder.powerPerSecond(value.tier8PowerNight() * 2) + " (Night)");




}
