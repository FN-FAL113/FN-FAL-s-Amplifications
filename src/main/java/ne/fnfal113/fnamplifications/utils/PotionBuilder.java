package ne.fnfal113.fnamplifications.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A simple potion builder based from the given {@link PotionType}
 * @author FN_FAL113
 */
public class PotionBuilder {

    @Getter
    private final PotionType potionType;

    @ParametersAreNonnullByDefault
    public PotionBuilder(PotionType potionType){
        this.potionType = potionType;
    }

    /**
     *
     * @return the potion as itemstack
     */
    public ItemStack createPotion(){
        ItemStack itemStack = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();

        meta.setBasePotionData(new PotionData(getPotionType()));
        itemStack.setItemMeta(meta);

        return itemStack.clone();
    }

}
