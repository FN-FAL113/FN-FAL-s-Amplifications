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
                Utils.colorTranslator("&d&l• &r&d&lUpgradeable Gems!"),
                Utils.colorTranslator("  &bMost of the gems now has tiers from I to IV, where"),
                Utils.colorTranslator("  &btrigger chances are increased on each tier, any gems"),
                Utils.colorTranslator("  &byou have and socketed to items now default to tier I"),
                Utils.colorTranslator("  &bwhich has the lower chance of being triggered and you"),
                Utils.colorTranslator("  &bcan upgrade these gem through crafting and the use of"),
                Utils.colorTranslator("  &bthe new multiblock FN Gem Upgrader"),
                Utils.colorTranslator("  &bUpgradeable gem item is added in the Fn Gems category as"),
                Utils.colorTranslator("  &ba guide which shows how to craft and upgrade existing"),
                Utils.colorTranslator("  &bgems of same type and tier/level. Also check out the guide"),
                Utils.colorTranslator("  &bfor the tier chances for each gems!"),
                Utils.colorTranslator("&d&l• &r&d&lAdded &r&dFN Gem Upgrader Multiblock"),
                Utils.colorTranslator("&d&l• &r&d&lFixed &r&dsome bugs"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&eLifesteal gem to follow in the next build."),
                Utils.colorTranslator("&eif there are bugs please report on github"),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}