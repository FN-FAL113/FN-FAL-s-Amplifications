package ne.fnfal113.fnamplifications.materialgenerators.implementations;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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

    private static final Map<BlockPosition, Integer> generatorStatus = new HashMap<>();

    private static final CustomItemStack NOT_GENERATING = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&cNot Generating",
            "&ePlace a chest above first!"
    );

    private static final CustomItemStack NOT_GENERATING_FULL = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&cNot Generating",
            "&eChest inventory full!"
    );

    private static final CustomItemStack CONDITION = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&cCurrent Condition: ",
            "&ePlace a chest above first!"
    );

    private static final CustomItemStack CONDITION_BROKEN = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&cCurrent Condition: ",
            "&eGenerator is broken! please repair!",
            "&eDestroy the block and craft a new one"
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
            blockMenuPreset.addItem(4, NOT_GENERATING);
            blockMenuPreset.addItem(0, CONDITION);
        });

        try {
            FNAmplifications.getInstance().getConfigManager().setIntegerValues(item.getItemId(), "tickrate" , tickRate, "material-gen-tickrate");
            Utils.setLore(this.getItem(), this.getId(), "tickrate", "ticks", "&6", " ticks");
        } catch (IOException e) {
            e.printStackTrace();
        }

        addItemHandler(
                new BlockPlaceHandler(false) {
                    @Override
                    public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                        BlockStorage.addBlockInfo(e.getBlock().getLocation(), "generator_status",
                                "100");
                        generatorStatus.put(new BlockPosition(e.getBlock().getLocation()), 100);
                    }
                },
                new BlockBreakHandler(false, false) {
                    @Override
                    @SuppressWarnings("ConstantConditions")
                    public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                        if(BlockStorage.getLocationInfo(e.getBlock().getLocation(), "generator_status") != null) {
                            e.setDropItems(false);
                            e.setCancelled(true);
                            e.getBlock().setType(Material.AIR);
                            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                                    SlimefunItem.getById(BlockStorage.getLocationInfo(e.getBlock().getLocation(), "id") + "_BROKEN").getItem());
                            generatorStatus.remove(new BlockPosition(e.getBlock().getLocation()));
                            BlockStorage.clearBlockInfo(e.getBlock().getLocation());
                        } else {
                            e.getPlayer().sendMessage(Utils.
                                    colorTranslator("&cSlimefun block data is missing! Please ask for replacement from your server admin"));
                        }
                    }
                });
    }

    @Override
    public void preRegister() {
        addItemHandler(
                new BlockTicker() {
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
        final BlockPosition pos = new BlockPosition(b);

        if(!generatorStatus.containsKey(pos)) {
            if (BlockStorage.getLocationInfo(b.getLocation(), "generator_status") != null) {
                generatorStatus.put(pos, Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "generator_status")));
            } else {
                BlockStorage.addBlockInfo(b.getLocation(), "generator_status", "100");
                generatorStatus.put(pos, 100);
            }
        }

        if (targetBlock.getType() == Material.CHEST) {
            BlockState state = PaperLib.getBlockState(targetBlock, false).getState();
            if (state instanceof InventoryHolder && generatorStatus.get(pos) > 0) {
                Inventory inv = ((InventoryHolder) state).getInventory();
                if (inv.firstEmpty() != -1) {
                    int progress = generatorProgress.getOrDefault(pos, 0);
                    int generatorCondition = generatorStatus.get(pos);

                    if (invMenu.toInventory() != null && invMenu.hasViewer()) {
                        invMenu.replaceExistingItem(4, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aGenerating Material",
                                "", "&bMaterial: " + this.material,
                                "&bRate: " + "" + ChatColor.GREEN + FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "tickrate") + " &aticks", "",
                                "&2Progress: " + progress + "/"+ FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "tickrate")));

                        if(generatorCondition > 0){
                            if(generatorCondition > 75 && generatorCondition <= 100) {
                                invMenu.replaceExistingItem(0, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aCurrent Condition:",
                                    "", "&eIn best condition" + " (" + generatorCondition + "%)"));
                            } else if(generatorCondition > 50 && generatorCondition < 75){
                                invMenu.replaceExistingItem(0, new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "&aCurrent Condition:",
                                        "", "&eIn good condition" + " (" + generatorCondition + "%)"));
                            } else if(generatorCondition > 25 && generatorCondition < 50){
                                invMenu.replaceExistingItem(0, new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&aCurrent Condition:",
                                        "", "&eIn bad condition" + " (" + generatorCondition + "%)"));
                            } else if(generatorCondition < 25){
                                invMenu.replaceExistingItem(0, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&aCurrent Condition:",
                                        "", "&eIn worst condition" + " (" + generatorCondition + "%)"));
                            }
                        } else {
                            invMenu.replaceExistingItem(0, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&aCurrent Condition:",
                                    "", "&eBroken generator" + " (" + generatorStatus.get(pos) + "%)"));
                        }
                    }

                    if (progress >= FNAmplifications.getInstance().getConfigManager().getValueById(this.getId(), "tickrate")) {
                        progress = 0;
                        if(ThreadLocalRandom.current().nextInt(100) < 32 && generatorCondition > 0){
                            BlockStorage.addBlockInfo(b.getLocation(), "generator_status", String.valueOf(generatorCondition - 1));
                            generatorStatus.put(pos, generatorCondition - 1);
                        }
                        inv.addItem(this.item);
                    } else {
                        progress++;
                    }
                    generatorProgress.put(pos, progress);
                } else if(invMenu.toInventory() != null && invMenu.hasViewer()){
                    invMenu.replaceExistingItem(4, NOT_GENERATING_FULL);
                }
            } else if(invMenu.toInventory() != null && invMenu.hasViewer()){
                invMenu.replaceExistingItem(4, ChestMenuUtils.getBackground());
                invMenu.replaceExistingItem(0, CONDITION_BROKEN);
            }
        } else if(invMenu.toInventory() != null && invMenu.hasViewer()) {
            invMenu.replaceExistingItem(4, NOT_GENERATING);
            invMenu.replaceExistingItem(0, CONDITION);
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
