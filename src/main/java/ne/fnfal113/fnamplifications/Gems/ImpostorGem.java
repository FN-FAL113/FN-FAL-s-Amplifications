package ne.fnfal113.fnamplifications.Gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.ConfigValues.ReturnConfValue;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gems.Interface.GemImpl;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnGemAltar;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ThreadLocalRandom;

public class ImpostorGem extends SlimefunItem implements GemImpl {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private static final ReturnConfValue value = new ReturnConfValue();

    public ImpostorGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){

        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());
        if(slimefunItem != null && currentItem != null && HELMET.contains(currentItem.getType())){
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

    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            if (ThreadLocalRandom.current().nextInt(100) < value.impostorGem() && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                double nX;
                double nZ;
                float nang = victim.getLocation().getYaw() + 90;

                if (nang < 0) nang += 360;

                nX = Math.cos(Math.toRadians(nang));
                nZ = Math.sin(Math.toRadians(nang));

                Location newDamagerLoc = new Location(victim.getWorld(), damager.getLocation().getX() - nX,
                        damager.getLocation().getY(), damager.getLocation().getZ() - nZ, damager.getLocation().getYaw(), damager.getLocation().getPitch());
                victim.teleport(newDamagerLoc);
            }
        }

    }

    public static void setup(){
        new ImpostorGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_IMPOSTOR, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.EARTH_RUNE, 4), new SlimefunItemStack(SlimefunItems.COMMON_TALISMAN, 1), new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 4),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.AIR_RUNE, 4), new SlimefunItemStack(SlimefunItems.COMMON_TALISMAN, 1), SlimefunItems.WATER_RUNE})
                .register(plugin);
    }
}
