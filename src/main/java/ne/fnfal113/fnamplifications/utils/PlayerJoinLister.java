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
        }, 45L);

    }

    public String[] getChangelog(){
        return Utils.stringSequence(
                "||---------------------------------------------------||",
                Utils.colorTranslator("&e&lFN &c&lAmpli&b&lfications &r&e" + FNAmplifications.getInstance().getDescription().getVersion()),
                Utils.colorTranslator("&fChangelog"),
                "",
                Utils.colorTranslator("&b&l• &r&b&lBug fixes and new items!"),
                Utils.colorTranslator("  &d- Added FN Throwable Torch"),
                Utils.colorTranslator("    &eRight click to throw this torch to a target"),
                Utils.colorTranslator("    &elocation and it will be placed if got hit"),
                Utils.colorTranslator("  &d- Added Berserk Gem"),
                Utils.colorTranslator("    &eGain attack damage when your health is below"),
                Utils.colorTranslator("    &e30%, higher tier gem higher base damage!"),
                Utils.colorTranslator("  &d- Fixed Electric Jukebox bug relating to functionality"),
                Utils.colorTranslator("    &dwhen there is not enough power supply"),
                Utils.colorTranslator("  &d- Minor code base improvements (API)"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&eChangelog notification can be disabled in config.yml"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://fnfal113.tech/pluginStats"),
                Utils.colorTranslator("&ehttps://fnfal113.tech/fnAmpItems"),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113/"),
                "||---------------------------------------------------||"
        );
    }

}