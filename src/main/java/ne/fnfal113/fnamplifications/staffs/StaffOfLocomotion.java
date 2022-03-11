package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.handlers.EntityStaffImpl;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.*;

@SuppressWarnings("ConstantConditions")
public class StaffOfLocomotion extends SlimefunItem implements EntityStaffImpl {

    private final Map<PersistentDataContainer, LivingEntity> ENTITY_OWNER = new HashMap<>();

    private final NamespacedKey defaultUsageKey;

    private final NamespacedKey defaultUsageKey2;

    private final MainStaff mainStaff;

    @SneakyThrows
    public StaffOfLocomotion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int maxUses) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "movestaff");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "identifier");
        this.mainStaff = new MainStaff(lore(), 10, getStorageKey(), this.getItem(), this.getId());
    }

    public @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    public @Nonnull
    NamespacedKey getStorageKey2() {
        return defaultUsageKey2;
    }

    @Override
    public List<String> lore(){
        List<String> lore = new ArrayList<>();
        lore.add(0, "");
        lore.add(1, ChatColor.LIGHT_PURPLE + "Move entities to a target location by right");
        lore.add(2, ChatColor.LIGHT_PURPLE + "clicking to select and left click to move");

        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();

        if (!(event.getRightClicked() instanceof LivingEntity) && event.getRightClicked() instanceof Player) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou right clicked an invalid entity"));
            return;
        }

        LivingEntity en = (LivingEntity) event.getRightClicked();

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                en.getLocation(),
                Interaction.INTERACT_ENTITY)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to select this entity!");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();

        PersistentDataContainer data = meta.getPersistentDataContainer();
        PersistentDataContainer max_Uses = meta.getPersistentDataContainer();

        int uses_Left = max_Uses.getOrDefault(getStorageKey(), PersistentDataType.INTEGER,  FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-max-uses"));

        if(!ENTITY_OWNER.containsValue(en)) {
            ENTITY_OWNER.remove(data);
            data.set(getStorageKey2(), PersistentDataType.DOUBLE, Math.random());
            mainStaff.updateLore(lore);
            lore.add(3, "");
            lore.add(4, ChatColor.YELLOW + "Uses left: " + uses_Left);
            lore.add(5, "");
            lore.add(6,ChatColor.WHITE + "Entity right clicked: " + ChatColor.ITALIC +en.getName());
            lore.add(7,ChatColor.WHITE + "Entity ID: " + ChatColor.ITALIC + en.getEntityId());
            meta.setLore(lore);
            item.setItemMeta(meta);
            ENTITY_OWNER.put(data, en);
            Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1 ,1);
        } else {
            player.sendMessage(Utils.colorTranslator("&eThis entity has been right clicked already!"));
        }
    }

    @Override
    public void onLeftClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        Block block = player.getTargetBlockExact(100);

        if (block == null) {
            return;
        }

        if (!Slimefun.getProtectionManager().hasPermission(
                Bukkit.getOfflinePlayer(player.getUniqueId()),
                block,
                Interaction.INTERACT_BLOCK)
        ) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to teleport entity there!");
            return;
        }

        if(ENTITY_OWNER.get(data) == null){
            player.sendMessage("You haven't right clicked an entity or Entity ID changed after server restart");
            return;
        }

        if(ENTITY_OWNER.containsKey(data)) {
            LivingEntity entity = ENTITY_OWNER.get(data);
            entity.teleport(block.getLocation().add(0.5, 1, 0.5));
            ENTITY_OWNER.remove(data);
            Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1 ,1);
            mainStaff.updateMeta(item, meta, player);
        }
    }
}