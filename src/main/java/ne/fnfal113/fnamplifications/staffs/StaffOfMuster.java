package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.staffs.implementations.MainStaff;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class StaffOfMuster extends AbstractStaff {

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfMuster(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "musterstaff");
        this.mainStaff = new MainStaff(getStorageKey(), this.getId());
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getPlayer().getTargetBlockExact(50);

        if(block == null || item.getType() == Material.AIR){
            return;
        }

        if (!hasPermissionToCast(item.getItemMeta().getDisplayName(), player, block.getLocation())) {
            return;
        }

        int amount = 0;
        for (Entity entity : block.getWorld().getNearbyEntities(block.getLocation(), 50, 50, 50)) {
                if (entity instanceof LivingEntity && !(entity instanceof ArmorStand) && !(entity instanceof Player)
                        && !((LivingEntity) entity).isLeashed() && entity.isOnGround()) {
                    if(Slimefun.getProtectionManager().hasPermission
                            (Bukkit.getOfflinePlayer(player.getUniqueId()),
                                    entity.getLocation(),
                                    Interaction.INTERACT_ENTITY)) {
                    entity.teleport(block.getLocation().clone().add(0.5, 1, 0.5));
                    amount = amount + 1;
                } // permission check
            } // instanceof check
        } // for each

        player.sendMessage(Utils.colorTranslator( "&aMustered " + amount + " &aentities"));

        block.getWorld().playEffect(block.getLocation().clone().add(0.5, 1, 0.5), Effect.ENDER_SIGNAL, 1);

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

    }

}