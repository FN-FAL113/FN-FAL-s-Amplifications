package ne.fnfal113.fnamplifications.items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gears.FnBoots;
import ne.fnfal113.fnamplifications.gears.FnChestPlate;
import ne.fnfal113.fnamplifications.gears.FnHelmet;
import ne.fnfal113.fnamplifications.gears.FnLeggings;
import ne.fnfal113.fnamplifications.gems.*;
import ne.fnfal113.fnamplifications.machines.*;
import ne.fnfal113.fnamplifications.materialgenerators.FNMaterialGenerators;
import ne.fnfal113.fnamplifications.multiblocks.FnAssemblyStation;
import ne.fnfal113.fnamplifications.multiblocks.FnGemAltar;
import ne.fnfal113.fnamplifications.multiblocks.FnMysteryStickAltar;
import ne.fnfal113.fnamplifications.multiblocks.FnScrapRecycler;
import ne.fnfal113.fnamplifications.mysteriousitems.*;
import ne.fnfal113.fnamplifications.powergenerators.FNSolarGenerators;
import ne.fnfal113.fnamplifications.powergenerators.PowahGenerator;
import ne.fnfal113.fnamplifications.quivers.Quiver;
import ne.fnfal113.fnamplifications.quivers.SpectralQuiver;
import ne.fnfal113.fnamplifications.quivers.UpgradedQuiver;
import ne.fnfal113.fnamplifications.quivers.UpgradedSpectralQuiver;
import ne.fnfal113.fnamplifications.staffs.*;
import ne.fnfal113.fnamplifications.tools.FnHoe;
import ne.fnfal113.fnamplifications.tools.FnHoeAutoPlant;

public final class FNAmpItemSetup {

    public static final FNAmpItemSetup INSTANCE = new FNAmpItemSetup();
    private final SlimefunAddon plugin = FNAmplifications.getInstance();
    private boolean initialised;

    private FNAmpItemSetup() {}

    public void init() {
        if (initialised) {
            return;
        }

        initialised = true;

        registerPowerGens();
        registerMaterialGens();
        registerSolarGens();
        registerTransformers();
        registerCompressors();
        registerCondensers();
        registerRecyclers();
        registerDowngrader();
        registerRecipeItems();
        registerMultiBlock();
        registerScrapRecipes();
        registerBlockBreaker();
        registerStick();
        registerGear();
        registerStaff();
        registerQuiver();
        registerTools();
        registerGems();
    }

    private void registerPowerGens() {
        PowahGenerator.setup();
    }

    private void registerMaterialGens() {
        FNMaterialGenerators.setup();
    }

    private void registerSolarGens() {
        FNSolarGenerators.setup();
    }

    private void registerCompressors() {
        ElectricCompressor.setup();
    }

    private void registerCondensers() {
        ElectricIngotCondenser.setup();
    }

    private void registerDowngrader() {
        ElectricMachineDowngrader.setup();
    }

    private void registerRecyclers() {
        ElectricRecycler.setup();
    }

    private void registerTransformers() {
        ElectricTransformer.setup();
    }

    private void registerRecipeItems() {
        FnItemRecipes.setup();
    }

    private void registerMultiBlock() {
        new FnAssemblyStation().register(FNAmplifications.getInstance());
        new FnScrapRecycler().register(FNAmplifications.getInstance());
        new FnMysteryStickAltar().register(FNAmplifications.getInstance());
        new FnGemAltar().register(FNAmplifications.getInstance());
    }

    private void registerScrapRecipes() {
        FnScrapRecipes.setup();
    }

    private void registerBlockBreaker() {
        ElectricBlockBreaker.setup();
    }

    private void registerStick() {
        MysteryStick.setup();
        MysteryStick2.setup();
        MysteryStick3.setup();
        MysteryStick4.setup();
        MysteryStick5.setup();
        MysteryStick6.setup();
        MysteryStick7.setup();
        MysteryStick8.setup();
        MysteryStick9.setup();
        MysteryStick10.setup();
        MysteryStick11.setup();
    }

    private void registerGear(){
        FnChestPlate.setup();
        FnLeggings.setup();
        FnHelmet.setup();
        FnBoots.setup();
    }

    private void registerStaff(){
        StaffOfTeleportation.setup();
        StaffOfInvisibility.setup();
        StaffOfLocomotion.setup();
        StaffOfHellFire.setup();
        StaffOfDeepFreeze.setup();
        StaffOfConfusion.setup();
        StaffOfGravitationalPull.setup();
        StaffOfStallion.setup();
        StaffOfForce.setup();
        StaffOfHealing.setup();
        StaffOfInvulnerability.setup();
        StaffOfExplosion.setup();
        StaffOfMuster.setup();
        StaffOfAwareness.setup();
        StaffOfMinerals.setup();
        StaffOfFangs.setup();
        StaffOfSkulls.setup();
        StaffOfAirStrider.setup();
        StaffOfWebs.setup();
    }

    public void registerQuiver(){
        Quiver.setup();
        SpectralQuiver.setup();
        UpgradedQuiver.setup();
        UpgradedSpectralQuiver.setup();
    }

    public void registerGems(){
        InfernoGem.setup();
        ArmorImpairGem.setup();
        TelepathyGem.setup();
        ThunderBoltGem.setup();
        HastyGem.setup();
        ThornAwayGem.setup();
        ImpostorGem.setup();
        PsychokinesisGem.setup();
        AxeThrowieGem.setup();
        BlindBindGem.setup();
        TriSwordGem.setup();
        DamnationGem.setup();
        RetaliateGem.setup();
        GuardianGem.setup();
    }

    public void registerTools(){
        FnHoe.setup();
        FnHoeAutoPlant.setup();
    }
}
