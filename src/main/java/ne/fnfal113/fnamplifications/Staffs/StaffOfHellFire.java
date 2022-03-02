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
import ne.fnfal113.fnamplifications.Staffs.Interface.StaffImpl;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffOfHellFire extends SlimefunItem implements StaffImpl {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfHellFire(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "hellstaff");
        this.mainStaff = new MainStaff(lore(), value.staffOfHellFire(), getStorageKey());
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    @Override
    public List<String> lore(){
        List<String> lore = new ArrayList<>();
        lore.add(0, "");
        lore.add(1, ChatColor.LIGHT_PURPLE + "Spawn an area of effect cloud");
        lore.add(2, ChatColor.LIGHT_PURPLE + "where entities are set on fire");
        lore.add(3, ChatColor.LIGHT_PURPLE + "if inside the radius for 8 seconds");

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
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast hellfire there!");
            return;
        }

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

        AreaEffectCloud effectCloud = (AreaEffectCloud) player.getWorld().spawnEntity(block.getLocation().add(0.5, 1, 0.5) , EntityType.AREA_EFFECT_CLOUD);
        effectCloud.setParticle(Particle.SMOKE_NORMAL);
        effectCloud.setDuration(160);
        effectCloud.setRadius(2.85F);
        effectCloud.setCustomName("FN_HELL_FIRE");
        effectCloud.setCustomNameVisible(false);
        effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 0 , 0, false, false, false), true);

        // Commented out in favor of AreaCloudEffectApply Event
        /*World world = player.getWorld();
        AtomicInteger i = new AtomicInteger(8);
        taskID = Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), () -> {
            for (Entity e : world.getNearbyEntities(effectCloud.getLocation(), 2.85F, 2, 2.85F)) {

                if (e instanceof LivingEntity) {
                    if (e.getLocation().distance(effectCloud.getLocation()) <= 2.85F) {
                        e.setFireTicks(20);
                    }

                }
            }

            if (i.get() == 0) {
                taskID.cancel();
            }
            i.getAndDecrement();

        }, 0, 20L);*/

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public static void setup(){
        new StaffOfHellFire(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_HELLFIRE, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8), new ItemStack(Material.LINGERING_POTION), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 8),
                SlimefunItems.MAGICAL_BOOK_COVER, new ItemStack(Material.BLAZE_ROD), SlimefunItems.MAGICAL_BOOK_COVER,
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 3), SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3)})
                .register(plugin);
    }
}