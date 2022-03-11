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
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.gems.implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnGemAltar;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("ConstantConditions")
public class RetaliateGem extends AbstractGem {

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

        if(slimefunItem != null && currentItem != null) {
            if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType())) {
                if (hasNeededGem(currentItem.getItemMeta().getPersistentDataContainer())) {
                    new Gem(slimefunItem, currentItem, player).onDrag(event, true);
                } else {
                    player.sendMessage(Utils.colorTranslator("&eWeapon is missing the needed gem in the weapon, please read the lore of the gem!"));
                }
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes and swords only"));
            }
        }
    }

    public boolean hasNeededGem(PersistentDataContainer pdc){
        return pdc.has(Keys.RETURN_DAMNATION_KEY, PersistentDataType.STRING) ||
                pdc.has(Keys.RETURN_TRISWORD_KEY, PersistentDataType.STRING) ||
                pdc.has(Keys.RETURN_AXE_KEY, PersistentDataType.STRING);
    }

    public static void setup(){
        new RetaliateGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_RETALIATE, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.COMMON_TALISMAN, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2),  SlimefunItems.TALISMAN_WHIRLWIND,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_WHIRLWIND, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2),  SlimefunItems.COMMON_TALISMAN})
                .register(plugin);
    }
}