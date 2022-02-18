package ne.fnfal113.fnamplifications.Gears.Commands;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gears.FnBoots;
import ne.fnfal113.fnamplifications.Gears.FnChestPlate;
import ne.fnfal113.fnamplifications.Gears.FnHelmet;
import ne.fnfal113.fnamplifications.Gears.FnLeggings;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
public class CheckProgress implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if(args.length == 0){
            Player player = (Player) sender;

            player.sendMessage(Utils.colorTranslator("&d◬◬◬◬◬◬◬◬◬◬ &c&lFN Gear Progress &6◬◬◬◬◬◬◬◬◬◬"));
            for(ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (itemStack != null) {
                    SlimefunItem item = SlimefunItem.getByItem(itemStack);
                    PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

                    if(item instanceof FnHelmet){
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "helmet"), PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "helmetfinal"), PersistentDataType.INTEGER));
                    }
                    if(item instanceof FnChestPlate){
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "armor"), PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "armorfinal"), PersistentDataType.INTEGER));
                    }
                    if(item instanceof FnLeggings){
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "leggings"), PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "leggingsfinal"), PersistentDataType.INTEGER));
                    }
                    if(item instanceof FnBoots){
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "boots"), PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(new NamespacedKey(FNAmplifications.getInstance(), "boostfinal"), PersistentDataType.INTEGER));
                    }
                }
            }
        }

        return true;
    }
}
