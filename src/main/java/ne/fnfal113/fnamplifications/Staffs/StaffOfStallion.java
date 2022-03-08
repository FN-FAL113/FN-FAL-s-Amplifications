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
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class StaffOfStallion extends AbstractStaff {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfStallion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "stallionstaff");
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
        lore.add(1, ChatColor.LIGHT_PURPLE + "Spawns a skeleton horse that is");
        lore.add(2, ChatColor.LIGHT_PURPLE + "rideable until passenger dismount");

        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getPlayer().getTargetBlockExact(50);

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast stallion there!");
            return;
        }

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

        SkeletonHorse skeletonHorse = (SkeletonHorse) player.getWorld().spawnEntity(player.getLocation() , EntityType.SKELETON_HORSE);
        skeletonHorse.setAdult();
        skeletonHorse.setTamed(true);
        skeletonHorse.setCustomName("FN_SKELETON_HORSE");
        skeletonHorse.setCustomNameVisible(false);
        skeletonHorse.getPersistentDataContainer().set(new NamespacedKey((Plugin) plugin, "Horsey"), PersistentDataType.INTEGER, new Random().nextInt(9999));
        skeletonHorse.setOwner(player);
        skeletonHorse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        skeletonHorse.addPassenger(player);

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public static void setup(){
        new StaffOfStallion(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_STALLION, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 32), new ItemStack(Material.SADDLE), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 32),
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.BLAZE_ROD),  SlimefunItems.ESSENCE_OF_AFTERLIFE,
                new ItemStack(Material.BONE, 32), SlimefunItems.MAGIC_SUGAR, new ItemStack(Material.BONE, 32)})
                .register(plugin);
    }
}