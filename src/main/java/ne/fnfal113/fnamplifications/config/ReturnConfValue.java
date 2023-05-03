package ne.fnfal113.fnamplifications.config;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import ne.fnfal113.fnamplifications.FNAmplifications;

public class ReturnConfValue {

    private final SlimefunAddon plugin = FNAmplifications.getInstance();

    FileConfiguration config = plugin.getJavaPlugin().getConfig();

    public ReturnConfValue() {}

    public boolean liteXpansionRecipe() {
        return config.getBoolean("LiteX-Recipe-Integration");
    }

    public boolean smgRecipe() {
        return config.getBoolean("SMG-Recipe-Integration");
    }

    public boolean latestMcVersionRecipe() {
        return config.getBoolean("New-Recipe-Integration");
    }

    public boolean changelogEnable(){
        return config.getBoolean("Enable-One-Time-Chat-Changelog");
    }

}
