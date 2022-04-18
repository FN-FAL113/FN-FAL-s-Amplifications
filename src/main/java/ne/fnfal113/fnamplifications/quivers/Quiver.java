package ne.fnfal113.fnamplifications.quivers;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.quivers.abstracts.AbstractQuiver;
import ne.fnfal113.fnamplifications.quivers.implementations.MainQuiver;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
public class Quiver extends AbstractQuiver {

    private final int quiverSize;

    @Getter
    private final ItemStack arrowType;

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;
    private final NamespacedKey defaultUsageKey3;

    private final MainQuiver mainQuiver;

    public Quiver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int quiverSize, ItemStack arrowType) {
        super(itemGroup, item, recipeType, recipe);

        this.quiverSize = quiverSize;
        this.arrowType = arrowType;
        this.defaultUsageKey = Keys.ARROWS_KEY;
        this.defaultUsageKey2 = Keys.ARROWS_ID_KEY;
        this.defaultUsageKey3 = Keys.QUIVER_STATE_KEY;
        this.mainQuiver = new MainQuiver(getStorageKey(), getStorageKey2(), getStorageKey3(), getQuiverSize(), getArrowType(), item);
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
    public void onArrowDeposit(Player player, ItemStack item) {
        mainQuiver.depositArrows(item, item.getItemMeta(), item.getItemMeta().getPersistentDataContainer(), player);
    }

    @Override
    public void onArrowWithdraw(PlayerInteractEvent event, ItemStack item){
        mainQuiver.withdrawArrows(item, item.getItemMeta(), event.getPlayer(), item.getItemMeta().getPersistentDataContainer());
    }

    @Override
    public void onChangeState(ItemStack itemStack){
        mainQuiver.changeState(itemStack, itemStack.getItemMeta(), itemStack.getItemMeta().getPersistentDataContainer());
    }

    @Override
    public void onBowShoot(EntityShootBowEvent event, ItemStack itemStack){
        mainQuiver.bowShoot(event, itemStack,true);
    }
}