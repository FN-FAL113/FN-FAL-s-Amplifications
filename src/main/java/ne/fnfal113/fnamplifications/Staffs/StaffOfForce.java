package ne.fnfal113.fnamplifications.Staffs;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfForce extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    private final NamespacedKey defaultUsageKey;

    public StaffOfForce(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "forcestaff");
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        NamespacedKey key = getStorageKey();
        Block block = event.getPlayer().getTargetBlockExact(50);

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.BREAK_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast force cloud there!");
            return;
        }

        if(item.getItemMeta() == null){
            return;
        }

        ItemMeta meta = item.getItemMeta();

        updateMeta(item, meta, key, player);

        if(player.isSneaking()) {
            AreaEffectCloud effectCloudBack = (AreaEffectCloud) player.getWorld().spawnEntity(block.getLocation().add(0.5, 1, 0.5), EntityType.AREA_EFFECT_CLOUD);
            effectCloudBack.setParticle(Particle.END_ROD);
            effectCloudBack.setDuration(160);
            effectCloudBack.setRadius(2.85F);
            effectCloudBack.setCustomName("FN_BACKWARD_FORCE");
            effectCloudBack.setCustomNameVisible(false);
            effectCloudBack.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 0, 0, false, false, false), true);
            player.sendMessage(ChatColor.RED  + "You spawned a cloud effect with backward force");
        } else {
            AreaEffectCloud effectCloudForward = (AreaEffectCloud) player.getWorld().spawnEntity(block.getLocation().add(0.5, 1, 0.5), EntityType.AREA_EFFECT_CLOUD);
            effectCloudForward.setParticle(Particle.ELECTRIC_SPARK);
            effectCloudForward.setDuration(160);
            effectCloudForward.setRadius(2.85F);
            effectCloudForward.setCustomName("FN_FORCE");
            effectCloudForward.setCustomNameVisible(false);
            effectCloudForward.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 0, 0, false, false, false), true);
            player.sendMessage(ChatColor.GREEN + "You spawned a cloud effect with forward force");
        }

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public void updateMeta(ItemStack item, ItemMeta meta, NamespacedKey key, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(key, PersistentDataType.INTEGER, value.staffOfForce());
        int decrement = uses_Left - 1;

        List<String> lore = new ArrayList<>();

        if(decrement > 0) {
            max_Uses.set(key, PersistentDataType.INTEGER, decrement);
            lore.add(0, "");
            lore.add(1, ChatColor.LIGHT_PURPLE + "Right click to spawn a cloud of effect");
            lore.add(2, ChatColor.LIGHT_PURPLE + "that gives a force push forward or");
            lore.add(3, ChatColor.LIGHT_PURPLE + "shift-right-click to spawn a different cloud");
            lore.add(4, ChatColor.LIGHT_PURPLE + "of effect that gives a backward force");
            lore.add(5, "");
            lore.add(6, ChatColor.YELLOW + "Uses left: " + decrement);
            meta.setLore(lore);
            item.setItemMeta(meta);
        } else {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lForce staff has reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }


    }

    public static void setup(){
        new StaffOfForce(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_FORCE, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 32), new ItemStack(Material.SADDLE), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 32),
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.BLAZE_ROD),  SlimefunItems.ESSENCE_OF_AFTERLIFE,
                new ItemStack(Material.BONE, 32), SlimefunItems.MAGIC_SUGAR, new ItemStack(Material.BONE, 32)})
                .register(plugin);
    }
}