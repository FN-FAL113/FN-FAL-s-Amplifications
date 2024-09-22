package ne.fnfal113.fnamplifications.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A simple potion builder based from the given {@link PotionType}
 */
public class PotionBuilder {

    private final PotionType potionType;

    @ParametersAreNonnullByDefault
    public PotionBuilder(PotionType potionType) {
        this.potionType = potionType;
    }

    /**
     *
     * @return the potion as itemstack
     */
    public ItemStack createPotion() {
        ItemStack itemStack = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();

        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_20_5)) {
            meta.setBasePotionType(potionType);
        } else {
            meta.setBasePotionData(new PotionData(getPotionType()));
        }
        
        itemStack.setItemMeta(meta);

        return itemStack.clone();
    }

    public PotionType getPotionType() {
        return potionType;
    }

}
