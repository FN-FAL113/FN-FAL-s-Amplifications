package ne.fnfal113.fnamplifications.tools;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.tools.implementation.ThrowableItemTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ThrowableTorch extends SlimefunItem {

    private static final Set<Material> IGNORED_SOLID_BLOCKS = new HashSet<>();

    static {
        IGNORED_SOLID_BLOCKS.addAll(Tag.SLABS.getValues());
        IGNORED_SOLID_BLOCKS.addAll(Tag.STAIRS.getValues());
        IGNORED_SOLID_BLOCKS.addAll(Tag.FENCES.getValues());
        IGNORED_SOLID_BLOCKS.addAll(Tag.DOORS.getValues());

        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)) {
            IGNORED_SOLID_BLOCKS.add(Material.AMETHYST_CLUSTER);
            IGNORED_SOLID_BLOCKS.add(Material.SMALL_AMETHYST_BUD);
            IGNORED_SOLID_BLOCKS.add(Material.MEDIUM_AMETHYST_BUD);
            IGNORED_SOLID_BLOCKS.add(Material.LARGE_AMETHYST_BUD);
        }
    }

    public ThrowableTorch(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public void onClick(Player player, ItemStack itemInMainHand){
        if(!Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()), player.getLocation(), Interaction.BREAK_BLOCK)){
            return;
        }

        Vector vector = player.getLocation().add(player.getLocation().getDirection().multiply(9).normalize())
                .subtract(player.getLocation().toVector()).toVector();

        ThrowableItemTask throwableItemTask = new ThrowableItemTask(player, itemInMainHand, vector, torchConsumer());

        throwableItemTask.runTaskTimer(FNAmplifications.getInstance(), 0L, 1L);

        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
    }

    public Consumer<ThrowableItemTask> torchConsumer() {
        return throwableItemTask -> {
            Player owner = throwableItemTask.getPlayer();
            ArmorStand armorStand = throwableItemTask.getArmorStand();
            ItemStack torch = throwableItemTask.getItemStack().clone();
            Predicate<Block> blockTypePredicate = block -> block.isLiquid() || !block.getType().isSolid();

            armorStand.teleport(armorStand.getLocation().add(throwableItemTask.getVector().subtract(new Vector(0, 0.045, 0))));

            RayTraceResult result = armorStand.rayTraceBlocks(0.80);
            List<Entity> entityList = armorStand.getNearbyEntities(0.3, 0.3, 0.3);

            if(result != null && Objects.requireNonNull(result.getHitBlock()).getType() != Material.GRASS 
                && !Tag.FLOWERS.isTagged(result.getHitBlock().getType())
            ) {
                Block blockHit = result.getHitBlock();

                if(blockHit == null || blockTypePredicate.test(blockHit) || IGNORED_SOLID_BLOCKS.contains(blockHit.getType())
                        || blockNameContains(blockHit, "gate") || blockNameContains(blockHit, "leaves")){
                    throwableItemTask.dropTorch();

                    return;
                }

                if(result.getHitBlockFace() != null) {
                    BlockFace blockFace = result.getHitBlockFace();
                    Block blockHitRelative = blockHit.getRelative(blockFace);

                    Predicate<Block> blockPredicate = block -> block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR;

                    if(blockHit.getType() == Material.TORCH || blockHit.getType() == Material.WALL_TORCH){
                        throwableItemTask.dropTorch();

                        return;
                    }

                    // additional checks here for accuracy since raytracing with an armorstand
                    // is not that really feasible in terms of hitting the right block face
                    if (blockPredicate.test(blockHitRelative) && blockFace != BlockFace.DOWN) {
                        placeTorch(blockHitRelative, blockFace);
                    } else if(blockPredicate.test(blockHit.getRelative(owner.getFacing().getOppositeFace()))) {
                        placeTorch(blockHit.getRelative(owner.getFacing().getOppositeFace()), owner.getFacing().getOppositeFace());
                    } else if(blockPredicate.test(blockHit.getRelative(BlockFace.UP))){
                        placeTorch(blockHit.getRelative(BlockFace.UP), BlockFace.UP);
                    } else {
                        throwableItemTask.dropTorch();;

                        return;
                    }


                    throwableItemTask.stopTask();

                    return;
                }
            }

            if(!entityList.isEmpty() && !entityList.contains(owner)) {
                for (int i = 0; i < entityList.size(); i++) { // using primitive for-loop here, can also use enhanced for-loop
                    if (entityList.get(i) instanceof Damageable && entityList.get(i).getUniqueId() != owner.getUniqueId()) {
                        entityList.get(i).setFireTicks(80);

                        throwableItemTask.stopTask();

                        return;
                    }
                }

                throwableItemTask.dropTorch();;
            }

            if(armorStand.getLocation().distanceSquared(owner.getLocation()) > 6400){ // drop the torch if it reaches 80 blocks away
                throwableItemTask.dropTorch();;
            }
        };
    }

    public void placeTorch(Block blockHitRelative, BlockFace blockFace){
        if(blockFace != BlockFace.UP && blockFace != BlockFace.DOWN) {
            blockHitRelative.setType(Material.WALL_TORCH);

            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                Directional wallTorchBlockData = (Directional) Material.WALL_TORCH.createBlockData();
                wallTorchBlockData.setFacing(blockFace);

                blockHitRelative.setBlockData(wallTorchBlockData);
                blockHitRelative.getState().update(true);
            }, 2L);
        } else {
            blockHitRelative.setType(Material.TORCH);
        }

        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> BlockStorage.store(blockHitRelative, FNAmpItems.FN_THROWABLE_TORCH), 2L);
    }

    public boolean blockNameContains(Block blockHit, String name){
       return blockHit.getType().toString().toLowerCase().contains(name);
    }

}