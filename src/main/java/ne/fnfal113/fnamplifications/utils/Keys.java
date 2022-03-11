package ne.fnfal113.fnamplifications.utils;

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
    public static final NamespacedKey FN_GEAR_CHEST = createKey("armor");
    public static final NamespacedKey FN_GEAR_CHEST_LEVEL = createKey("armorlevel");
    public static final NamespacedKey FN_GEAR_CHEST_FINAL = createKey("armorfinal");
    // Leggings
    public static final NamespacedKey FN_GEAR_LEGGINGS = createKey("leggings");
    public static final NamespacedKey FN_GEAR_LEGGINGS_LEVEL = createKey("leggingslevel");
    public static final NamespacedKey FN_GEAR_LEGGINGS_FINAL = createKey("leggingsfinal");
    // Boots
    public static final NamespacedKey FN_GEAR_BOOTS = createKey("boots");
    public static final NamespacedKey FN_GEAR_BOOTS_LEVEL = createKey("bootslevel");
    public static final NamespacedKey FN_GEAR_BOOTS_FINAL = createKey("boostfinal");

    // Mystery Sticks
    public static final NamespacedKey STICK_1_EXP_LEVELS = createKey("stick_1_exp_levels_consumed");
    public static final NamespacedKey STICK_1_DAMAGE = createKey("stick_1_damage_inflicted");

    public static final NamespacedKey STICK_2_EXP_LEVELS = createKey("stick_2_exp_levels_consumed");
    public static final NamespacedKey STICK_2_DAMAGE = createKey("stick_2_damage_inflicted");

    public static final NamespacedKey STICK_3_EXP_LEVELS = createKey("stick_3_exp_levels_consumed");
    public static final NamespacedKey STICK_3_DAMAGE = createKey("stick_3_damage_inflicted");

    public static final NamespacedKey STICK_4_EXP_LEVELS = createKey("stick_4_exp_levels_consumed");
    public static final NamespacedKey STICK_4_DAMAGE = createKey("stick_4_damage_inflicted");

    public static final NamespacedKey STICK_5_EXP_LEVELS = createKey("stick_5_exp_levels_consumed");
    public static final NamespacedKey STICK_5_DAMAGE = createKey("stick_5_damage_inflicted");

    public static final NamespacedKey STICK_6_EXP_LEVELS = createKey("stick_6_exp_levels_consumed");
    public static final NamespacedKey STICK_6_DAMAGE = createKey("stick_6_damage_inflicted");

    public static final NamespacedKey STICK_7_EXP_LEVELS = createKey("stick_7_exp_levels_consumed");
    public static final NamespacedKey STICK_7_DAMAGE = createKey("stick_7_damage_inflicted");

    public static final NamespacedKey STICK_8_EXP_LEVELS = createKey("stick_8_exp_levels_consumed");
    public static final NamespacedKey STICK_8_DAMAGE = createKey("stick_8_damage_inflicted");

    public static final NamespacedKey STICK_9_EXP_LEVELS = createKey("stick_9_exp_levels_consumed");
    public static final NamespacedKey STICK_9_DAMAGE = createKey("stick_9_damage_inflicted");

    public static final NamespacedKey STICK_10_EXP_LEVELS = createKey("stick_10_exp_levels_consumed");
    public static final NamespacedKey STICK_10_DAMAGE = createKey("stick_10_damage_inflicted");

    public static final NamespacedKey STICK_11_EXP_LEVELS = createKey("stick_11_exp_levels_consumed");
    public static final NamespacedKey STICK_11_DAMAGE = createKey("stick_11_damage_inflicted");


    public static NamespacedKey createKey(String identifier){
        return new NamespacedKey(FNAmplifications.getInstance(), identifier);
    }
}
