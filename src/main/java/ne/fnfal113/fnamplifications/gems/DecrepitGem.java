package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class DecrepitGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public DecrepitGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 11);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if (WeaponArmorEnum.HELMET.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.CHESTPLATE.isTagged(itemStackToSocket.getType()) ||
                WeaponArmorEnum.LEGGINGS.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.BOOTS.isTagged(itemStackToSocket.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on armors only"));
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack){
        if(!(event.getDamager() instanceof LivingEntity)){
            return;
        }
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        if(event.isCancelled()){
            return;
        }

        Player player = (Player) event.getEntity();

        LivingEntity livingEntity = (LivingEntity) event.getDamager();

        int tier = getTier(itemStack, this.getId());

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / tier){
            int level = Math.abs(tier - 4);

            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, level));
            sendGemMessage(player, this.getItemName());
        }

    }

}