package ne.fnfal113.fnamplifications.integrations;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Optional;

public class VaultIntegration {

    private final SlimefunAddon slimefunAddon;

    private boolean isVaultInstalled;

    private Economy economy = null;

    public VaultIntegration(SlimefunAddon addon) {
        this.slimefunAddon = addon;

        if(!setupEconomy()){
            getSlimefunAddon().getLogger().info("Vault API not detected! Loot Gem will not be registered.");
        } else {
            getSlimefunAddon().getLogger().info("Vault API detected! Loot Gem will be registered.");
        }

    }

    public boolean setupEconomy(){
        if(!getSlimefunAddon().getJavaPlugin().getServer().getPluginManager().isPluginEnabled("Vault")) {
            this.isVaultInstalled = false;
            return false;
        }

        try {
            Optional<RegisteredServiceProvider<Economy>> economyRegisteredServiceProvider =
                    Optional.ofNullable(getSlimefunAddon().getJavaPlugin().getServer().getServicesManager().getRegistration(Economy.class));

            if (economyRegisteredServiceProvider.isPresent()) {
                this.economy = economyRegisteredServiceProvider.get().getProvider();
                this.isVaultInstalled = true;
                return true;
            }
        } catch (NoClassDefFoundError e) {
            this.isVaultInstalled = false;
            return false;
        }

        this.isVaultInstalled = false;
        
        return false;
    }

    public SlimefunAddon getSlimefunAddon() {
        return slimefunAddon;
    }

    public boolean isVaultInstalled() {
        return isVaultInstalled;
    }

    public Economy getEconomy() {
        return economy;
    }

}
