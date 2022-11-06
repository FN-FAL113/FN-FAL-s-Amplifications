package ne.fnfal113.fnamplifications.multiblocks;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FnGemDowngrader extends MultiBlockMachine {

    private final int[] slot = {0, 2, 4, 6, 8};
    private final int[] blankSlots = {1, 3, 5, 7};

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(FNAmplifications.getInstance(), "fn_gem_downgrader"),
            FNAmpItems.FN_GEM_DOWNGRADER,
            "",
            "&fThis is where you downgrade those shiny gems!"
    );


    public FnGemDowngrader() {
        super(FNAmpItems.MULTIBLOCK, FNAmpItems.FN_GEM_DOWNGRADER, new ItemStack[] {
                null , null , null,
                null, new ItemStack(Material.ACACIA_FENCE), null,
                new ItemStack(Material.GRINDSTONE), new ItemStack(Material.DISPENSER), new ItemStack(Material.STONECUTTER)
        }, BlockFace.SELF);
    }

    @Override
    public void onInteract(Player p, Block b) {
        Block dispBlock = b.getRelative(BlockFace.DOWN);
        BlockState state = PaperLib.getBlockState(dispBlock, false).getState();

        if (state instanceof Dispenser) {
            Dispenser disp = (Dispenser) state;
            Inventory inv = disp.getInventory();

            if(inv.getContents()[4] != null && inv.getContents()[0] != null) {
                if (canCraft(inv, inv.getContents()[4].clone(), p)) {
                    String id = Objects.requireNonNull(SlimefunItem.getByItem(inv.getContents()[4])).getId();
                    ItemStack output = Objects.requireNonNull(inv.getItem(4)).clone();

                    output.setAmount(1);
                    craft(dispBlock, p, b, inv, output, id);
                    return;
                }
            }

            if (SlimefunUtils.isInventoryEmpty(inv)) {
                Slimefun.getLocalization().sendMessage(p, "machines.inventory-empty", true);
            } else {
                Slimefun.getLocalization().sendMessage(p, "machines.pattern-not-found", true);
            }
        }
    }

    private boolean canCraft(Inventory inv, ItemStack gem, Player p) {
        for (int i : blankSlots) {
            if(inv.getContents()[i] != null){
                return false;
            }
        }

        for (int i : slot) {
            if(i == 4){
                continue;
            }

            if(!SlimefunUtils.isItemSimilar(inv.getContents()[i], FNAmpItems.FN_GEM_FINE_JASPER_CRAFTING, true, false)) {
                return false;
            }
        }

        SlimefunItem sfItem = SlimefunItem.getByItem(gem);

        if(!(sfItem instanceof GemUpgrade)){
            return false;
        }

        if(((GemUpgrade) sfItem).getTier(gem, sfItem.getId()) == 4){
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
            p.sendMessage(Utils.colorTranslator("&cTier 1 gem cannot be downgraded!"));

            return false;
        }

        return true;
    }

    protected void craft(Block dispenser, Player p, Block b, Inventory inv, ItemStack output, String id) {
        Inventory fakeInv = createVirtualInventory(inv);
        ItemStack finalOutput = setOutput(output, id);
        Inventory outputInv = findOutputInventory(finalOutput, dispenser, inv, fakeInv);

        craftItem(inv, b);
        finalOutput.setAmount(ThreadLocalRandom.current().nextInt() < 50 ? 2 : 3);
        if (outputInv != null) {

            outputInv.addItem(finalOutput);
        } else {
            dispenser.getWorld().dropItem(b.getLocation(), finalOutput);

            Slimefun.getLocalization().sendMessage(p, "machines.full-inventory", true);
            p.sendMessage(Utils.colorTranslator("&dCrafted item has been dropped instead"));
        }

        if(output.getItemMeta().hasDisplayName()){
            p.sendMessage(Utils.colorTranslator("&dSuccessfully downgraded to " + output.getItemMeta().getDisplayName() + "!"));
        } else{
            p.sendMessage(Utils.colorTranslator("&dSuccessfully downgraded the gem!"));
        }
    }

    public void craftItem(Inventory inv, Block b){
        for (int i : slot) {
            ItemStack item = inv.getContents()[i];

            ItemUtils.consumeItem(item, 1, true);
        }

        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            b.getWorld().playEffect(b.getLocation().add(0.5, 0.7, 0.5), Effect.SMOKE, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.3, 1.7, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
            b.getWorld().playSound(b.getLocation(), Sound.UI_STONECUTTER_SELECT_RECIPE, 1, 1);

            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.4, 0.45, 0.5), 2, 0.1, 0.1, 0.1, 0.1);
                b.getWorld().spawnParticle(Particle.SMOKE_LARGE, b.getLocation().add(0.4, 0.5, 0.5), 2, 0.1, 0.1, 0.1, 0.1);
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1, 1);

                Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                    b.getWorld().playEffect(b.getLocation().add(0.35, 0.75, 0.35), Effect.MOBSPAWNER_FLAMES, 1);
                    b.getWorld().spawnParticle(Particle.SMOKE_LARGE, b.getLocation().add(0.2, 1.7, 0.2), 2, 0.1, 0.1, 0.1, 0.1);
                    b.getWorld().playSound(b.getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);

                    Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                        b.getWorld().playEffect(b.getLocation().add(0.5, 0.7, 0.5), Effect.SMOKE, 1);
                        b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.35, 0.4, 0.4), 2, 0.1, 0.1, 0.1, 0.1);
                        b.getWorld().spawnParticle(Particle.CLOUD, b.getLocation().add(0.35, 0.5, 0.4), 2, 0.1, 0.1, 0.1, 0.1);
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);

                    }, 30);
                }, 30);
            }, 30);
        }, 30);
    }

    public ItemStack setOutput(ItemStack output, String id){
        ItemMeta meta = output.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        NamespacedKey key = Keys.createKey(id + "_gem_tier");
        int tier = pdc.getOrDefault(key, PersistentDataType.INTEGER, 4);

        pdc.set(key, PersistentDataType.INTEGER, tier + 1);
        if(tier == 3){
            meta.setDisplayName(meta.getDisplayName().replace(" " + getTierRomanNumeral(tier), getTierRomanNumeral(tier + 1)));
        } else {
            meta.setDisplayName(meta.getDisplayName().replace(getTierRomanNumeral(tier), getTierRomanNumeral(tier + 1)));
        }
        output.setItemMeta(meta);

        return output.clone();
    }

    public String getTierRomanNumeral(int tier){
        if(tier == 3){
            return "II";
        } else if(tier == 2){
            return "III";
        } else if (tier == 1){
            return "IV";
        }

        return "";
    }

    protected @Nonnull
    Inventory createVirtualInventory(@Nonnull Inventory inv) {
        Inventory fakeInv = Bukkit.createInventory(null, 9, "Fake Inventory");

        for (int i : slot) {
            ItemStack stack = inv.getContents()[i];

            if (stack != null) {
                stack = stack.clone();
                ItemUtils.consumeItem(stack, true);
            }

            fakeInv.setItem(i, stack);
        }

        return fakeInv;
    }
}
