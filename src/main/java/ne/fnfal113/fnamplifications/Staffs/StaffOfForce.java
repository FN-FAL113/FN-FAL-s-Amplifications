package ne.fnfal113.fnamplifications.Staffs;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfForce extends AbstractStaff {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfForce(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "forcestaff");
        this.mainStaff = new MainStaff(lore(), value.staffOfForce(), getStorageKey());
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    @Override
    public List<String> lore(){
        List<String> lore = new ArrayList<>();
        lore.add(0, "");
        lore.add(1, ChatColor.LIGHT_PURPLE + "Right click to spawn a cloud of effect");
        lore.add(2, ChatColor.LIGHT_PURPLE + "that gives a force push forward or");
        lore.add(3, ChatColor.LIGHT_PURPLE + "shift-right-click to spawn a different cloud");
        lore.add(4, ChatColor.LIGHT_PURPLE + "of effect that gives a backward force");

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
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast force cloud there!");
            return;
        }

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

        spawnCloud(player, block, player.isSneaking());

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public void spawnCloud(Player player, Block block, boolean isSneaking){
        AreaEffectCloud effectCloud = (AreaEffectCloud) player.getWorld().spawnEntity(block.getLocation().add(0.5, 1, 0.5), EntityType.AREA_EFFECT_CLOUD);

        effectCloud.setDuration(160);
        effectCloud.setRadius(2.85F);
        if(!isSneaking) {
            effectCloud.setParticle(Particle.ELECTRIC_SPARK);
            effectCloud.setCustomName("FN_FORCE");
            player.sendMessage(ChatColor.GREEN + "You spawned a cloud effect with forward force");
        } else{
            effectCloud.setParticle(Particle.END_ROD);
            effectCloud.setCustomName("FN_BACKWARD_FORCE");
            player.sendMessage(ChatColor.RED  + "You spawned a cloud effect with backward force");
        }
        effectCloud.setCustomNameVisible(false);
        effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 0, 0, false, false, false), true);
    }

    public static void setup(){
        new StaffOfForce(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_FORCE, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 24), new ItemStack(Material.FEATHER, 12), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 24),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3), new ItemStack(Material.BLAZE_ROD),  new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3),
                new ItemStack(Material.BLAZE_POWDER, 12), SlimefunItems.MAGIC_SUGAR, new ItemStack(Material.BLAZE_POWDER, 12)})
                .register(plugin);
    }
}