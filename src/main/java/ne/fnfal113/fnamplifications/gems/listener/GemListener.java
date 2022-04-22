package ne.fnfal113.fnamplifications.gems.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.events.GuardianSpawnEvent;
import ne.fnfal113.fnamplifications.gems.handlers.*;
import ne.fnfal113.fnamplifications.gems.implementation.GemKeysEnum;
import ne.fnfal113.fnamplifications.gems.implementation.TargetReasonEnum;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class GemListener implements Listener {

    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onGuardianSpawn(GuardianSpawnEvent event){
       if(!(event.getDamager() instanceof Player)) {
           return;
       }
       Player player = (Player) event.getDamager();

       if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
           return;
       }
       ItemStack itemStack = player.getInventory().getItemInMainHand();

       PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();

       callGemHandler(OnGuardianSpawnHandler.class, handler -> handler.onGuardianSpawn(event, itemStack), itemStack, pdc);
    }

    public <T extends GemHandler> void callGemHandler(Class<T> clazz, Consumer<T> consumer, ItemStack itemStack, PersistentDataContainer pdc) {
        if (pdc.has(new NamespacedKey(FNAmplifications.getInstance(),
                itemStack.getType().toString().toLowerCase() + "_socket_amount"), PersistentDataType.INTEGER)) {
            for (NamespacedKey key : GemKeysEnum.GEM_KEYS_ENUM.getGEM_KEYS()) {
                if (pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = getSfItem(key, pdc);

                    if(item instanceof AbstractGem) {
                        AbstractGem gem = (AbstractGem) item;
                        if(clazz.isInstance(gem)) {
                            consumer.accept(clazz.cast(gem));
                        } // is gem instance of sub interface of GemHandler
                    } // is gem instance of AbstractGem
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event){

        Player player = event.getEntity();
        for (ItemStack armor: player.getInventory().getArmorContents()) {
            if(armor != null) {
                callGemHandler(OnPlayerDeathHandler.class, handler -> handler.onPlayerDeath(event, armor), armor, getPersistentDataContainer(armor));
            }
        }

    }

    @EventHandler
    public void onDragDrop(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        if (event.getAction() != InventoryAction.SWAP_WITH_CURSOR) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());

        if(slimefunItem instanceof AbstractGem) {
            ((AbstractGem) slimefunItem).onDrag(event, player);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity)){
            return;
        }

        if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
            return;
        }

        /*
         * If the damager is a projectile and the shooter is a player
         */
        if(event.getDamager() instanceof Projectile && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            Projectile projectile = (Projectile) event.getDamager();

            if(!(projectile.getShooter() instanceof Player)){
                return;
            }

            if(!(event.getEntity() instanceof LivingEntity)){
                return;
            }

            Player player = (Player) projectile.getShooter();
            LivingEntity livingEntity = (LivingEntity) event.getEntity();

            if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
                return;
            } // check if player is holding an item in main hand

            ItemStack itemStackHand = player.getInventory().getItemInMainHand();
            PersistentDataContainer pdcHand = getPersistentDataContainer(itemStackHand);

            callGemHandler(OnProjectileDamageHandler.class,
                    handler -> handler.onProjectileDamage(event, player, livingEntity, projectile, itemStackHand),
                    itemStackHand, pdcHand);
        }

        /*
         * If the damager is a player and victim is a living entity
         */
        if(event.getDamager() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Player player = (Player) event.getDamager();

            if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return;
            } // check if player is holding an item in main hand

            ItemStack itemStackHand = player.getInventory().getItemInMainHand();
            PersistentDataContainer pdcHand = getPersistentDataContainer(itemStackHand);

            callGemHandler(OnDamageHandler.class, handler -> handler.onDamage(event, itemStackHand), itemStackHand, pdcHand);

            if(event.getEntity().getPersistentDataContainer().has(Keys.GUARDIAN_KEY, PersistentDataType.STRING)){
                if(Objects.equals(event.getEntity().getPersistentDataContainer().get(Keys.GUARDIAN_KEY, PersistentDataType.STRING), player.getName())) {
                    event.setCancelled(true);
                } else if(!Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()),
                        event.getEntity().getLocation(), Interaction.ATTACK_ENTITY)) {
                    event.setCancelled(true);
                }
            } // check if entity is a guardian and pdc data is same as the owner then cancel event
        }

        /*
         * If the victim is a player and damager is a living entity or may be projectile
         */
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            for (ItemStack armor: player.getInventory().getArmorContents()) {
                if(armor != null) {
                    callGemHandler(OnDamageHandler.class, handler -> handler.onDamage(event, armor), armor, getPersistentDataContainer(armor));
                }
            }
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.isCancelled()){
            return;
        }

        Player player = event.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR){
            return;
        }

        if(event.getBlock().getState() instanceof Container){
            return;
        }

        if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        PersistentDataContainer pdc = getPersistentDataContainer(itemStack);

        callGemHandler(OnBlockBreakHandler.class, handler -> handler.onBlockBreak(event, player), itemStack, pdc);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getHand() != EquipmentSlot.HAND){
            return;
        }

        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        Player player = event.getPlayer();

        if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        PersistentDataContainer pdc = getPersistentDataContainer(itemStack);

        callGemHandler(OnRightClickHandler.class, handler -> handler.onRightClick(player), itemStack, pdc);
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event){
        if(event.isCancelled()){
            return;
        }
        PersistentDataContainer pdc = getPersistentDataContainer(event.getItem());

        callGemHandler(OnItemDamageHandler.class, handler -> handler.onDurabilityChange(event), event.getItem(), pdc);
    }

    @EventHandler
    public void onMobTarget(EntityTargetLivingEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity)){
            return;
        }

        if(event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();

            if (event.getTarget() instanceof Player) {
                Player player = (Player) event.getTarget();

                if (!zombie.getPersistentDataContainer().has(Keys.GUARDIAN_KEY, PersistentDataType.STRING)) {
                    return;
                }

                if(Objects.equals(zombie.getPersistentDataContainer()
                        .get(Keys.GUARDIAN_KEY, PersistentDataType.STRING), player.getName())){
                    event.setCancelled(true);
                }
                if(TargetReasonEnum.PLAYER_TARGET.isTagged(event.getReason())) {
                    event.setTarget(null);
                    event.setCancelled(true);
                }

            } // check if target is player

            if(event.getTarget() instanceof Zombie){
                Zombie zombieTarget = (Zombie) event.getTarget();

                if(zombieTarget.getPersistentDataContainer().has(Keys.GUARDIAN_KEY, PersistentDataType.STRING)
                        && TargetReasonEnum.ZOMBIE_TARGET.isTagged(event.getReason())) {
                   event.setCancelled(true);
                }
            } // check if target is zombie

        } // check if zombie entity
    }

    @Nullable
    public SlimefunItem getSfItem(NamespacedKey key, PersistentDataContainer pdc){
        return SlimefunItem.getById(Objects.requireNonNull(pdc.get(key, PersistentDataType.STRING)));
    }

    public PersistentDataContainer getPersistentDataContainer(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        Validate.notNull(meta, "Meta must not be null! Item type (for debugging): " + itemStack.getType());
        return meta.getPersistentDataContainer();
    }

}