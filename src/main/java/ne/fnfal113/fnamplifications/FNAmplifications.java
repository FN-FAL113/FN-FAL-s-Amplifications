package ne.fnfal113.fnamplifications;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import ne.fnfal113.fnamplifications.Items.FNAmpItemSetup;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

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


        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }


    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return null;
    }

    private static void setInstance(FNAmplifications ins) {
        instance = ins;
    }

    public static FNAmplifications getInstance() {
        return instance;
    }
}
