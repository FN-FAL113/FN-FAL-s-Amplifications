package ne.fnfal113.fnamplifications.Staffs;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MainStaff {

    @Getter
    private final List<String> defaultLore;
    @Getter
    private final int staffUses;
    @Getter
    private final NamespacedKey storageKey;

    public MainStaff(List<String> lore, int uses, NamespacedKey storageKey) {
        this.defaultLore = lore;
        this.staffUses = uses;
        this.storageKey = storageKey;
    }

    public void updateLore(List<String> lore){
        for(int i = 0; i < getDefaultLore().size(); i++){
            lore.add(i , getDefaultLore().get(i));
        }
    }

    public void updateMeta(ItemStack item, ItemMeta meta, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, getStaffUses());
        int decrement = uses_Left - 1;

        List<String> lore = new ArrayList<>();

        if(decrement > 0) {
            max_Uses.set(getStorageKey(), PersistentDataType.INTEGER, decrement);
            updateLore(lore);
            lore.add("");
            lore.add(ChatColor.YELLOW + "Uses left: " + decrement);
            meta.setLore(lore);
            item.setItemMeta(meta);
        } else {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',  meta.getDisplayName() + " &d&lhas reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }

    }

}