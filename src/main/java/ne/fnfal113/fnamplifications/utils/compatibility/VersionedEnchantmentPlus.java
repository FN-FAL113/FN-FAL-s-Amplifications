package ne.fnfal113.fnamplifications.utils.compatibility;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

/*
 * Slimefun's versioned enchantment class does not cover all 1.20.5 changes
 */
public class VersionedEnchantmentPlus {
    
    public static final Enchantment POWER = getKey("power");
    public static final Enchantment INFINITY = getKey("infinity");
    public static final Enchantment PUNCH = getKey("punch");
    public static final Enchantment FLAME = getKey("flame");
    public static final Enchantment BANE_OF_ARTHROPODS = getKey("bane_of_arthropods");
    public static final Enchantment SMITE = getKey("smite"); 

    public VersionedEnchantmentPlus() {}

    @Nonnull
    private static Enchantment getKey(@Nonnull String key) {
      return (Enchantment)Registry.ENCHANTMENT.get(NamespacedKey.minecraft(key));
    }

}
