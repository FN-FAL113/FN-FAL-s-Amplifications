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
                Utils.colorTranslator("&d&l• &r&dRewritten most of the code inside the plugin"),
                Utils.colorTranslator("  &dQuality of life changes for developers and translators"),
                Utils.colorTranslator("  &d&dRewritten stuffs are:"),
                Utils.colorTranslator("  &f- Staffs"),
                Utils.colorTranslator("  &f- FN Gears"),
                Utils.colorTranslator("  &f- Mystery Sticks"),
                Utils.colorTranslator("  &f- FN Hoe"),
                Utils.colorTranslator("  &f- Quivers"),
                Utils.colorTranslator("  &f- Gems"),
                Utils.colorTranslator("&d&l• &r&dFixed bug with FN gears specifically FN ChestPlate"),
                Utils.colorTranslator("&d&l• &r&dAdjusted the chances (higher chance) for FN Gears to receive progress"),
                Utils.colorTranslator("&e-FN_FAL113"),
                "||---------------------------------------------------||"
        );
    }

}