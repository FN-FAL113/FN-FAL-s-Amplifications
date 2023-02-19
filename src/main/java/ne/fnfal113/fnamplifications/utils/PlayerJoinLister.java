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

        // only send once when player joins fresh after server restart
        if(players.contains(player.getUniqueId())){
           return;
        }

        players.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            for(String string : getChangelog()) {
                player.sendMessage(string);
            }
        }, 45L);

    }

    public String[] getChangelog(){
        return Utils.stringSequence(
                "||---------------------------------------------------||",
                Utils.colorTranslator("&e&lFN &c&lAmpli&b&lfications &r&e" + FNAmplifications.getInstance().getDescription().getVersion()),
                Utils.colorTranslator("&fChangelog"),
                "",
                Utils.colorTranslator("&eChanges"),
                Utils.colorTranslator("  &d- Refactored shockwave gem, added 5 seconds cooldown and server load optimizations"),
                Utils.colorTranslator("  &d- Codebase Chores"),
                "",
                Utils.colorTranslator("  &fNote: Addon requires Slimefun Dev 1031+ (Previously Dev 980+) as of Build 83"),
                "",
                Utils.colorTranslator("&eChangelog notification can be disabled in config.yml"),
                "",
                Utils.colorTranslator("&eYou may visit this website for the fn items wiki:"),
                Utils.colorTranslator("&e&nhttps://fnfal113.tech/fnAmpItems"),
                "||---------------------------------------------------||"
        );
    }

}