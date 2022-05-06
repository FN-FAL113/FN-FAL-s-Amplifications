package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class LootGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public LootGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 13);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onDrag(InventoryClickEvent event, Player player, SlimefunItem slimefunItem, ItemStack currentItem){
        if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType())) {
            if(isUpgradeGem(event.getCursor(), this.getId())) {
                upgradeGem(slimefunItem, currentItem, event, player, this.getId());
            } else {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on swords and axes only"));
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack){
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        if(!(event.getDamager() instanceof Player)){
            return;
        }
        if(event.isCancelled()){
            return;
        }

        Player victim = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        int random = ThreadLocalRandom.current().nextInt(100);

        if(random < getChance() / getTier(itemStack, this.getId())){
            FNAmplifications.getVaultIntegration().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(victim.getUniqueId()), 4.0D);
            FNAmplifications.getVaultIntegration().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(damager.getUniqueId()), 4.0D);
            sendGemMessage(damager, this.getItemName());
            sendGemMessage(victim, Utils.colorTranslator("&cEnemy ") + this.getItemName());
        }

    }

}
