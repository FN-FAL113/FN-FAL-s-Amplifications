package ne.fnfal113.fnamplifications;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;

import ne.fnfal113.fnamplifications.Gears.Commands.CheckProgress;
import ne.fnfal113.fnamplifications.Gears.Listeners.GearListener;
import ne.fnfal113.fnamplifications.Gears.Runnables.ArmorEquipRunnable;
import ne.fnfal113.fnamplifications.Gems.Listeners.GemListener;
import ne.fnfal113.fnamplifications.MysteriousItems.Listeners.MysteryStickListener;
import ne.fnfal113.fnamplifications.Quiver.Listener.QuiverListener;
import ne.fnfal113.fnamplifications.Staffs.Listener.StaffListener;
import ne.fnfal113.fnamplifications.Tools.Listener.HoeListener;
import ne.fnfal113.fnamplifications.Utils.PlayerJoinLister;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ne.fnfal113.fnamplifications.Items.FNAmpItemSetup;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

public final class FNAmplifications extends JavaPlugin implements SlimefunAddon {

    private static FNAmplifications instance;

    @Override
    public void onEnable() {
        setInstance(this);

        Metrics metrics = new Metrics(this, 13219);
        addPieChartMatGen(metrics);

        getLogger().info("************************************************************");
        getLogger().info("*         FN Amplifications - Created by FN_FAL113         *");
        getLogger().info("* Credits to Jeff(LiteXpansion) and Walshy(SF) for letting *");
        getLogger().info("*            me use their utils and resources              *");
        getLogger().info("*         Add me on discord if there are any issues        *");
        getLogger().info("*                       FN_FAL#7779                        *");
        getLogger().info("************************************************************");

        FNAmpItemSetup.INSTANCE.init();

        getServer().getPluginManager().registerEvents(new MysteryStickListener(), this);
        getServer().getPluginManager().registerEvents(new GearListener(), this);
        getServer().getPluginManager().registerEvents(new StaffListener(), this);
        getServer().getPluginManager().registerEvents(new QuiverListener(), this);
        getServer().getPluginManager().registerEvents(new HoeListener(), this);
        getServer().getPluginManager().registerEvents(new GemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLister(), this);
        Objects.requireNonNull(getCommand("fngear")).setExecutor(new CheckProgress());
        getServer().getScheduler().runTaskTimerAsynchronously(this, new ArmorEquipRunnable(), 0L, getConfig().getInt("armor-update-period") * 20L);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (getConfig().getBoolean("auto-update", true) && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "FN-FAL113/FN-FAL-s-Amplifications/main").start();
        }
    }

    @Override
    public void onDisable(){
        Bukkit.getScheduler().cancelTasks(FNAmplifications.getInstance());
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/FN-FAL113/FN-FAL-s-Amplifications/issues";
    }

    private static void setInstance(FNAmplifications ins) {
        instance = ins;
    }

    public static FNAmplifications getInstance() {
        return instance;
    }

    public void addPieChartMatGen(Metrics metrics){
        metrics.addCustomChart(new AdvancedPie("material_gen_tickrates", new Callable<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> call() {
                Map<String, Integer> matGens = new HashMap<>();
                for(Map.Entry<String, Object> config: getConfig().getValues(true).entrySet()){
                    if(config.getKey().startsWith("FN_")){
                        matGens.put(config.getKey() + ": " + config.getValue().toString(),
                                matGens.getOrDefault(config.getKey() + ": " + config.getValue().toString(), 0) + 1);
                    }
                }

                return matGens;
            }
        }));
    }

}
