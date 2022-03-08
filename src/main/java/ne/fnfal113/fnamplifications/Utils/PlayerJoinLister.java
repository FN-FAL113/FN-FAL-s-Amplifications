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
                Utils.colorTranslator("&fChangelog (Huge code rewrites!)"),
                "",
                Utils.colorTranslator("&d&l• &r&dFile Configuration management has been rewritten,"),
                Utils.colorTranslator("  &dTake a backup of your current config.yml and"),
                Utils.colorTranslator("  &ddelete it to update the entries. Item setting"),
                Utils.colorTranslator("  &dconfiguration now have their own configuration file"),
                Utils.colorTranslator("  &dinstead of storing them all inside config.yml"),
                Utils.colorTranslator("&d&l• &r&dRewritten PowerXpansion generators, it now uses"),
                Utils.colorTranslator("  &dholograms and can right clicked to toggle it on/off"),
                Utils.colorTranslator("&d&l• &r&dHuge re-balancing for PowerXpansion and Solar"),
                Utils.colorTranslator("  &dgenerators, nerfed power rates and capacity and"),
                Utils.colorTranslator("  &dadjusted recipes for balancing, rates/capacity are"),
                Utils.colorTranslator("  &dstill configurable inside their own config files"),
                Utils.colorTranslator("&d&l• &r&dMaterial Generators server performance improvements"),
                Utils.colorTranslator("&d&l• &r&dThrowable weapons now cannot be thrown if the"),
                Utils.colorTranslator("  &dplayer has no permission to build or place inside"),
                Utils.colorTranslator("  &dothers protected land claims"),
                Utils.colorTranslator("&e-FN_FAL113"),
                "||---------------------------------------------------||"
        );
    }

}