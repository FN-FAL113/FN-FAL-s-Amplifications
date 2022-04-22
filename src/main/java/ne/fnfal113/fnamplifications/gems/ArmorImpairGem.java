package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class ArmorImpairGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    @Getter
    private final int chance;

    public ArmorImpairGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 15);

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
            if ((WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType()))) {
                if(isUpgradeGem(event.getCursor(), this.getId())) {
                    upgradeGem(slimefunItem, currentItem, event, player, this.getId());
                } else {
                    new Gem(slimefunItem, currentItem, player).onDrag(event, false);
                }
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on axes and swords only"));
            }
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack){
        if(event.isCancelled()){
            return;
        }

        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        ItemStack[] armorContents = livingEntity.getEquipment().getArmorContents();

        for(ItemStack entityEquipment : armorContents){
            if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId()) && entityEquipment != null){
                ItemMeta meta = entityEquipment.getItemMeta();
                if(meta instanceof Damageable){
                    Damageable damageable = (Damageable) meta;
                    damageable.setDamage(damageable.getDamage() + 4);
                    entityEquipment.setItemMeta(meta);
                    if(event.getDamager() instanceof Player) {
                        sendGemMessage((Player) event.getDamager(), this.getItemName());
                    }
                }
            }
        }

    }
}