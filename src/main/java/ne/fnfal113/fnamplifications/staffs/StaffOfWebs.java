package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfWebs extends AbstractStaff {

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfWebs(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "webstaff");
        this.mainStaff = new MainStaff(lore(), 10, getStorageKey(), this.getItem(), this.getId());
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    @Override
    public List<String> lore(){
        List<String> lore = new ArrayList<>();
        lore.add(0, "");
        lore.add(1, ChatColor.LIGHT_PURPLE + "Right click a target block");
        lore.add(2, ChatColor.LIGHT_PURPLE + "to spawn a wall of cobwebs");

        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getPlayer().getTargetBlockExact(50);

        if(event.getHand() != EquipmentSlot.HAND){
            return;
        }

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast cobwebs there!");
            return;
        }

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

        int x = 0, z = 0, i = 0, j = 0;
        boolean isX = true;

        if(player.getFacing() == BlockFace.EAST){
            z = -1;
            j = 1;
            isX = false;
        } // positive x
        else if(player.getFacing() == BlockFace.SOUTH){
            x = -1;
            i = 1;
        } // positive z
        else if(player.getFacing() == BlockFace.WEST){
            z = -1;
            j = 1;
            isX = false;
        } // negative X
        else if(player.getFacing() == BlockFace.NORTH){
            x = -1;
            i = 1;
        } // negative z

        Location loc = block.getLocation().clone();

        if(isX){
            for(int y = 1; y <= 3; y++){
                for(int a = x; a <= i; a++){
                    if(loc.getBlock().getRelative(a, y, 0).getType() == Material.AIR){
                        loc.getBlock().getRelative(a, y, 0).setType(Material.COBWEB);
                    }
                }
            }
        } else {
            for(int y = 1; y <= 3; y++){
                for(int b = z; b <= j; b++){
                    if(loc.getBlock().getRelative(0, y, b).getType() == Material.AIR) {
                        loc.getBlock().getRelative(0, y, b).setType(Material.COBWEB);
                    }
                }
            }
        }

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }
}