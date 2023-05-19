package ne.fnfal113.fnamplifications.materialgenerators.implementations;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import lombok.Getter;
import lombok.Setter;
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

    @Getter
    private final Map<BlockPosition, Integer> generatorProgress = new HashMap<>();

    @Getter
    private final Map<BlockPosition, Integer> generatorCondition = new HashMap<>();

    @Getter
    private final Map<BlockPosition, FastProduceCache> generatorFastProduce = new HashMap<>();

    @Getter
    private final int sfTickerDelay = Slimefun.getTickerTask().getTickRate();

    @Getter
    private final boolean breakOverTime = FNAmplifications.getInstance().getConfig().getBoolean("Enable-Mat-Gen-Break-Over-Time", true);

    @Getter
    private final boolean dropBrokenVariantOnBreak = FNAmplifications.getInstance().getConfig().getBoolean("Enable-Mat-Gen-Broken-Drop", true);

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
            "&eDestroy the block and craft a new one or",
            "&eUse repair item to add durability"
    );

    private ItemStack item;
    private String materialName;

    @ParametersAreNonnullByDefault
    public CustomMaterialGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int tickRate) {
        super(itemGroup, item, recipeType, recipe);

        FNAmplifications.getInstance().getConfigManager().initializeConfig(item.getItemId(), "tickrate" , tickRate, "material-gen-tickrate");
        Utils.setLoreByConfigValue(this.getItem(), this.getId(), "tickrate", "ticks", "&6", " ticks", "material-gen-tickrate");

        createPreset(this, getInventoryTitle(), blockMenuPreset -> {
            for (int i = 0; i < 9; i++) {
                blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            
            blockMenuPreset.addItem(4, NOT_GENERATING);
            
            if(breakOverTime) {
                blockMenuPreset.addItem(0, CONDITION);
            }
        });

        addItemHandler(
                new BlockPlaceHandler(false) {
                    @Override
                    public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                        if(breakOverTime) {
                            BlockStorage.addBlockInfo(e.getBlock().getLocation(), "generator_status",
                            "100");
                            getGeneratorCondition().put(new BlockPosition(e.getBlock().getLocation()), 100);
                        }
                    }
                },
                new BlockBreakHandler(false, false) {
                    @Override
                    @SuppressWarnings("ConstantConditions")
                    public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                        if(dropBrokenVariantOnBreak) {
                            e.setDropItems(false);
                            e.setCancelled(true);

                            e.getBlock().setType(Material.AIR);
                            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                                    SlimefunItem.getById(BlockStorage.getLocationInfo(e.getBlock().getLocation(), "id") + "_BROKEN").getItem());    
                        }
                        
                        // remove from cache on block break
                        getGeneratorCondition().remove(new BlockPosition(e.getBlock().getLocation()));
                        getGeneratorProgress().remove(new BlockPosition(e.getBlock().getLocation()));
                        getGeneratorFastProduce().remove(new BlockPosition(e.getBlock().getLocation()));

                        BlockStorage.clearBlockInfo(e.getBlock().getLocation());
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
        BlockPosition pos = new BlockPosition(b);

        // caching condition/status
        if(breakOverTime && getGeneratorCondition().get(pos) == null) {
            if (BlockStorage.getLocationInfo(b.getLocation(), "generator_status") != null) {
                getGeneratorCondition().put(pos, Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "generator_status")));
            } else { // for previously placed blocks before the new mat gen durability update
                BlockStorage.addBlockInfo(b.getLocation(), "generator_status", "100");
                getGeneratorCondition().put(pos, 100);
            }
        }

        // caching fast produce
        if(getGeneratorFastProduce().get(pos) == null) {
            FastProduceCache fastProduceCache = new FastProduceCache(0, 0, 0);
            
            if(BlockStorage.getLocationInfo(b.getLocation(), "fast_produce_multiplier") != null) {
                fastProduceCache.setMultiplier(Double.parseDouble(BlockStorage.getLocationInfo(b.getLocation(), "fast_produce_multiplier")));
                fastProduceCache.setCurrentLifetime(Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "fast_produce_current_lifetime")));
                // seconds * (mc ticks per second / sf ticker delay)
                fastProduceCache.setMaxLifetime((int) (Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "fast_produce_max_lifetime")) * (20.0 / getSfTickerDelay())));
            }

            getGeneratorFastProduce().put(pos, fastProduceCache);
        }

        if(targetBlock.getType() == Material.CHEST) {
            BlockState state = PaperLib.getBlockState(targetBlock, false).getState();

            if(state instanceof InventoryHolder && (breakOverTime ? getGeneratorCondition().get(pos) > 0 : true)) {
                Inventory inv = ((InventoryHolder) state).getInventory();
                
                if(inv.firstEmpty() != -1) {
                    double fastProduce = getGeneratorFastProduce().get(pos).getMultiplier() != 0 ? getGeneratorFastProduce().get(pos).getMultiplier() : 1;
                    int progress = getGeneratorProgress().getOrDefault(pos, 0);
                    int generatorCondition = getGeneratorCondition().getOrDefault(pos, 0);
                    int tickRate = (int) (FNAmplifications.getInstance().getConfigManager().getCustomConfig("material-gen-tickrate").getInt(this.getId() + "." + "tickrate", 1) / fastProduce);

                    if(invMenu.toInventory() != null && invMenu.hasViewer()) {
                        invMenu.replaceExistingItem(4, new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&aGenerating Material",
                                "", "&bMaterial: " + this.materialName,
                                "&bDefault Rate: " + "" + ChatColor.GREEN + FNAmplifications.getInstance().getConfigManager().getCustomConfig("material-gen-tickrate").getInt(this.getId() + "." + "tickrate") + " &aticks", "",
                                "&2Progress: " + progress + "/" + tickRate, "",
                                getGeneratorFastProduce().get(pos).getMultiplier() != 0 ? "&2Fast Produce Lifetime: " +
                                        getGeneratorFastProduce().get(pos).getCurrentLifetime() + "/" + getGeneratorFastProduce().get(pos).getMaxLifetime() : "&2Fast Produce: &cInactive"
                        ));

                        if(breakOverTime) { // update ui condition info if block breaks overtime
                            handleConditionBlockInterface(invMenu, generatorCondition);
                        } else { // replace ui for condition with empty background
                            invMenu.replaceExistingItem(0, ChestMenuUtils.getBackground());
                        }
                    }

                    // if config tickrate is set to 0 then do 1 tick rate else use config value
                    if(progress >= (tickRate == 0 ? 1 : tickRate)) {
                        progress = 0;
                        
                        // if generator condition is greater than 0, check
                        if(breakOverTime && getGeneratorCondition().get(pos) != 0) {
                            if (ThreadLocalRandom.current().nextInt(100) < 25) {
                                BlockStorage.addBlockInfo(b.getLocation(), "generator_status", String.valueOf(generatorCondition - 1));
                                getGeneratorCondition().put(pos, generatorCondition - 1);
                            }
                        }

                        inv.addItem(this.item);
                    } else {
                        progress++;
                    }

                    // if generator has fast produce upgrade, check lifetime
                    if(getGeneratorFastProduce().get(pos).getMultiplier() != 0) {
                        int currentLifeTime = getGeneratorFastProduce().get(pos).getCurrentLifetime();
                        int maxLifeTime = getGeneratorFastProduce().get(pos).getMaxLifetime();

                        if(currentLifeTime == maxLifeTime) {
                            getGeneratorFastProduce().get(pos).reset();

                            BlockStorage.addBlockInfo(b.getLocation(), "fast_produce_multiplier", "0");
                            BlockStorage.addBlockInfo(b.getLocation(), "fast_produce_current_lifetime", "0");
                            BlockStorage.addBlockInfo(b.getLocation(), "fast_produce_max_lifetime", "0");
                        } else if(currentLifeTime >= 0) {
                            BlockStorage.addBlockInfo(b.getLocation(), "fast_produce_current_lifetime", String.valueOf(currentLifeTime + 1));
                            getGeneratorFastProduce().get(pos).setCurrentLifetime(currentLifeTime + 1);
                        }
                    }

                    getGeneratorProgress().put(pos, progress);
                } else if(invMenu.toInventory() != null && invMenu.hasViewer()) { // if output chest is full
                    invMenu.replaceExistingItem(4, NOT_GENERATING_FULL);
                }

            } else if(invMenu.toInventory() != null && invMenu.hasViewer()) { // if generator condition is 0 and target block is not an inventory holder
                invMenu.replaceExistingItem(4, ChestMenuUtils.getBackground());
                if(breakOverTime) invMenu.replaceExistingItem(0, CONDITION_BROKEN);
            }

        } else if(invMenu.toInventory() != null && invMenu.hasViewer()) { // if target output block is not a chest
            invMenu.replaceExistingItem(4, NOT_GENERATING);
            invMenu.replaceExistingItem(0, CONDITION);
        }
    }

    public void handleConditionBlockInterface(BlockMenu invMenu, int generatorCondition) {
        if(generatorCondition > 0) {
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
                    "", "&eBroken generator (0%)"));
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

    public final CustomMaterialGenerator setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public static class FastProduceCache{
        @Getter
        @Setter
        private double multiplier;

        @Getter
        @Setter
        private int currentLifetime;

        @Getter
        @Setter
        private int maxLifetime;

        public FastProduceCache(double multiplier, int currentLifetime, int maxLifetime) {
            this.multiplier = multiplier;
            this.currentLifetime = currentLifetime;
            this.maxLifetime = maxLifetime;
        }

        public void reset(){
            this.multiplier = 0;
            this.currentLifetime = 0;
            this.maxLifetime = 0;

        }
    }
}
