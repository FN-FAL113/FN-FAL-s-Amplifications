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
                Utils.colorTranslator("&d&l• &r&dAdded Block Rotator"),
                Utils.colorTranslator("  &dblock rotator can rotate terracotta blocks, chest and"),
                Utils.colorTranslator("  &dany directional blocks even chains, it can also flip stairs,"),
                Utils.colorTranslator("  &dchains, slabs and many more as long as the block is"),
                Utils.colorTranslator("  &dorientable and bisected"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&fWe have reached 90 servers yesterday using this addon,"),
                Utils.colorTranslator("&fThank you for giving this addon a chance to be in your"),
                Utils.colorTranslator("&fservers. I learned a lot developing this addon and gave"),
                Utils.colorTranslator("&fme headaches but I didn't give up just to give you guys"),
                Utils.colorTranslator("&fextra stuffs for your server and players to play with."),
                Utils.colorTranslator("&fI do this things for free and I hope you all appreciate it :)"),
                Utils.colorTranslator("&e-FN_FAL113"),
                "||---------------------------------------------------||"
        );
    }

}