package ne.fnfal113.fnamplifications.Items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.MaterialGenerators.FNMaterialGenerators;
import ne.fnfal113.fnamplifications.PowerGenerators.FNSolarGenerators;
import ne.fnfal113.fnamplifications.PowerGenerators.PowahGenerator;


public final class FNAmpItemSetup {

    public static final FNAmpItemSetup INSTANCE = new FNAmpItemSetup();
    private final SlimefunAddon plugin = FNAmplifications.getInstance();
    private boolean initialised;

    static ReturnConfValue value = new ReturnConfValue();

    private FNAmpItemSetup() {
    }

    public void init() {
        if (initialised) {
            return;
        }

        initialised = true;

        registerPowerGens();
        registerMaterialGens();
        registerSolarGens();
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


}
