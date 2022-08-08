package ne.fnfal113.fnamplifications.machines.implementation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.Getter;
import lombok.Setter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import ne.fnfal113.fnamplifications.machines.abstracts.AbstractJukeBox;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Jukebox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class JukeBox extends AbstractJukeBox {

    @Getter
    private final int lowerBound;
    @Getter
    private final int upperBound;
    @Getter
    private final int secondLowerBound;
    @Getter
    private final int secondUpperBound;
    @Getter
    private final int defaultSlot;
    @Getter
    @Setter
    private boolean newMusic = false;

    @Getter
    private final boolean secondBound;

    @Getter
    private static final Map<Location, JukeboxCache> CACHE_MAP = new HashMap<>();

    public JukeBox(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                   int lowerBound, int upperBound, int defaultSlot, int secondLowerBound, int secondUpperBound, boolean hasSecondBound) {
        super(itemGroup, item, recipeType, recipe);

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.secondLowerBound = secondLowerBound;
        this.secondUpperBound = secondUpperBound;
        this.defaultSlot = defaultSlot;
        this.secondBound = hasSecondBound;
    }

    @Override
    public void changeStatus(BlockMenu invMenu){
        invMenu.replaceExistingItem(49, new CustomItemStack(Material.MAGENTA_STAINED_GLASS_PANE,
                "&dNo music disc is being played",
                "&ePlace a music disc then click",
                "&eplay button or left/right arrows"));
    }

    @Override
    public void toggleOnOrOff(BlockMenu blockMenu) {
        final Location location = blockMenu.getLocation();
        final JukeboxCache cache = getCACHE_MAP().get(location);

        cache.isOn = !cache.isOn;

        BlockStorage.addBlockInfo(location, "toggled_On", String.valueOf(cache.isOn));
        blockMenu.replaceExistingItem(50, cache.isOn ? TOGGLED_ON : TOGGLED_OFF);

        getCACHE_MAP().put(location, cache);
    }

    @Override
    public void previousDiscButton(@Nullable Player player, BlockMenu menu, boolean goBackToDefaultSlot){
        final Location location = menu.getLocation();
        final JukeboxCache cache = getCACHE_MAP().get(location);

        Jukebox jukebox = (Jukebox) menu.getBlock().getState();
        if(cache.isOn) { // is jukebox toggle on
            if (isSlotNotNull(menu, cache, -1)) { // if previous slot has a disc then play that
                playCurrentSlot(menu, cache, jukebox, location, -1);
            } else { // decrement the current slot by 1
                isOutOfBounds(menu, jukebox, cache, location, -1);
            }
        } else if (player != null) {
            player.sendMessage(Utils.colorTranslator("&eJukebox is turned off, please turn it on"));
        }
    }

    @Override
    public void nextDiscButton(@Nullable Player player, BlockMenu menu){
        final Location location = menu.getLocation();
        final JukeboxCache cache = getCACHE_MAP().get(location);

        Jukebox jukebox = (Jukebox) menu.getBlock().getState();

        if(cache.isOn) {
            if (isSlotNotNull(menu, cache, 1)) { // if slot next slot is not null, play disc in the new slot
                playCurrentSlot(menu, cache, jukebox, location, 1);
            } else {
                isOutOfBounds(menu, jukebox, cache, location, 1);
            }
        } else if (player != null) {
            player.sendMessage(Utils.colorTranslator("&eJukebox is turned off, please turn it on"));
        }
    }

    @Override
    public void playOrStopButton(Player player, BlockMenu menu){
        final Location location = menu.getLocation();
        final JukeboxCache cache = getCACHE_MAP().get(location);

        Jukebox jukebox = (Jukebox) menu.getBlock().getState();

        if(cache.isOn) {
            if (cache.isPlaying) { // stop the current song being played
                cache.isPlaying = false;
                cache.itemStack = null;

                BlockStorage.addBlockInfo(location, "is_Playing", String.valueOf(false));

                menu.replaceExistingItem(4, STOP);

                stopCurrentSlot(jukebox, location, cache, menu);
                getCACHE_MAP().put(menu.getLocation(), cache);
            } else { // play the current disc in slot, will use default slot on first jukebox use
                if (isSlotNotNull(menu, cache, 0)) {
                    playCurrentSlot(menu, cache, jukebox, location, 0);
                } else {
                    player.sendMessage(Utils.colorTranslator("&dCurrent slot has no music disc"));
                }
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eJukebox is turned off, please turn it on"));
        }
    }

    @Override
    public void playCurrentSlot(BlockMenu menu, JukeboxCache cache, Jukebox jukebox, Location location, int arithmetic){
        unSelectDisc(cache.itemStack != null ? cache.itemStack : menu.getItemInSlot(cache.currentSlot), menu);
        unSelectSlot(menu, cache);

        cache.currentSlot = cache.currentSlot + arithmetic;
        cache.isPlaying = true;
        cache.itemStack = menu.getItemInSlot(cache.currentSlot);

        menu.replaceExistingItem(4, PLAY);

        BlockStorage.addBlockInfo(location, "is_Playing", String.valueOf(true));
        BlockStorage.addBlockInfo(location, "current_Slot", String.valueOf(cache.currentSlot));

        selectDisc(menu.getItemInSlot(cache.currentSlot), menu);

        jukebox.setPlaying(menu.getItemInSlot(cache.currentSlot).getType());
        jukebox.update(true);
        setNewMusic(true);

        getCACHE_MAP().put(menu.getLocation(), cache);
    }

    @Override
    public void stopCurrentSlot(Jukebox jukebox, Location location, JukeboxCache cache, BlockMenu menu){
        unSelectDisc(cache.itemStack != null ? cache.itemStack : menu.getItemInSlot(cache.currentSlot), menu);

        cache.isPlaying = false;
        cache.itemStack = null;

        menu.replaceExistingItem(4, STOP);
        BlockStorage.addBlockInfo(location, "is_Playing", String.valueOf(false));

        jukebox.setRecord(null);
        jukebox.update(true);

        getCACHE_MAP().put(location, cache);
    }

    @Override
    public void isOutOfBounds(BlockMenu menu, Jukebox jukebox, JukeboxCache cache, Location location, int arithmetic){
        if(isSecondBound()){ // jukebox has multiple lower bound and upper bound on each row
            if(cache.currentSlot + (arithmetic) == getSecondUpperBound() + 1){
                changeSlot(menu, jukebox, cache, location, arithmetic, true);
                return;
            } // go back to default slot if last upper bound is reached
            if((!(cache.currentSlot + (arithmetic) > getSecondUpperBound()) && !(cache.currentSlot + (arithmetic) < getSecondLowerBound())) ||
                    (!(cache.currentSlot + (arithmetic) > getUpperBound()) && !(cache.currentSlot + (arithmetic) < getLowerBound()))){
                changeSlot(menu, jukebox, cache, location, arithmetic, false);
                return;
            } // if new slot is not between upper bound and second lower bound
            else if(cache.currentSlot + (arithmetic) < getSecondLowerBound() && cache.currentSlot + (arithmetic) > getUpperBound()){
                changeSlot(menu, jukebox, cache, location, arithmetic == 1 ? getSecondLowerBound() - getUpperBound() : getUpperBound() - getSecondLowerBound(), false);
                return;
            } // if new slot is between upper bound and second row lower bound
        }
        if(cache.currentSlot + (arithmetic) == getUpperBound() + 1){
            changeSlot(menu, jukebox, cache, location, arithmetic, true);
            return;
        } // go back to default slot if upper bound is reached
        if (isSlotNotNull(menu, cache, arithmetic) && cache.currentSlot == getDefaultSlot() && cache.isPlaying){
            stopCurrentSlot(jukebox, location, cache, menu);
            changeSlot(menu, jukebox, cache, location, arithmetic, false);
            return;
        } // if current slot is in the default slot and jukebox has finished playing, stop the jukebox instead of changing slot
        if(!(cache.currentSlot + (arithmetic) > getUpperBound()) && !(cache.currentSlot + (arithmetic) < getLowerBound())) {
            changeSlot(menu, jukebox, cache, location, arithmetic, false);
        } // if current slot is between the bound limits then change the current slot
    }

    @Override
    public void changeSlot(BlockMenu menu, Jukebox jukebox, JukeboxCache cache, Location location, int arithmetic, boolean goToDefaultSlot){
        if(cache.isPlaying) {
            stopCurrentSlot(jukebox, location, cache, menu);
        }  // stop jukebox after changing slot

        if(isSlotNotNull(menu, cache, arithmetic)) {
            playCurrentSlot(menu, cache, jukebox, location, arithmetic);

            return;
        } // if new slot has a disc play it instead

        if(goToDefaultSlot){
            unSelectSlot(menu, cache);
            cache.currentSlot = getDefaultSlot();

            if(isSlotNotNull(menu, cache, 0)){
                playCurrentSlot(menu, cache, jukebox, location, 0);
                return;
            }
        } // play disc in default slot if last upper bound has been reached

        // select a new empty slot, remove previous slot indicator
        unSelectSlot(menu, cache);

        cache.currentSlot = goToDefaultSlot ? getDefaultSlot() : cache.currentSlot + arithmetic;

        BlockStorage.addBlockInfo(location, "current_Slot", String.valueOf(cache.currentSlot));

        menu.replaceExistingItem(cache.currentSlot, new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,
                "&eNo music disc in current slot",
                "&eplease change the slot"));
        menu.reload();

        getCACHE_MAP().put(menu.getLocation(), cache);
    }

    @Override
    public void unSelectSlot(BlockMenu menu, JukeboxCache cache){
        if(menu.getItemInSlot(cache.currentSlot) != null && menu.getItemInSlot(cache.currentSlot).getType() == Material.PINK_STAINED_GLASS_PANE) {
            menu.replaceExistingItem(cache.currentSlot, new ItemStack(Material.AIR));
            menu.reload();
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void selectDisc(@Nullable ItemStack itemStack, BlockMenu menu){
        if(itemStack == null){
            return;
        }

        ItemMeta meta = itemStack.getItemMeta().clone();

        meta.addEnchant(Enchantment.BINDING_CURSE, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(meta);
        menu.reload();
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void unSelectDisc(@Nullable ItemStack itemStack, BlockMenu menu){
        if(itemStack == null){
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();

        meta.removeEnchant(Enchantment.BINDING_CURSE);

        itemStack.setItemMeta(meta);
        menu.reload();
    }

    @Override
    public boolean isSlotNotNull(BlockMenu menu, JukeboxCache cache, int arithmetic){
        return menu.getItemInSlot(cache.currentSlot + arithmetic) != null && menu.getItemInSlot(cache.currentSlot + arithmetic).getType() != Material.AIR
                && isMusicDisc(menu.getItemInSlot(cache.currentSlot + arithmetic));
    }

    @Override
    public boolean isMusicDisc(ItemStack itemStack){
        return Tag.ITEMS_MUSIC_DISCS.isTagged(itemStack.getType());
    }

    public static class JukeboxCache {
        public boolean isOn;
        public boolean isPlaying;
        public int currentSlot;
        @Nullable
        public ItemStack itemStack;

        public JukeboxCache(boolean isOn, boolean isPlaying, int currentSlot, @Nullable ItemStack itemStack) {
            this.isOn = isOn;
            this.currentSlot = currentSlot;
            this.isPlaying = isPlaying;
            this.itemStack = itemStack;
        }
    }
}