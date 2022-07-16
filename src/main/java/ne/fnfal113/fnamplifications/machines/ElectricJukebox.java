package ne.fnfal113.fnamplifications.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import ne.fnfal113.fnamplifications.machines.implementation.DiscDurationsEnum;
import ne.fnfal113.fnamplifications.machines.implementation.JukeBox;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ElectricJukebox extends JukeBox {

    private final Map<Location, Integer> durationMap = new HashMap<>();

    @ParametersAreNonnullByDefault
    public ElectricJukebox(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int lowerBound, int upperBound, int defaultSlot, int secondLowerBound, int secondUpperBound, boolean hasSecondBound) {
        super(itemGroup, item, recipeType, recipe, lowerBound, upperBound, defaultSlot, secondLowerBound, secondUpperBound, hasSecondBound);

        addItemHandler(
                new BlockTicker() {
                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }

                    @Override
                    public void tick(Block block, SlimefunItem slimefunItem, Config data) {
                        onTick(block);
                    }
                },
                new BlockPlaceHandler(false) {
                    @Override
                    public void onPlayerPlace(@Nonnull BlockPlaceEvent event) {
                        BlockStorage.addBlockInfo(event.getBlock(), "owner", event.getPlayer().getUniqueId().toString());
                    }
                },
                new BlockBreakHandler(false, false) {
                    @Override
                    public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                        BlockMenu menu = BlockStorage.getInventory(e.getBlock());
                        if(menu != null){
                            int upperBound = getSecondUpperBound() > 0 ? getSecondUpperBound() : getUpperBound();

                            for(int i = getLowerBound(); i < upperBound + 1; i++){
                                if(menu.getItemInSlot(i) != null) {
                                    ItemStack itemStack = menu.getItemInSlot(i);
                                    ItemMeta meta = itemStack.getItemMeta();

                                    if(meta.hasEnchant(Enchantment.BINDING_CURSE)) { // un-enchant any disc that is being played/selected
                                        meta.removeEnchant(Enchantment.BINDING_CURSE);
                                        itemStack.setItemMeta(meta);
                                    }

                                    if(itemStack.getType() != Material.PINK_STAINED_GLASS_PANE && itemStack.getType() != Material.GRAY_STAINED_GLASS_PANE) {
                                        menu.dropItems(menu.getLocation(), i);
                                    }
                                }
                            } //  bound loop
                        } // menu null check
                    } // overridden method
                } // anonymous
        );

        createPreset(this, getItemName(), blockMenuPreset -> { // create inventory gui preset, use the current consumer input
            addGuiItems(blockMenuPreset);
            blockMenuPreset.addItem(5, NO_POWER);
        });
    }

    // accumulate the gui slots with empty click slots, skip the slots between the lower bound and upper bound
    public void addGuiItems(BlockMenuPreset menuPreset){
        for (int i = 0; i < 54; i ++) {
            if(i < getLowerBound()) {
                menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            if(getSecondLowerBound() > 0 && getSecondUpperBound() > 0){
                if(i < getSecondLowerBound() && i > getUpperBound()) {
                    menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
                if(i > getSecondUpperBound()){
                    menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
            } else if (i > getUpperBound()) {
                menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
        }
    }

    public boolean isJukeboxPowered(Block b, Player p){
        if(getCharge(b.getLocation()) > 0){
            return true;
        }

        p.sendMessage(Utils.colorTranslator("&eJukebox is not powered, needs power supply."));
        return false;
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {
            @Override
            public void init() {
              addGuiItems(this);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return ElectricJukebox.this.canUse(player, false)
                        && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[]{0};
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                String isRunning = BlockStorage.getLocationInfo(b.getLocation(), "toggled_On");
                String currentSlot = BlockStorage.getLocationInfo(b.getLocation(), "current_Slot");
                String isPlaying = BlockStorage.getLocationInfo(b.getLocation(), "is_Playing");

                // isRunning
                boolean isOn = false;
                if (isRunning != null) {
                    isOn = Boolean.parseBoolean(isRunning);
                }
                menu.replaceExistingItem(50, isOn ? TOGGLED_ON : TOGGLED_OFF);
                menu.addMenuClickHandler(50, (p, slot, item, action) -> {
                    if(isJukeboxPowered(b, p)) {
                        toggleOnOrOff(menu);
                    }

                    return false;
                });

                // currentSlot
                int discSlot = getDefaultSlot();
                if (currentSlot != null) {
                    discSlot = Integer.parseInt(currentSlot);
                }
                menu.replaceExistingItem(0, PREVIOUS);
                menu.addMenuClickHandler(0, (p, slot, item, action) -> {
                    if(isJukeboxPowered(b, p)) {
                        previousDiscButton(p, menu, false);
                    }

                    return false;
                });

                menu.replaceExistingItem(8, NEXT);
                menu.addMenuClickHandler(8, (p, slot, item, action) -> {
                    if(isJukeboxPowered(b, p)) {
                        nextDiscButton(p, menu);
                    }

                    return false;
                });

                // isPlaying
                boolean playing = false;
                if(isPlaying != null){
                    playing = Boolean.parseBoolean(isPlaying);
                }

                menu.replaceExistingItem(4, playing ? PLAY : STOP);
                menu.addMenuClickHandler(4, (p, slot, item, action) -> {
                    if(isJukeboxPowered(b, p)) {
                        playOrStopButton(p, menu);
                    }

                    return false;
                });

                JukeboxCache cache = new JukeboxCache(isOn, playing, discSlot, null);
                getCACHE_MAP().put(menu.getLocation(), cache);
            }
        };
    }

    public void onTick(@Nonnull Block b) {
        BlockMenu invMenu = BlockStorage.getInventory(b);
        if(!(b.getBlockData() instanceof org.bukkit.block.data.type.Jukebox)){
            return;
        }

        Jukebox jukebox = (Jukebox) invMenu.getBlock().getState();
        JukeboxCache cache = getCACHE_MAP().get(b.getLocation());

        if (getCharge(b.getLocation()) > 0) { // is jukebox powered
            if(invMenu.hasViewer()) {
                invMenu.replaceExistingItem(48, NOT_RUNNING);
            }

            if (cache.isOn) { // is jukebox turned on
                if(cache.isPlaying) {
                    int currentTime = durationMap.getOrDefault(b.getLocation(), 0);

                    if(isNewMusic()){
                        durationMap.put(b.getLocation(), 0);
                        setNewMusic(false);
                        return;
                    } // new music being played

                    if(invMenu.hasViewer()){
                        invMenu.replaceExistingItem(48, OPERATING);
                        if(jukebox.getPlaying() != Material.AIR) {
                            invMenu.replaceExistingItem(49, new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
                                    "&d&lPlaying: " + jukebox.getPlaying().toString().replace("_", " "),
                                    "&eDuration : " + durationMap.get(b.getLocation()) + "/" + (DiscDurationsEnum.valueOf(jukebox.getPlaying().toString().toUpperCase()).getDurationInSec() * 2)));
                        }
                    } // has viewer

                    if(durationMap.containsKey(b.getLocation())) {
                        if (durationMap.get(b.getLocation()) >= (DiscDurationsEnum.valueOf(jukebox.getPlaying().toString().toUpperCase()).getDurationInSec() * 2)) {
                            nextDiscButton(null, invMenu);
                            currentTime = 0;
                        } // when disc duration is done, check next slot if there is a disc else stop the jukebox or go back to default slot when upper bound is reached
                        else { // increment current time if music has not reached the duration
                            currentTime++;

                            if(currentTime == 1){ // re-update the jukebox state, for persistent state after server restart (might not work on 1.18 below)
                                jukebox.update(true);
                            }
                        }
                    } // duration map contains

                    durationMap.put(b.getLocation(), currentTime);
                    Objects.requireNonNull(jukebox.getLocation().getWorld()).spawnParticle(Particle.NOTE, b.getLocation().add(0, 0.8, 0), 2);
                } else { // if jukebox is not playing, reset current jukebox duration and change the status of the gui panels
                    durationMap.put(b.getLocation(), 0);

                    if(invMenu.hasViewer()) {
                        invMenu.replaceExistingItem(48, NOT_OPERATING);

                        changeStatus(invMenu);
                    }
                }

            takeCharge(b.getLocation());
            } else if(cache.isPlaying){ // if jukebox is turned off, stop current music disc and change status of gui panels
                stopCurrentSlot(jukebox, invMenu.getLocation(), cache, invMenu);

                durationMap.put(b.getLocation(), 0);

                if(invMenu.hasViewer()) {
                    changeStatus(invMenu);
                }
            }
        } else if(cache.isOn){ // stop the jukebox when there is not enough power
            if(cache.isPlaying) {
                stopCurrentSlot(jukebox, invMenu.getLocation(), cache, invMenu);
            }

            invMenu.replaceExistingItem(48, ChestMenuUtils.getBackground());

            changeStatus(invMenu);

            toggleOnOrOff(invMenu);
        }

    }

}