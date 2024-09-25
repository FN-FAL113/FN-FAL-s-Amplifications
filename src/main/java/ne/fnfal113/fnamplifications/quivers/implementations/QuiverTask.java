package ne.fnfal113.fnamplifications.quivers.implementations;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import ne.fnfal113.fnamplifications.quivers.abstracts.AbstractQuiver;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.compatibility.VersionedEnchantmentPlus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

// To do: Method Documentation
public class QuiverTask {

    private final AbstractQuiver quiver;

    

    public QuiverTask(AbstractQuiver quiver) {
        this.quiver = quiver;
    }

    @Nullable
    public SlimefunItem getSfItem(ItemStack itemStack) {
        return SlimefunItem.getByItem(itemStack);
    }

    public int getArrows(ItemMeta meta) {
        return meta.getPersistentDataContainer().getOrDefault(getQuiver().getStoredArrowsKey(), PersistentDataType.INTEGER, 0);
    }

    public boolean isQuiver(@Nullable SlimefunItem sfItem) {
        return sfItem instanceof AbstractQuiver;
    }

    /**
     *
     * @param quiverItemStack the quiver itemstack
     * @param meta the meta of the quiver
     * @param player the player who owns the quiver
     */
    public void withdrawArrows(ItemStack quiverItemStack, ItemMeta meta, Player player) {
        int currentStoredArrowCount = getArrows(meta); 
        
        if(currentStoredArrowCount <= 0) {
            return;
        }

        int newStoredArrowAcount = currentStoredArrowCount - 1;
        
        meta.getPersistentDataContainer().set(getQuiver().getStoredArrowsKey(), PersistentDataType.INTEGER, newStoredArrowAcount);
        
        if(newStoredArrowAcount == 0) { 
            quiverItemStack.setType(Material.LEATHER);

            meta.getPersistentDataContainer().set(getQuiver().getStateKey(), PersistentDataType.STRING, "closed");
            
            Utils.sendMessage(getQuiver().getItemName() + " has been emptied! No more arrows stored", player);
            
            Utils.setLoreByPdc(quiverItemStack, meta, "Closed (empty)", "State: ", "&e", "&f", "");
        }

        Utils.setLoreByPdc(quiverItemStack, meta, String.valueOf(newStoredArrowAcount), "Arrows: ", "&e", "&f", " left");
        
        player.getInventory().addItem(getQuiver().getArrowType().clone());
    }

    public void depositArrows(ItemStack quiverItemStack, ItemMeta meta, Player player) {
        ItemStack arrowItemStack = player.getInventory().getItemInMainHand();
        
        PersistentDataContainer quiverPdc = meta.getPersistentDataContainer();
        
        int currentStoredArrowCount = getArrows(meta);

        if(arrowItemStack.getType() != getQuiver().getArrowType().getType()) {
            return;
        }
        
        if(isQuiver(getSfItem(arrowItemStack))) { 
            return;
        } // prevent quiver in a open state (arrow type) from being deposited 
        
        if(quiverItemStack.getAmount() != 1) {
            Utils.sendMessage("Cannot use quiver! Unstack the quivers first before using", player);
            
            return;
        }
        
        int newStoredArrowCount = currentStoredArrowCount + 1;

        // update stored arrow count
        if(newStoredArrowCount <= getQuiver().getQuiverSize()) { 
            quiverPdc.set(getQuiver().getStoredArrowsKey(), PersistentDataType.INTEGER, newStoredArrowCount);
            
            if(!quiverPdc.has(getQuiver().getRandomIdKey(), PersistentDataType.INTEGER)) {
                int random = ThreadLocalRandom.current().nextInt(1, 1000000);
                
                quiverPdc.set(getQuiver().getRandomIdKey(), PersistentDataType.INTEGER, random);
            
            } // pdc to make quiver unique and unstackable

            //quiverItemStack.setType(getQuiver().getArrowType().getType());
            arrowItemStack.setAmount(arrowItemStack.getAmount() - 1);
            
            Utils.setLoreByPdc(quiverItemStack, meta, String.valueOf(newStoredArrowCount), "Arrows: ", "&e", "&f", " left");
            Utils.setLoreByPdc(quiverItemStack, meta, "Open", "State: ", "&e", "&f", "");
        } else {
            Utils.sendMessage(getQuiver().getItemName() + " is full! Cannot deposit arrows", player);
        }
    }

    // 1.20.6 broke previous quiver functionality due to arrows being consume on event cancel,
    // https://github.com/PaperMC/Paper/pull/9949, fixes this but it not yet merged if ever it gets
    // merged then I might revert back old functionality without needed an extra space for arrows on bow shoot.
    public void bowShoot(PlayerInteractEvent event, ItemStack quiverItemStack, Material itemInMainHandType) {
        Player player = (Player) event.getPlayer();

        ItemMeta meta = quiverItemStack.getItemMeta();
        // when player equips bow on both hands, main hand bow is prioritized
        ItemMeta bowMeta = itemInMainHandType == Material.BOW || itemInMainHandType == Material.CROSSBOW ? 
            player.getInventory().getItemInMainHand().getItemMeta() : player.getInventory().getItemInOffHand().getItemMeta();

        if(meta == null || bowMeta == null) return;

        PersistentDataContainer quiverPdc = meta.getPersistentDataContainer();
        
        boolean hasInfinity = bowMeta.hasEnchant(VersionedEnchantmentPlus.INFINITY);
        int currentStoredArrowCount = getArrows(meta);
        int newStoredArrowCount = quiver.getArrowType().getType() == Material.ARROW && hasInfinity ?
            currentStoredArrowCount : currentStoredArrowCount - 1;

        // if bow has infinity, then quiver has no use on this scenario
        // player can draw an arrow manually instead to prevent stacking
        if(quiver.getArrowType().getType() == Material.ARROW && !hasInfinity) {
            player.getInventory().addItem(new ItemStack(Material.ARROW));
        } else if(quiver.getArrowType().getType() == Material.SPECTRAL_ARROW && !hasInfinity) {
            player.getInventory().addItem(new ItemStack(Material.SPECTRAL_ARROW));
        }

        if(newStoredArrowCount >= 0) { // update quiver lore and pdc
            quiverPdc.set(getQuiver().getStoredArrowsKey(), PersistentDataType.INTEGER, newStoredArrowCount);

            Utils.setLoreByPdc(quiverItemStack, meta, String.valueOf(newStoredArrowCount), "Arrows: ", "&e", "&f", " left");

            if(newStoredArrowCount == 0) {              
                meta.getPersistentDataContainer().set(getQuiver().getStateKey(), PersistentDataType.STRING, "closed");

                Utils.sendMessage(getQuiver().getItemName() + " has been emptied! No more arrows stored", player);
                
                Utils.setLoreByPdc(quiverItemStack, meta, "Closed (empty)", "State: ", "&e", "&f", "");
            }
        }

    }

    public AbstractQuiver getQuiver() {
        return quiver;
    }
}