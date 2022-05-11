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
                Utils.colorTranslator("&d&l• &r&d&lBug fixes and code improvements!"),
                Utils.colorTranslator("  &bThis update introduces bug fixes and code"),
                Utils.colorTranslator("  &bimprovements for quality of life changes"),
                Utils.colorTranslator("  &bFile size has been reduced, these code changes may not"),
                Utils.colorTranslator("  &bbe applicable to all players but dev side it helps :)"),
                Utils.colorTranslator("&d&l• &r&dFixed Quiver bugs"),
                Utils.colorTranslator("&d&l• &r&dFixed &r&dsmall bugs with staffs"),
                Utils.colorTranslator("&d&l• &r&dBalanced FN Armor set trigger chance for progress"),
                Utils.colorTranslator("&d&l• &r&dPermission checking is now based on if players"),
                Utils.colorTranslator("  &bcan interact blocks on protected claims for "),
                Utils.colorTranslator("  &bthrowable weapons and staffs, so block interactions must"),
                Utils.colorTranslator("  &bbe enabled in order to use them on grief protected claims"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://fnfal113.tech/pluginStats"),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113/"),
                "||---------------------------------------------------||"
        );
    }

}