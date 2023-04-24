package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.OnRightClickHandler;
import ne.fnfal113.fnamplifications.gems.implementation.ThrowWeaponTask;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class DamnationGem extends AbstractGem implements OnRightClickHandler {

    @Getter
    private final Map<UUID, Integer> currentWeaponMap = new HashMap<>();

    public DamnationGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if ((WeaponArmorEnum.SWORDS.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.AXES.isTagged(itemStackToSocket.getType()))) {
            bindGem(slimefunGemItem, itemStackToSocket, player);
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes and swords only"));
        }
    }

    @Override
    public void onRightClick(Player player){
        if(!player.isSneaking()){
            return;
        }
        if(!hasPermissionToThrow(player)){
            player.sendMessage(Utils.colorTranslator("&eYou don't have the permission to use damnation here! (Needs block interaction flag enabled)"));
            return;
        } // check if player has the permission to build on current location

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemStack.getItemMeta();

        // check if player has less than 4 floating weapons
        if(isBelowWeaponLimit(player)) {
            PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

            try{
                String pdcValue = pdc.getOrDefault(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "false");

                // creates throw a task from this object instance
                ThrowWeaponTask throwWeaponTask = new ThrowWeaponTask(player, itemStack.clone(), false, false, pdcValue.equalsIgnoreCase("true"));
                floatAndThrowWeapon(throwWeaponTask);
            } catch (IllegalArgumentException e){
                pdc.set(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "true");
                itemStack.setItemMeta(meta);

                return;
            }

            itemStack.setAmount(0);
        }
    }

    public boolean isBelowWeaponLimit(Player player){
        if(!getCurrentWeaponMap().containsKey(player.getUniqueId())){
            getCurrentWeaponMap().put(player.getUniqueId(), 0);
        }

        if(getCurrentWeaponMap().get(player.getUniqueId()) < 4){
            getCurrentWeaponMap().put(player.getUniqueId(), getCurrentWeaponMap().get(player.getUniqueId()) + 1);

            return true;
        } else{
            player.sendMessage(Utils.colorTranslator("&eLimit reached! You can only have 4 weapons simultaneously"));

            return false;
        }
    }

    public void floatAndThrowWeapon(ThrowWeaponTask throwWeaponTask){ // used by damnation gem
        ArmorStand as = throwWeaponTask.getArmorStand();
        Player player = throwWeaponTask.getPlayer();

        // make the floating item move in the direction of every corner of a square
        int id = Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), () -> {
            double xFinal = ThreadLocalRandom.current().nextInt(3) == 2 ? 1.2 : -1.2;
            double zFinal = ThreadLocalRandom.current().nextInt(3) == 2 ? 1.2 : -1.2;

            as.teleport(player.getLocation().clone().add(xFinal, 0.8, zFinal));
        }, 5L, 12L).getTaskId();

        Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
            getCurrentWeaponMap().put(player.getUniqueId(), getCurrentWeaponMap().get(player.getUniqueId()) - 1);

            Bukkit.getScheduler().cancelTask(id);

            throwWeaponTask.resetArmorstandArmPos();

            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () ->{
                throwWeaponTask.setVector(player.getLocation().add(player.getLocation().getDirection().multiply(9).normalize())
                        .subtract(player.getLocation().toVector()).toVector());
                throwWeaponTask.centeredThrow();
                throwWeaponTask.runTaskTimer(FNAmplifications.getInstance(), 0L, 1L);
            }, 1L);
        }, 160L);

    }

}