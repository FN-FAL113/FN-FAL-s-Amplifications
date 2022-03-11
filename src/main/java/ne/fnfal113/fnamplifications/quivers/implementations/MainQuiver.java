package ne.fnfal113.fnamplifications.quivers.implementations;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import lombok.Getter;
import ne.fnfal113.fnamplifications.quivers.Quiver;
import ne.fnfal113.fnamplifications.quivers.SpectralQuiver;
import ne.fnfal113.fnamplifications.quivers.UpgradedQuiver;
import ne.fnfal113.fnamplifications.quivers.UpgradedSpectralQuiver;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// To do: Method Documentation
public class MainQuiver {

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
    @Getter
    private final List<String> defaultLore;

    public MainQuiver(NamespacedKey key1, NamespacedKey key2, NamespacedKey key3, int quiverSize, ItemStack arrow, SlimefunItemStack slimefunItemStack, List<String> lore){
        this.storageKey = key1;
        this.storageKey2 = key2;
        this.storageKey3 = key3;
        this.quiverSize = quiverSize;
        this.arrowType = arrow;
        this.sfItemStack = slimefunItemStack;
        this.defaultLore = lore;
    }

    public void updateLore(List<String> lore){
        for(int i = 0; i < 7; i++){
            lore.add(i , getDefaultLore().get(i));
        }
    }

    public SlimefunItem getSfItem(ItemStack itemStack){
        return SlimefunItem.getByItem(itemStack);
    }

    public int getArrows(PersistentDataContainer arrowPdc, NamespacedKey key){
        return arrowPdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    public boolean isQuiver(SlimefunItem item){
        if(item instanceof Quiver){
            return true;
        } else if(item instanceof UpgradedQuiver){
            return true;
        } else if(item instanceof SpectralQuiver){
            return true;
        } else return item instanceof UpgradedSpectralQuiver;
    }

    public void changeState(ItemStack itemState, ItemMeta arrowMeta, PersistentDataContainer arrowsCheck){
        int arrowsCheckPDC = getArrows(arrowsCheck, getStorageKey());
        boolean pdcCheck = arrowsCheckPDC == 0;
        if(pdcCheck){
            return;
        }
        List<String> lore = new ArrayList<>();

        if(itemState.getType() == Material.LEATHER) {
            arrowMeta.getPersistentDataContainer().set(getStorageKey3(), PersistentDataType.STRING, "opened");
            itemState.setType(getArrowType().getType());
            updateLore(lore);
            lore.add(7, ChatColor.YELLOW + "Arrows: " + ChatColor.WHITE + arrowsCheckPDC);
            lore.add(8,  ChatColor.YELLOW + "State: Open Quiver");
        } else {
            arrowMeta.getPersistentDataContainer().set(getStorageKey3(), PersistentDataType.STRING, "closed");
            itemState.setType(Material.LEATHER);
            updateLore(lore);
            lore.add(7, ChatColor.YELLOW + "Arrows: " + ChatColor.WHITE + arrowsCheckPDC);
            lore.add(8,  ChatColor.YELLOW + "State: Closed Quiver");
        }
        arrowMeta.setLore(lore);
        itemState.setItemMeta(arrowMeta);
    }

    public void withdrawArrows(ItemStack itemState, ItemMeta meta, Player player, PersistentDataContainer arrowsCheck){
        int arrowsCheckPDC = getArrows(arrowsCheck, getStorageKey());
        boolean pdcCheck = arrowsCheckPDC == 0;
        if(pdcCheck){
            return;
        }

        int amount = arrowsCheckPDC - 1;
        meta.getPersistentDataContainer().set(getStorageKey(), PersistentDataType.INTEGER, amount);
        List<String> itemLore = new ArrayList<>();
        updateLore(itemLore);
        itemLore.add(7, ChatColor.YELLOW + "Arrows: " + ChatColor.WHITE + amount);
        if(amount == 0){
            if(meta.getPersistentDataContainer().has(getStorageKey3(), PersistentDataType.STRING)) {
                meta.getPersistentDataContainer().remove(getStorageKey3());
            }
            itemLore.add(8, ChatColor.YELLOW + "State: Closed Quiver (Empty)");
            itemState.setType(Material.LEATHER);
            player.sendMessage(ChatColor.GOLD + getSfItemStack().getDisplayName() + " is now empty");
        } else if (itemState.getType() == Material.SPECTRAL_ARROW){
            itemLore.add(8, ChatColor.YELLOW + "State: Open Quiver");
        } else {
            itemLore.add(8, ChatColor.YELLOW + "State: Closed Quiver");
        }
        meta.setLore(itemLore);
        itemState.setItemMeta(meta);
        player.getInventory().addItem(getArrowType().clone());
    }

    public void depositArrows(ItemStack item, ItemMeta meta, PersistentDataContainer arrowsCheck, Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        boolean isNormalItem = isQuiver(getSfItem(itemStack));
        int arrowsCheckPDC = getArrows(arrowsCheck, getStorageKey());
        if(itemStack.getType() != getArrowType().getType()){
            return;
        }
        if(isNormalItem){
            return;
        }
        if(item.getAmount() != 1){
            player.sendMessage(Utils.colorTranslator("&eUnstack the quivers first before using them"));
            return;
        }
        int increment = arrowsCheckPDC + 1;

        List<String> lore = new ArrayList<>();

        if (increment != getQuiverSize() + 1) {
            meta.getPersistentDataContainer().set(getStorageKey(), PersistentDataType.INTEGER, increment);
            updateLore(lore);
            lore.add(7, ChatColor.YELLOW + "Arrows: " + ChatColor.WHITE + increment);
            lore.add(8, ChatColor.YELLOW + "State: Open Quiver");
            meta.setLore(lore);
            if(increment <= 2) {
                int random = ThreadLocalRandom.current().nextInt(1, 999999 + 1);
                meta.getPersistentDataContainer().set(getStorageKey2(), PersistentDataType.INTEGER, random);
            } // pdc to make item un-stackable and unique
            item.setItemMeta(meta);
            item.setType(getArrowType().getType());
            itemStack.setAmount(itemStack.getAmount() - 1);

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
        event.setConsumeItem(false);
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

        List<String> lore = new ArrayList<>();

        if (decrement >= 0) {
            arrow_Left.set(getStorageKey(), PersistentDataType.INTEGER, decrement);
            updateLore(lore);
            lore.add(7, ChatColor.YELLOW + "Arrows: " + ChatColor.WHITE + decrement);
            lore.add(8, ChatColor.YELLOW + "State: Open Quiver");

            if (decrement == 0) {
                if(arrow_Left.has(getStorageKey3(), PersistentDataType.STRING)) {
                    meta.getPersistentDataContainer().remove(getStorageKey3());
                }
                itemStack.setType(Material.LEATHER);
                lore.set(8, ChatColor.YELLOW + "State: Closed Quiver (Empty)");
            }
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }

    }
}
