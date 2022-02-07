package ne.fnfal113.fnamplifications.Utils;

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

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

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
                Utils.colorTranslator("&d&l• &dAdded 7 new gems!"),
                Utils.colorTranslator("     &d&l- &r&dThis gems can be crafted in the Fn Gem Altar"),
                Utils.colorTranslator("     &d&l- &r&dand can be bounded to tools, weapons and gears."),
                Utils.colorTranslator("     &d&l- &r&dGems that works by chance can be configured in"),
                Utils.colorTranslator("     &d&l- &r&dthe config.yml to increase or decrease the chance."),
                Utils.colorTranslator("     &d&l- &r&dMore gems are coming soon!"),
                Utils.colorTranslator("&d&l• &r&dFixed issue report #43 - Staff of Cobweb bug"),
                Utils.colorTranslator("&d&l• &r&dRebalanced Mysterious sticks"),
                Utils.colorTranslator("&d&l• &r&dAdded countdown to Staff of Air Strider"),
                Utils.colorTranslator("&e-FN_FAL113"),
                "||---------------------------------------------------||"
        );
    }

}
