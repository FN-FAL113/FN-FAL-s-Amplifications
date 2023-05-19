package ne.fnfal113.fnamplifications.materialgenerators.upgrades;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.materialgenerators.implementations.CustomMaterialGenerator;
import ne.fnfal113.fnamplifications.materialgenerators.upgrades.abstracts.AbstractUpgrades;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairItem extends AbstractUpgrades {

    private final boolean breakOverTime = FNAmplifications.getInstance().getConfig().getBoolean("Enable-Mat-Gen-Break-Over-Time", true);

    public RepairItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, Utils.colorTranslator("&aMaterial Gen repair successful!"));
    }

    @Override
    public boolean upgradeMaterialGenerator(Block sfBlock, Player player, CustomMaterialGenerator matGen) {
        if(!breakOverTime) {
            return false;
        }

        int generatorCondition = Integer.parseInt(BlockStorage.getLocationInfo(sfBlock.getLocation(), "generator_status"));
        int addCondition = 20;

        if (generatorCondition == 100) {
            player.sendMessage(Utils.colorTranslator("&6Cannot repair! material generator in good condition!"));
            return false;
        }

        if (generatorCondition + addCondition > 100) {
            BlockStorage.addBlockInfo(sfBlock, "generator_status", "100");
            matGen.getGeneratorCondition().put(new BlockPosition(sfBlock.getLocation()), 100);
        } else {
            BlockStorage.addBlockInfo(sfBlock.getLocation(), "generator_status", String.valueOf(generatorCondition + addCondition));
            matGen.getGeneratorCondition().put(new BlockPosition(sfBlock.getLocation()), generatorCondition + addCondition);
        }

        return true;
    }

}
