package ne.fnfal113.fnamplifications.utils;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.Material;

/**
 * Enum that contains every vanilla weapon and armors
 */
public enum WeaponArmorEnum {

    PICKAXE(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_PICKAXE : Material.DIAMOND_PICKAXE, Material.DIAMOND_PICKAXE,
            Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE,
            Material.STONE_PICKAXE, Material.WOODEN_PICKAXE),
    HELMET(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_HELMET : Material.DIAMOND_HELMET, Material.DIAMOND_HELMET,
            Material.GOLDEN_HELMET, Material.IRON_HELMET,
            Material.CHAINMAIL_HELMET, Material.LEATHER_HELMET),
    LEGGINGS(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_LEGGINGS : Material.DIAMOND_LEGGINGS, Material.DIAMOND_LEGGINGS,
            Material.GOLDEN_LEGGINGS, Material.IRON_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS, Material.LEATHER_LEGGINGS),
    CHESTPLATE(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_CHESTPLATE : Material.DIAMOND_CHESTPLATE, Material.DIAMOND_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE, Material.IRON_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE, Material.LEATHER_CHESTPLATE),
    BOOTS(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_BOOTS : Material.DIAMOND_BOOTS, Material.DIAMOND_BOOTS,
            Material.GOLDEN_BOOTS, Material.IRON_BOOTS,
            Material.CHAINMAIL_BOOTS, Material.LEATHER_BOOTS),
    SWORDS(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_SWORD : Material.DIAMOND_SWORD, Material.DIAMOND_SWORD,
            Material.GOLDEN_SWORD, Material.IRON_SWORD,
            Material.STONE_SWORD, Material.WOODEN_SWORD),
    AXES(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_AXE : Material.DIAMOND_AXE, Material.DIAMOND_AXE,
            Material.GOLDEN_AXE, Material.IRON_AXE,
            Material.STONE_AXE, Material.WOODEN_AXE),
    SHOVELS(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_SHOVEL : Material.DIAMOND_SHOVEL, Material.DIAMOND_SHOVEL,
           Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL,
           Material.STONE_SHOVEL, Material.WOODEN_SHOVEL),
    HOES(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)
            ? Material.NETHERITE_HOE : Material.DIAMOND_HOE, Material.DIAMOND_HOE,
            Material.DIAMOND_HOE, Material.DIAMOND_HOE,
            Material.DIAMOND_HOE, Material.DIAMOND_HOE),
    BOWS(Material.BOW, Material.CROSSBOW)
    ;

    private final Material[] materialSet;

    WeaponArmorEnum(Material... materials) {
        this.materialSet = materials;
    }

    public Material[] getMaterialSet(){
        return materialSet;
    }

    public boolean isTagged(Material material){
        for(Material i : getMaterialSet()){
            if(i == material){
                return true;
            }
        }
        return false;
    }

}
