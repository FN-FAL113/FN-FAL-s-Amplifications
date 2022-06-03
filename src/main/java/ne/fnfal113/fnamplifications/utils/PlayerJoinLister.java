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
                Utils.colorTranslator("&fChangelog (Huge Update)"),
                "",
                Utils.colorTranslator("&b&l• &r&b&lNew items!"),
                Utils.colorTranslator("  &d- Added FN Auto Ladder"),
                Utils.colorTranslator("    &eThis item can automatically place ladder"),
                Utils.colorTranslator("    &efrom 8 blocks top and bottom"),
                Utils.colorTranslator("  &d- Added FN Orient Pearl"),
                Utils.colorTranslator("    &eWith this pearl, you can teleport rideable"),
                Utils.colorTranslator("    &eentities to your new location while riding them!"),
                Utils.colorTranslator("&b&l• &r&b&lFN Gears Changes!"),
                Utils.colorTranslator("  &d- Configurable gear enchants and enchant levels"),
                Utils.colorTranslator("  &d- Configurable gear max levels and max attributes"),
                Utils.colorTranslator("    &eWith these changes, an admin can now customize"),
                Utils.colorTranslator("    &efn gears! from changing the enchants per level and"),
                Utils.colorTranslator("    &eand changing attributes on levels that are divisible"),
                Utils.colorTranslator("    &eby 5. Admins can remove entries from the config"),
                Utils.colorTranslator("    &ewhich will not give any new rewards when the armor"),
                Utils.colorTranslator("    &elevels up. Admins can also increase the max levels and"),
                Utils.colorTranslator("    &emay add new config levels with enchants or attributes"),
                Utils.colorTranslator("    &eas a reward. as long as they are under the new "),
                Utils.colorTranslator("    &emax level. Max attributes can also be changed where "),
                Utils.colorTranslator("    &ean admin can add or remove bonus attributes if the max "),
                Utils.colorTranslator("    &eattributes setting are changed."),
                Utils.colorTranslator("    &eNew settings are in the &6fn-gear-level-settings.yml &efile"),
                Utils.colorTranslator("&b&l• &r&b&lFN Gems Changes!"),
                Utils.colorTranslator("  &d- Configurable world settings per gems"),
                Utils.colorTranslator("    &eAdmins can now disable any gem per world if they want."),
                Utils.colorTranslator("    &eThe gem will not take effect if the player resides"),
                Utils.colorTranslator("    &ein a world where they are current disabled."),
                Utils.colorTranslator("    &eNew settings are in the &6gem-settings.yml &efile"),
                Utils.colorTranslator("  &d- More realistic shockwave gem effect"),
                Utils.colorTranslator("    &eWhen it procs, the gem now simulates a shockwave"),
                Utils.colorTranslator("    &eeffect where blocks and entities are bounced off"),
                Utils.colorTranslator("    &efrom the ground."),
                Utils.colorTranslator("&b&l• &r&b&lFN Staffs Changes!"),
                Utils.colorTranslator("  &d- Configurable staff item material"),
                Utils.colorTranslator("    &eAdmins can now change the material of the staff."),
                Utils.colorTranslator("    &eIt can be a weapon like a diamond sword or any"),
                Utils.colorTranslator("    &ematerial that exist in game."),
                Utils.colorTranslator("    &eNew settings are in the &6staffs-settings.yml &efile"),
                Utils.colorTranslator("&b&l• &r&b&lItem Recipe Changes!"),
                Utils.colorTranslator("  &d- Rebalanced some recipes for crafting gems"),
                Utils.colorTranslator("  &d- Rebalanced some recipes for FN PowerX and Solar Gens"),
                Utils.colorTranslator("&b&l• &r&b&lAPI Changes! (for developers)"),
                Utils.colorTranslator("  &d- Refactored most of the code and added new pdc types"),
                Utils.colorTranslator(""),
                Utils.colorTranslator("&ehttps://fnfal113.tech/pluginStats"),
                Utils.colorTranslator("&ehttps://fnfal113.tech/fnAmpItems"),
                Utils.colorTranslator("&ehttps://github.com/FN-FAL113/"),
                "||---------------------------------------------------||"
        );
    }

}