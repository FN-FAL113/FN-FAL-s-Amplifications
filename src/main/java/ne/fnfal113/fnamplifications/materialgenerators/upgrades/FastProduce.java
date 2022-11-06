package ne.fnfal113.fnamplifications.materialgenerators.upgrades;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.materialgenerators.implementations.CustomMaterialGenerator;
import ne.fnfal113.fnamplifications.materialgenerators.upgrades.abstracts.AbstractUpgrades;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FastProduce extends AbstractUpgrades {

    public FastProduce(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, Utils.colorTranslator("&aMaterial Gen upgrade successful!"));
    }


    @Override
    public boolean upgradeMaterialGenerator(Block sfBlock, Player player, CustomMaterialGenerator matGen) {
        String blockInfo = BlockStorage.getLocationInfo(sfBlock.getLocation(), "fast_produce_multiplier");

        if(blockInfo != null && Double.parseDouble(BlockStorage.getLocationInfo(sfBlock.getLocation(), "fast_produce_multiplier")) > 0){
            player.sendMessage(Utils.colorTranslator("&cThis material generator still has fast produce upgrade!"));

            return false;
        }

        // seconds * (mc ticks per second / sf ticker delay)
        matGen.getGeneratorFastProduce().put(new BlockPosition(sfBlock.getLocation()),
                new CustomMaterialGenerator.FastProduceCache(1.75, 0, (int) (1800 * (20.0 / Slimefun.getTickerTask().getTickRate()))));

        BlockStorage.addBlockInfo(sfBlock.getLocation(), "fast_produce_multiplier", "1.75");
        BlockStorage.addBlockInfo(sfBlock.getLocation(), "fast_produce_current_lifetime", "0");
        BlockStorage.addBlockInfo(sfBlock.getLocation(), "fast_produce_max_lifetime", "1800");

        return true;
    }

}
