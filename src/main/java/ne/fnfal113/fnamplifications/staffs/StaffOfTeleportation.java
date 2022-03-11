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
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfTeleportation extends AbstractStaff {

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfTeleportation(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "tpstaff");
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
        lore.add(1, ChatColor.LIGHT_PURPLE + "Teleport to a target block by");
        lore.add(2, ChatColor.LIGHT_PURPLE + "right clicking it");

        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Vector directional = player.getLocation().getDirection();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getPlayer().getTargetBlockExact(100);

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to teleport there!");
            return;
        }

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);
        player.teleport(block.getLocation().add(0.5, 1, 0.5).setDirection(directional));

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

    }
}