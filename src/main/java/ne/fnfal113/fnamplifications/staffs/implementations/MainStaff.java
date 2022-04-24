package ne.fnfal113.fnamplifications.staffs.implementations;

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

import java.util.Objects;

public class MainStaff {

    @Getter
    private final NamespacedKey storageKey;
    @Getter
    private final String id;

    @SneakyThrows
    public MainStaff(NamespacedKey storageKey, String id) {
        this.storageKey = storageKey;
        this.id = id;
    }

    /**
     * this is used for updating the lore (uses left) when using the staffs
     * @param item the staff used
     * @param meta the item meta of the staff
     * @param player the player who used the staff
     */
    public void updateMeta(ItemStack item, ItemMeta meta, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "max-uses"));
        int decrement = uses_Left - 1;

        if(decrement > 0) { // update the staff uses left the lore
            max_Uses.set(getStorageKey(), PersistentDataType.INTEGER, decrement);
            Utils.updateValueByPdc(item, meta, String.valueOf(decrement), "Uses: ", "&e", "", " left");
        } else { // destroy the staff when it reached the max uses
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(Utils.colorTranslator(meta.getDisplayName() + " &d&lhas reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }
        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);
    }

}