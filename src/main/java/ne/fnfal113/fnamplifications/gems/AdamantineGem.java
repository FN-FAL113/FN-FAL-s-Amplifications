package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnItemDamageHandler;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class AdamantineGem extends AbstractGem implements OnItemDamageHandler, GemUpgrade {

    public AdamantineGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 15);
    }

    @Override
    public void onDrag(Player player, SlimefunItem gem, ItemStack gemItem, ItemStack currentItem){
        if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.PICKAXE.isTagged(currentItem.getType()) ||
                WeaponArmorEnum.AXES.isTagged(currentItem.getType()) || WeaponArmorEnum.SHOVELS.isTagged(currentItem.getType()) ||
                WeaponArmorEnum.BOWS.isTagged(currentItem.getType()) || WeaponArmorEnum.HOES.isTagged(currentItem.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(gem, currentItem, gemItem, player, this.getId());
            } else {
                bindGem(gem, currentItem, player, false);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on weapons and tools only"));
        }
    }

    @Override
    public void onDurabilityChange(PlayerItemDamageEvent event) {
        if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(event.getItem(), this.getId())){
            event.setCancelled(true);
            sendGemMessage(event.getPlayer(), this.getItemName());
        }
    }

}