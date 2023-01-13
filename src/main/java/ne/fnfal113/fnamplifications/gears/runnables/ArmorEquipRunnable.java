package ne.fnfal113.fnamplifications.gears.runnables;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gears.FnChestPlate;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorEquipRunnable implements Runnable {

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.isValid() || p.isDead()) {
                continue;
            }

            ItemStack itemStack = p.getInventory().getChestplate();

            if(SlimefunItem.getByItem(itemStack) instanceof FnChestPlate){
                if(getArmorLevel(itemStack) >= 30){
                    // add potion effect synchronously to prevent api level async usage errors
                    Bukkit.getScheduler().runTask(FNAmplifications.getInstance(), () ->
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 600, 1, false, false, false))
                    );
                } // armor level check
            } // instanceof FnChestPlate
        } // for each online players

    }

    /**
     * Get the current level of the armor through pdc
     * @param itemStack the item to check
     * @return the persistent data armor level
     */
    public int getArmorLevel(ItemStack itemStack){
        if(itemStack.getItemMeta() == null){
            return 0;
        }

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.getOrDefault(Keys.createKey("armorlevel"), PersistentDataType.INTEGER, 0);
    }

}
