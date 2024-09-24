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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffOfAirStrider extends AbstractStaff {

    private final Map<UUID, AirStriderTask> taskMap = new HashMap<>();
    private final Map<UUID, Long> playerCooldowMap = new HashMap<>();

    public StaffOfAirStrider(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10, Keys.createKey("airstriderstaff"));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        ItemStack item = player.getInventory().getItemInMainHand();

        if(taskMap.get(player.getUniqueId()) != null) {
            Utils.sendMessage("Air strider is not yet expired!", player);
            
            return;
        } else if(hasPermissionToCast(item.getItemMeta().getDisplayName(), player, player.getLocation())) {
            Utils.sendMessage("You can now walk on air for 10 seconds", player);
            
            playerCooldowMap.put(player.getUniqueId(), System.currentTimeMillis());

            taskMap.put(player.getUniqueId(), new AirStriderTask(player));
            taskMap.get(player.getUniqueId()).runTaskTimer(FNAmplifications.getInstance(), 0, 1L);
        } else {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        getStaffTask().updateMeta(item, meta, player);

        Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task -> {
            Long timer = Utils.cooldownHelper(playerCooldowMap.get(player.getUniqueId()));

            if(timer >= 7) {
                Utils.sendMessage("Air strider will expire in" + (12 - timer) + " seconds!", player);
            }

            if(timer >= 12 || !player.isOnline()) {
                Utils.sendMessage("Air Strider has expired!", player);

                playerCooldowMap.remove(player.getUniqueId());

                taskMap.get(player.getUniqueId()).setDone(true);;
                taskMap.remove(player.getUniqueId());
                
                task.cancel();
            }
        }, 0L, 20L);
    }
}
