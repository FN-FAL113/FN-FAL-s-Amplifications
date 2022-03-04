package ne.fnfal113.fnamplifications.Gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gems.Implementation.Gem;
import ne.fnfal113.fnamplifications.Gems.Abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.Gems.Interface.OnRightClickHandler;
import ne.fnfal113.fnamplifications.Utils.Keys;
import ne.fnfal113.fnamplifications.Gems.Implementation.ThrowableWeapon;
import ne.fnfal113.fnamplifications.Gems.Implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnGemAltar;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        if(slimefunItem != null && currentItem != null &&
                (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType()))){
            ItemMeta meta = currentItem.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();

            if(checkGemAmount(container, currentItem) < 4) {
                Gem gem = new Gem(slimefunItem, currentItem, player);
                if(!gem.isSameGem(currentItem)){
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    gem.socketItem();
                } else{
                    player.sendMessage(Utils.colorTranslator("&6Your item has " + gem.getSfItemName() + " &6socketed already!"));
                }
            } else {
                player.sendMessage(Utils.colorTranslator("&eOnly 4 gems per item is allowed!"));
                player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1.0F, 1.0F);
            }
            event.setCancelled(true);
        }

    }

    @Override
    public int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack){
        return pdc.getOrDefault(
                new NamespacedKey(FNAmplifications.getInstance(), itemStack.getType().toString().toLowerCase() + "_socket_amount"),
                PersistentDataType.INTEGER, 0);
    }

    @Override
    public void onRightClick(Player player){
        if(!player.isSneaking()){
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(throwableWeapon.isBelow4Weapons(player)) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

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