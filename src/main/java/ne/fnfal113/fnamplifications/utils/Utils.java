package ne.fnfal113.fnamplifications.utils;

import ne.fnfal113.fnamplifications.FNAmplifications;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class Utils {

    public static String colorTranslator(String strings){
        return ChatColor.translateAlternateColorCodes('&', strings);
    }

    public static String[] stringSequence(String... stringSequence){
        return stringSequence;
    }

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

    public static void upgradeGemLore(ItemStack itemStack, ItemMeta meta, String configId, String configIdSuffix, String stringToReplace, String color, String suffix, int tier){
        List<String> lore = meta.getLore();
        int decrementTier = tier;

        for(int i = 0 ; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + stringToReplace))){
                String line = lore.get(i).replace(Utils.colorTranslator(color + stringToReplace),
                            Utils.colorTranslator(color + (FNAmplifications.getInstance().getConfigManager().getValueById(configId, configIdSuffix) / decrementTier--) + suffix));
                lore.set(i, line);
            }
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public static void setLore(@Nonnull ItemStack itemStack, String configId, String configIdSuffix, String stringToReplace, String color, String suffix){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        for(int i = 0 ; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + stringToReplace))){
                String line = lore.get(i).replace(Utils.colorTranslator(color + stringToReplace),
                        Utils.colorTranslator(color + FNAmplifications.getInstance().getConfigManager().getValueById(configId, configIdSuffix) + suffix));
                lore.set(i, line);
            }
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public static void updateValueByPdc(ItemStack itemStack, ItemMeta meta, String value, String prefix, String color, String color2, String suffix){
        List<String> lore = meta.getLore();
        for(int i = 0 ; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + prefix))){
                lore.set(i, Utils.colorTranslator(color + prefix + color2 + value + suffix));
            }
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }



}