package ne.fnfal113.fnamplifications.gems.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Getter;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGemUnbinder;
import ne.fnfal113.fnamplifications.gems.implementation.GemUnbinderTask;
import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.Material;
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

    /**
     * On gem item to unbind click, player selects a gem to unbind from the available gems inventory UI
     */
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals(Utils.colorTranslator("&cSelect a gem to unbind"))){
            if(event.getClickedInventory() != null && event.getClickedInventory().getHolder() instanceof Player) {
                event.setCancelled(true);

                return;
            }

            Optional<SlimefunItem> gem = Optional.ofNullable(SlimefunItem.getByItem(event.getCurrentItem()));
            Player player = (Player) event.getWhoClicked();

            if(gem.isPresent() && gem.get() instanceof AbstractGem) {
                new GemUnbinderTask(player, player.getInventory().getItemInOffHand())
                        .unbindGem(gem.get(), getUnbindChanceMap().get(player.getUniqueId()));
                
                event.getWhoClicked().closeInventory(); // close available gems user interface
            }

            event.setCancelled(true);
        }
    }

    /**
     * On gem unbinder item right click, if conditions are sufficed then show available gems to unbind inventory UI 
     */
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        SlimefunItem slimefunItem = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());

        if(slimefunItem instanceof AbstractGemUnbinder) {
            event.setUseItemInHand(Event.Result.DENY); // prevent item from being consumed
            player.updateInventory(); // refresh player inventory to prevent visual bugs

            if(player.getInventory().getItemInOffHand().getType() == Material.AIR) {
                player.sendMessage(Utils.colorTranslator("&cYou have no item in your offhand that contain bounded gems!"));
                
                return;
            }
            
            getUnbindChanceMap().put(player.getUniqueId(), ((AbstractGemUnbinder) slimefunItem).getChance());

            new GemUnbinderTask(player, player.getInventory().getItemInOffHand()).showAvailableGemsUI();
        }

    }

}