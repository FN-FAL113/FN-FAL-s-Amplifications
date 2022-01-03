package ne.fnfal113.fnamplifications.Quiver.Listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.Quiver.Quiver;
import ne.fnfal113.fnamplifications.Quiver.SpectralQuiver;
import ne.fnfal113.fnamplifications.Quiver.UpgradedQuiver;
import ne.fnfal113.fnamplifications.Quiver.UpgradedSpectralQuiver;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class QuiverListener implements Listener {

    // Commented out for optimizations (Unused Event)
    /*@EventHandler
    public void checkCrossBow(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        int slot = event.getNewSlot();
        ItemStack itemStack = player.getInventory().getItem(slot);*/

        /*if(itemStack != null && itemStack.getType() == Material.CROSSBOW) {
            for (int i = 0; i < player.getInventory().getContents().length; i++) {
                SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItem(i));

                if (sfItem instanceof Quiver) {
                    ItemStack itemStack1 = player.getInventory().getItem(i);

                    if(itemStack1 == null){
                        return;
                    }
                    player.sendMessage("Quiver doesn't support crossbows, change slot to bow to change quiver back to arrow");
                    itemStack1.setType(Material.LEATHER);
                }

            }
        } else*/
        /*if(itemStack != null){
            for (int i = 0; i < player.getInventory().getContents().length; i++) {
                SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItem(i));

                if (sfItem instanceof Quiver) {
                    ((Quiver) sfItem).checkBow(event);
                }

                if (sfItem instanceof SpectralQuiver) {
                    ((SpectralQuiver) sfItem).checkBow(event);
                }

                if (sfItem instanceof UpgradedQuiver) {
                    ((UpgradedQuiver) sfItem).checkBow(event);
                }

                if (sfItem instanceof UpgradedSpectralQuiver) {
                    ((UpgradedSpectralQuiver) sfItem).checkBow(event);
                }

            }
        }*/

    /*}*/

    // Commented out for optimizations (Unused Event)
    /*@EventHandler
    public void onSfItemPickup(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        Player player = (Player) event.getEntity();
        ItemStack itemStack = event.getItem().getItemStack();
        SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
        if(sfItem instanceof Quiver) {
            for (int i = 0; i < player.getInventory().getContents().length; i++) {
                ItemStack item = player.getInventory().getItem(i);

                if(item != null && item.getType() == Material.CROSSBOW){
                    itemStack.setType(Material.LEATHER);
                }
            }
        }

        if(itemStack.getType() == Material.CROSSBOW){
            for (int i = 0; i < player.getInventory().getContents().length; i++) {
                ItemStack item2 = player.getInventory().getItem(i);
                SlimefunItem slimefunItem = SlimefunItem.getByItem(item2);

                if(item2 != null && slimefunItem instanceof Quiver){
                    item2.setType(Material.LEATHER);
                }
            }

        }

    }*/

    @EventHandler(priority = EventPriority.NORMAL)
    public void onRightClick(PlayerInteractEvent event){

        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        int length = player.getInventory().getContents().length;

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemStack.getType() == Material.CROSSBOW) {
                for (int i = 0; i < length; i++) {
                    ItemStack itemStack1 = player.getInventory().getItem(i);
                    SlimefunItem item = SlimefunItem.getByItem(itemStack1);

                    if (item instanceof Quiver) {
                        itemStack1.setType(Material.LEATHER);
                    }

                    if (item instanceof SpectralQuiver) {
                        itemStack1.setType(Material.LEATHER);
                    }

                    if (item instanceof UpgradedQuiver) {
                        itemStack1.setType(Material.LEATHER);
                    }

                    if (item instanceof UpgradedSpectralQuiver) {
                        itemStack1.setType(Material.LEATHER);
                    }

                } // loop
            } // crossbow
        } // event

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ||
                event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

            if(itemStack.getType() == Material.LEATHER || itemStack.getType() == Material.ARROW || itemStack.getType() == Material.SPECTRAL_ARROW) {
                for (int i = 0; i < length; i++) {
                    SlimefunItem sfItem = SlimefunItem.getByItem(player.getInventory().getItem(i));

                    if (sfItem instanceof Quiver) {
                        ((Quiver) sfItem).onRightClick(event);
                    }

                    if (sfItem instanceof SpectralQuiver) {
                        ((SpectralQuiver) sfItem).onRightClick(event);
                    }

                    if (sfItem instanceof UpgradedQuiver) {
                        ((UpgradedQuiver) sfItem).onRightClick(event);
                    }

                    if (sfItem instanceof UpgradedSpectralQuiver) {
                        ((UpgradedSpectralQuiver) sfItem).onRightClick(event);
                    }

                } // loop
            } // item type

        } // event action

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBowShoot(EntityShootBowEvent event){

        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getGameMode() != GameMode.CREATIVE) {
                SlimefunItem sfItem = SlimefunItem.getByItem(event.getConsumable());

                if (sfItem instanceof Quiver) {
                    ((Quiver) sfItem).bowShoot(event);
                }

                if (sfItem instanceof SpectralQuiver) {
                    ((SpectralQuiver) sfItem).bowShoot(event);
                }

                if (sfItem instanceof UpgradedQuiver) {
                    ((UpgradedQuiver) sfItem).bowShoot(event);
                }

                if (sfItem instanceof UpgradedSpectralQuiver) {
                    ((UpgradedSpectralQuiver) sfItem).bowShoot(event);
                }

            } // game mode
        } // instanceof

    }


}
