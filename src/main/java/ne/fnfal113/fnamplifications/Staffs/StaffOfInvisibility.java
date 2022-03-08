package ne.fnfal113.fnamplifications.Staffs;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.*;

public class StaffOfInvisibility extends AbstractStaff {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

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
            online.hidePlayer((Plugin) plugin, player);
        }
        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING,115,1, false, false);
        PotionEffect effect2 = new PotionEffect(PotionEffectType.INVISIBILITY,115,1, false, false);
        player.addPotionEffect(effect);
        player.addPotionEffect(effect2);
        player.sendMessage(ChatColor.GREEN + "You are now invisible to all players!");

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);
        Bukkit.getScheduler().runTaskLater((Plugin) plugin, () -> {
            if(!player.isOnline()){
                return;
            }
            for(Player online : Bukkit.getOnlinePlayers()){
                online.showPlayer((Plugin) plugin, player);
            }
        },120L);

    }

    public static void setup(){
        new StaffOfInvisibility(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_INVI, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2), SlimefunItems.MAGICAL_GLASS, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 6),
                SlimefunItems.AIR_RUNE, new ItemStack(Material.BLAZE_ROD), SlimefunItems.AIR_RUNE,
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 6), SlimefunItems.MAGIC_SUGAR,  new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2)})
                .register(plugin);
    }
}
