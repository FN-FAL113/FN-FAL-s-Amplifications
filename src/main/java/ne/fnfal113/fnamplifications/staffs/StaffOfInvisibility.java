package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.*;

public class StaffOfInvisibility extends AbstractStaff {

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfInvisibility(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "invistaff");
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
        lore.add(1, ChatColor.LIGHT_PURPLE + "6 seconds of invisibility");
        lore.add(2, ChatColor.LIGHT_PURPLE + "even your armor and name are hidden");

        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (player.isInvisible()) {
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

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);
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
