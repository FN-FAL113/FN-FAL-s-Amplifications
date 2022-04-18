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
                Utils.colorTranslator("&d&l• &r&d&lAdded Disarm and Smoke Criminal Gem"),
                Utils.colorTranslator("  &bdisarm weapons with disarm gem while smoke criminal"),
                Utils.colorTranslator("  &bprotects you when your health is below 30%"),
                Utils.colorTranslator("&d&l• &r&d&lResolved &r&dgit issue #73, #74 and #75"),
                Utils.colorTranslator("&d&l• &r&dFixed a weird bug with tri-sword gem"),
                Utils.colorTranslator("  &bif you're old sword stopped working with tri-sword gem"),
                Utils.colorTranslator("  &bit now autofixes it self by right clicking the sword two"),
                Utils.colorTranslator("  &btimes to update its data and can be thrown again"),
                Utils.colorTranslator("&d&l• &r&dA lot of code refactoring and cleanups"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&eBig thanks to Romanovski#2267 for reporting these"),
                Utils.colorTranslator("&eand letting me debug it on their server"),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}