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
                Utils.colorTranslator("  &d- Retaliate gem now returns weapon to player without dropping it on the ground"),
                Utils.colorTranslator("  &d- Fixed block rotator able to rotate wall attachable items that " +
                        "shouldn't be rotated if the next block being attached to is not solid"),
                Utils.colorTranslator("  &d- Downgrade gems through FN Gem Downgrader multiblock and" +
                        "yield back 2 or 3 previous tier gem"),
                Utils.colorTranslator("  &d- Fixed mat gen not resetting progress on break at the block placed location"),
                Utils.colorTranslator("  &d- Introducing material generator upgrades:"),
                Utils.colorTranslator("  &f  + Repair item - repair your mat gens"),
                Utils.colorTranslator("  &f  + Fast Produce - +1.75x speed to your mat gens for 30 minutes"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&eChangelog notification can be disabled in config.yml"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&eYou may visit this website for the fn items wiki:"),
                Utils.colorTranslator("&e&nhttps://fnfal113.tech/fnAmpItems"),
                "||---------------------------------------------------||"
        );
    }

}