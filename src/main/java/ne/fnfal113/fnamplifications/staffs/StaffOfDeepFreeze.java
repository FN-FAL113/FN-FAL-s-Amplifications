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

public class StaffOfDeepFreeze extends AbstractStaff {

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfDeepFreeze(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "deepfreezestaff");
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
        lore.add(1, ChatColor.LIGHT_PURPLE + "Spawn an area of effect cloud where");
        lore.add(2, ChatColor.LIGHT_PURPLE + "entities are being slowed by the freezing");
        lore.add(3, ChatColor.LIGHT_PURPLE + "cold if inside the radius for 8 seconds");

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
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to cast deep-freeze there!");
            return;
        }

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

        AreaEffectCloud effectCloud = (AreaEffectCloud) player.getWorld().spawnEntity(block.getLocation().add(0.5, 1, 0.5) , EntityType.AREA_EFFECT_CLOUD);
        effectCloud.setParticle(Particle.SNOWFLAKE);
        effectCloud.setDuration(160);
        effectCloud.setRadius(2.85F);
        effectCloud.setCustomName("FN_DEEP_FREEZE");
        effectCloud.setCustomNameVisible(false);
        effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 0, 0, false, false, false), true);

        // Commented out in favor of AreaCloudEffectApply Event
        /*World world = player.getWorld();
        AtomicInteger i = new AtomicInteger(8);
        taskID = Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), () -> {
            for (Entity e : world.getNearbyEntities(effectCloud.getLocation(), 2.85F, 2, 2.85F)) {

                if (e instanceof LivingEntity) {
                    if (e.getLocation().distance(effectCloud.getLocation()) <= 2.85F) {
                        e.setFreezeTicks(210);
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
}
