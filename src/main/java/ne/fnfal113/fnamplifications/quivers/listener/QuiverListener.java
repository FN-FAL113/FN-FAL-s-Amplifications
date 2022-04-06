package ne.fnfal113.fnamplifications.quivers.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.quivers.abstracts.AbstractQuiver;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("ConstantConditions")
public class QuiverListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        int length = player.getInventory().getContents().length;
        boolean actionRight = (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK);
        boolean actionLeft = (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK);

        if(actionRight) {
            if (itemStack.getType() == Material.CROSSBOW) {
                for (int i = 0; i < length; i++) {
                    ItemStack itemStack1 = player.getInventory().getItem(i);
                    SlimefunItem item = SlimefunItem.getByItem(itemStack1);

                    if (item instanceof AbstractQuiver) {
                        itemStack1.setType(Material.LEATHER);
                    }
                } // loop
            } // crossbow
        } // event

        if(actionRight || actionLeft) {
            SlimefunItem item = SlimefunItem.getByItem(itemStack);
            if(player.isSneaking()) {
                if (item instanceof AbstractQuiver) {
                    ((AbstractQuiver) item).onArrowWithdraw(event, itemStack);
                }
            } else {
                if (item instanceof AbstractQuiver) {
                    ((AbstractQuiver) item).onChangeState(itemStack);
                }
            }

        } // check if action is right or left click

        if(actionRight) {
            if(itemStack.getType() == Material.ARROW || itemStack.getType() == Material.SPECTRAL_ARROW) {
                for (int i = 0; i < length; i++) {
                    SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItem(i));
                    ItemStack item = player.getInventory().getItem(i);

                    if (sfItem instanceof AbstractQuiver && itemStack.getType() == Material.ARROW) {
                        if(getArrows(item, ((AbstractQuiver) sfItem).getStorageKey(), ((AbstractQuiver) sfItem).getQuiverSize()) &&
                        getState(item, ((AbstractQuiver) sfItem).getStorageKey3())) {
                            ((AbstractQuiver) sfItem).onArrowDeposit(player, player.getInventory().getItem(i));
                        }
                    }

                    if (sfItem instanceof AbstractQuiver && itemStack.getType() == Material.SPECTRAL_ARROW) {
                        if(getArrows(item, ((AbstractQuiver) sfItem).getStorageKey(), ((AbstractQuiver) sfItem).getQuiverSize()) &&
                        getState(item, ((AbstractQuiver) sfItem).getStorageKey3())) {
                            ((AbstractQuiver) sfItem).onArrowDeposit(player, player.getInventory().getItem(i));
                        }
                    }
                } // loop
            } // item type

        } // event action

    }

    @EventHandler
    public void onEntityBowShoot(EntityShootBowEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getGameMode() != GameMode.CREATIVE) {
                Inventory playerInven = player.getInventory();
                int slot = playerInven.first(Material.matchMaterial(event.getProjectile().getName()));
                if(slot == -1){
                    return;
                }

                ItemStack itemStack = playerInven.getItem(slot);
                SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);

                if (sfItem instanceof AbstractQuiver) {
                    ((AbstractQuiver) sfItem).onBowShoot(event, itemStack);
                }
            } // player is not in creative mode
        } // shooter is a player

    }

    public boolean getState(ItemStack quiver, NamespacedKey key){
        PersistentDataContainer pdc = quiver.getItemMeta().getPersistentDataContainer();
        if(pdc.has(key, PersistentDataType.STRING)){
            return pdc.get(key, PersistentDataType.STRING).equals("opened");
        }
        return true;
    }

    public boolean getArrows(ItemStack quiver, NamespacedKey key, int size){
        PersistentDataContainer pdc = quiver.getItemMeta().getPersistentDataContainer();
        int getArrows = pdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
        return getArrows < size;
    }

}