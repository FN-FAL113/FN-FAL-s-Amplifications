package ne.fnfal113.fnamplifications.Gems.Implementation;

import org.bukkit.Material;

public enum WeaponArmorEnum {

    PICKAXE(Material.NETHERITE_PICKAXE, Material.DIAMOND_PICKAXE,
            Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE,
            Material.STONE_PICKAXE, Material.WOODEN_PICKAXE),
    HELMET(Material.NETHERITE_HELMET, Material.DIAMOND_HELMET,
            Material.GOLDEN_CHESTPLATE, Material.IRON_HELMET,
            Material.CHAINMAIL_HELMET, Material.LEATHER_HELMET),
    LEGGINGS(Material.NETHERITE_LEGGINGS, Material.DIAMOND_LEGGINGS,
            Material.GOLDEN_LEGGINGS, Material.IRON_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS, Material.LEATHER_LEGGINGS),
    CHESTPLATE(Material.NETHERITE_CHESTPLATE, Material.DIAMOND_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE, Material.IRON_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE, Material.LEATHER_CHESTPLATE),
    BOOTS(Material.NETHERITE_BOOTS, Material.DIAMOND_BOOTS,
            Material.GOLDEN_BOOTS, Material.IRON_BOOTS,
            Material.CHAINMAIL_BOOTS, Material.LEATHER_BOOTS),
    SWORDS(Material.NETHERITE_SWORD, Material.DIAMOND_SWORD,
            Material.GOLDEN_SWORD, Material.IRON_SWORD,
            Material.STONE_SWORD, Material.WOODEN_SWORD),
    AXES(Material.NETHERITE_AXE, Material.DIAMOND_AXE,
            Material.GOLDEN_AXE, Material.IRON_AXE,
            Material.STONE_AXE, Material.WOODEN_AXE),
    SHOVELS(Material.NETHERITE_SHOVEL, Material.DIAMOND_SHOVEL,
           Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL,
           Material.STONE_SHOVEL, Material.WOODEN_SHOVEL)
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
