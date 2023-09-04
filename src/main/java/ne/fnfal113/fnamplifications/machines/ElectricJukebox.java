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

import lombok.Getter;
import lombok.Setter;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.machines.abstracts.AbstractJukeBox;
import ne.fnfal113.fnamplifications.machines.implementation.DiscDurationsEnum;
import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ElectricJukebox extends AbstractJukeBox {

    @Getter
    @Setter
    private boolean newMusic = false;

    @Getter
    private final int lowerBound;
    
    @Getter
    private final int upperBound;
    
    @Getter
    private final int secondLowerBound;
    
    @Getter
    private final int secondUpperBound;

    @Getter
    private final boolean secondBound;

    private final Map<Location, Integer> durationMap = new HashMap<>();

    private final Map<Location, JukeboxCache> cacheMap = new HashMap<>();

    @ParametersAreNonnullByDefault
    public ElectricJukebox(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, 
    int lowerBound, int upperBound, int secondLowerBound, int secondUpperBound, boolean hasSecondBound) {
        super(itemGroup, item, recipeType, recipe);

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.secondLowerBound = secondLowerBound;
        this.secondUpperBound = secondUpperBound;
        this.secondBound = hasSecondBound;

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
                        
                        if(menu != null) {
                            // get upper bound conditionally if there is second bound ot not
                            int upperBound = isSecondBound() ? getSecondUpperBound() : getUpperBound();

                            for(int i = getLowerBound(); i <= upperBound; i++){
                                if(menu.getItemInSlot(i) != null) {
                                    ItemStack itemStack = menu.getItemInSlot(i);
                                    ItemMeta meta = itemStack.getItemMeta();

                                     // unenchant playing or selected disc
                                    if(meta.hasEnchant(Enchantment.BINDING_CURSE)) {
                                        meta.removeEnchant(Enchantment.BINDING_CURSE);
                                        itemStack.setItemMeta(meta);
                                    }

                                    if(itemStack.getType() != Material.PINK_STAINED_GLASS_PANE && itemStack.getType() != Material.GRAY_STAINED_GLASS_PANE) {
                                        menu.dropItems(menu.getLocation(), i);
                                    }
                                }
                            }
                        } 
                    }
                }
        );

        createPreset(this, getItemName(), blockMenuPreset -> { // create inventory gui preset, use the current consumer input
            addGuiItems(blockMenuPreset);
            
            blockMenuPreset.addItem(5, NO_POWER);
        });
    }

    // accumulate the gui slots with empty click handlers, skip the slots between lower bound and upper bound
    public void addGuiItems(BlockMenuPreset menuPreset) {
        for (int i = 0; i < 54; i ++) {
            if(i < getLowerBound()) {
                menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }

            // if jukebox has secondary lower and upper bound
            if(isSecondBound()) {
                // in between the upper bound and second lower bound
                if(i > getUpperBound() && i < getSecondLowerBound()) {
                    menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
                
                 // above the second uppder bond
                if(i > getSecondUpperBound()) {
                    menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
            } else if (i > getUpperBound()) {
                // above upper bound
                menuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
        }
    }

    public boolean isJukeboxPowered(Block b, Player p) {
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
                int discSlot = getLowerBound();
                if (currentSlot != null) {
                    discSlot = Integer.parseInt(currentSlot);
                }
                menu.replaceExistingItem(0, PREVIOUS);
                menu.addMenuClickHandler(0, (p, slot, item, action) -> {
                    if(isJukeboxPowered(b, p)) {
                        goToPreviousSlot(menu, p);
                    }

                    return false;
                });

                menu.replaceExistingItem(8, NEXT);
                menu.addMenuClickHandler(8, (p, slot, item, action) -> {
                    if(isJukeboxPowered(b, p)) {
                        goToNextSlot(menu, p);
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
                        playOrStopJukebox(menu, p);
                    }

                    return false;
                });

                /* BlockMenuPreset#reload() creates a new instance based from this BlockMenuPreset, 
                do not override current cache map else cached itemstack is overridden with null on every menu reload */
                if(!cacheMap.containsKey(menu.getLocation())) {
                    cacheMap.put(menu.getLocation(), new JukeboxCache(isOn, playing, discSlot, null));
                }
            }
        };
    }

    public void onTick(@Nonnull Block b) {
        BlockMenu menu = BlockStorage.getInventory(b);
        if(!(b.getBlockData() instanceof org.bukkit.block.data.type.Jukebox)) {
            return;
        }

        Jukebox jukebox = (Jukebox) menu.getBlock().getState();
        JukeboxCache cache = cacheMap.get(b.getLocation());

        // jukebox not powered
        if(getCharge(b.getLocation()) <= 0) { 
            // stop the jukebox when there is not enough power
            if(cache.isOn){ 
                if(cache.isPlaying) {
                    unselectAndStopPlayingSlot(menu, jukebox);
                }

                menu.replaceExistingItem(48, ChestMenuUtils.getBackground());

                changeStatus(menu);

                toggleOnOrOff(menu);
            }

            return;
        }

        if(menu.hasViewer()) {
            menu.replaceExistingItem(48, NOT_RUNNING);
        }

        // jukebox is running/turned on
        if(cache.isOn) { 
            // jukebox is playing
            if(cache.isPlaying) {
                int currentTime = durationMap.getOrDefault(b.getLocation(), 0);

                // jukebox has viewer
                if(menu.hasViewer()) {
                    menu.replaceExistingItem(48, OPERATING);

                    if(jukebox.getPlaying() != Material.AIR) {
                        menu.replaceExistingItem(49, new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
                            "&d&lPlaying: " + jukebox.getPlaying().toString().replace("_", " "),
                            "&eDuration : " + 
                            durationMap.get(b.getLocation()) + "/" + 
                            (DiscDurationsEnum.valueOf(jukebox.getPlaying().toString().toUpperCase()).getDurationInSec() * 2)));
                    }
                } 

                // when disc duration has elapsed, check next slot if there is a disc else stop the jukebox or go back to default slot when upper bound is reached
                if(durationMap.containsKey(b.getLocation())) {
                    if (durationMap.get(b.getLocation()) >= (DiscDurationsEnum.valueOf(jukebox.getPlaying().toString().toUpperCase()).getDurationInSec() * 2)) {
                        goToNextSlot(menu, null);
                        
                        currentTime = 0;
                    } 
                    else { 
                        // increment current time if music has not reached the duration
                        currentTime++;

                        // re-update the jukebox state, for persistent state after server restart (might not work on 1.18 below)
                        if(currentTime == 1) { 
                            jukebox.update(true);
                        }
                    }
                } // duration map contains

                durationMap.put(b.getLocation(), currentTime);

                Objects.requireNonNull(jukebox.getLocation().getWorld()).spawnParticle(Particle.NOTE, b.getLocation().add(0, 0.8, 0), 2);
            } else { 
                // jukebox is not playing, reset current jukebox duration and change the status of the gui panels
                durationMap.put(b.getLocation(), 0);

                if(menu.hasViewer()) {
                    menu.replaceExistingItem(48, NOT_OPERATING);

                    changeStatus(menu);
                }
            }

            takeCharge(b.getLocation());
        } else if(cache.isPlaying) { 
            // if jukebox is turned off while playing a music disc, stop and change status of gui panels
            unselectAndStopPlayingSlot(menu, jukebox);

            durationMap.put(b.getLocation(), 0);

            if(menu.hasViewer()) {
                changeStatus(menu);
            }
        }
    }

    @Override
    public void changeStatus(BlockMenu invMenu){
        invMenu.replaceExistingItem(49, new CustomItemStack(Material.MAGENTA_STAINED_GLASS_PANE,
                "&dNo music disc is being played",
                "&ePlace a music disc then click",
                "&eplay button or left/right arrows"));
    }

    @Override
    public void toggleOnOrOff(BlockMenu menu) {
        Location location = menu.getLocation();
        JukeboxCache cache = cacheMap.get(location);

        cache.isOn = !cache.isOn;
        cacheMap.put(location, cache);

        BlockStorage.addBlockInfo(location, "toggled_On", String.valueOf(cache.isOn));

        menu.replaceExistingItem(50, cache.isOn ? TOGGLED_ON : TOGGLED_OFF);
    }

    @Override
    public void goToPreviousSlot(BlockMenu menu, @Nullable Player player){
        Location location = menu.getLocation();
        JukeboxCache cache = cacheMap.get(location);

        Jukebox jukebox = (Jukebox) menu.getBlock().getState();

        // is jukebox toggle on
        if(!cache.isOn && player != null) { 
            player.sendMessage(Utils.colorTranslator("&eJukebox is turned off, please turn it on"));

            return;
        }

        validateSlotChange(menu, jukebox, -1);
    }

    @Override
    public void goToNextSlot(BlockMenu menu, @Nullable Player player){
        Location location = menu.getLocation();
        JukeboxCache cache = cacheMap.get(location);

        Jukebox jukebox = (Jukebox) menu.getBlock().getState();

        if(!cache.isOn && player != null) {
            player.sendMessage(Utils.colorTranslator("&eJukebox is turned off, please turn it on"));

            return;
        }
            
        validateSlotChange(menu, jukebox, 1);
    }

    @Override
    public void playOrStopJukebox(BlockMenu menu, Player player) {
        Location location = menu.getLocation();
        JukeboxCache cache = cacheMap.get(location);

        Jukebox jukebox = (Jukebox) menu.getBlock().getState();

        if(!cache.isOn) { 
            player.sendMessage(Utils.colorTranslator("&eJukebox is turned off, please turn it on"));

            return;
        }

        // stop the current song being played
        if(cache.isPlaying) { 
            unselectAndStopPlayingSlot(menu, jukebox);

            return;
        } 

        // play the current disc in slot, will use default slot on first jukebox use
        if(slotContainsMusicDisc(menu, 0)) {
            playSlot(menu, jukebox, 0);

            return; 
        } 

        player.sendMessage(Utils.colorTranslator("&dCurrent slot has no music disc"));
    }

    @Override
    public void playSlot(BlockMenu menu, Jukebox jukebox, int arithmetic) {
        JukeboxCache cache = cacheMap.get(menu.getLocation());

        // if arithmetic is set to 0 then it is probably switching to the lower bound slot
        cache.currentSlot = cache.currentSlot + arithmetic;
        cache.isPlaying = true;
        cache.itemStack = menu.getItemInSlot(cache.currentSlot);

        menu.replaceExistingItem(4, PLAY);

        BlockStorage.addBlockInfo(menu.getLocation(), "is_Playing", String.valueOf(true));
        BlockStorage.addBlockInfo(menu.getLocation(), "current_Slot", String.valueOf(cache.currentSlot));

        // select disc by adding an indicator (enchant)
        selectDisc(menu);

        jukebox.setPlaying(cache.itemStack.getType());
        jukebox.update(true);
        
        // reset jukebox duration to 0 by updating duration cache map
        if(cache.isPlaying) {
            durationMap.put(menu.getLocation(), 0);
        }

        cacheMap.put(menu.getLocation(), cache);
    }

    @Override
    public void unselectAndStopPlayingSlot(BlockMenu menu, Jukebox jukebox){
        JukeboxCache cache = cacheMap.get(menu.getLocation());

        // unselect disc by removing indicator (enchant)
        unselectDisc(menu);

        cache.isPlaying = false;
        cache.itemStack = null;

        menu.replaceExistingItem(4, STOP);

        BlockStorage.addBlockInfo(menu.getLocation(), "is_Playing", String.valueOf(false));

        jukebox.setRecord(null);
        jukebox.update(true);

        cacheMap.put(menu.getLocation(), cache);
    }

    @Override
    public void validateSlotChange(BlockMenu menu, Jukebox jukebox, int arithmetic) {
        JukeboxCache cache = cacheMap.get(menu.getLocation());

        if(cache.currentSlot + (arithmetic) == (getLowerBound() - 1)) {
            // prevent navigating slots behind lower bound slot
            return;
        }

        if(cache.itemStack != null && cache.isPlaying) {
            // if there is a selected disc playing, remove disc indicator (enchant)
            unselectAndStopPlayingSlot(menu, jukebox);
        } else {
            // if slot has no disc, remove panel indicator
            unselectCurrentSlot(menu);
        }

        if(isSecondBound()) { 
            // jukebox has second upper and lower bound, second row of disc slots
            
            // go back to lower bound slot if second upper bound limit reached
            // the difference determines the slots to skip to reach lower bound slot
            if(cache.currentSlot + (arithmetic) == (getSecondUpperBound() + 1)) {
                changeSlot(menu, jukebox, getLowerBound() - getSecondUpperBound());
                
                return;
            }
            
            // next slot is between the upper bound and second lower bound, must skip empty click slots
            // the difference determines the slots to skip to reach upper or second lower bound slot
            if(cache.currentSlot + (arithmetic) < getSecondLowerBound() && cache.currentSlot + (arithmetic) > getUpperBound()){
                changeSlot(menu, jukebox, arithmetic == 1 ? getSecondLowerBound() - getUpperBound() : getUpperBound() - getSecondLowerBound());
            
                return;
            }
        } 
        
        if(cache.currentSlot + (arithmetic) == (getUpperBound() + 1)) {
            // go back to lower bound slot if upper bound limit reached
            // the difference determines the slots to skip to reach lower bound slot
            changeSlot(menu, jukebox, getLowerBound() - getUpperBound());
            
            return;
        }
        
        changeSlot(menu, jukebox, arithmetic);
    }

    @Override
    public void changeSlot(BlockMenu menu, Jukebox jukebox, int arithmetic){
        JukeboxCache cache = cacheMap.get(menu.getLocation());
        
        // if next slot has a music disc play it instead
        if(slotContainsMusicDisc(menu, arithmetic)) {
            playSlot(menu, jukebox, arithmetic);

            return;
        } 

        cache.currentSlot = cache.currentSlot + arithmetic;

        BlockStorage.addBlockInfo(menu.getLocation(), "current_Slot", String.valueOf(cache.currentSlot));

        menu.replaceExistingItem(cache.currentSlot, 
            new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
                "&eNo music disc in current slot",
                "&eplease change the slot"
            )
        );
        menu.reload();

        cacheMap.put(menu.getLocation(), cache);
    }

    @Override
    public void unselectCurrentSlot(BlockMenu menu){
        JukeboxCache cache = cacheMap.get(menu.getLocation());

        if(menu.getItemInSlot(cache.currentSlot) != null && menu.getItemInSlot(cache.currentSlot).getType() == Material.PINK_STAINED_GLASS_PANE) {
            menu.replaceExistingItem(cache.currentSlot, new ItemStack(Material.AIR));
            
            menu.reload();
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void selectDisc(BlockMenu menu){
        JukeboxCache cache = cacheMap.get(menu.getLocation());
        ItemStack disc = cache.itemStack != null ? cache.itemStack : menu.getItemInSlot(cache.currentSlot);
        
        if(disc == null) {
            return;
        }

        ItemMeta meta = disc.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        disc.setItemMeta(meta);
        menu.reload();
    }

    @Override
    public void unselectDisc(BlockMenu menu){
        JukeboxCache cache = cacheMap.get(menu.getLocation());
        ItemStack disc = cache.itemStack != null ? cache.itemStack : menu.getItemInSlot(cache.currentSlot);
        
        if(disc == null) {
            return;
        }

        ItemMeta meta = disc.getItemMeta();

        meta.removeEnchant(Enchantment.BINDING_CURSE);

        disc.setItemMeta(meta);
        menu.reload();
    }

    @Override
    public boolean slotContainsMusicDisc(BlockMenu menu, int arithmetic){
        JukeboxCache cache = cacheMap.get(menu.getLocation());
        int slot = cache.currentSlot + arithmetic;
        
        return menu.getItemInSlot(slot) != null && menu.getItemInSlot(slot).getType() != Material.AIR
            && Tag.ITEMS_MUSIC_DISCS.isTagged(menu.getItemInSlot(slot).getType());
    }

    // inner class for caching
    public static class JukeboxCache {
        public boolean isOn;
        public boolean isPlaying;
        public int currentSlot;
        public ItemStack itemStack;

        public JukeboxCache(boolean isOn, boolean isPlaying, int currentSlot, @Nullable ItemStack itemStack) {
            this.isOn = isOn;
            this.currentSlot = currentSlot;
            this.isPlaying = isPlaying;
            this.itemStack = itemStack;
        }
    }

}