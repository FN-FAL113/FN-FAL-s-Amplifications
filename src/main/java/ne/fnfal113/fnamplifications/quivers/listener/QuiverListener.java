package ne.fnfal113.fnamplifications.quivers.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Getter;
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

import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("ConstantConditions")
public class QuiverListener implements Listener {

    @Getter
    private final Predicate<SlimefunItem> ifQuiver = sfItem -> sfItem instanceof AbstractQuiver;
    @Getter
    private Consumer<AbstractQuiver> abstractQuiverConsumer;

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
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

            if(itemStack.getType() == Material.ARROW || itemStack.getType() == Material.SPECTRAL_ARROW) {
                if(itemInOffHand.getType() == Material.BOW || itemInOffHand.getType() == Material.CROSSBOW){
                    return;
                } // prevent arrows from being deposited when shooting a bow in the offhand while main-hand has arrows

                for (int i = 0; i < length; i++) {
                    SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItem(i));
                    ItemStack item = player.getInventory().getItem(i);

                    if(getIfQuiver().test(sfItem)) {
                        AbstractQuiver abstractQuiver = (AbstractQuiver) sfItem;

                        if (getArrows(item, abstractQuiver.getStorageKey(), abstractQuiver.getQuiverSize()) &&
                                getState(item, abstractQuiver.getStorageKey3())) {
                            abstractQuiverConsumer = quiver ->
                                    quiver.getQuiverTask().depositArrows(item, item.getItemMeta(), item.getItemMeta().getPersistentDataContainer(), player);
                            getAbstractQuiverConsumer().accept(abstractQuiver);
                        }
                    } // is instance of AbstractQuiver
                } // loop
            } // arrow type

        } // event

        if(actionRight || actionLeft) {
            SlimefunItem item = SlimefunItem.getByItem(itemStack);

            if(getIfQuiver().test(item)){
                AbstractQuiver abstractQuiver = (AbstractQuiver) item;

                if(player.isSneaking()) {
                    abstractQuiverConsumer = quiver ->
                            quiver.getQuiverTask().withdrawArrows(itemStack, itemStack.getItemMeta(), event.getPlayer(), itemStack.getItemMeta().getPersistentDataContainer());
                } else {
                    abstractQuiverConsumer = quiver ->
                            quiver.getQuiverTask().changeState(itemStack, itemStack.getItemMeta(), itemStack.getItemMeta().getPersistentDataContainer());
                }

                getAbstractQuiverConsumer().accept(abstractQuiver);
            }
        } // check if action is right or left click

    }

    @EventHandler
    public void onEntityBowShoot(EntityShootBowEvent event){
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Inventory playerInven = player.getInventory();
        int slot = playerInven.first(Material.matchMaterial(event.getProjectile().getName()));

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();

        ItemStack itemStack =
                isItemInHandQuiver(itemInMainHand) ? itemInMainHand :
                isItemInHandQuiver(itemInOffHand) ? itemInOffHand :
                        (slot == -1) ? null : playerInven.getItem(slot);

        SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);

        if (getIfQuiver().test(sfItem)) {
            ((AbstractQuiver) sfItem).getQuiverTask().bowShoot(event, itemStack, itemStack.getType() == Material.ARROW);
        }

    }

    public boolean isItemInHandQuiver(ItemStack itemStack){
        return getIfQuiver().test(SlimefunItem.getByItem(itemStack)) && itemStack.getType() != Material.LEATHER;
    }

    // get the state of the bow if its opened or close
    public boolean getState(ItemStack quiver, NamespacedKey key){
        PersistentDataContainer pdc = quiver.getItemMeta().getPersistentDataContainer();
        if(pdc.has(key, PersistentDataType.STRING)){
            return pdc.get(key, PersistentDataType.STRING).equals("opened");
        }
        return true;
    }

    // get the current amount of arrows in the quiver
    // must be under the max size of the quiver
    public boolean getArrows(ItemStack quiver, NamespacedKey key, int size){
        PersistentDataContainer pdc = quiver.getItemMeta().getPersistentDataContainer();
        int getArrows = pdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
        return getArrows < size;
    }

}