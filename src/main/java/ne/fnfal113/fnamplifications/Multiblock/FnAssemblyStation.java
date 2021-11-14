package ne.fnfal113.fnamplifications.Multiblock;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FnAssemblyStation extends MultiBlockMachine {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(FNAmplifications.getInstance(), "fn_assembly_station"),
            FNAmpItems.FN_ASSEMBLY_STATION,
            "",
            "&fThis is where you craft some FN stuffs!"
    );


    public FnAssemblyStation() {
        super(FNAmpItems.MULTIBLOCK, FNAmpItems.FN_ASSEMBLY_STATION, new ItemStack[] {
                null , null , null,
                null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
                new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.DISPENSER), new ItemStack(Material.CRAFTING_TABLE)
        }, BlockFace.SELF);
    }

    @Override
    public void onInteract(Player p, Block b) {
        Block dispBlock = b.getRelative(BlockFace.DOWN);
        BlockState state = PaperLib.getBlockState(dispBlock, false).getState();

        if (state instanceof Dispenser) {
            Dispenser disp = (Dispenser) state;
            Inventory inv = disp.getInventory();
            List<ItemStack[]> inputs = RecipeType.getRecipeInputList(this);

            for (int i = 0; i < inputs.size(); i++) {
                if (canCraft(inv, inputs, i)) {
                    ItemStack output = RecipeType.getRecipeOutputList(this, inputs.get(i)).clone();

                    if (SlimefunUtils.canPlayerUseItem(p, output, true)) {
                        Inventory outputInv = findOutputInventory(output, dispBlock, inv);

                        if (outputInv != null) {
                            craft(p, b, inv, inputs.get(i), output, outputInv);
                        } else {
                            Slimefun.getLocalization().sendMessage(p, "machines.full-inventory", true);
                        }
                    }

                    return;
                }
            }

            Slimefun.getLocalization().sendMessage(p, "machines.unknown-material", true);
        }
    }

    private boolean canCraft(Inventory inv, List<ItemStack[]> inputs, int i) {
        for (ItemStack expectedInput : inputs.get(i)) {
            if (expectedInput != null) {
                for (int j = 0; j < inv.getContents().length; j++) {
                    if (j == (inv.getContents().length - 1) && !SlimefunUtils.isItemSimilar(inv.getContents()[j], expectedInput, true)) {
                        return false;
                    } else if (SlimefunUtils.isItemSimilar(inv.getContents()[j], expectedInput, true)) {
                        break;
                    }
                }
            }
        }

        return true;
    }

    protected void craft(Player p, Block b, Inventory inv, ItemStack[] recipe, ItemStack output, Inventory outputInv) {
        for (ItemStack removing : recipe) {
            if (removing != null) {
                InvUtils.removeItem(inv, removing.getAmount(), true, stack -> SlimefunUtils.isItemSimilar(stack, removing, true));
            }
        }

        outputInv.addItem(output);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        p.getWorld().playEffect(b.getLocation(), Effect.SMOKE, 1);
    }


}
