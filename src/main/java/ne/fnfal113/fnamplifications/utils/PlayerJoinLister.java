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
                Utils.colorTranslator("&d&l• &r&dAdded Quartz Material Generator"),
                Utils.colorTranslator("&d&l• &r&dAdded tickrates of material generators in the lore"),
                Utils.colorTranslator("&d&l• &r&dAdded scrap mechanics on machine downgrader lore"),
                Utils.colorTranslator("&d&l• &r&dScrap drops chance from 10% to 25%, only drops"),
                Utils.colorTranslator("  &dwhen machine downgrader gui is opened or being viewed"),
                Utils.colorTranslator("  &dduring downgrading a machine"),
                Utils.colorTranslator("&d&l• &r&dRenamed the item groups in the sf guide and the"),
                Utils.colorTranslator("  &dtitle of the addon to FN Amplifications"),
                Utils.colorTranslator("&d&l• &r&dMachine and item formatting changes to make the lore"),
                Utils.colorTranslator("  &dshorter and better to read"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}