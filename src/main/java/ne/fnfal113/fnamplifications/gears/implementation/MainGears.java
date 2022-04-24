package ne.fnfal113.fnamplifications.gears.implementation;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

// To DO: Method Documentation
@SuppressWarnings("ConstantConditions")
public class MainGears {

    @Getter
    private final NamespacedKey storageKey;
    @Getter
    private final NamespacedKey storageKey2;
    @Getter
    private final NamespacedKey storageKey3;
    @Getter
    public final List<String> defaultLore;
    @Getter
    private final int startingProgress;
    @Getter
    private final int incrementProgress;
    @Getter final ItemStack itemStack;

    @Getter
    @Setter
    private int level;

    public MainGears(NamespacedKey key1, NamespacedKey key2, NamespacedKey key3, List<String> lore, ItemStack item, int startingProgress, int incrementProgress){
        this.storageKey = key1;
        this.storageKey2 = key2;
        this.storageKey3 = key3;
        this.defaultLore = lore;
        this.itemStack = item;
        this.startingProgress = startingProgress;
        this.incrementProgress = incrementProgress;
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public void updateLore(List<String> lore){
        for(int i = 0; i < getDefaultLore().size(); i++){
            lore.set(i , getDefaultLore().get(i));
        }
    }

    public boolean onHit(EntityDamageByEntityEvent event, Player p, ItemStack item){
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer progress = meta.getPersistentDataContainer();
        PersistentDataContainer level = meta.getPersistentDataContainer();
        PersistentDataContainer max = meta.getPersistentDataContainer();
        int amount = progress.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, 0);
        int armorLevel = level.getOrDefault(getStorageKey2(), PersistentDataType.INTEGER, 0);
        int maxReq = max.getOrDefault(getStorageKey3(), PersistentDataType.INTEGER, getStartingProgress());
        int total = amount + 1;
        progress.set(getStorageKey(), PersistentDataType.INTEGER, total);

        List<String> lore = meta.getLore();

        if (total >= 0) {
            updateLore(lore);
            lore.set(7, ChatColor.YELLOW + "Level: " + armorLevel);
            lore.set(8, ChatColor.YELLOW + "Progress:");
            lore.set(9, ChatColor.GRAY + "[" + getProgressBar(total, maxReq, 10, '■', ChatColor.YELLOW, ChatColor.GRAY) + ChatColor.GRAY + "]");
            if(WeaponArmorEnum.CHESTPLATE.isTagged(itemStack.getType()) && armorLevel == 30 && total == 1){
                lore.add(10,"");
                lore.add(11, ChatColor.RED + "◬◬◬◬◬◬| " + ChatColor.LIGHT_PURPLE + ""
                        + ChatColor.BOLD + "Effects " + ChatColor.GOLD + "|◬◬◬◬◬◬");
                lore.add(12, ChatColor.GREEN + "Permanent Saturation");
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        if (total == maxReq) {
            int totalLevel = armorLevel + 1;
            progress.set(getStorageKey(), PersistentDataType.INTEGER, 0);
            level.set(getStorageKey2(), PersistentDataType.INTEGER, totalLevel);
            updateLore(lore);
            lore.set(7, ChatColor.YELLOW + "Level: " + totalLevel);
            lore.set(8, ChatColor.YELLOW + "Progress:");
            lore.set(9, ChatColor.GRAY + "[" + getProgressBar(total, maxReq, 10, '■', ChatColor.YELLOW, ChatColor.GRAY) + ChatColor.GRAY + "]");
            max.set(getStorageKey3(), PersistentDataType.INTEGER, maxReq + getIncrementProgress());
            meta.setLore(lore);
            item.setItemMeta(meta);
            levelUp(p);
            setLevel(totalLevel);
            return true;
        }
        return false;
    }

    public void levelUp(Player p){
        p.sendMessage(Utils.colorTranslator("&c&l[FNAmpli&b&lfications]> " + getItemStack().getItemMeta().getDisplayName()  + " leveled up!"));
        p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1 , 1);
    }

}