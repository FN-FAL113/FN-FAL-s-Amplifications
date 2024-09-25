package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ImpostorGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public ImpostorGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 14);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket) {
        if (WeaponArmorEnum.HELMET.isTagged(itemStackToSocket.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            Utils.sendMessage("Invalid item to socket! Gem works on helmets only", player);
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(event.isCancelled()) return;

        if(!(event.getEntity() instanceof Player)) return;
        
        if(!(event.getDamager() instanceof LivingEntity)) return;

        Player player = (Player) event.getEntity();
        LivingEntity damager = (LivingEntity) event.getDamager();

        if(ThreadLocalRandom.current().nextInt(100) < (getChance() / getTier(itemStack, this.getId())) &&
            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                double nX;
                double nZ;
                float yow = damager.getLocation().getYaw();
                
                float angleDeg = yow < 0 ? 180 + yow : yow + 180;

                // Yaw in-game has an offset degree of -90, so adding 90 degrees translates properly to degrees
                // minecraft, why make this sht confusing, so 0 yaw is east but shows south in-game
                // might be mc and spigot api not having an aligned coordinate system 
                nX = Math.cos(Math.toRadians(angleDeg + 90));
                nZ = Math.sin(Math.toRadians(angleDeg + 90));

                Location newDamagerLoc = new Location(
                    damager.getWorld(), 
                    damager.getLocation().getX() + (nX),
                    damager.getLocation().getY(), 
                    damager.getLocation().getZ() + (nZ),
                    damager.getLocation().getYaw(),
                    damager.getLocation().getPitch()
                );

                player.teleport(newDamagerLoc.clone());
            
            sendGemMessage(player, this.getItemName());
        } // teleport behind the attacker

    }

}