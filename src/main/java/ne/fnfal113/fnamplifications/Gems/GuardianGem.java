package ne.fnfal113.fnamplifications.Gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Gems.Implementation.Gem;
import ne.fnfal113.fnamplifications.Gems.Abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.Gems.Interface.OnDamageHandler;
import ne.fnfal113.fnamplifications.Utils.Keys;
import ne.fnfal113.fnamplifications.Gems.Implementation.GuardianTask;
import ne.fnfal113.fnamplifications.Gems.Implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnGemAltar;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class GuardianGem extends AbstractGem implements OnDamageHandler {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    @Getter
    private final int chance;

    private final Map<UUID, Zombie> entityUUIDMap = new HashMap<>();
    private final Map<UUID, BukkitTask> runnableMap = new HashMap<>();

    public GuardianGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 8);

        this.chance = FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-percent-chance");
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){

        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());
        if(slimefunItem != null && currentItem != null && WeaponArmorEnum.CHESTPLATE.isTagged(currentItem.getType())){
            ItemMeta meta = currentItem.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();

            if(checkGemAmount(container, currentItem) < 4) {
                Gem gem = new Gem(slimefunItem, currentItem, player);
                if(!gem.isSameGem(currentItem)){
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    gem.socketItem();
                } else{
                    player.sendMessage(Utils.colorTranslator("&6Your item has " + gem.getSfItemName() + " &6socketed already!"));
                }
            } else {
                player.sendMessage(Utils.colorTranslator("&eOnly 4 gems per item is allowed!"));
                player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1.0F, 1.0F);
            }
            event.setCancelled(true);
        }

    }

    @Override
    public int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack){
        return pdc.getOrDefault(
                new NamespacedKey(FNAmplifications.getInstance(), itemStack.getType().toString().toLowerCase() + "_socket_amount"),
                PersistentDataType.INTEGER, 0);
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.isCancelled()){
            return;
        }

        Player player = (Player) event.getEntity();

        if(event.getDamager() instanceof Phantom || event.getDamager() instanceof Flying || event.getDamager() instanceof EnderDragon){
            return;
        } // prevent guardian from attacking flying entities

        if(runnableMap.containsKey(player.getUniqueId())){
            if(runnableMap.get(player.getUniqueId()).isCancelled()){
                entityUUIDMap.remove(player.getUniqueId());
                runnableMap.remove(player.getUniqueId());
                return;
            }
        }

        if(!entityUUIDMap.containsKey(player.getUniqueId())) {
            if(ThreadLocalRandom.current().nextInt(100) < getChance()) {
                GuardianTask guardianTask = new GuardianTask(player, event);

                runnableMap.put(player.getUniqueId(),
                        guardianTask.runTaskTimer(FNAmplifications.getInstance(), 5L, 3L));
                entityUUIDMap.put(player.getUniqueId(), guardianTask.getZombie());
            }
        } else {
            Zombie zombie = entityUUIDMap.get(player.getUniqueId());

            if(zombie.getTarget() != null && !zombie.getTarget().isDead()){
                return;
            }

            if(event.getDamager().getPersistentDataContainer().has(Keys.GUARDIAN_KEY, PersistentDataType.STRING)){
                zombie.setTarget(Bukkit.getPlayer(event.getDamager().getPersistentDataContainer().get(Keys.GUARDIAN_KEY, PersistentDataType.STRING)));
            } else {
                zombie.setTarget(event.getDamager() instanceof Projectile ?
                        (LivingEntity) ((Projectile) event.getDamager()).getShooter() : (LivingEntity) event.getDamager());
            }

        }
    }

    public static void setup(){
        new GuardianGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_GUARDIAN, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_ANGEL, new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), SlimefunItems.TALISMAN_FIRE,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.MAGIC_SUGAR, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 1),  SlimefunItems.MAGIC_SUGAR})
                .register(plugin);
    }
}