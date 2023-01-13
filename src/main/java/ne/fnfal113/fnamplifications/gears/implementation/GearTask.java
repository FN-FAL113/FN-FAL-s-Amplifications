package ne.fnfal113.fnamplifications.gears.implementation;

import com.google.common.base.Strings;
import lombok.Getter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// To DO: Method Documentation
@SuppressWarnings("ConstantConditions")
public class GearTask {

    @Getter
    private final NamespacedKey storageKey;
    @Getter
    private final NamespacedKey storageKey2;
    @Getter
    private final NamespacedKey storageKey3;
    @Getter
    private final int startingProgress;
    @Getter
    private final int incrementProgress;
    @Getter
    private final int maxLevel;
    @Getter
    private final ItemStack itemStack;

    private final List<UUID> uuidList = new ArrayList<>();

    public GearTask(NamespacedKey key1, NamespacedKey key2, NamespacedKey key3, ItemStack item, int startingProgress, int incrementProgress, int maxLevel){
        this.storageKey = key1;
        this.storageKey2 = key2;
        this.storageKey3 = key3;
        this.itemStack = item;
        this.startingProgress = startingProgress;
        this.incrementProgress = incrementProgress;
        this.maxLevel = maxLevel;
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max; // divide the current progress to the max value to get the percent
        int progressBars = (int) (totalBars * percent); // multiply the percent value to total progress bars to get current bar amount

        // repeat the progress bar icon with the initial value
        // then get the difference between the initial value and total progress bars to get not completed bars
        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public boolean onHit(EntityDamageByEntityEvent event, Player p, ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer progress = meta.getPersistentDataContainer();

        int xpAmount = progress.getOrDefault(getStorageKey(), PersistentDataType.INTEGER, 0);
        int armorLevel = progress.getOrDefault(getStorageKey2(), PersistentDataType.INTEGER, 0);
        int maxXpReq = progress.getOrDefault(getStorageKey3(), PersistentDataType.INTEGER, getStartingProgress());
        int xpAmountIncremented = xpAmount + 1;

        if(isMaxLevel(armorLevel)){
            if(!uuidList.contains(p.getUniqueId())) {
                p.sendMessage(Utils.colorTranslator(meta.getDisplayName() + "&c has reached max level!"));
                uuidList.add(p.getUniqueId());
            }
            return false;
        }

        progress.set(getStorageKey(), PersistentDataType.INTEGER, xpAmountIncremented);

        List<String> lore = meta.getLore();

        if (xpAmountIncremented >= 0) {
           updateArmour(armorLevel, xpAmountIncremented, maxXpReq, item, meta, lore);
        }

        if (xpAmountIncremented == maxXpReq) {
            return levelUpArmour(armorLevel, xpAmountIncremented, maxXpReq, item, meta, progress, lore, p);
        }

        return false;
    }

    public void updateArmour(int armorLevel, int xpAmountIncremented, int maxXpReq, ItemStack item, ItemMeta meta, List<String> lore){
        lore.set(7, Utils.colorTranslator("&eLevel: ") + armorLevel);
        lore.set(8, Utils.colorTranslator("&eProgress:"));
        lore.set(9, Utils.colorTranslator("&7[&r" + getProgressBar(xpAmountIncremented, maxXpReq, 10, '■', ChatColor.YELLOW, ChatColor.GRAY) + "&7]"));

        if(WeaponArmorEnum.CHESTPLATE.isTagged(getItemStack().getType()) && armorLevel == 30 && xpAmountIncremented == 1){
            lore.add(10,"");
            lore.add(11, ChatColor.RED + "◬◬◬◬◬◬| " + ChatColor.LIGHT_PURPLE + ""
                    + ChatColor.BOLD + "Effects " + ChatColor.GOLD + "|◬◬◬◬◬◬");
            lore.add(12, ChatColor.GREEN + "Permanent Saturation");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public boolean levelUpArmour(int armorLevel, int xpAmountIncremented, int maxXpReq, ItemStack item, ItemMeta meta, PersistentDataContainer progress, List<String> lore, Player p){
        if(isMaxLevel(armorLevel)){
            p.sendMessage(Utils.colorTranslator(meta.getDisplayName() + "&c has reached max level!"));
            return false;
        }

        int currentArmorLevel = armorLevel + 1;

        // reset armor pdc xp to 0
        progress.set(getStorageKey(), PersistentDataType.INTEGER, 0);
        // increase armor pdc level by 1
        progress.set(getStorageKey2(), PersistentDataType.INTEGER, currentArmorLevel);
        // increase armor pdc max xp requirement
        progress.set(getStorageKey3(), PersistentDataType.INTEGER, maxXpReq + getIncrementProgress());

        lore.set(7, Utils.colorTranslator("&eLevel: ") + currentArmorLevel);
        lore.set(8, Utils.colorTranslator("&eProgress:"));
        lore.set(9, Utils.colorTranslator("&7[&r" + getProgressBar(xpAmountIncremented, maxXpReq, 10, '■', ChatColor.YELLOW, ChatColor.GRAY) + "&7]"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        sendLevelUpMessage(p);

        return true;
    }

    public boolean isMaxLevel(int armorLevel){
        return armorLevel >= getMaxLevel();
    }

    public void sendLevelUpMessage(Player p){
        p.sendMessage(Utils.colorTranslator("&c&l[FNAmpli&b&lfications]> " + getItemStack().getItemMeta().getDisplayName()  + " leveled up!"));
        p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1 , 1);
    }

}