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

        if(players.contains(player.getUniqueId())){
           return;
        }

        players.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            for(String string : getChangelog()) {
                player.sendMessage(string);
            }
        }, 25L);

    }

    public String[] getChangelog(){
        return Utils.stringSequence(
                "||---------------------------------------------------||",
                Utils.colorTranslator("&e&lFN &c&lAmpli&b&lfications &r&e" + FNAmplifications.getInstance().getDescription().getVersion()),
                Utils.colorTranslator("&fChangelog"),
                "",
                Utils.colorTranslator("&d&l• &r&d&lAdded Lifesteal Gem"),
                Utils.colorTranslator("  &bsteal 1 heart from your enemy by chance"),
                Utils.colorTranslator("  &bas long your health is not full"),
                Utils.colorTranslator("&d&l• &r&d&lRefactored &r&dconfiguration management (API)"),
                Utils.colorTranslator("&d&l• &r&dCode refactoring and changes"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&eServer admins are advised to delete all of the"),
                Utils.colorTranslator("&econfig files inside /plugins/FNAmplifications to"),
                Utils.colorTranslator("&eregenerate new and clean config entries"),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}
