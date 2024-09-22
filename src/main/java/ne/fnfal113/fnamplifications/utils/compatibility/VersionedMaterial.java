package ne.fnfal113.fnamplifications.utils.compatibility;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

public class VersionedMaterial {

    public static Material SHORT_GRASS = getKey("grass");

    static { 
      SHORT_GRASS = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_20) ? Material.SHORT_GRASS : getKey("grass");
    }

    public VersionedMaterial() {}

    @Nonnull
    private static Material getKey(@Nonnull String key) {
      return (Material)Registry.MATERIAL.get(NamespacedKey.minecraft(key));
    }

}
