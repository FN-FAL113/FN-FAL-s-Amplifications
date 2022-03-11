package ne.fnfal113.fnamplifications.gems.listener;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.implementation.TargetReasonEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnArrowHitHandler;
import ne.fnfal113.fnamplifications.gems.handlers.OnBlockBreakHandler;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.gems.handlers.OnRightClickHandler;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class GemListener implements Listener {

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
         * If the damager is a player and victim is a living entity
         */
        if(event.getDamager() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Player player = (Player) event.getDamager();

            if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack itemStackHand = player.getInventory().getItemInMainHand();
                PersistentDataContainer pdcHand = getPersistentDataContainer(itemStackHand);

                hasDamageHandler(event, pdcHand);
            } // check if player is holding an item in main hand

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
         * If the victim is a player and damager is a living entity
         */
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(player.getInventory().getChestplate() != null) {
                ItemStack itemStackChest = player.getInventory().getChestplate();
                PersistentDataContainer pdcChest = getPersistentDataContainer(itemStackChest);

                hasDamageHandler(event, pdcChest);
            } // check if player is wearing a chestplate

            if(player.getInventory().getHelmet() != null) {
                ItemStack itemStackHelmet = player.getInventory().getHelmet();
                PersistentDataContainer pdcHelmet = getPersistentDataContainer(itemStackHelmet);

                hasDamageHandler(event, pdcHelmet);
            } // check if player is wearing a helmet
        }

    }

    public void hasDamageHandler(EntityDamageByEntityEvent event, PersistentDataContainer pdc){
        if (!pdc.isEmpty()) {
            for (NamespacedKey key : pdc.getKeys()) {
                if (pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = getSfItem(key, pdc);

                    if (item instanceof OnDamageHandler) {
                        ((OnDamageHandler) item).onDamage(event);
                    }
                } // make sure pdc type is string only
            } // loop all pdc keys inside the item
        } // pdc check
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
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

        hasBlockBreakHandler(event, pdc, player);
    }

    public void hasBlockBreakHandler(BlockBreakEvent event, PersistentDataContainer pdc, Player player) {
        if (!pdc.isEmpty()) {
            for (NamespacedKey key : pdc.getKeys()) {
                if (pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = getSfItem(key, pdc);

                    if (item instanceof OnBlockBreakHandler) {
                        ((OnBlockBreakHandler) item).onBlockBreak(event, player);
                    }
                } // make sure pdc type is string only
            } // loop all pdc keys inside the item
        } // pdc check
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event){
        if(!(event.getEntity() instanceof Arrow)){
            return;
        }
        Arrow arrow = (Arrow) event.getEntity();

        if(!(arrow.getShooter() instanceof Player)){
            return;
        }

        if(!(event.getHitEntity() instanceof LivingEntity)){
            return;
        }

        LivingEntity livingEntity = (LivingEntity) event.getHitEntity();

        Player player = (Player) arrow.getShooter();

        if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        PersistentDataContainer pdc = getPersistentDataContainer(itemStack);

        hasProjectileHandler(event, pdc, player, livingEntity);
    }

    public void hasProjectileHandler(ProjectileHitEvent event, PersistentDataContainer pdc, Player player, LivingEntity livingEntity){
        if (!pdc.isEmpty()) {
            for (NamespacedKey key : pdc.getKeys()) {
                if (pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = getSfItem(key, pdc);

                    if (item instanceof OnArrowHitHandler) {
                        ((OnArrowHitHandler) item).onArrowHit(event, player, livingEntity);
                    }
                } // make sure pdc type is string only
            } // loop all pdc keys inside the item
        } // pdc check
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

        hasRightClickHandler(pdc, player);
    }

    public void hasRightClickHandler(PersistentDataContainer pdc, Player player){
        if (!pdc.isEmpty()) {
            for (NamespacedKey key : pdc.getKeys()) {
                if (pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = getSfItem(key, pdc);

                    if (item instanceof OnRightClickHandler) {
                        ((OnRightClickHandler) item).onRightClick(player);
                    }
                } // make sure pdc type is string only
            } // loop all pdc keys inside the item
        } // pdc check
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

    public SlimefunItem getSfItem(NamespacedKey key, PersistentDataContainer pdc){
        return SlimefunItem.getById(Objects.requireNonNull(pdc.get(key, PersistentDataType.STRING)));
    }

    public PersistentDataContainer getPersistentDataContainer(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        Validate.notNull(meta, "Meta must not be null!");
        return meta.getPersistentDataContainer();
    }

}