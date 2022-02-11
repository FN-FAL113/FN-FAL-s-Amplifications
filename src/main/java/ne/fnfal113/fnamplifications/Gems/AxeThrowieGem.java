package ne.fnfal113.fnamplifications.Gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gems.Interface.GemImpl;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnGemAltar;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class AxeThrowieGem extends SlimefunItem implements GemImpl {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    public AxeThrowieGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){

        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());
        if(slimefunItem != null && currentItem != null && AXE.contains(currentItem.getType())){
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
            } else {
                player.sendMessage(Utils.colorTranslator("&eOnly 3 gems per item is allowed!"));
                player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1.0F, 1.0F);
            }
            event.setCancelled(true);
        }

    }

    @Override
    public int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack){
        return pdc.getOrDefault(
                new NamespacedKey(FNAmplifications.getInstance(), itemStack.getType().toString().toLowerCase() + "_socket_amount"),
                PersistentDataType.INTEGER, 0);
    }

    public void onRightClick(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        throwAxe(itemStack.clone(), player);

        itemStack.setAmount(0);
    }

    public void throwAxe(ItemStack itemStack, Player player){
        ArmorStand as = player.getWorld().spawn(player.getLocation().add(0, 0.9, 0), ArmorStand.class, armorStand ->{
            armorStand.setArms(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            armorStand.setMarker(true);
            Objects.requireNonNull(armorStand.getEquipment()).setItemInMainHand(itemStack);
        });

        Vector vector = player.getLocation().add(player.getLocation().getDirection().multiply(9).normalize())
                .subtract(player.getLocation().toVector()).toVector();

        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () ->
                Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task -> {
                    as.teleport(as.getLocation().add(vector));

                    RayTraceResult result = as.rayTraceBlocks(0.25);
                    List<Entity> entityList = as.getNearbyEntities(0.25, 0.25, 0.25);

                    double rightArmPose = as.getRightArmPose().getX();
                    as.setRightArmPose(new EulerAngle(rightArmPose + 0.40D, 0.00D, 0.00D));

                    if(result != null && result.getHitBlock().getType() != Material.GRASS && !Tag.FLOWERS.isTagged(result.getHitBlock().getType())){
                        player.sendMessage(axeTask(as, player, task, itemStack));
                        return;
                    }
                    if(!entityList.isEmpty() && !entityList.contains(player)){
                        for(int i = 0; i < entityList.size(); i++){
                            if(entityList.get(i) instanceof Damageable && entityList.get(i).getUniqueId() != player.getUniqueId()){
                                if(Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()), entityList.get(i).getLocation(), Interaction.BREAK_BLOCK)) {
                                    ((Damageable) entityList.get(i)).damage(ThreadLocalRandom.current().nextInt(100) < 35 ? 8 : 4);
                                }
                            }
                        }
                        axeTask(as, player, task, itemStack);
                        return;
                    }
                    if(!as.getLocation().getChunk().isEntitiesLoaded()){
                       player.sendMessage(Utils.colorTranslator("&eYour axe has reached an unloaded chunk, " + axeTask(as, player, task, itemStack)));
                    }
                }, 0L, 2L),
                3L);

    }

    public String axeTask(ArmorStand as, Player player ,BukkitTask task, ItemStack itemStack){
        Item droppedItem = as.getWorld().dropItem(as.getLocation(), itemStack.clone());
        Location locInfo = droppedItem.getLocation();
        droppedItem.setOwner(player.getUniqueId());
        droppedItem.setGlowing(true);
        as.remove();
        task.cancel();

        return Utils.colorTranslator("&eAxe dropped near at " +
                "x: " + (int) locInfo.getX() + ", " +
                "y: " + (int) locInfo.getY() + ", " +
                "z: " + (int) locInfo.getZ());
    }

    public static void setup(){
        new AxeThrowieGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_AXETHROWIE, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3),  SlimefunItems.TALISMAN_WARRIOR,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_WARRIOR, new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 2),  SlimefunItems.TALISMAN_WARRIOR})
                .register(plugin);
    }
}