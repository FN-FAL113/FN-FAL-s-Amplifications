package ne.fnfal113.fnamplifications.machines.abstracts;

import dev.j3fftw.extrautils.interfaces.InventoryBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemState;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import ne.fnfal113.fnamplifications.machines.implementation.JukeBox.JukeboxCache;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractJukeBox extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public CustomItemStack NOT_OPERATING = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&cNot Operating!",
            "&eClick Play Button"
    );

    public CustomItemStack OPERATING = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&cOperating!"
    );

    public CustomItemStack NO_POWER = new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&cNo Power!",
            "&ePower it up first!"
    );

    public CustomItemStack NOT_RUNNING = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,
            "&cNot Running",
            "&eToggle it on first"
    );

    public CustomItemStack TOGGLED_ON = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE,
            "&dToggle:",
            "&eEnabled",
            "Click to change"
    );

    public CustomItemStack TOGGLED_OFF = new CustomItemStack(Material.WHITE_STAINED_GLASS_PANE,
            "&dToggle:",
            "&eDisabled",
            "Click to change"
    );

    public CustomItemStack PLAY = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "cecd041f628c005a690fc6b8237e7311bb7c3b3aac10539fefe396a4c7c783e7")),
            "&dStop"
    );

    public CustomItemStack STOP = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "fa8f6b131ef847d9160e516a6f44bfa932554d40c18a81796d766a5487b9f710")),
            "&dPlay"
    );

    public CustomItemStack PREVIOUS = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "32ff8aaa4b2ec30bc5541d41c8782199baa25ae6d854cda651f1599e654cfc79")),
            "&dPrevious disc"
    );

    public CustomItemStack NEXT = new CustomItemStack(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
            "aab95a8751aeaa3c671a8e90b83de76a0204f1be65752ac31be2f98feb64bf7f")),
            "&dNext disc"
    );

    private int energyCapacity = -1;
    private int energyConsumedPerTick = -1;


    public AbstractJukeBox(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    public int getEnergyConsumption() {
        return energyConsumedPerTick;
    }

    public void takeCharge(@Nonnull Location l) {

        if (isChargeable()) {
            int charge = getCharge(l);

            if (charge < getEnergyConsumption()) {
                return;
            }

            setCharge(l, charge - getEnergyConsumption());
        }
    }

    public final AbstractJukeBox setEnergyConsumption(int energyConsumption) {
        Validate.isTrue(energyConsumption > 0, "The energy consumption must be greater than zero!");
        Validate.isTrue(energyCapacity > 0, "You must specify the capacity before you can set the consumption amount.");
        Validate.isTrue(energyConsumption <= energyCapacity, "The energy consumption cannot be higher than the capacity (" + energyCapacity + ')');

        this.energyConsumedPerTick = energyConsumption;
        return this;
    }

    @Override
    public int getCapacity() {
        return energyCapacity;
    }

    public final AbstractJukeBox setCapacity(int capacity) {
        Validate.isTrue(capacity > 0, "The capacity must be greater than zero!");

        if (getState() == ItemState.UNREGISTERED) {
            this.energyCapacity = capacity;
            return this;
        } else {
            throw new IllegalStateException("You cannot modify the capacity after the Item was registered.");
        }
    }

    /**
     * Displays the current slow and if there is a music disc being played
     * @param invMenu the menu associated with the block
     */
    public abstract void changeStatus(BlockMenu invMenu);

    /**
     * The method for toggling the jukebox on or off
     * @param blockMenu the menu associated with block
     */
    public abstract void toggleOnOrOff(BlockMenu blockMenu);

    /**
     * Change the current slot back to the previous one in the left
     * @param player the player who changed the jukebox slot
     * @param menu the menu associated with block
     * @param goBackToDefaultSlot play default slot after the last music disc has finished playing
     */
    public abstract void previousDiscButton(@Nullable Player player, BlockMenu menu, boolean goBackToDefaultSlot);

    /**
     * Change the current slot to the next one in the right
     * @param player the player who changed the jukebox slot
     * @param menu the menu associated with block
     */
    public abstract void nextDiscButton(@Nullable Player player, BlockMenu menu);

    /**
     * The play and stop button, it will play a music disc according to current slot
     * @param player the player who clicked the play or stop button
     * @param menu the menu associated with block
     */
    public abstract void playOrStopButton(Player player, BlockMenu menu);

    /**
     * This method plays the selected slot if a disc exist
     * @param menu the menu associated with block
     * @param cache the cache hashmap that holds persistent data values
     * @param jukebox the jukebox block involved
     * @param location the location of jukebox block menu
     * @param arithmetic integer number that determines whether to increase or decrease slot
     */
    public abstract void playCurrentSlot(BlockMenu menu, JukeboxCache cache, Jukebox jukebox, Location location, int arithmetic);

    /**
     * Stops the music disc in the current slot
     * @param jukebox the jukebox block involved
     */
    public abstract void stopCurrentSlot(Jukebox jukebox, Location location, JukeboxCache cache, BlockMenu menu);

    /**
     * Checks if the new current slot is out of bounds or reached the last index
     * @param arithmetic integer number that determines to increase or decrease slot by 1
     */
    public abstract void isOutOfBounds(BlockMenu menu, Jukebox jukebox, JukeboxCache cache, Location location, int arithmetic);

    /**
     * Change the slot if no music disc exist in the next or previous slot,
     * glass pane indicates the current slot
     * @param menu the menu associated with block
     * @param jukebox the jukebox involved
     * @param cache the cache hashmap that holds persistent data values
     * @param location the location of jukebox block menu
     * @param arithmetic integer number that determines whether to increase or decrease slot
     * @param goToDefaultSlot if true go back to default slot
     */
    public abstract void changeSlot(BlockMenu menu, Jukebox jukebox, JukeboxCache cache, Location location, int arithmetic, boolean goToDefaultSlot);

    /**
     * Unselect the slot by removing the glass pane indicator after changing to a new slot
     * @param menu the menu associated with block
     * @param cache the cache hashmap that holds persistent data values
     */
    public abstract void unSelectSlot(BlockMenu menu, JukeboxCache cache);

    /**
     * Enchants the music disc to indicate it is the current disc being played
     * @param itemStack the current playing music disc
     * @param menu the menu associated with block
     */
    public abstract void selectDisc(@Nullable ItemStack itemStack, BlockMenu menu);

    /**
     * Un-enchants the unselected music disc
     * @param itemStack the current disc being unselected
     * @param menu the menu associated with block
     */
    public abstract void unSelectDisc(ItemStack itemStack, BlockMenu menu);

    /**
     * Check the next slot if its null
     * @param menu the menu associated with block
     * @param cache the cache hashmap that holds persistent data values
     * @param arithmetic integer number that determines whether to increase or decrease slot
     * @return true if slot has no music disc and is empty
     */
    public abstract boolean isSlotNotNull(BlockMenu menu, JukeboxCache cache, int arithmetic);

    /**
     * Checks if the item in the slot is a type of music disc
     * @param itemStack the item to be checked
     * @return true if itemstack is a music disc
     */
    public abstract boolean isMusicDisc(ItemStack itemStack);

}