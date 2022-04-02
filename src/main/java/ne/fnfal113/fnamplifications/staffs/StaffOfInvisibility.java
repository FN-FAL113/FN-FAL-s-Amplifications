package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.staffs.implementations.MainStaff;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

public class StaffOfInvisibility extends AbstractStaff {

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfInvisibility(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "invistaff");
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

        boolean isInvisible = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17) ?
                player.isInvisible() : player.hasPotionEffect(PotionEffectType.INVISIBILITY);

        if (isInvisible) { // #isInvisible() only supports 1.16 above
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour invisibility is still active!"));
            return;
        }
        ItemStack item = player.getInventory().getItemInMainHand();

        ItemMeta meta = item.getItemMeta();

        mainStaff.updateMeta(item, meta, player);

        for(Player online : Bukkit.getOnlinePlayers()){
            online.hidePlayer(FNAmplifications.getInstance(), player);
        }
        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING,115,1, false, false);
        PotionEffect effect2 = new PotionEffect(PotionEffectType.INVISIBILITY,115,1, false, false);
        player.addPotionEffect(effect);
        player.addPotionEffect(effect2);
        player.sendMessage(ChatColor.GREEN + "You are now invisible to all players!");

        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            if(!player.isOnline()){
                return;
            }
            for(Player online : Bukkit.getOnlinePlayers()){
                online.showPlayer(FNAmplifications.getInstance(), player);
            }
        },120L);

    }
}