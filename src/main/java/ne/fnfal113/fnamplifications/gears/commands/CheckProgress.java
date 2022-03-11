package ne.fnfal113.fnamplifications.gears.commands;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.gears.FnBoots;
import ne.fnfal113.fnamplifications.gears.FnChestPlate;
import ne.fnfal113.fnamplifications.gears.FnHelmet;
import ne.fnfal113.fnamplifications.gears.FnLeggings;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
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
                                pdc.get(Keys.FN_GEAR_HELMET, PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(Keys.FN_GEAR_HELMET_FINAL, PersistentDataType.INTEGER));
                    }
                    if(item instanceof FnChestPlate){
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(Keys.FN_GEAR_CHEST, PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(Keys.FN_GEAR_CHEST_FINAL, PersistentDataType.INTEGER));
                    }
                    if(item instanceof FnLeggings){
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(Keys.FN_GEAR_LEGGINGS, PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(Keys.FN_GEAR_LEGGINGS_FINAL, PersistentDataType.INTEGER));
                    }
                    if(item instanceof FnBoots){
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(Keys.FN_GEAR_BOOTS, PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(Keys.FN_GEAR_BOOTS_FINAL, PersistentDataType.INTEGER));
                    }
                }
            }
        }

        return true;
    }
}
