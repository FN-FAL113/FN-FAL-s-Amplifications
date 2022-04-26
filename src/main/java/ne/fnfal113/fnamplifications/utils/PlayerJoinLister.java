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
                Utils.colorTranslator("&d&l• &r&d&lAdded 6 new gems"),
                Utils.colorTranslator("  &b+ Atrophy gem - deal 4 second wither effect"),
                Utils.colorTranslator("  &b+ Bane gem - deal 4 second poison effect"),
                Utils.colorTranslator("  &b+ Sedate gem - deal 4 second slowness effect"),
                Utils.colorTranslator("  &b+ Decrepit gem - deal 4 second weakness effect"),
                Utils.colorTranslator("  &b+ Deception gem - deal 4 second blindness effect"),
                Utils.colorTranslator("  &b+ Celerity gem - receive 4 second swiftness effect"),
                Utils.colorTranslator("  &ehigher tier means higher effect level."),
                Utils.colorTranslator("  &etier 1 = level 1 effect until tier 4 = level 4 effect"),
                Utils.colorTranslator("&d&l• &r&d&lFixed &r&dtelepathy gem dropping normal vanilla block or item"),
                Utils.colorTranslator("  &binstead of the actual slimefun block or item"),
                Utils.colorTranslator("  &bgem works vanilla only and slimefun blocks will drop"),
                Utils.colorTranslator("&d&l• &r&dReadjusted Power gens recipe starting from R8 to R12"),
                Utils.colorTranslator("&d&l• &r&dCode refactor and improvements"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113"),
                "||---------------------------------------------------||"
        );
    }

}