package ne.fnfal113.fnamplifications.Gems.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.Getter;
import ne.fnfal113.fnamplifications.Gems.*;
import ne.fnfal113.fnamplifications.Gems.Implementation.TargetReasonEnum;
import ne.fnfal113.fnamplifications.Utils.Keys;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GemListener implements Listener {

    @Getter
    private final List<EntityTargetEvent.TargetReason> reasonList = Arrays.asList(EntityTargetEvent.TargetReason.values());

    @EventHandler
    public void onDragDrop(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)){
            return;
        }

        if(event.getAction() != InventoryAction.SWAP_WITH_CURSOR){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        SlimefunItem gem = SlimefunItem.getByItem(event.getCursor());

        if(gem instanceof InfernoGem){
            ((InfernoGem) gem).onDrag(event, player);
        } else if(gem instanceof ArmorImpairGem){
            ((ArmorImpairGem) gem).onDrag(event, player);
        } else if(gem instanceof TelepathyGem){
            ((TelepathyGem) gem).onDrag(event, player);
        } else if(gem instanceof ThunderBoltGem){
            ((ThunderBoltGem) gem).onDrag(event, player);
        } else if(gem instanceof HastyGem){
            ((HastyGem) gem).onDrag(event, player);
        } else if(gem instanceof ThornAwayGem){
            ((ThornAwayGem) gem).onDrag(event, player);
        } else if(gem instanceof ImpostorGem){
            ((ImpostorGem) gem).onDrag(event, player);
        } else if(gem instanceof PsychokinesisGem){
            ((PsychokinesisGem) gem).onDrag(event, player);
        } else if(gem instanceof AxeThrowieGem){
            ((AxeThrowieGem) gem).onDrag(event, player);
        } else if(gem instanceof BlindBindGem){
            ((BlindBindGem) gem).onDrag(event, player);
        } else if(gem instanceof TriSwordGem){
            ((TriSwordGem) gem).onDrag(event, player);
        } else if(gem instanceof DamnationGem){
            ((DamnationGem) gem).onDrag(event, player);
        } else if(gem instanceof RetaliateGem){
            ((RetaliateGem) gem).onDrag(event, player);
        } else if(gem instanceof GuardianGem){
            ((GuardianGem) gem).onDrag(event, player);
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity)){
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

                if (!pdcHand.isEmpty()) {
                    for (NamespacedKey key : pdcHand.getKeys()) {
                        if (pdcHand.has(key, PersistentDataType.STRING)) {
                            SlimefunItem item = SlimefunItem.getById(Objects.requireNonNull(pdcHand.get(
                                    key,
                                    PersistentDataType.STRING)));

                            if (item instanceof InfernoGem) {
                                ((InfernoGem) item).onDamage(event);
                            }
                            if (item instanceof ArmorImpairGem) {
                                ((ArmorImpairGem) item).onDamage(event);
                            }
                            if (item instanceof ThunderBoltGem) {
                                ((ThunderBoltGem) item).onDamage(event, player);
                            }
                        } // make sure pdc type is string only
                    } // loop all pdc keys inside the item
                } // pdc check
            }

            if(event.getEntity().getPersistentDataContainer()
                    .has(Keys.GUARDIAN_KEY, PersistentDataType.STRING)){
                if(Objects.equals(event.getEntity().getPersistentDataContainer()
                        .get(Keys.GUARDIAN_KEY, PersistentDataType.STRING), player.getName())) {
                    event.setCancelled(true);
                } else if(!Slimefun.getProtectionManager().hasPermission(
                        Bukkit.getOfflinePlayer(player.getUniqueId()),
                        event.getEntity().getLocation(), Interaction.ATTACK_ENTITY)) {
                    event.setCancelled(true);
                }
            } // check if entity is a guardian
        }

        /*
         * If the victim is a player and damager is a living entity
         */
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(player.getInventory().getChestplate() != null) {
                ItemStack itemStackChest = player.getInventory().getChestplate();
                PersistentDataContainer pdcChest = getPersistentDataContainer(itemStackChest);

                if (!pdcChest.isEmpty()) {
                    for (NamespacedKey key : pdcChest.getKeys()) {
                        if (pdcChest.has(key, PersistentDataType.STRING)) {
                            SlimefunItem item = SlimefunItem.getById(Objects.requireNonNull(pdcChest.get(
                                    key,
                                    PersistentDataType.STRING)));

                            if (item instanceof ThornAwayGem) {
                                ((ThornAwayGem) item).onDamage(event);
                            }
                            if (item instanceof GuardianGem) {
                                ((GuardianGem) item).onDamage(event, player);
                            }
                        } // make sure pdc type is string only
                    } // loop all pdc keys inside the item
                } // pdc check
            }
            if(player.getInventory().getHelmet() != null) {
                ItemStack itemStackHelmet = player.getInventory().getHelmet();
                PersistentDataContainer pdcHelmet = getPersistentDataContainer(itemStackHelmet);

                if (!pdcHelmet.isEmpty()) {
                    for (NamespacedKey key : pdcHelmet.getKeys()) {
                        if (pdcHelmet.has(key, PersistentDataType.STRING)) {
                            SlimefunItem item = SlimefunItem.getById(Objects.requireNonNull(pdcHelmet.get(
                                    key,
                                    PersistentDataType.STRING)));

                            if (item instanceof ImpostorGem) {
                                ((ImpostorGem) item).onDamage(event);
                            }
                        } // make sure pdc type is string only
                    } // loop all pdc keys inside the item
                } // pdc check
            }
        }

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

        if(!pdc.isEmpty()){
            for(NamespacedKey key : pdc.getKeys()) {
                if(pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = SlimefunItem.getById(Objects.requireNonNull(pdc.get(
                            key,
                            PersistentDataType.STRING)));

                    if (item instanceof TelepathyGem) {
                        ((TelepathyGem) item).onBlockBreak(event, player);
                    }
                    if (item instanceof HastyGem) {
                        ((HastyGem) item).onBlockBreak(event, player);
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

        if(!pdc.isEmpty()){
            for(NamespacedKey key : pdc.getKeys()) {
                if(pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = SlimefunItem.getById(Objects.requireNonNull(pdc.get(
                            key,
                            PersistentDataType.STRING)));

                    if (item instanceof PsychokinesisGem) {
                        ((PsychokinesisGem) item).onArrowHit(event, player, livingEntity);
                    }
                    if (item instanceof BlindBindGem) {
                        ((BlindBindGem) item).onArrowHit(event, player, livingEntity, arrow);
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

        if(!pdc.isEmpty()){
            for(NamespacedKey key : pdc.getKeys()) {
                if(pdc.has(key, PersistentDataType.STRING)) {
                    SlimefunItem item = SlimefunItem.getById(Objects.requireNonNull(pdc.get(
                            key,
                            PersistentDataType.STRING)));

                    if(player.isSneaking()) {
                        if (item instanceof DamnationGem) {
                            ((DamnationGem) item).onRightClick(player);
                        }
                    }
                    if(!player.isSneaking()) {
                        if (item instanceof AxeThrowieGem) {
                            ((AxeThrowieGem) item).onRightClick(player);
                        }

                        if (item instanceof TriSwordGem) {
                            ((TriSwordGem) item).onRightClick(player);
                        }
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

    public PersistentDataContainer getPersistentDataContainer(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        Validate.notNull(meta, "Meta must not be null!");
        return meta.getPersistentDataContainer();
    }

}