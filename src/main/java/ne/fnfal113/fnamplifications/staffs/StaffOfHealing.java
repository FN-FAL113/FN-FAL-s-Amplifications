package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.staffs.implementations.AreaOfEffectStaffTask;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffOfHealing extends AbstractStaff {

    public StaffOfHealing(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10, Keys.createKey("healingstaff"));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        ItemStack item = player.getInventory().getItemInMainHand();
        
        Block block = event.getPlayer().getTargetBlockExact(50);

        if(block == null || item.getType() == Material.AIR) return;

        if(!hasPermissionToCast(item.getItemMeta().getDisplayName(), player, block.getLocation())) return;

        ItemMeta meta = item.getItemMeta();

        getStaffTask().updateMeta(item, meta, player);

        AreaOfEffectStaffTask cloudStaff = new AreaOfEffectStaffTask(player, block, "FN_HEALING", 2.85F, 160, Particle.HEART, Keys.createKey("cloudfn"));
        cloudStaff.spawnCloud();
    }

}