package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ArrowAvertGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public ArrowAvertGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 14);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if (WeaponArmorEnum.HELMET.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.CHESTPLATE.isTagged(itemStackToSocket.getType()) ||
        WeaponArmorEnum.LEGGINGS.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.BOOTS.isTagged(itemStackToSocket.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on armors only"));
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(event.isCancelled()){
            return;
        }

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId()) &&
                event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            if(event.getEntity() instanceof Player) {
                sendGemMessage((Player) event.getEntity(), this.getItemName());
            }
            event.setCancelled(true);
        }
    }

}