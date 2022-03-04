package ne.fnfal113.fnamplifications.Quiver;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Quiver.Abstracts.AbstractQuiver;
import ne.fnfal113.fnamplifications.Utils.Keys;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class SpectralQuiver extends AbstractQuiver {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final int quiverSize;

    @Getter
    private final ItemStack arrowType;

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;
    private final NamespacedKey defaultUsageKey3;

    private final MainQuiver mainQuiver;

    public SpectralQuiver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int quiverSize, ItemStack arrowType) {
        super(itemGroup, item, recipeType, recipe);

        this.quiverSize = quiverSize;
        this.arrowType = arrowType;
        this.defaultUsageKey = Keys.SPECTRAL_ARROWS_KEY;
        this.defaultUsageKey2 = Keys.SPECTRAL_ARROWS_ID_KEY;
        this.defaultUsageKey3 = Keys.SPECTRAL_STATE_KEY;
        this.mainQuiver = new MainQuiver(getStorageKey(), getStorageKey2(), getStorageKey3(), getQuiverSize(), getArrowType(), item, defaultLore());
    }

    @Override
    public int getQuiverSize(){
        return quiverSize;
    }

    @Override
    public @Nonnull NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    @Override
    public @Nonnull NamespacedKey getStorageKey2() {
        return defaultUsageKey2;
    }

    @Override
    public @Nonnull NamespacedKey getStorageKey3() {
        return defaultUsageKey3;
    }

    @Override
    public List<String> defaultLore(){
        List<String> lore = new ArrayList<>();
        lore.add(0, "");
        lore.add(1, ChatColor.LIGHT_PURPLE + "Store inside the quiver by");
        lore.add(2, ChatColor.LIGHT_PURPLE + "right clicking spectral arrows or");
        lore.add(3, ChatColor.LIGHT_PURPLE + "shift click quiver to withdraw");
        lore.add(4, "");
        lore.add(5, ChatColor.YELLOW + "Left/Right click to change state");
        lore.add(6, ChatColor.YELLOW + "Size: 192 Spectral Arrows");
        return lore;
    }

    @Override
    public void onRightClick(Player player, ItemStack item) {
        mainQuiver.depositArrows(item, item.getItemMeta(), item.getItemMeta().getPersistentDataContainer(), player);
    }

    @Override
    public void withdraw(PlayerInteractEvent event, ItemStack item){
        mainQuiver.withdrawArrows(item, item.getItemMeta(), event.getPlayer(), item.getItemMeta().getPersistentDataContainer());
    }

    @Override
    public void changeState(ItemStack itemStack){
        mainQuiver.changeState(itemStack, itemStack.getItemMeta(), itemStack.getItemMeta().getPersistentDataContainer());
    }

    @Override
    public void bowShoot(EntityShootBowEvent event, ItemStack itemStack){
        mainQuiver.bowShoot(event, itemStack,false);
    }

    public static void setup() {
        new SpectralQuiver(FNAmpItems.FN_MISC, FNAmpItems.FN_SPECTRAL_QUIVER, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.GOLD_4K, 3), new ItemStack(Material.LEAD, 3), new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 3),
                new ItemStack(Material.STRING, 24), new ItemStack(Material.STICK, 24),  new ItemStack(Material.STRING, 24),
                new SlimefunItemStack(SlimefunItems.GOLD_4K, 3), new ItemStack(Material.LEATHER, 16), new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 3)},
                192, new ItemStack(Material.SPECTRAL_ARROW, 1))
                .register(plugin);
    }
}