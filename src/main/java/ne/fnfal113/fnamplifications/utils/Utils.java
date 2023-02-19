package ne.fnfal113.fnamplifications.utils;

import ne.fnfal113.fnamplifications.FNAmplifications;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * util class for FNAmplifications
 * @author FN_FAL113
 */
@SuppressWarnings("ConstantConditions")
public class Utils {

    public static String colorTranslator(String strings){
        return ChatColor.translateAlternateColorCodes('&', strings);
    }

    public static String[] stringSequence(String... stringSequence){
        return stringSequence;
    }

    // Armorstand methods for setting euler angles
    public static EulerAngle setRightArmAngle(ArmorStand armorStand, int x, int y, int z){
        double armorStandX = armorStand.getRightArmPose().getX();
        double armorStandY = armorStand.getRightArmPose().getY();
        double armorStandZ = armorStand.getRightArmPose().getZ();

        return new EulerAngle(armorStandX + Math.toRadians(x), armorStandY + Math.toRadians(y), armorStandZ + Math.toRadians(z));
    }

    public static EulerAngle setLeftArmAngle(ArmorStand armorStand, int x, int y, int z){
        double armorStandX = armorStand.getLeftArmPose().getX();
        double armorStandY = armorStand.getLeftArmPose().getY();
        double armorStandZ = armorStand.getLeftArmPose().getZ();

        return new EulerAngle(armorStandX + Math.toRadians(x), armorStandY + Math.toRadians(y), armorStandZ + Math.toRadians(z));
    }

    public static EulerAngle setHeadAngle(ArmorStand armorStand, int x, int y, int z){
        double armorStandX = armorStand.getHeadPose().getX();
        double armorStandY = armorStand.getHeadPose().getY();
        double armorStandZ = armorStand.getHeadPose().getZ();

        return new EulerAngle(armorStandX + Math.toRadians(x), armorStandY + Math.toRadians(y), armorStandZ + Math.toRadians(z));
    }

    /**
     * 
     * @param itemStack the itemstack involved
     * @param configSection usually the itemstack's id as config section
     * @param configSectionSetting the setting under the config section
     * @param stringToReplace serves as the index for searching and replacing the lore line
     * @param color color formatting for the lore line
     * @param suffix the end string of the config value (units, percent, etc)
     * @param tier the current gem tier
     * @param fileName the filename of the config for retrieving the value
     */
    public static void setGemTierLore(ItemStack itemStack, String configSection, String configSectionSetting, String stringToReplace, String color, String suffix, int tier, String fileName){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        for(int i = 0; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + stringToReplace))){
                String line = lore.get(i).replace(Utils.colorTranslator(color + stringToReplace),
                        Utils.colorTranslator(color + (FNAmplifications.getInstance().getConfigManager().getCustomConfig(fileName).getInt(configSection + "." + configSectionSetting) / tier--) + suffix));
                lore.set(i, line);
            }
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    /**
     * 
     * @param itemStack the itemstack involved
     * @param configSection usually the itemstack's id as config section
     * @param configSectionSetting the setting under the config section
     * @param stringToReplace serves as the index for searching and replacing the lore line
     * @param color color formatting for the lore line
     * @param suffix the end string of the config value (units, percent, etc)
     * @param fileName the filename of the config for retrieving the value
     */
    public static void setLoreByConfigValue(@Nonnull ItemStack itemStack, String configSection, String configSectionSetting, String stringToReplace, String color, String suffix, String fileName){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        for(int i = 0; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + stringToReplace))){
                String line = lore.get(i).replace(Utils.colorTranslator(color + stringToReplace),
                        Utils.colorTranslator(color + FNAmplifications.getInstance().getConfigManager().getCustomConfig(fileName).get(configSection + "." + configSectionSetting) + suffix));
                lore.set(i, line);
            }
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    /**
     * 
     * @param itemStack the itemstack involved
     * @param meta itemstack's item meta
     * @param value value being to set in the lore
     * @param prefix serves as the index for searching and replacing the lore line
     * @param color color formatting for the prefix
     * @param color2 color formatting for the value and suffix
     * @param suffix the end string of the pdc value (units, percent, etc)
     */
    public static void setLoreByPdc(ItemStack itemStack, ItemMeta meta, String value, String prefix, String color, String color2, String suffix){
        List<String> lore = meta.getLore();
        for(int i = 0; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + prefix))){
                lore.set(i, Utils.colorTranslator(color + prefix + color2 + value + suffix));
            }
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public static Long cooldownHelper(Long timeInMs){
        return (long) Math.floor((System.currentTimeMillis() - timeInMs) / 1000);
    }

}
