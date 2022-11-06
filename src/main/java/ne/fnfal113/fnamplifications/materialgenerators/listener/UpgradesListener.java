package ne.fnfal113.fnamplifications.materialgenerators.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ne.fnfal113.fnamplifications.materialgenerators.implementations.CustomMaterialGenerator;
import ne.fnfal113.fnamplifications.materialgenerators.upgrades.abstracts.AbstractUpgrades;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class UpgradesListener implements Listener {

    @EventHandler
    public void onUpgradeClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);

        if(event.getHand() == EquipmentSlot.OFF_HAND){
            return;
        }

        if(event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
            return;
        }

        Block block = event.getClickedBlock();

        if(itemStack.getType() == Material.AIR) {
            return;
        }

        SlimefunItem sfBlock = BlockStorage.check(block);
        if(!(sfBlock instanceof CustomMaterialGenerator)){
            return;
        }

        CustomMaterialGenerator matGen = (CustomMaterialGenerator) sfBlock;

        if(sfItem instanceof AbstractUpgrades) {
            AbstractUpgrades upgrader = (AbstractUpgrades) sfItem;
           if(upgrader.upgradeMaterialGenerator(block, player, matGen)) {
               itemStack.setAmount(itemStack.getAmount() - 1);

               player.sendMessage(upgrader.getUpgradeMessage());
           }

            event.setCancelled(true);
        }

    }

}
