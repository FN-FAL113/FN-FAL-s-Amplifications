package ne.fnfal113.fnamplifications.Utils;

import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
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
                Utils.colorTranslator("&d&l• &dAdded 2 new gems!"),
                Utils.colorTranslator("     &e&l+ &r&eDamnnation Gem - make your weapons serve you"),
                Utils.colorTranslator("     &e&l+ &r&eTri-sword Gem - 3 sword homing destruction"),
                Utils.colorTranslator("&d&l• &r&dFixed hoe bugs and errors, addon should work"),
                Utils.colorTranslator("  &don &e1.15.x up &dto &e1.18.x &dminecraft versions again"),
                Utils.colorTranslator("&d&l• &r&dCode rewrites and improvements"),
                Utils.colorTranslator("&e-FN_FAL113"),
                "||---------------------------------------------------||"
        );
    }

}
