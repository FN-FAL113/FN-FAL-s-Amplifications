package ne.fnfal113.fnamplifications.staffs;

import lombok.Getter;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainStaff {

    @Getter
    private final List<String> defaultLore;
    @Getter
    private final NamespacedKey storageKey;
    @Getter
    private final String id;

    @SneakyThrows
    public MainStaff(List<String> lore, int uses, NamespacedKey storageKey, ItemStack itemStack, String id) {
        this.defaultLore = lore;
        this.storageKey = storageKey;
        this.id = id;

        setConfigValues(uses);
        Utils.setLore(itemStack, this.getId(), "-max-uses", "left", "&e", " left");
    }

    /**
     * this adds the config entries if none exist
     * @param maxUses the max uses value that will be set in the config
     */
    public void setConfigValues(int maxUses) throws IOException {
        FNAmplifications.getInstance().getConfigManager().setIntegerValues(this.getId() + "-max-uses",  maxUses, "staffs-settings");
    }

    /**
     * used for rebuilding the lore of the staff instead of using {@link ItemMeta#getLore()}
     * @param lore this adds the default lore of the staff to a string list
     */
    public void updateLore(List<String> lore){
        for(int i = 0; i < getDefaultLore().size(); i++){
            lore.add(i , getDefaultLore().get(i));
        }
    }

    /**
     * this is used for updating the lore (uses left) when using the staffs
     * @param item the staff used
     * @param meta the item meta of the staff
     * @param player the player who used the staff
     */
    public void updateMeta(ItemStack item, ItemMeta meta, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-max-uses"));
        int decrement = uses_Left - 1;

        List<String> lore = new ArrayList<>();

        if(decrement > 0) { // update the staff uses left the lore
            max_Uses.set(getStorageKey(), PersistentDataType.INTEGER, decrement);
            updateLore(lore);
            lore.add("");
            lore.add(Utils.colorTranslator("&eUses: " + decrement + " left"));
            meta.setLore(lore);
            item.setItemMeta(meta);
        } else { // destroy the staff when it reached the max uses
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(Utils.colorTranslator(meta.getDisplayName() + " &d&lhas reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }

    }

}