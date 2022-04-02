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
                Utils.colorTranslator("&d&l• &r&d&lAdded &r&dStout and Adamantine Gem"),
                Utils.colorTranslator("&d&l• &r&b&lCompatibility &r&bfor 1.15 version has been restored"),
                Utils.colorTranslator("  &bfixed a lot of compatibility issues and addon"),
                Utils.colorTranslator("  &bcan now be used for 1.15.X and above"),
                Utils.colorTranslator("&d&l• &r&d&lIncreased &r&dgem bind limit from 4 to 5"),
                Utils.colorTranslator("&d&l• &r&d&lOptimized &r&dgem handling (server side improvements)"),
                Utils.colorTranslator("&d&l• &r&d&lChanged &r&dall FN Multiblocks to use wood type fences"),
                Utils.colorTranslator("&d&l• &r&d&lChanged &r&dsome mystery stick enchant levels for balancing"),
                Utils.colorTranslator("&d&l• &r&d&lFixed &r&dLocomotion Staff permission checking"),
                Utils.colorTranslator("&d&l• &r&d&lFixed &r&darrows shot from quiver not able to pickup arrows"),
                Utils.colorTranslator("&d&l• &r&dA lot of code cleanups and refactoring"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}
