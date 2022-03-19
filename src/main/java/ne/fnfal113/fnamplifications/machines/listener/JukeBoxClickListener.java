package ne.fnfal113.fnamplifications.machines.listener;

import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class JukeBoxClickListener implements Listener {

    @EventHandler
    public void onDiscClick(InventoryClickEvent event){
        if(event.getView().getTitle().contains(Utils.colorTranslator("&5F&dN &fJ&bu&ek&ce&5b&do&4x"))){
            if(event.getCurrentItem() != null) {
                ItemStack itemStack = event.getCurrentItem();
                if (Tag.ITEMS_MUSIC_DISCS.isTagged(itemStack.getType()) && itemStack.getItemMeta().hasEnchant(Enchantment.BINDING_CURSE)) {
                    event.setCancelled(true);
                }
                if(itemStack.getType() == Material.PINK_STAINED_GLASS_PANE){
                    event.setCancelled(true);
                }
                if(!Tag.ITEMS_MUSIC_DISCS.isTagged(itemStack.getType())){
                    event.setCancelled(true);
                }
            }
        }

    }

}
