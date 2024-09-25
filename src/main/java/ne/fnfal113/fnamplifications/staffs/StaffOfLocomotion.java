package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;

import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import ne.fnfal113.fnamplifications.staffs.handlers.EntityStaffImpl;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class StaffOfLocomotion extends AbstractStaff implements EntityStaffImpl {

    private final Map<PersistentDataContainer, LivingEntity> entityOwnerMap = new HashMap<>();

    private final Map<UUID, Boolean> stateMap = new HashMap<>();

    private final NamespacedKey identifierKey = Keys.createKey("identifier");

    public StaffOfLocomotion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10, Keys.createKey("movestaff"));
    }

    @Override
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if(event.getRightClicked() instanceof Player) {
            Utils.sendMessage("Staff is not allowed to move players!", player);
            
            return;
        }

        if(!(event.getRightClicked() instanceof LivingEntity)) {
            Utils.sendMessage("You right clicked an invalid entity", player);
            
            return;
        }

        LivingEntity entityRightClicked = (LivingEntity) event.getRightClicked();

        if(!Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()), player.getLocation(), Interaction.BREAK_BLOCK)) {
            Utils.sendMessage("You don't have permission to cast " + this.getItemName() + " here!", player);
            
            return;
        }

        ItemStack staffItemStack = player.getInventory().getItemInMainHand();
        ItemMeta meta = staffItemStack.getItemMeta();
        PersistentDataContainer staffPdc = meta.getPersistentDataContainer();

        if(!getEntityOwnerMap().containsValue(entityRightClicked)) {
            getEntityOwnerMap().remove(staffPdc);
            
            staffPdc.set(getIdentifierKey(), PersistentDataType.DOUBLE, Math.random()); // for Unique PDC (avoid same pdc contents)
            
            getEntityOwnerMap().put(staffPdc, entityRightClicked);
            
            Utils.setLoreByPdc(staffItemStack, meta, entityRightClicked.getName(), "Entity stored: ", "&e", "", " entity");
            Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1 ,1);
            
            getStateMap().put(player.getUniqueId(), true);
        } else {
            Utils.sendMessage("This entity has been stored already by others!", player);
        }
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        // the distant block that the player right clicked
        Block block = player.getTargetBlockExact(100);

        // fixes unintentional teleport while trying to store an entity
        // player interact event trigger multiple times by action type (left|right_click_block|air)
        if(getStateMap().containsKey(player.getUniqueId()) && getStateMap().get(player.getUniqueId())) {
            getStateMap().remove(player.getUniqueId());

            return;
        }

        if(block == null) {
            return;
        }

        if(!Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(player.getUniqueId()), block, Interaction.BREAK_BLOCK)) {
            Utils.sendMessage("You don't have permission to teleport entity there!", player);
            
            return;
        }

        if(getEntityOwnerMap().get(data) == null) {
            Utils.sendMessage("You haven't right clicked an entity or Entity ID changed after server restart", player);
            
            return;
        }

        if(getEntityOwnerMap().containsKey(data)) {
            LivingEntity entity = getEntityOwnerMap().get(data);
            
            entity.teleport(block.getLocation().add(0.5, 1, 0.5));
            
            getEntityOwnerMap().remove(data);
            
            Utils.setLoreByPdc(item, meta, "none", "Entity stored: ", "&e", "", "");
            
            getStaffTask().updateMeta(item, meta, player);
        }
    }

    public Map<PersistentDataContainer, LivingEntity> getEntityOwnerMap() {
        return entityOwnerMap;
    }

    public Map<UUID, Boolean> getStateMap() {
        return stateMap;
    }

    public NamespacedKey getIdentifierKey() {
        return identifierKey;
    }

}