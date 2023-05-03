package ne.fnfal113.fnamplifications.items;

import org.bukkit.plugin.Plugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gears.implementation.RegisterGears;
import ne.fnfal113.fnamplifications.gears.listener.GearListener;
import ne.fnfal113.fnamplifications.gems.implementation.RegisterGems;
import ne.fnfal113.fnamplifications.gems.listener.GemListener;
import ne.fnfal113.fnamplifications.gems.listener.GemUnbinderListener;
import ne.fnfal113.fnamplifications.machines.implementation.RegisterMachines;
import ne.fnfal113.fnamplifications.machines.listener.JukeBoxClickListener;
import ne.fnfal113.fnamplifications.materialgenerators.implementations.RegisterMaterialGeneratorUpgrades;
import ne.fnfal113.fnamplifications.materialgenerators.implementations.RegisterMaterialGenerators;
import ne.fnfal113.fnamplifications.materialgenerators.listener.UpgradesListener;
import ne.fnfal113.fnamplifications.multiblocks.*;
import ne.fnfal113.fnamplifications.mysteriousitems.implementation.RegisterSticks;
import ne.fnfal113.fnamplifications.mysteriousitems.listener.MysteryStickListener;
import ne.fnfal113.fnamplifications.powergenerators.implementation.RegisterPowerGenerators;
import ne.fnfal113.fnamplifications.quivers.implementations.RegisterQuiver;
import ne.fnfal113.fnamplifications.quivers.listener.QuiverListener;
import ne.fnfal113.fnamplifications.staffs.implementations.RegisterStaffs;
import ne.fnfal113.fnamplifications.staffs.listener.StaffListener;
import ne.fnfal113.fnamplifications.tools.implementation.RegisterTools;
import ne.fnfal113.fnamplifications.tools.listener.HoeListener;
import ne.fnfal113.fnamplifications.tools.listener.LadderListener;
import ne.fnfal113.fnamplifications.tools.listener.OrientPearlListener;
import ne.fnfal113.fnamplifications.tools.listener.RotatorListener;
import ne.fnfal113.fnamplifications.tools.listener.ThrowableItemListener;

public final class FNAmpItemSetup {

    public static final FNAmpItemSetup INSTANCE = new FNAmpItemSetup();
    private final SlimefunAddon sfPlugin = FNAmplifications.getInstance();
    private final Plugin plugin = FNAmplifications.getInstance();
    private boolean initialised;

    private FNAmpItemSetup() {}

    public void init() {
        if (initialised) {
            return;
        }

        initialised = true;

        // Item Recipes
        FnItemRecipes.setup(sfPlugin);
        FnScrapRecipes.setup(sfPlugin);

        // Items, Multi-blocks and Events
        new FnAssemblyStation().register(FNAmplifications.getInstance());
        new FnScrapRecycler().register(FNAmplifications.getInstance());
        new FnMagicAltar().register(FNAmplifications.getInstance());

        if(plugin.getConfig().getBoolean("Enable-Machineries", true)){
            RegisterMachines.setup(sfPlugin);

            plugin.getServer().getPluginManager().registerEvents(new JukeBoxClickListener(), plugin);
        }

        if(plugin.getConfig().getBoolean("Enable-Gems", true)){
            RegisterGems.setup(sfPlugin);

            new FnGemAltar().register(FNAmplifications.getInstance());
            new FnGemUpgrader().register(FNAmplifications.getInstance());
            new FnGemDowngrader().register(FNAmplifications.getInstance());

            plugin.getServer().getPluginManager().registerEvents(new GemListener(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new GemUnbinderListener(), plugin);
        }

        if(plugin.getConfig().getBoolean("Enable-Gears", true)){
            RegisterGears.setup(sfPlugin);

            plugin.getServer().getPluginManager().registerEvents(new GearListener(), plugin);
        }

        if(plugin.getConfig().getBoolean("Enable-Mystery-Sticks", true)){
            RegisterSticks.setup(sfPlugin);

            new FnMysteryStickAltar().register(FNAmplifications.getInstance());
            plugin.getServer().getPluginManager().registerEvents(new MysteryStickListener(), plugin);
        }

        if(plugin.getConfig().getBoolean("Enable-Solar-Generators", true)){
            RegisterPowerGenerators.setupSolarGen(sfPlugin);
        }

        if(plugin.getConfig().getBoolean("Enable-PowerXpansion", true)){
            RegisterPowerGenerators.setupPowerX(sfPlugin);
        }

        if(plugin.getConfig().getBoolean("Enable-Staffs", true)){
            RegisterStaffs.setup(sfPlugin);

            plugin.getServer().getPluginManager().registerEvents(new StaffListener(), plugin);
        }

        if(plugin.getConfig().getBoolean("Enable-Quivers", true)){
            RegisterQuiver.setup(sfPlugin);    
            
            plugin.getServer().getPluginManager().registerEvents(new QuiverListener(), plugin);
        }

        if(plugin.getConfig().getBoolean("Enable-Misc", true)){
            RegisterTools.setup(sfPlugin);

            plugin.getServer().getPluginManager().registerEvents(new HoeListener(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new RotatorListener(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new OrientPearlListener(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new LadderListener(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new ThrowableItemListener(), plugin);
        }
        
        if(plugin.getConfig().getBoolean("Enable-Material-Generators", true)){
            RegisterMaterialGenerators.setup(sfPlugin);
            RegisterMaterialGeneratorUpgrades.setup(sfPlugin);      

            plugin.getServer().getPluginManager().registerEvents(new UpgradesListener(), plugin);
        }
    }

}
