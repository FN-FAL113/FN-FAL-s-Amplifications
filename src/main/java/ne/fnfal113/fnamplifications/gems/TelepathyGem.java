package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnBlockBreakHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Optional;

public class TelepathyGem extends AbstractGem implements OnBlockBreakHandler {

    public TelepathyGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(Player player, SlimefunItem gem, ItemStack gemItem, ItemStack currentItem){
        if (WeaponArmorEnum.PICKAXE.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType())) {
            bindGem(gem, currentItem, player, false);
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on pickaxes and axes only"));
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack itemStack){
        if (event.isCancelled()){
            return;
        }

        Block block = event.getBlock();

        if(block.getType() == Material.LADDER){ // dupe fix against auto ladder
            return;
        }

        Optional<SlimefunItem> sfItem = Optional.ofNullable(BlockStorage.check(block));

        if(sfItem.isPresent()){ // drop sf blocks instead
            return;
        }

        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());

        if(drops.isEmpty()){
            return;
        } // if collection is empty don't do anything further

        if(player.getInventory().firstEmpty() == -1){
            return;
        } // if player inventory is full drop item instead

        event.setDropItems(false);

        player.getInventory().addItem(drops.iterator().next());
    }

}