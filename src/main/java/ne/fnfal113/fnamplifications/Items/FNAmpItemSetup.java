package ne.fnfal113.fnamplifications.Items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gears.FnBoots;
import ne.fnfal113.fnamplifications.Gears.FnChestPlate;
import ne.fnfal113.fnamplifications.Gears.FnHelmet;
import ne.fnfal113.fnamplifications.Gears.FnLeggings;
import ne.fnfal113.fnamplifications.Machines.*;
import ne.fnfal113.fnamplifications.MaterialGenerators.FNMaterialGenerators;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import ne.fnfal113.fnamplifications.Multiblock.FnMysteryStickAltar;
import ne.fnfal113.fnamplifications.Multiblock.FnScrapRecycler;
import ne.fnfal113.fnamplifications.MysteriousItems.*;
import ne.fnfal113.fnamplifications.PowerGenerators.FNSolarGenerators;
import ne.fnfal113.fnamplifications.PowerGenerators.PowahGenerator;
import ne.fnfal113.fnamplifications.Quiver.Quiver;
import ne.fnfal113.fnamplifications.Quiver.SpectralQuiver;
import ne.fnfal113.fnamplifications.Quiver.UpgradedQuiver;
import ne.fnfal113.fnamplifications.Quiver.UpgradedSpectralQuiver;
import ne.fnfal113.fnamplifications.Staffs.*;
import ne.fnfal113.fnamplifications.Tools.FnHoe;

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
    }

    private void registerPowerGens() {
        new PowahGenerator(PowahGenerator.Type.RANK1).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK2).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK3).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK4).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK5).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK6).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK7).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK8).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK9).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK10).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK11).register(plugin);
        new PowahGenerator(PowahGenerator.Type.RANK12).register(plugin);
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
    }

    public void registerQuiver(){
        Quiver.setup();
        SpectralQuiver.setup();
        UpgradedQuiver.setup();
        UpgradedSpectralQuiver.setup();
    }

    public void registerTools(){
        FnHoe.setup();
    }
}
