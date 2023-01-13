package ne.fnfal113.fnamplifications.gears.commands;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.gears.abstracts.AbstractGears;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class GearCommands implements TabExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (args.length == 0) {
            Player player = (Player) sender;

            player.sendMessage(Utils.colorTranslator("&d◬◬◬◬◬◬◬◬◬◬ &c&lFN Gear Progress &6◬◬◬◬◬◬◬◬◬◬"));
            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (itemStack != null) {
                    SlimefunItem item = SlimefunItem.getByItem(itemStack);
                    PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

                    if (item instanceof AbstractGears) {
                        AbstractGears gear = (AbstractGears) item;
                        player.sendMessage(Utils.colorTranslator(item.getItemName() + ": &e" +
                                pdc.get(gear.getDefaultUsageKey(), PersistentDataType.INTEGER)) +
                                "/" +
                                pdc.get(gear.getDefaultUsageKey3(), PersistentDataType.INTEGER));
                    }
                }
            }
        } else if (args.length == 1) {
            Player player = (Player) sender;

            if (args[0].equalsIgnoreCase("levelupgears")) {
                if(!player.hasPermission("fngear.levelupgears")){
                    player.sendMessage(Utils.colorTranslator("Access denied! missing permission node: fngear.levelupgears"));
                    return true;
                }

                for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                    if (itemStack != null) {
                        SlimefunItem item = SlimefunItem.getByItem(itemStack);
                        ItemMeta meta = itemStack.getItemMeta();
                        List<String> lore = meta.getLore();
                        PersistentDataContainer progress = meta.getPersistentDataContainer();

                        if (item instanceof AbstractGears) {
                            AbstractGears fnGear = (AbstractGears) item;
                            int armorLevel = progress.getOrDefault(fnGear.getDefaultUsageKey2(), PersistentDataType.INTEGER, 0);
                            int maxReq = progress.getOrDefault(fnGear.getDefaultUsageKey3(), PersistentDataType.INTEGER, fnGear.getStartingProgress());

                            fnGear.getGearTask().levelUpArmour(armorLevel, maxReq, maxReq, itemStack, meta, progress, lore, player);
                            player.sendMessage(Utils.colorTranslator("&6Successfully leveled up " + fnGear.getItemName() + " &6to " +
                                    progress.get(fnGear.getDefaultUsageKey2(), PersistentDataType.INTEGER)));

                            fnGear.upgradeArmor(itemStack, progress.getOrDefault(fnGear.getDefaultUsageKey2(), PersistentDataType.INTEGER, 0), player, fnGear.getEquipmentSlot());
                        }
                    }
                }
            }
        } // if args length == 1

        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, String[] args) {
        List<String> levelUpArg = new ArrayList<>();
        if(args.length == 1){
            levelUpArg.add("levelUpGears");
        }

        return levelUpArg;
    }
}
