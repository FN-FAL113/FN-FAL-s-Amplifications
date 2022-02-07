package ne.fnfal113.fnamplifications.Gems.Interface;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.EnumSet;
import java.util.Set;

public interface GemImpl {

    Set<Material> PICKAXE = EnumSet.of(
            Material.NETHERITE_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.IRON_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOODEN_PICKAXE

    );

    Set<Material> SHOVELS = EnumSet.of(
            Material.NETHERITE_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.IRON_SHOVEL,
            Material.STONE_SHOVEL,
            Material.WOODEN_SHOVEL

    );

    Set<Material> SWORDS = EnumSet.of(
            Material.NETHERITE_SWORD,
            Material.DIAMOND_SWORD,
            Material.GOLDEN_SWORD,
            Material.IRON_SWORD,
            Material.STONE_SWORD,
            Material.WOODEN_SWORD
    );

    Set<Material> AXE = EnumSet.of(
            Material.NETHERITE_AXE,
            Material.DIAMOND_AXE,
            Material.GOLDEN_AXE,
            Material.IRON_AXE,
            Material.STONE_AXE,
            Material.WOODEN_AXE
    );

    Set<Material> HELMET = EnumSet.of(
            Material.NETHERITE_HELMET,
            Material.DIAMOND_HELMET,
            Material.GOLDEN_HELMET,
            Material.IRON_HELMET,
            Material.LEATHER_HELMET,
            Material.CHAINMAIL_HELMET
    );

    Set<Material> CHESTPLATE = EnumSet.of(
            Material.NETHERITE_CHESTPLATE,
            Material.DIAMOND_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE,
            Material.IRON_CHESTPLATE,
            Material.LEATHER_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE
    );

    Set<Material> LEGGINGS = EnumSet.of(
            Material.NETHERITE_LEGGINGS,
            Material.DIAMOND_LEGGINGS,
            Material.GOLDEN_LEGGINGS,
            Material.IRON_LEGGINGS,
            Material.LEATHER_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS
    );

    void onDrag(InventoryClickEvent event, Player player);

    int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack);
}
