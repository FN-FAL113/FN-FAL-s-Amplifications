package ne.fnfal113.fnamplifications;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;

import ne.fnfal113.fnamplifications.ConfigUpdater.ConfigUpdater;
import ne.fnfal113.fnamplifications.MysteriousItems.Listeners.MysteryStickListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import ne.fnfal113.fnamplifications.Items.FNAmpItemSetup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class FNAmplifications extends JavaPlugin implements SlimefunAddon {

    private static FNAmplifications instance;

    @Override
    public void onEnable() {
        setInstance(this);
        new Metrics(this, 13219);

        getLogger().info("************************************************************");
        getLogger().info("*         FN Amplifications - Created by FN_FAL113         *");
        getLogger().info("* Credits to Jeff(LiteXpansion) and Walshy(SF) for letting *");
        getLogger().info("*            me use their utils and resources              *");
        getLogger().info("*         Add me on discord if there are any issues        *");
        getLogger().info("*                       FN_FAL#7779                        *");
        getLogger().info("************************************************************");

        FNAmpItemSetup.INSTANCE.init();

        getServer().getPluginManager().registerEvents(new MysteryStickListener(), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //The config needs to exist before using the updater
        File configFile = new File(getDataFolder(), "config.yml");

        if(configFile.exists()) {
            try {
                ConfigUpdater.update(FNAmplifications.instance, "config.yml", configFile, Arrays.asList());
            } catch (IOException e) {
                e.printStackTrace();
            }

            reloadConfig();
        }

        if (getConfig().getBoolean("auto-update", true) && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "FN-FAL113/FN-FAL-s-Amplifications/main").start();
        }
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
}
