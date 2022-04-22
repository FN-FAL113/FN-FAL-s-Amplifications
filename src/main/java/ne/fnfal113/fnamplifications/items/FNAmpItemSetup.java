package ne.fnfal113.fnamplifications.items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gears.implementation.RegisterGears;
import ne.fnfal113.fnamplifications.gems.implementation.RegisterGems;
import ne.fnfal113.fnamplifications.machines.implementation.RegisterMachines;
import ne.fnfal113.fnamplifications.materialgenerators.FNMaterialGenerators;
import ne.fnfal113.fnamplifications.multiblocks.*;
import ne.fnfal113.fnamplifications.mysteriousitems.implementation.RegisterSticks;
import ne.fnfal113.fnamplifications.powergenerators.FNSolarGenerators;
import ne.fnfal113.fnamplifications.powergenerators.PowahGenerator;
import ne.fnfal113.fnamplifications.quivers.implementations.RegisterQuiver;
import ne.fnfal113.fnamplifications.staffs.implementations.RegisterStaffs;
import ne.fnfal113.fnamplifications.tools.implementation.RegisterTools;

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

        FNSolarGenerators.setup(plugin);
        PowahGenerator.setup(plugin);
        FnItemRecipes.setup(plugin);
        FnScrapRecipes.setup(plugin);
        FNMaterialGenerators.setup(plugin);
        RegisterMachines.setup(plugin);
        RegisterSticks.setup(plugin);
        RegisterGears.setup(plugin);
        RegisterQuiver.setup(plugin);
        RegisterGems.setup(plugin);
        RegisterStaffs.setup(plugin);
        RegisterTools.setup(plugin);

        new FnAssemblyStation().register(FNAmplifications.getInstance());
        new FnScrapRecycler().register(FNAmplifications.getInstance());
        new FnMysteryStickAltar().register(FNAmplifications.getInstance());
        new FnGemAltar().register(FNAmplifications.getInstance());
        new FnMagicAltar().register(FNAmplifications.getInstance());
        new FnGemUpgrader().register(FNAmplifications.getInstance());
    }

}