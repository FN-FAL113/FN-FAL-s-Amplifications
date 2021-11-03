package ne.fnfal113.fnamplifications.MaterialGenerators.Methods;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

public class CustomMaterialGenerator extends SlimefunItem {

    private static final Map<BlockPosition, Integer> generatorProgress = new HashMap<>();

    private int rate = 2;
    private ItemStack item;

    @ParametersAreNonnullByDefault
    public CustomMaterialGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            @ParametersAreNonnullByDefault
            public void tick(Block b, SlimefunItem sf, Config data) {
                CustomMaterialGenerator.this.tick(b);
            }

            @Override
            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(@Nonnull Block b) {
        Block targetBlock = b.getRelative(BlockFace.UP);
        if (targetBlock.getType() == Material.CHEST) {
            BlockState state = PaperLib.getBlockState(targetBlock, false).getState();
            if (state instanceof InventoryHolder) {
                Inventory inv = ((InventoryHolder) state).getInventory();
                if (inv.firstEmpty() != -1) {
                    final BlockPosition pos = new BlockPosition(b);
                    int progress = generatorProgress.getOrDefault(pos, 0);

                    if (progress >= this.rate) {
                        progress = 0;
                        inv.addItem(this.item);
                    } else {
                        progress++;
                    }
                    generatorProgress.put(pos, progress);
                }
            }
        }
    }

    public final CustomMaterialGenerator setItem(@Nonnull Material material) {
        this.item = new ItemStack(material);
        return this;
    }

    public final CustomMaterialGenerator setRate(int rateTicks) {
        this.rate = Math.max(rateTicks, 2);
        return this;
    }
}
