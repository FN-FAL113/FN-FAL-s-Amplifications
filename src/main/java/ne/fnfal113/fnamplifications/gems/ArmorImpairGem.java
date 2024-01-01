package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class ArmorImpairGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public ArmorImpairGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 15);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket) {
        if((WeaponArmorEnum.SWORDS.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.AXES.isTagged(itemStackToSocket.getType()))) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes and swords only"));
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        
        if(event.isCancelled()) {
            return;
        }

        if(event.getEntity().isInvulnerable()) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        ItemStack[] armorContents = livingEntity.getEquipment().getArmorContents();

        for(ItemStack entityEquipment : armorContents) {
            if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId()) && entityEquipment != null) {
                ItemMeta meta = entityEquipment.getItemMeta();
                
                if(meta instanceof Damageable) {
                    Damageable damageable = (Damageable) meta;
                    
                    damageable.setDamage(damageable.getDamage() + 8);
                    
                    entityEquipment.setItemMeta(meta);
                    
                    if(event.getDamager() instanceof Player) {
                        sendGemMessage((Player) event.getDamager(), this.getItemName());
                    }
                }
            }
        }

    }
}