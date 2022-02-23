package ne.fnfal113.fnamplifications.Gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gems.Implementation.Gem;
import ne.fnfal113.fnamplifications.Gems.Implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.Gems.Interface.GemImpl;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnGemAltar;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("ConstantConditions")
public class RetaliateGem extends SlimefunItem implements GemImpl {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    public RetaliateGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
                if(hasNeededGem(container)) {
                    if (!gem.isSameGem(currentItem)) {
                        player.setItemOnCursor(new ItemStack(Material.AIR));
                        gem.socketItem();
                        gem.retaliateWeapon();
                    } else {
                        player.sendMessage(Utils.colorTranslator("&6Your item has " + gem.getSfItemName() + " &6socketed already!"));
                    }
                } else {
                    player.sendMessage(Utils.colorTranslator("&eWeapon is missing the needed gem in the weapon, please read the lore of the gem!"));
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

    public boolean hasNeededGem(PersistentDataContainer pdc){
        NamespacedKey damnedKey = new NamespacedKey(FNAmplifications.getInstance(), "fn_gem_damnation");
        NamespacedKey triKey = new NamespacedKey(FNAmplifications.getInstance(), "fn_gem_tri_sword");
        NamespacedKey throwieKey = new NamespacedKey(FNAmplifications.getInstance(), "fn_gem_axethrowie");

        return pdc.has(damnedKey, PersistentDataType.STRING) || pdc.has(triKey, PersistentDataType.STRING) ||
                pdc.has(throwieKey, PersistentDataType.STRING);
    }

    public static void setup(){
        new RetaliateGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_RETALIATE, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2),  SlimefunItems.TALISMAN_WHIRLWIND,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_WHIRLWIND, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2),  SlimefunItems.COMMON_TALISMAN})
                .register(plugin);
    }
}
