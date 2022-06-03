package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StaffOfInvisibility extends AbstractStaff {

    public StaffOfInvisibility(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10, Keys.createKey("invistaff"));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();

        boolean isInvisible = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17) ?
                player.isInvisible() : player.hasPotionEffect(PotionEffectType.INVISIBILITY);

        if (isInvisible) { // #isInvisible() only supports 1.16 above
            player.sendMessage(Utils.colorTranslator("&cYour invisibility is still active!"));
            return;
        }
        ItemStack item = player.getInventory().getItemInMainHand();

        ItemMeta meta = item.getItemMeta();

        if (!hasPermissionToCast(meta.getDisplayName(), player, player.getLocation())) {
            return;
        }

        getStaffTask().updateMeta(item, meta, player);

        for(Player online : Bukkit.getOnlinePlayers()){
            online.hidePlayer(FNAmplifications.getInstance(), player);
        }
        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING,115,1, false, false);
        PotionEffect effect2 = new PotionEffect(PotionEffectType.INVISIBILITY,115,1, false, false);
        player.addPotionEffect(effect);
        player.addPotionEffect(effect2);
        player.sendMessage(Utils.colorTranslator("&aYou are now invisible to all players!"));

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