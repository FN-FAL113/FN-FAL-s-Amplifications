package ne.fnfal113.fnamplifications;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.bakedlibs.dough.updater.BlobBuildUpdater;
import ne.fnfal113.fnamplifications.config.ConfigManager;
import ne.fnfal113.fnamplifications.gears.commands.GearCommands;
import ne.fnfal113.fnamplifications.gears.runnables.ArmorEquipRunnable;
import ne.fnfal113.fnamplifications.integrations.VaultIntegration;
import ne.fnfal113.fnamplifications.test.ShockwaveTest;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ne.fnfal113.fnamplifications.items.FNAmpItemSetup;

import java.util.Objects;
import java.util.logging.Level;

public final class FNAmplifications extends JavaPlugin implements SlimefunAddon {

    private static FNAmplifications instance;
    private static VaultIntegration vaultIntegration;

    private final ConfigManager configManager = new ConfigManager();

    @Override
    public void onEnable() {
        setInstance(this);

        new Metrics(this, 13219);

        getLogger().info("************************************************************");
        getLogger().info("*         FN Amplifications - Created by FN_FAL113         *");
        getLogger().info("*         Add me on discord if there are any issues        *");
        getLogger().info("*          FN_FAL#7779 or join SF Addon Community          *");
        getLogger().info("*                https://discord.gg/SqD3gg5SAU             *");
        getLogger().info("************************************************************");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // instantiate vault integration
        setVaultIntegration(this);

        // setup items
        FNAmpItemSetup.INSTANCE.init();

        registerCommands();
        getServer().getScheduler().runTaskTimerAsynchronously(this, new ArmorEquipRunnable(), 0L, getConfig().getInt("armor-update-period") * 20L);

        if (getConfig().getBoolean("auto-update", true) && getDescription().getVersion().startsWith("Dev - ")) {
            new BlobBuildUpdater(this, getFile(), "FNAmplifications").start();
        }
    }

    @Override
    public void onDisable(){
        Bukkit.getScheduler().cancelTasks(FNAmplifications.getInstance());
        getLogger().log(Level.INFO, "Cancelled any running task that exist");
    }

    public void registerCommands(){
        Objects.requireNonNull(getCommand("fngear")).setExecutor(new GearCommands());
        getCommand("fnshockwavetest").setExecutor(new ShockwaveTest());
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

    public ConfigManager getConfigManager(){
        return instance.configManager;
    }

    private static void setInstance(FNAmplifications ins) {
        instance = ins;
    }

    public static FNAmplifications getInstance() {
        return instance;
    }

    public static void setVaultIntegration(FNAmplifications ins) {
        vaultIntegration = new VaultIntegration(ins);
    }

    public static VaultIntegration getVaultIntegration() {
        return vaultIntegration;
    }

}
