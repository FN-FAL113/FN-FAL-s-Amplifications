package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class InfernoGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public InfernoGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 16);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onDrag(InventoryClickEvent event, Player player, SlimefunItem slimefunItem, ItemStack currentItem){
        if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType())) {
            if(isUpgradeGem(event.getCursor(), this.getId())) {
                upgradeGem(slimefunItem, currentItem, event, player, this.getId());
            } else {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on swords only"));
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

        LivingEntity livingEntity = (LivingEntity) event.getEntity();

        int random = ThreadLocalRandom.current().nextInt(100);

        if(random < getChance() / getTier(itemStack, this.getId())){
            livingEntity.setFireTicks(60);
            if(event.getDamager() instanceof Player) {
                sendGemMessage((Player) event.getDamager(), this.getItemName());
            }
        } // set the attacked entity on fire

        for(Entity entity : livingEntity.getNearbyEntities(7, 4,7)){
            if(random < getChance()){
                if(entity.getUniqueId() != event.getDamager().getUniqueId()){
                    entity.setFireTicks(60);
                } // make sure entity is not the attacker
            }
        } // loop nearby entities in a 7 block bounding box and set them on fire
    }

}