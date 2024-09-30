package ne.fnfal113.fnamplifications.utils.compatibility;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;

public class VersionedMaterial {

    public static Material SHORT_GRASS;

    // SHORT_GRASS exists on 1.20.4 versions above
    static { 
      String version = Bukkit.getBukkitVersion().split("-")[0];
      String[] versionArr = version.split("\\.");

      int major = Integer.parseInt(versionArr[1]);
      int minor = Integer.parseInt(versionArr.length == 2 ? "0" : versionArr[2]);

      SHORT_GRASS = major >= 20 && minor >= 4 ? Material.SHORT_GRASS : getKey("grass");
    }

    public VersionedMaterial() {}

    @Nonnull
    private static Material getKey(@Nonnull String key) {
      return (Material)Registry.MATERIAL.get(NamespacedKey.minecraft(key));
    }

}
