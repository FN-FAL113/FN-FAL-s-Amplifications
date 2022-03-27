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
                Utils.colorTranslator("&d&l• &r&d&lAdded Disrupted Gem"),
                Utils.colorTranslator("&d&l• &r&dFixed 1.16.5 noSuchField error with Blindbind gem"),
                Utils.colorTranslator("&d&l• &r&dLittle code rewrite on Gem Handling (Dev side)"),
                Utils.colorTranslator("&d&l• &r&dAdded GuardianSpawnEvent (API for devs)"),
                Utils.colorTranslator("&d&l• &r&dSmall code chores and cleanups"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&eWe have reached 100+ servers using this addon according"),
                Utils.colorTranslator("&eto the stats, appreciate you all for this."),
                Utils.colorTranslator("&eGives me joy to continue giving contents for everyone."),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}