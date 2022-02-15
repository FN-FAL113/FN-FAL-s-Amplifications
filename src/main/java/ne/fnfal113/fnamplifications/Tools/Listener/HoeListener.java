package ne.fnfal113.fnamplifications.Tools.Listener;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Tools.FnHoe;
import ne.fnfal113.fnamplifications.Tools.FnHoeAutoPlant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.Set;

public class HoeListener implements Listener {

    private static Material GRASS = Material.matchMaterial("GRASS_PATH");

    static {
        if(Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)){
            GRASS = Material.DIRT_PATH;
        }
    }

    private final Set<Material> dirtBlocks = EnumSet.of(
            Material.DIRT,
            Material.GRASS_BLOCK,
            GRASS
    );

    private final Set<Material> grass = EnumSet.of(
            Material.GRASS,
            Material.FERN,
            Material.LARGE_FERN,
            Material.TALL_GRASS
    );

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        boolean rightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK;
        boolean leftClick = event.getAction() == Action.LEFT_CLICK_BLOCK;

        if (event.getHand() == EquipmentSlot.HAND) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if(itemStack.getType() == Material.DIAMOND_HOE || itemStack.getType() == Material.NETHERITE_HOE){
                SlimefunItem hoe = SlimefunItem.getByItem(itemStack);

                if (hoe instanceof FnHoe || hoe instanceof FnHoeAutoPlant) {
                    Block clickedBlock = event.getClickedBlock();
                    boolean checkBlock = clickedBlock != null && dirtBlocks.contains(clickedBlock.getType());
                    boolean checkBlockLeftClick = clickedBlock != null;
                    int x = 0, z = 0, i = 0, j = 0, k = 0;

                    if (player.getFacing() == BlockFace.EAST) {
                        i = 5;
                        z = -2;
                        j = 3;
                    } // positive x
                    else if (player.getFacing() == BlockFace.SOUTH) {
                        x = -2;
                        i = 3;
                        j = 5;
                    } // positive z
                    else if (player.getFacing() == BlockFace.WEST) {
                        x = -4;
                        i = 1;
                        z = -2;
                        j = 3;
                    } // negative X
                    else if (player.getFacing() == BlockFace.NORTH){
                        x = -2;
                        i = 3;
                        z = -4;
                        j = 1;
                    } // negative z

                    if (checkBlock && rightClick) {
                        for (int a = x; a < i; a++) {
                            for (int b = z; b < j; b++) {
                                if (dirtBlocks.contains(clickedBlock.getRelative(a, 0, b).getType())){
                                    if(Slimefun.getProtectionManager().hasPermission
                                            (Bukkit.getOfflinePlayer(player.getUniqueId()), clickedBlock.getRelative(a, 0, b), Interaction.INTERACT_BLOCK)){

                                        Block land = clickedBlock.getRelative(a, 0, b);
                                         land.setType(Material.FARMLAND);
                                         if(grass.contains(clickedBlock.getRelative(a, 1, b).getType()) ||
                                                 Tag.FLOWERS.isTagged(clickedBlock.getRelative(a, 1, b).getType()) ||
                                                 Tag.SMALL_FLOWERS.isTagged(clickedBlock.getRelative(a, 1, b).getType()) ||
                                                 Tag.TALL_FLOWERS.isTagged(clickedBlock.getRelative(a, 1, b).getType()) ||
                                                 Tag.SAPLINGS.isTagged(clickedBlock.getRelative(a, 1, b).getType())){

                                             clickedBlock.getRelative(a, 1, b).breakNaturally();
                                         } // check if grass above the farmland exist
                                    } // perm check
                                } // check the necessary block
                            } // z axis loop
                        } // x axis loop
                    }

                    boolean isAgeable = checkBlockLeftClick && clickedBlock.getBlockData().clone() instanceof Ageable;

                    if (leftClick && isAgeable && hoe instanceof FnHoe) {
                        for (int a = x; a < i; a++) {
                            for (int b = z; b < j; b++) {
                                if (Tag.CROPS.isTagged(clickedBlock.getRelative(a, 0, b).getType()) &&
                                        clickedBlock.getRelative(a, 0, b).getBlockData().clone() instanceof Ageable) {
                                    if(Slimefun.getProtectionManager().hasPermission
                                            (Bukkit.getOfflinePlayer(player.getUniqueId()), clickedBlock.getRelative(a, 0, b), Interaction.BREAK_BLOCK)){

                                        clickedBlock.getRelative(a, 0, b).breakNaturally(itemStack);
                                    } // perm check
                                } // check if block is a crop
                            } // z axis loop
                        } // x axis loop
                    } else if (leftClick && isAgeable && hoe instanceof FnHoeAutoPlant) {
                        for (int a = x; a < i; a++) {
                            for (int b = z; b < j; b++) {
                                if (Tag.CROPS.isTagged(clickedBlock.getRelative(a, 0, b).getType()) &&
                                        clickedBlock.getRelative(a, 0, b).getBlockData().clone() instanceof Ageable){

                                    Ageable ageable = (Ageable) clickedBlock.getRelative(a, 0, b).getBlockData().clone();
                                    if(Slimefun.getProtectionManager().hasPermission
                                            (Bukkit.getOfflinePlayer(player.getUniqueId()), clickedBlock.getRelative(a, 0, b), Interaction.BREAK_BLOCK)){

                                        Block block = clickedBlock.getRelative(a, 0, b);
                                        Material material = block.getBlockData().getMaterial();
                                        clickedBlock.getRelative(a, 0, b).breakNaturally(itemStack);
                                        if(ageable.getAge() == ageable.getMaximumAge()) {
                                            k = k + 1;
                                            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                                                block.setType(material);
                                            }, 5L);
                                        }
                                    } // perm check
                                } // check if block is a crop
                            } // z axis loop
                        } // x axis loop
                    }
                    if(k > 0){
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Replanted " + k + " crops");
                    }

                } // instance of fn item check
            } // main hand item type check
        } // click type check

    }


}
