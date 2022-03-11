package ne.fnfal113.fnamplifications.materialgenerators.implementations;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;

public class CustomMaterialGenerator extends SlimefunItem implements InventoryBlock {

    private static final Map<BlockPosition, Integer> generatorProgress = new HashMap<>();

    private static final CustomItemStack notGenerating = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&cNot Generating",
            "&ePlace a chest above first!"
    );

    private ItemStack item;
    private String material;

    @ParametersAreNonnullByDefault
    public CustomMaterialGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int tickRate) {
        super(itemGroup, item, recipeType, recipe);

        createPreset(this, getInventoryTitle(), blockMenuPreset -> {
            for (int i = 0; i < 9; i++) {
                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            blockMenuPreset.addItem(4, notGenerating);
        });

        try {
            FNAmplifications.getInstance().getConfigManager().setIntegerValues(item.getItemId(), tickRate, "material-gen-tickrate");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        BlockMenu invMenu = BlockStorage.getInventory(b);
        Block targetBlock = b.getRelative(BlockFace.UP);

        if(invMenu.toInventory() != null && invMenu.hasViewer()) {
            invMenu.replaceExistingItem(4, notGenerating);
        }
        if (targetBlock.getType() == Material.CHEST) {
            BlockState state = PaperLib.getBlockState(targetBlock, false).getState();
            if (state instanceof InventoryHolder) {
                Inventory inv = ((InventoryHolder) state).getInventory();
                if (inv.firstEmpty() != -1) {
                    final BlockPosition pos = new BlockPosition(b);
                    int progress = generatorProgress.getOrDefault(pos, 0);

                    if (invMenu.toInventory() != null && invMenu.hasViewer()) {
                        invMenu.replaceExistingItem(4, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aGenerating Material",
                                "", "&bMaterial: " + this.material, "&bRate: " + "" + ChatColor.GREEN + FNAmplifications.getInstance().getConfigManager().getValueById(this.getId()) + " &aticks", "", "&2Progress: " + progress
                                + "/"+ FNAmplifications.getInstance().getConfigManager().getValueById(this.getId())));
                    }

                    if (progress >= FNAmplifications.getInstance().getConfigManager().getValueById(this.getId())) {
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

    @Nonnull
    public String getInventoryTitle() {
        return getItemName();
    }

    public final CustomMaterialGenerator setItem(@Nonnull Material material) {
        this.item = new ItemStack(material);
        return this;
    }

    public final CustomMaterialGenerator getMaterialName(String materialName) {
        this.material = materialName;
        return this;
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }
}
