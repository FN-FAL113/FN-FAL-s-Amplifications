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
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BerserkGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    private static final Map<Integer, Double> tierDamageMap = new HashMap<>();

    static {
        tierDamageMap.put(4, 0.06);
        tierDamageMap.put(3, 0.12);
        tierDamageMap.put(2, 0.18);
        tierDamageMap.put(1, 0.30);
    }

    public BerserkGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 20);
    }

    @Override
    public void onDrag(Player player, SlimefunItem gem, ItemStack gemItem, ItemStack currentItem){
        if (WeaponArmorEnum.AXES.isTagged(currentItem.getType()) || WeaponArmorEnum.SWORDS.isTagged(currentItem.getType())) {
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
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(event.isCancelled()){
            return;
        }

        Player damager = (Player) event.getDamager();
        int tier = getTier(itemStack, this.getId());

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / tier){
            double playerDefaultHealth = Objects.requireNonNull(damager.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
            double playerCurrentHealth = damager.getHealth();

            if(playerCurrentHealth <= 0 || playerDefaultHealth <= 0){
                return;
            }

            if (playerCurrentHealth <= playerDefaultHealth * 0.30 && !damager.isDead()) {
                double damage = event.getDamage();
                double finalDamage = (tierDamageMap.get(tier) * damage) + damage;

                event.setDamage(finalDamage);

                sendGemMessage(damager, this.getItemName());
            }
        }
    }

}
