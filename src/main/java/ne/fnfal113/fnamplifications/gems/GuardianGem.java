package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.events.GuardianSpawnEvent;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.gems.implementation.GuardianTask;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Flying;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class GuardianGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    private final Map<UUID, Zombie> entityUUIDMap = new HashMap<>();
    private final Map<UUID, BukkitTask> runnableMap = new HashMap<>();

    public GuardianGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 11);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if ((WeaponArmorEnum.CHESTPLATE.isTagged(itemStackToSocket.getType()))) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            Utils.sendMessage("Invalid item to socket! Gem works on chestplate only", player);
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(event.isCancelled()) return;

        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if(event.getDamager() instanceof Phantom || event.getDamager() instanceof Flying || event.getDamager() instanceof EnderDragon) {
            return;
        } // prevent guardian from attacking flying entities

        if(runnableMap.containsKey(player.getUniqueId())) {
            if(runnableMap.get(player.getUniqueId()).isCancelled()) {
                entityUUIDMap.remove(player.getUniqueId());
                runnableMap.remove(player.getUniqueId());
                return;
            } // remove the map when the runnable associated with the UUID is cancelled
        }

        if(!entityUUIDMap.containsKey(player.getUniqueId())) {
            if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId())) {
                GuardianTask guardianTask = new GuardianTask(player, event);
                GuardianSpawnEvent guardianSpawnEvent = new GuardianSpawnEvent(player, guardianTask.getZombie(), event.getDamager());
                Bukkit.getPluginManager().callEvent(guardianSpawnEvent);

                if(guardianSpawnEvent.isCancelled()) {
                    guardianSpawnEvent.getZombieGuardian().remove();

                    return;
                }

                sendGemMessage(player, this.getItemName());
                runnableMap.put(player.getUniqueId(),
                        guardianTask.runTaskTimer(FNAmplifications.getInstance(), 5L, 3L));
                entityUUIDMap.put(player.getUniqueId(), guardianTask.getZombie());
            } // make a guardian runnable for the player using UUID and a new instance of the guardian task
        } else {
            Zombie zombie = entityUUIDMap.get(player.getUniqueId());

            if(zombie.getTarget() != null && !zombie.getTarget().isDead()) {
                return;
            } // don't change target if the current target is not yet dead

            boolean isDamagerLivingEntity = event.getDamager() instanceof LivingEntity;

            // if the damager has a guardian, attack the owner of the guardian instead
            if(isDamagerLivingEntity && event.getDamager().getPersistentDataContainer().has(Keys.GUARDIAN_KEY, PersistentDataType.STRING)) {
                zombie.setTarget(Bukkit.getPlayer(event.getDamager().getPersistentDataContainer().get(Keys.GUARDIAN_KEY, PersistentDataType.STRING)));
            } else {
                if(event.getDamager() instanceof Projectile) {
                    if(((Projectile) event.getDamager()).getShooter() instanceof LivingEntity) {
                        zombie.setTarget((LivingEntity) ((Projectile) event.getDamager()).getShooter());
                    }
                } else if (isDamagerLivingEntity) {
                    zombie.setTarget((LivingEntity) event.getDamager());
                }
            } // target the damager, will attack projectile shooter

        } // guardian targets the entity that damaged the player
    }

}