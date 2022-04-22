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
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class DisarmorGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    @Getter
    private final int chance;

    public DisarmorGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10);

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
            if (WeaponArmorEnum.AXES.isTagged(currentItem.getType()) || WeaponArmorEnum.SWORDS.isTagged(currentItem.getType())) {
                if(isUpgradeGem(event.getCursor(), this.getId())) {
                    upgradeGem(slimefunItem, currentItem, event, player, this.getId());
                } else {
                    new Gem(slimefunItem, currentItem, player).onDrag(event, false);
                }
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on swords and axes only"));
            }
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        Player victim = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId())) {
            int slot = victim.getInventory().firstEmpty();

            if (victim.getInventory().getHelmet() != null && ThreadLocalRandom.current().nextInt(100) < 50) {
                ItemStack helmet = victim.getInventory().getHelmet();
                victim.getInventory().setHelmet(null);

                moveArmorToInventory(slot, victim, damager, helmet);
            } else if(victim.getInventory().getChestplate() != null && ThreadLocalRandom.current().nextInt(100) < 50){
                ItemStack chestplate = victim.getInventory().getChestplate();
                victim.getInventory().setChestplate(null);

                moveArmorToInventory(slot, victim, damager, chestplate);
            } else if(victim.getInventory().getLeggings() != null && ThreadLocalRandom.current().nextInt(100) < 50){
                ItemStack leggings = victim.getInventory().getLeggings();
                victim.getInventory().setLeggings(null);

                moveArmorToInventory(slot, victim, damager, leggings);
            } else if(victim.getInventory().getBoots() != null && ThreadLocalRandom.current().nextInt(100) < 50){
                ItemStack boots = victim.getInventory().getBoots();
                victim.getInventory().setBoots(null);

                moveArmorToInventory(slot, victim, damager, boots);
            }
        }

    }

    public void moveArmorToInventory(int slot, Player victim, Player damager, ItemStack armor){
        if (slot != -1) {
            victim.getInventory().setItem(slot, armor.clone());
        } else {
            victim.getWorld().dropItem(victim.getLocation(), armor.clone());
        }
        sendGemMessage(damager, this.getItemName());
    }

}