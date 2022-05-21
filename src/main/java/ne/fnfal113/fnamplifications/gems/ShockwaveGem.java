package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ShockwaveGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    private final Boolean checkMcVersion = Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17);

    public ShockwaveGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 15);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onDrag(InventoryClickEvent event, Player player, SlimefunItem slimefunItem, ItemStack currentItem){
        if (WeaponArmorEnum.HELMET.isTagged(currentItem.getType()) || WeaponArmorEnum.CHESTPLATE.isTagged(currentItem.getType()) ||
                WeaponArmorEnum.LEGGINGS.isTagged(currentItem.getType()) || WeaponArmorEnum.BOOTS.isTagged(currentItem.getType())) {
            if(isUpgradeGem(event.getCursor(), this.getId())) {
                upgradeGem(slimefunItem, currentItem, event, player, this.getId());
            } else {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on armors only"));
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack){
        if(event.isCancelled()){
            return;
        }
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        if(!(event.getDamager() instanceof LivingEntity)){
            return;
        }

        Player player = (Player) event.getEntity();
        LivingEntity livingEntity = (LivingEntity) event.getDamager();

        int tier = getTier(itemStack, this.getId());
        double amount = 3.0D * (tier == 4 ? 1 : Math.abs(tier - 5)); // damage multiplier per tier

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / tier){
            sendGemMessage(player, this.getItemName());
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 1.0F, 1.0F);
            livingEntity.damage(amount, player);
            livingEntity.setVelocity(new Vector(0, 0.8, 0));

            for (Entity entity: livingEntity.getNearbyEntities(8, 8, 8)) {
                if(entity instanceof LivingEntity && !entity.getUniqueId().equals(player.getUniqueId())){
                    ((Damageable) entity).damage(amount, player);
                    entity.setVelocity(new Vector(0, 0.8, 0));
                }
            }

            AtomicInteger integer = new AtomicInteger(0);
            Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task ->{
                int rad = integer.getAndIncrement();
                for (double c = 0; c <= 360; c++) {
                    double x = rad * Math.cos(c);
                    double z = rad * Math.sin(c);

                    player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(x, 0.5, z), 0);
                    player.getWorld().spawnParticle(this.checkMcVersion ? Particle.ELECTRIC_SPARK : Particle.CLOUD, player.getLocation().add(x, 0.5, z), 0);
                }

                if(rad == 8){
                    task.cancel();
                }

            }, 0L, 1L);
        }
    }

}