package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ParryGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    @Getter
    private final int chance;

    public ParryGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 14);

        this.chance = FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-percent-chance");
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player) {
        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());

        if(slimefunItem != null && currentItem != null) {
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
    }


    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(!(event.getDamager() instanceof LivingEntity)){
            return;
        }
        LivingEntity damager = (LivingEntity) event.getDamager();

        if(damager.getEquipment() == null){
            return;
        }

        if(WeaponArmorEnum.SWORDS.isTagged(damager.getEquipment().getItemInMainHand().getType())){
            if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId())){
                event.setDamage(event.getDamage() * 0.75);
                if(event.getEntity() instanceof Player) {
                    sendGemMessage((Player) event.getEntity(), this.getItemName());
                }
            }
        } // damager must wield a sword
    }

}