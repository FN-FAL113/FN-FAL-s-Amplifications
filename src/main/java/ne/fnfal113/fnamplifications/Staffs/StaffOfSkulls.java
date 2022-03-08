package ne.fnfal113.fnamplifications.Staffs;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfSkulls extends AbstractStaff {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfSkulls(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "skullsstaff");
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
        lore.add(1, ChatColor.LIGHT_PURPLE + "Right click to launch");
        lore.add(2, ChatColor.LIGHT_PURPLE + "wither skull projectiles");

        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getPlayer().getTargetBlock(null, 50);

        if (block.getType() != Material.AIR && !Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast skull there!");
            return;
        }

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

        for(int i = 0; i < 7; i++) {
            WitherSkull skull = (WitherSkull) player.getWorld().spawnEntity
                    (player.getEyeLocation().clone().add(player.getLocation().getDirection().clone().multiply(i).setY(-0.5)),
                            EntityType.WITHER_SKULL);
            skull.setDirection(player.getLocation().getDirection().clone());
            skull.setGlowing(true);
            skull.setIsIncendiary(true);
            skull.setBounce(false);
        }

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public static void setup(){
        new StaffOfSkulls(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_SKULLS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.NECROTIC_SKULL, 2), new ItemStack(Material.WITHER_SKELETON_SKULL, 3),  new SlimefunItemStack(SlimefunItems.NECROTIC_SKULL, 2),
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 6), new ItemStack(Material.BLAZE_ROD), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 6),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.BLAZE_POWDER, 14), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8)})
                .register(plugin);
    }
}