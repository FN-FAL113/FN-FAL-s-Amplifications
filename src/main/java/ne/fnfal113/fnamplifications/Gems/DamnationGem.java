package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.OnRightClickHandler;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.gems.implementation.ThrowableWeapon;
import ne.fnfal113.fnamplifications.gems.implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnGemAltar;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("ConstantConditions")
public class DamnationGem extends AbstractGem implements OnRightClickHandler {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final ThrowableWeapon throwableWeapon = new ThrowableWeapon();

    public DamnationGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){
        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());

        if(slimefunItem != null && currentItem != null) {
            if ((WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType()))) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes and swords only"));
            }
        }
    }

    @Override
    public void onRightClick(Player player){
        if(!player.isSneaking()){
            return;
        }
        if(!hasPermissionToThrow(player)){
            player.sendMessage(Utils.colorTranslator("&c&l[FNAmpli" + "&b&lfications] > " + "&eYou don't have the permission to throw here!"));
            return;
        } // check if player has the permission to build on current location

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        // check if player has below 4 floating weapons
        if(throwableWeapon.isBelow4Weapons(player)) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            // creates a throwable task from the object instance
            throwableWeapon.floatThrowItem(player, itemStack.clone(), Boolean.parseBoolean(pdc.getOrDefault(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "false")));

            itemStack.setAmount(0);
        }
    }

    public static void setup(){
        new DamnationGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_DAMNATION, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 4),  SlimefunItems.TALISMAN_KNIGHT,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 3),
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.RAINBOW_RUNE, 4),  SlimefunItems.TALISMAN_KNIGHT})
                .register(plugin);
    }
}