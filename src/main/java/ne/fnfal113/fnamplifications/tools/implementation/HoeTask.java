package ne.fnfal113.fnamplifications.tools.implementation;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HoeTask {

    private static Material GRASS = Material.matchMaterial("GRASS_PATH");

    private static final Set<Material> MAT = new HashSet<>();

    static {
        if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)) {
            GRASS = Material.DIRT_PATH;
        }
        MAT.addAll(Tag.FLOWERS.getValues());
        MAT.addAll(Tag.SMALL_FLOWERS.getValues());
        MAT.addAll(Tag.TALL_FLOWERS.getValues());
        MAT.addAll(Tag.SAPLINGS.getValues());
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

    public HoeTask(){}

    public void tillLand(Player player, Block clickedBlock){
        if(!dirtBlocks.contains(clickedBlock.getType())){
            return;
        }

        Map<String, Integer> areaMap = setValues(player);

        for (int a = areaMap.get("x"); a < areaMap.get("i"); a++) {
            for (int b = areaMap.get("z"); b < areaMap.get("j"); b++) {
                if (dirtBlocks.contains(clickedBlock.getRelative(a, 0, b).getType())) {
                    if (Slimefun.getProtectionManager().hasPermission
                            (Bukkit.getOfflinePlayer(player.getUniqueId()), clickedBlock.getRelative(a, 0, b), Interaction.INTERACT_BLOCK)) {

                        Block land = clickedBlock.getRelative(a, 0, b);
                        land.setType(Material.FARMLAND);
                        if (grass.contains(clickedBlock.getRelative(a, 1, b).getType()) || MAT.contains(clickedBlock.getRelative(a, 1, b).getType())) {
                            clickedBlock.getRelative(a, 1, b).breakNaturally();
                        } // check if grass above the farmland exist
                    } // perm check
                } // check the necessary block
            } // z axis loop
        } // x axis loop
    }

    public void harvest(Player player, Block clickedBlock, ItemStack itemStack, boolean willReplant){
        Map<String, Integer> areaMap = setValues(player);
        int k = 0;

        for (int a = areaMap.get("x"); a < areaMap.get("i"); a++) {
            for (int b = areaMap.get("z"); b < areaMap.get("j"); b++) {
                if (Tag.CROPS.isTagged(clickedBlock.getRelative(a, 0, b).getType()) &&
                        clickedBlock.getRelative(a, 0, b).getBlockData().clone() instanceof Ageable) {
                    Ageable ageable = (Ageable) clickedBlock.getRelative(a, 0, b).getBlockData().clone();
                    if (Slimefun.getProtectionManager().hasPermission
                            (Bukkit.getOfflinePlayer(player.getUniqueId()), clickedBlock.getRelative(a, 0, b), Interaction.BREAK_BLOCK)) {

                        Block block = clickedBlock.getRelative(a, 0, b);
                        Material material = block.getBlockData().getMaterial();
                        clickedBlock.getRelative(a, 0, b).breakNaturally(itemStack);
                        if(willReplant){
                            if (ageable.getAge() == ageable.getMaximumAge()) {
                                k = k + 1;
                                Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> block.setType(material), 5L);
                            }
                        }
                    } // perm check
                } // check if block is a crop
            } // z axis loop
        } // x axis loop

        if (k > 0) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Replanted " + k + " crops");
        }
    }

    public Map<String, Integer> setValues(Player player){
        Map<String, Integer> integers = new HashMap<>();
        int x = 0, z = 0;
        if (player.getFacing() == BlockFace.EAST) {
            integers.put("x", x);
            integers.put("i", 5);
            integers.put("z", -2);
            integers.put("j", 3);
        } // positive x
        else if (player.getFacing() == BlockFace.SOUTH) {
            integers.put("x", -2);
            integers.put("i", 3);
            integers.put("z", z);
            integers.put("j", 5);
        } // positive z
        else if (player.getFacing() == BlockFace.WEST) {
            integers.put("x", -4);
            integers.put("i", 1);
            integers.put("z", -2);
            integers.put("j", 3);
        } // negative X
        else if (player.getFacing() == BlockFace.NORTH) {
            integers.put("x", -2);
            integers.put("i", 3);
            integers.put("z", -4);
            integers.put("j", 1);
        } // negative z

        return integers;
    }

}