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
                Utils.colorTranslator("&d&l• &r&d&lBreakable material generators"),
                Utils.colorTranslator("  &bmaterial generators now have their own condition"),
                Utils.colorTranslator("  &bstate where they can lose durability and needs"),
                Utils.colorTranslator("  &bto be repaired by recrafting the broken type to a"),
                Utils.colorTranslator("  &bfunctional one. A little warning, when you place the"),
                Utils.colorTranslator("  &bgenerator and broke it afterwards it will drop a broken"),
                Utils.colorTranslator("  &btype one instead of the functional generator, be careful"),
                Utils.colorTranslator("&d&l• &r&d&lRe-adjusted &r&dmaterial generator recipes"),
                Utils.colorTranslator("  &brecipe now only requires 1 broken type generator"),
                Utils.colorTranslator("&d&l• &r&d&lFixed &r&dmystery stick 6 small bug"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}