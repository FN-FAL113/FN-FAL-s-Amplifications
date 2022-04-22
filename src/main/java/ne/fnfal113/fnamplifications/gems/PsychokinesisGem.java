package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnProjectileDamageHandler;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class PsychokinesisGem extends AbstractGem implements OnProjectileDamageHandler, GemUpgrade {

    @Getter
    private final int chance;

    public PsychokinesisGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 17);

        this.chance = FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-percent-chance");
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){
        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());

        if(slimefunItem != null && currentItem != null) {
            if (WeaponArmorEnum.BOWS.isTagged(currentItem.getType())) {
                if(isUpgradeGem(event.getCursor(), this.getId())) {
                    upgradeGem(slimefunItem, currentItem, event, player, this.getId());
                } else {
                    new Gem(slimefunItem, currentItem, player).onDrag(event, false);
                }
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on bows and crossbows only"));
            }
        }

    }

    @Override
    public void onProjectileDamage(EntityDamageByEntityEvent event, Player shooter, LivingEntity entity, Projectile projectile, ItemStack itemStack) {
        if(!Slimefun.getProtectionManager().hasPermission(Bukkit.getOfflinePlayer(shooter.getUniqueId()),
                entity.getLocation(), Interaction.ATTACK_ENTITY)) {
            return;
        }

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId())){
            shooter.getWorld().spawnParticle(Particle.FLASH, entity.getLocation(), 2);
            entity.teleport(shooter);
            sendGemMessage(shooter, this.getItemName());
        }
    }

}