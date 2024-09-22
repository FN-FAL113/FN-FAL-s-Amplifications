package ne.fnfal113.fnamplifications.machines.listener;

import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.compatibility.VersionedClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class JukeBoxClickListener implements Listener {

    @EventHandler
    public void onDiscClick(InventoryClickEvent event) {
        String title = VersionedClass.invoke(event.getView(), "getTitle").toString();

        if(title.contains(Utils.colorTranslator("&5F&dN &fJ&bu&ek&ce&5b&do&4x"))) {
            if(event.getCurrentItem() != null) {
                ItemStack itemStack = event.getCurrentItem();

                if(!itemStack.getType().name().startsWith("MUSIC_DISC") || itemStack.getItemMeta().hasEnchant(Enchantment.BINDING_CURSE) || itemStack.getType() == Material.PINK_STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                }
            }
        }
      
    }

}
