package ne.fnfal113.fnamplifications.Gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gems.Interface.GemImpl;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnGemAltar;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

public class TelepathyGem extends SlimefunItem implements GemImpl {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    public TelepathyGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){
        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());
        if(slimefunItem != null && currentItem != null && (PICKAXE.contains(currentItem.getType()) || AXE.contains(currentItem.getType()))){
            ItemMeta meta = currentItem.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();

            if(checkGemAmount(container, currentItem) < 3) {
                Gem gem = new Gem(slimefunItem, currentItem, player);
                if(!gem.isSameGem(currentItem)){
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    gem.socketItem();
                } else{
                    player.sendMessage(Utils.colorTranslator("&6Your item has " + gem.getSfItemName() + " &6socketed already!"));
                }
                event.setCancelled(true);
            } else {
                player.sendMessage(Utils.colorTranslator("&eOnly 3 gems per item is allowed!"));
                player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1.0F, 1.0F);
                event.setCancelled(true);
            }
        }

    }

    @Override
    public int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack){
        return pdc.getOrDefault(
                new NamespacedKey(FNAmplifications.getInstance(), itemStack.getType().toString().toLowerCase() + "_socket_amount"),
                PersistentDataType.INTEGER, 0);
    }

    public void onBlockBreak(BlockBreakEvent event, Player player){
        Block block = event.getBlock();

        if (event.isCancelled()){
            return;
        }

        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());

        if(drops.isEmpty()){
            return;
        }

        if(player.getInventory().firstEmpty() == -1){
            return;
        }

        event.setDropItems(false);

        player.getInventory().addItem(drops.iterator().next());

    }

    public static void setup(){
        new TelepathyGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_TELEPATHY, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_MINER, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.TALISMAN_MINER,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.EARTH_RUNE, new ItemStack(Material.IRON_PICKAXE), SlimefunItems.EARTH_RUNE})
                .register(plugin);
    }
}
