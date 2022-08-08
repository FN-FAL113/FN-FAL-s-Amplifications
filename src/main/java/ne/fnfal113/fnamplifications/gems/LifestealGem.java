package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class LifestealGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public LifestealGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 12);
    }

    @Override
    public void onDrag(Player player, SlimefunItem gem, ItemStack gemItem, ItemStack currentItem){
        if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(gem, currentItem, gemItem, player, this.getId());
            } else {
                bindGem(gem, currentItem, player, false);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on swords and axes only"));
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack){
        if(event.isCancelled()){
            return;
        }
        if(!(event.getEntity() instanceof LivingEntity)){
            return;
        }
        if(!(event.getDamager() instanceof Player)){
            return;
        }

        Player player = (Player) event.getDamager();

        LivingEntity livingEntity = (LivingEntity) event.getEntity();

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId())){
            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> { // delay getting the actual hp of the damager
                int playerDefaultHealth = (int) Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
                if (player.getHealth() <= playerDefaultHealth - 2 && !livingEntity.isDead()) {
                    player.setHealth(player.getHealth() + 2);
                    livingEntity.setHealth(livingEntity.getHealth() < 2 ? livingEntity.getHealth() + (livingEntity.getHealth() * (-1)) :
                            livingEntity.getHealth() - 2);
                    sendGemMessage(player, this.getItemName());
                }
            }, 3L);
        }
    }

}