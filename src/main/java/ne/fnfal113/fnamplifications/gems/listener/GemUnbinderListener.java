package ne.fnfal113.fnamplifications.gems.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Getter;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGemUnbinder;
import ne.fnfal113.fnamplifications.gems.implementation.GemUnbinderTask;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GemUnbinderListener implements Listener {

    @Getter
    private final Map<UUID, Integer> unbindChanceMap = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals(Utils.colorTranslator("&cSelect a gem to unbind"))){
            if(event.getClickedInventory() != null && event.getClickedInventory().getHolder() instanceof Player){
                event.setCancelled(true);
                return;
            }

            Optional<SlimefunItem> gem = Optional.ofNullable(SlimefunItem.getByItem(event.getCurrentItem()));
            Player player = (Player) event.getWhoClicked();

            if(gem.isPresent() && gem.get() instanceof AbstractGem){
                new GemUnbinderTask(player, player.getInventory().getItemInOffHand())
                        .unBindGem(gem.get(), getUnbindChanceMap().get(player.getUniqueId()));
                event.getWhoClicked().closeInventory();
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }
        Player player = event.getPlayer();
        SlimefunItem slimefunItem = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());

        if(slimefunItem instanceof AbstractGemUnbinder) {
            event.setUseItemInHand(Event.Result.DENY);
            player.updateInventory();
            getUnbindChanceMap().put(player.getUniqueId(), ((AbstractGemUnbinder) slimefunItem).onRightClick(event, player));
        }

    }

}