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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ImpostorGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    @Getter
    private final int chance;

    public ImpostorGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 14);

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
            if (WeaponArmorEnum.HELMET.isTagged(currentItem.getType())) {
                if(isUpgradeGem(event.getCursor(), this.getId())) {
                    upgradeGem(slimefunItem, currentItem, event, player, this.getId());
                } else {
                    new Gem(slimefunItem, currentItem, player).onDrag(event, false);
                }
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on helmets only"));
            }
        }

    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack){
        if(event.isCancelled()){
            return;
        }

        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            if (ThreadLocalRandom.current().nextInt(100) < (getChance() / getTier(itemStack, this.getId())) &&
                    event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                double nX;
                double nZ;
                float nang = victim.getLocation().getYaw() + 90;

                if (nang < 0) nang += 360;

                nX = Math.cos(Math.toRadians(nang));
                nZ = Math.sin(Math.toRadians(nang));

                Location newDamagerLoc = new Location(victim.getWorld(), damager.getLocation().getX() - nX,
                        damager.getLocation().getY(), damager.getLocation().getZ() - nZ,
                        damager.getLocation().getYaw(),
                        damager.getLocation().getPitch());
                victim.teleport(newDamagerLoc);
                sendGemMessage(victim, this.getItemName());
            } // teleport behind the attacker
        }
    }

}