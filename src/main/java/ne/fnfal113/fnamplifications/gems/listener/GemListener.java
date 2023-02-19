package ne.fnfal113.fnamplifications.gems.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.events.GuardianSpawnEvent;
import ne.fnfal113.fnamplifications.gems.handlers.*;
import ne.fnfal113.fnamplifications.gems.implementation.GemKeysEnum;
import ne.fnfal113.fnamplifications.gems.implementation.TargetReasonEnum;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
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
import java.util.Optional;
import java.util.function.Consumer;

public class GemListener implements Listener {

    /**
     *
     * @param clazz the interface class for handling such methods
     * @param consumer the consumer that consumes a function based from the given class interface
     * @param itemStack the itemstack bound with gem
     * @param pdc the persistent data container of the itemstack bound with gem
     * @param p the player involved
     */
    public <T extends GemHandler> void callGemHandler(Class<T> clazz, Consumer<T> consumer, ItemStack itemStack, PersistentDataContainer pdc, Player p) {
        if (pdc.has(Keys.createKey(itemStack.getType().toString().toLowerCase() + "_socket_amount"), PersistentDataType.INTEGER)) {
            for (NamespacedKey key : GemKeysEnum.GEM_KEYS.getGemKeyList()) {
                if (pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = getSfItem(key, pdc);

                    if(item instanceof AbstractGem) {
                        // consumer requires an instance of the implementing class
                        AbstractGem gem = (AbstractGem) item;

                        if(clazz.isInstance(gem)) {
                            if(gem.isEnabledInCurrentWorld(p.getWorld().getName())) {
                                consumer.accept(clazz.cast(gem));
                            } else {
                                p.sendMessage(Utils.colorTranslator(gem.getItemName() + "&6 is disabled in your current world!"));
                            }
                        } // is gem an instance of the given interface class
                    } // is gem instance of AbstractGem
                }
            }
        }
    }

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

       callGemHandler(OnGuardianSpawnHandler.class, handler -> handler.onGuardianSpawn(event, itemStack), itemStack, pdc, player);
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event){

        Player player = event.getEntity();
        for (ItemStack armor: player.getInventory().getArmorContents()) {
            if(armor != null) {
                callGemHandler(OnPlayerDeathHandler.class, handler -> handler.onPlayerDeath(event, armor), armor, getPersistentDataContainer(armor), player);
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

        Optional<ItemStack> gemItem = Optional.ofNullable(event.getCursor());

        if(!gemItem.isPresent()){
            return;
        }

        Optional<SlimefunItem> slimefunGemItem = Optional.ofNullable(SlimefunItem.getByItem(gemItem.get()));
        Optional<ItemStack> currentItem = Optional.ofNullable(event.getCurrentItem());

        if(currentItem.isPresent() && slimefunGemItem.isPresent() && slimefunGemItem.get() instanceof AbstractGem) {
            ((AbstractGem) slimefunGemItem.get()).onDrag(player, slimefunGemItem.get(), gemItem.get(), currentItem.get());

            event.setCancelled(true);
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

        // if the damager is a projectile and the shooter is a player
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
                    itemStackHand, pdcHand, player);
        }

        // if the damager is a player and victim is a living entity
        if(event.getDamager() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Player player = (Player) event.getDamager();

            if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return;
            } // check if player is holding an item in main hand

            ItemStack itemStackHand = player.getInventory().getItemInMainHand();
            PersistentDataContainer pdcHand = getPersistentDataContainer(itemStackHand);

            callGemHandler(OnDamageHandler.class, handler -> handler.onDamage(event, itemStackHand), itemStackHand, pdcHand, player);

            if(event.getEntity().getPersistentDataContainer().has(Keys.GUARDIAN_KEY, PersistentDataType.STRING)){
                if(Objects.equals(event.getEntity().getPersistentDataContainer().get(Keys.GUARDIAN_KEY, PersistentDataType.STRING), player.getName())) {
                    event.setCancelled(true);
                } else if(!Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()),
                        event.getEntity().getLocation(), Interaction.ATTACK_ENTITY)) {
                    event.setCancelled(true);
                }
            } // check if entity is a guardian and pdc data is same as the owner then cancel event
        }

        // if the victim is a player and damager is a living entity or may be projectile
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            for (ItemStack armor: player.getInventory().getArmorContents()) {
                if(armor != null) {
                    callGemHandler(OnDamageHandler.class, handler -> handler.onDamage(event, armor), armor, getPersistentDataContainer(armor), player);
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

        callGemHandler(OnBlockBreakHandler.class, handler -> handler.onBlockBreak(event, player, itemStack), itemStack, pdc, player);
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

        callGemHandler(OnRightClickHandler.class, handler -> handler.onRightClick(player), itemStack, pdc, player);
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event){
        if(event.isCancelled()){
            return;
        }
        PersistentDataContainer pdc = getPersistentDataContainer(event.getItem());

        callGemHandler(OnItemDamageHandler.class, handler -> handler.onDurabilityChange(event), event.getItem(), pdc, event.getPlayer());
    }

    @EventHandler
    public void onMobTarget(EntityTargetLivingEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity)){
            return;
        }

        // specifically used by guardian gem to prevent targeting the owner
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

    @EventHandler
    public void entityBlockChange(EntityChangeBlockEvent event){
        if (event.getEntity().getType() == EntityType.FALLING_BLOCK && event.getEntity().hasMetadata("shockwave_gem")) {
            event.setCancelled(true);
            event.getEntity().remove();
        }
    }

    @Nullable
    public SlimefunItem getSfItem(NamespacedKey key, PersistentDataContainer pdc){
        return SlimefunItem.getById(Objects.requireNonNull(pdc.get(key, PersistentDataType.STRING)));
    }

    public PersistentDataContainer getPersistentDataContainer(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();

        return meta.getPersistentDataContainer();
    }

}