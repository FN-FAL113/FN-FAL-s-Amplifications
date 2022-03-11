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
                Utils.colorTranslator("&fChangelog (Another huge code rewrites!)"),
                "",
                Utils.colorTranslator("&d&l• &r&dRewritten Mystery Sticks again for the second"),
                Utils.colorTranslator("  &dtime, its now balanced and all op enchants are nerfed"),
                Utils.colorTranslator("  &dand the crafting recipes are adjusted for balancing."),
                Utils.colorTranslator("  &dThe stick will still consume exp levels from the owner"),
                Utils.colorTranslator("  &dby % chance at the same time giving the enemies mystery"),
                Utils.colorTranslator("  &ddeadly effects, the exp levels consumed is the price for"),
                Utils.colorTranslator("  &dusing the almighty stick. The current stats for your"),
                Utils.colorTranslator("  &dsticks might reset due to this changes"),
                Utils.colorTranslator("&d&l• &r&dHopefully make the Air Strider staff work properly"),
                Utils.colorTranslator("  &don servers. Note: Airplane server software is abandoned"),
                Utils.colorTranslator("  &dand things might not work properly due to bugs and glitches"),
                Utils.colorTranslator("  &dAddon supports spigot and forks above it including paper,"),
                Utils.colorTranslator("  &dpurpur, etc but it might not work properly on abandoned forks"),
                Utils.colorTranslator("  &djust like any other addons."),
                Utils.colorTranslator("&d&l• &r&dThe code for gems has been further improved and"),
                Utils.colorTranslator("  &dcleaned up along with the stick rewrite and chores. the jar"),
                Utils.colorTranslator("  &dfile size has been reduced by from 425kb to 382kb."),
                Utils.colorTranslator("&e-FN_FAL113"),
                "||---------------------------------------------------||"
        );
    }

}