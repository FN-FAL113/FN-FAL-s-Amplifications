package ne.fnfal113.fnamplifications.quivers.implementations;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import lombok.Getter;
import ne.fnfal113.fnamplifications.quivers.abstracts.AbstractQuiver;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

// To do: Method Documentation
public class QuiverTask {

    @Getter
    private final NamespacedKey storageKey;
    @Getter
    private final NamespacedKey storageKey2;
    @Getter
    private final NamespacedKey storageKey3;
    @Getter
    private final int quiverSize;
    @Getter
    private final ItemStack arrowType;
    @Getter
    private final SlimefunItemStack sfItemStack;

    public QuiverTask(NamespacedKey key1, NamespacedKey key2, NamespacedKey key3, int quiverSize, ItemStack arrow, SlimefunItemStack slimefunItemStack){
        this.storageKey = key1;
        this.storageKey2 = key2;
        this.storageKey3 = key3;
        this.quiverSize = quiverSize;
        this.arrowType = arrow;
        this.sfItemStack = slimefunItemStack;
    }

    @Nullable
    public SlimefunItem getSfItem(ItemStack itemStack){
        return SlimefunItem.getByItem(itemStack);
    }

    public int getArrows(PersistentDataContainer arrowPdc, NamespacedKey key){
        return arrowPdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    public boolean isQuiver(@Nullable SlimefunItem item){
        return item instanceof AbstractQuiver;
    }

    /**
     *
     * @param itemState the itemstack with the state of closed and open
     * @param meta the meta of the quiver with the current amount as pdc
     * @param arrowsCheck the pdc instance where current arrow value is checked
     */
    public void changeState(ItemStack itemState, ItemMeta meta, PersistentDataContainer arrowsCheck){
        int arrowsCheckPDC = getArrows(arrowsCheck, getStorageKey());
        boolean pdcCheck = arrowsCheckPDC == 0;
        if(pdcCheck){
            return;
        }

        if(itemState.getType() == Material.LEATHER) {
            meta.getPersistentDataContainer().set(getStorageKey3(), PersistentDataType.STRING, "opened");
            itemState.setType(getArrowType().getType());
            Utils.setLoreByPdc(itemState, meta, "Opened", "State: " ,"&e", "&f", " quiver");
        } else {
            meta.getPersistentDataContainer().set(getStorageKey3(), PersistentDataType.STRING, "closed");
            itemState.setType(Material.LEATHER);
            Utils.setLoreByPdc(itemState, meta, "Closed", "State: " ,"&e", "&f", " quiver");
        }
    }

    /**
     *
     * @param itemState the itemstack with the state of closed and open
     * @param meta the meta of the quiver with the current amount as pdc
     * @param player the player who owns the quiver
     * @param arrowsCheck the pdc instance where current arrow value is checked
     */
    public void withdrawArrows(ItemStack itemState, ItemMeta meta, Player player, PersistentDataContainer arrowsCheck){
        int arrowsCheckPDC = getArrows(arrowsCheck, getStorageKey());
        boolean pdcCheck = arrowsCheckPDC == 0;
        if(pdcCheck){
            return;
        }

        int amount = arrowsCheckPDC - 1;
        meta.getPersistentDataContainer().set(getStorageKey(), PersistentDataType.INTEGER, amount);
        if(amount == 0){
            if(meta.getPersistentDataContainer().has(getStorageKey3(), PersistentDataType.STRING)) {
                meta.getPersistentDataContainer().remove(getStorageKey3());
            }
            itemState.setType(Material.LEATHER);
            player.sendMessage(ChatColor.GOLD + getSfItemStack().getDisplayName() + " is now empty");
            Utils.setLoreByPdc(itemState, meta, "Closed (No arrows)", "State: " ,"&e", "&f", "");
        }

        Utils.setLoreByPdc(itemState, meta, String.valueOf(amount), "Arrows: " ,"&e", "&f", " left");
        player.getInventory().addItem(getArrowType().clone());
    }

    public void depositArrows(ItemStack item, ItemMeta meta, PersistentDataContainer arrowsCheck, Player player) {
        ItemStack arrow = player.getInventory().getItemInMainHand();
        int arrowsCheckPDC = getArrows(arrowsCheck, getStorageKey());

        if(arrow.getType() != getArrowType().getType()){
            return;
        }
        if(isQuiver(getSfItem(arrow))){
            return;
        }
        if(item.getAmount() != 1){
            player.sendMessage(Utils.colorTranslator("&eUnstack the quivers first before using them"));
            return;
        }
        int increment = arrowsCheckPDC + 1;

        if (increment != getQuiverSize() + 1) {
            meta.getPersistentDataContainer().set(getStorageKey(), PersistentDataType.INTEGER, increment);
            if(increment <= 2) {
                int random = ThreadLocalRandom.current().nextInt(1, 999999 + 1);
                meta.getPersistentDataContainer().set(getStorageKey2(), PersistentDataType.INTEGER, random);
            } // pdc to make item un-stackable and unique
            item.setType(getArrowType().getType());
            arrow.setAmount(arrow.getAmount() - 1);
            Utils.setLoreByPdc(item, meta, String.valueOf(increment), "Arrows: " ,"&e", "&f", " left");
            Utils.setLoreByPdc(item, meta, "Opened", "State: " ,"&e", "&f", " quiver");
            if(increment == getQuiverSize()){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', getSfItemStack().getDisplayName() + " is full!"));
            }

        }

    }

    public void bowShoot(EntityShootBowEvent event, ItemStack itemStack, boolean checkInfinity){
        if(itemStack == null){
            return;
        }

        Player player = (Player) event.getEntity();

        event.setCancelled(true);
        float bowForce = event.getForce();
        if(itemStack.getType() == Material.ARROW) {
            Arrow arrow = player.launchProjectile(Arrow.class);
            arrow.setVelocity(arrow.getVelocity().multiply(bowForce));
        }
        if(itemStack.getType() == Material.SPECTRAL_ARROW) {
            SpectralArrow spectralArrow = player.launchProjectile(SpectralArrow.class);
            spectralArrow.setVelocity(spectralArrow.getVelocity().multiply(bowForce));
        }

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 1.0F);
        player.updateInventory();
        ItemMeta meta = itemStack.getItemMeta();
        ItemMeta bowMeta = player.getInventory().getItemInMainHand().getItemMeta();

        if(meta == null || bowMeta == null){
            return;
        }

        PersistentDataContainer arrow_Left = meta.getPersistentDataContainer();
        int arrows = getArrows(arrow_Left, getStorageKey());
        int decrement = arrows - 1;

        if(checkInfinity && bowMeta.hasEnchant(Enchantment.ARROW_INFINITE)) {
            decrement = arrows;
        }

        if (decrement >= 0) { // if arrow amount is decremented and greater than 0 then update lore
            arrow_Left.set(getStorageKey(), PersistentDataType.INTEGER, decrement);

            if (decrement == 0) {
                if(arrow_Left.has(getStorageKey3(), PersistentDataType.STRING)) {
                    meta.getPersistentDataContainer().remove(getStorageKey3());
                }
                itemStack.setType(Material.LEATHER);
                Utils.setLoreByPdc(itemStack, meta, "Closed (No arrows)", "State: " ,"&e", "&f", "");
            }
            Utils.setLoreByPdc(itemStack, meta, String.valueOf(decrement), "Arrows: " ,"&e", "&f", " left");
        }
    }

}