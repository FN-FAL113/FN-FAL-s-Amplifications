package ne.fnfal113.fnamplifications.Staffs;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.*;

public class StaffOfInvisibility extends SlimefunItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    private final NamespacedKey defaultUsageKey;

    public StaffOfInvisibility(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "invistaff");
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        NamespacedKey key = getStorageKey();

        if(item.getItemMeta() == null){
            return;
        }

        ItemMeta meta = item.getItemMeta();

        updateMeta(item, meta, key, player);

        for(Player online : Bukkit.getOnlinePlayers()){
            online.hidePlayer((Plugin) plugin, player);
            PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING,115,1, false, false);
            PotionEffect effect2 = new PotionEffect(PotionEffectType.INVISIBILITY,115,1, false, false);
            player.addPotionEffect(effect);
            player.addPotionEffect(effect2);
        }
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

    public void updateMeta(ItemStack item, ItemMeta meta, NamespacedKey key, Player player){
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();
        int uses_Left = max_Uses.getOrDefault(key, PersistentDataType.INTEGER, value.staffOfInvisibility());
        int decrement = uses_Left - 1;

        List<String> lore = new ArrayList<>();

        if(decrement > 0) {
            max_Uses.set(key, PersistentDataType.INTEGER, decrement);
            lore.add(0, "");
            lore.add(1, ChatColor.LIGHT_PURPLE + "6 seconds of invisibility");
            lore.add(2, ChatColor.LIGHT_PURPLE + "even your armor and name is hidden");
            lore.add(3, "");
            lore.add(4, ChatColor.YELLOW + "Uses left: " + decrement);
            meta.setLore(lore);
            item.setItemMeta(meta);
        } else {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lInvisibility staff has reached max uses!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1 ,1);
        }

    }


    public static void setup(){
        new StaffOfInvisibility(FNAmpItems.FN_STAFFS, FNAmpItems.FN_STAFF_INVI, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2), SlimefunItems.MAGICAL_GLASS, new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 6),
                SlimefunItems.AIR_RUNE, new ItemStack(Material.BLAZE_ROD), SlimefunItems.AIR_RUNE,
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_3, 6), SlimefunItems.MAGIC_SUGAR,  new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 2)})
                .register(plugin);
    }
}
