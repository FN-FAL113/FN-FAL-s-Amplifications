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
                Utils.colorTranslator("&d&l• &r&d&lAdded &r&dArrow Avert, Parry and Deberserk Gem"),
                Utils.colorTranslator("&d&l• &r&b&lAdded &r&bFlawless, Precious, Blemished, Damaged Unbind Gem"),
                Utils.colorTranslator("  &bthese gems gives you the ability to unbind gems from an item"),
                Utils.colorTranslator("&d&l• &r&d&lFixed &r&dsome noSuchField error on 1.18 below versions"),
                Utils.colorTranslator("&d&l• &r&d&lAdded &r&dnew magical items, these are for crafting"),
                Utils.colorTranslator("&d&l• &r&d&lRe-adjusted &r&dStaffs and Gems recipe"),
                Utils.colorTranslator("&d&l• &r&dCode cleanups and chores"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}