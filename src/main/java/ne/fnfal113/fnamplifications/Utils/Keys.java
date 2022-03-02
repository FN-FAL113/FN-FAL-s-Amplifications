package ne.fnfal113.fnamplifications.Utils;

import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.NamespacedKey;

public class Keys {
    // Guardian Gem
    public static final NamespacedKey GUARDIAN_KEY = createKey("fnguardian");
    // Retaliate Gem
    public static final NamespacedKey RETURN_WEAPON_KEY = createKey("return_weapon");

    // Retaliate gem required keys
    public static final NamespacedKey RETURN_DAMNATION_KEY = createKey("fn_gem_damnation");
    public static final NamespacedKey RETURN_TRISWORD_KEY = createKey("fn_gem_tri_sword");
    public static final NamespacedKey RETURN_AXE_KEY = createKey("fn_gem_axethrowie");

    // Quiver
    public static final NamespacedKey ARROWS_KEY = createKey("arrows");
    public static final NamespacedKey ARROWS_ID_KEY = createKey("arrowid");
    public static final NamespacedKey QUIVER_STATE_KEY = createKey("quiver_state");
    // Spectral Quiver
    public static final NamespacedKey SPECTRAL_ARROWS_KEY = createKey("spectralarrows");
    public static final NamespacedKey SPECTRAL_ARROWS_ID_KEY = createKey("spectralarrowid");
    public static final NamespacedKey SPECTRAL_STATE_KEY = createKey("spectral_state");
    // Upgraded Quiver
    public static final NamespacedKey UPGRADED_ARROWS_KEY = createKey("upgradedarrows");
    public static final NamespacedKey UPGRADED_ARROWS_ID_KEY = createKey("upgradedarrowid");
    public static final NamespacedKey UPGRADED_QUIVER_STATE_KEY = createKey("upgraded_state");
    // Upgraded Spectral Quiver
    public static final NamespacedKey UPGRADED_SPECTRAL_ARROWS_KEY = createKey("upgradedspectralarrows");
    public static final NamespacedKey UPGRADED_SPECTRAL_ARROWS_ID_KEY = createKey("upgradedspectralarrowid");
    public static final NamespacedKey UPGRADED_SPECTRAL_QUIVER_STATE_KEY = createKey("upgradedspectral_state");

    // FN Gears
    // Helmet
    public static  final NamespacedKey FN_GEAR_HELMET = createKey("helmet");
    public static  final NamespacedKey FN_GEAR_HELMET_LEVEL = createKey("helmetlevel");
    public static  final NamespacedKey FN_GEAR_HELMET_FINAL = createKey("helmetfinal");
    // Chestplate
    public static  final NamespacedKey FN_GEAR_CHEST = createKey("armor");
    public static  final NamespacedKey FN_GEAR_CHEST_LEVEL = createKey("armorlevel");
    public static  final NamespacedKey FN_GEAR_CHEST_FINAL = createKey("armorfinal");
    // Leggings
    public static  final NamespacedKey FN_GEAR_LEGGINGS = createKey("leggings");
    public static  final NamespacedKey FN_GEAR_LEGGINGS_LEVEL = createKey("leggingslevel");
    public static  final NamespacedKey FN_GEAR_LEGGINGS_FINAL = createKey("leggingsfinal");
    // Boots
    public static  final NamespacedKey FN_GEAR_BOOTS = createKey("boots");
    public static  final NamespacedKey FN_GEAR_BOOTS_LEVEL = createKey("bootslevel");
    public static  final NamespacedKey FN_GEAR_BOOTS_FINAL = createKey("boostfinal");



    public static NamespacedKey createKey(String identifier){
        return new NamespacedKey(FNAmplifications.getInstance(), identifier);
    }
}
