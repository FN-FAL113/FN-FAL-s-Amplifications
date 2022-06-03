package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.staffs.implementations.AirStriderTask;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class StaffOfAirStrider extends AbstractStaff {

    private final Map<UUID, BukkitTask> taskMap = new HashMap<>();

    public StaffOfAirStrider(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10, Keys.createKey("airstriderstaff"));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(taskMap.containsKey(player.getUniqueId())) {
            if(!taskMap.get(player.getUniqueId()).isCancelled()){
                player.sendMessage(Utils.colorTranslator("&6Air strider is not yet expired!"));
            }
            return;
        } else {
            if(hasPermissionToCast(item.getItemMeta().getDisplayName(), player, player.getLocation())) {
                player.sendMessage(Utils.colorTranslator("&dYou can now walk on the air for 10 seconds"));
                taskMap.put(player.getUniqueId(), new AirStriderTask(player).runTaskTimer(FNAmplifications.getInstance(), 0, 1L));
            } else{
                return;
            }
        }

        ItemMeta meta = item.getItemMeta();

        getStaffTask().updateMeta(item, meta, player);

        AtomicInteger i = new AtomicInteger(10);
        Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task -> {
            if(i.get() <= 5){
                player.sendMessage(Utils.colorTranslator("&dAir strider will expire in ") + i + " seconds");
            }
            if(i.get() == 0){
                player.sendMessage(Utils.colorTranslator("&dAir Strider has expired!"));
                taskMap.get(player.getUniqueId()).cancel();
                taskMap.remove(player.getUniqueId());
                task.cancel();
            }
            i.getAndDecrement();
        },0L, 21L);

    }
}