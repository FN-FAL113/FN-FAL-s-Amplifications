package ne.fnfal113.fnamplifications.utils;

import ne.fnfal113.fnamplifications.config.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerJoinLister implements Listener {

    private final List<UUID> players = new ArrayList<>();

    private static final ReturnConfValue value = new ReturnConfValue();

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(!value.changelogEnable()){
            return;
        }

        if(players.contains(player.getUniqueId())){ // only send once when player joins fresh after server restart
           return;
        }

        players.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            for(String string : getChangelog()) {
                player.sendMessage(string);
            }
        }, 42L);

    }

    public String[] getChangelog(){
        return Utils.stringSequence(
                "||---------------------------------------------------||",
                Utils.colorTranslator("&e&lFN &c&lAmpli&b&lfications &r&e" + FNAmplifications.getInstance().getDescription().getVersion()),
                Utils.colorTranslator("&fChangelog"),
                "",
                Utils.colorTranslator("&d&l• &r&d&lAdded Loot Gem!"),
                Utils.colorTranslator("  &bA chance to steal money from your foes!"),
                Utils.colorTranslator("  &bGem only gets added if the server"),
                Utils.colorTranslator("  &beconomy plugin utilizes VaultAPI"),
                Utils.colorTranslator("&d&l• &r&d&lAdded &r&dVault integration (API)"),
                Utils.colorTranslator("&d&l• &r&dReadjusted FN Armor set progress chance trigger (balancing)"),
                Utils.colorTranslator("&d&l• &r&dReadjusted FN Solar Gen recipes (balancing)"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://fnfal113.tech/pluginStats"),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113/"),
                "||---------------------------------------------------||"
        );
    }

}