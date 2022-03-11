package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnArrowHitHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class BlindBindGem extends AbstractGem implements OnArrowHitHandler {

    @Getter
    private final int chance;

    public BlindBindGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 13);

        this.chance = FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-percent-chance");
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player){
        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());

        if(WeaponArmorEnum.BOWS.isTagged(currentItem.getType())) {
            if(slimefunItem != null && currentItem != null) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on bow and crossbows only"));
        }

    }

    @Override
    public void onArrowHit(ProjectileHitEvent event, Player player, LivingEntity entity){
        if(event.isCancelled()){
            return;
        }

        if(ThreadLocalRandom.current().nextInt(100) < getChance()){
            player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, entity.getLocation(), 5);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 2, true, false, false));
        }

    }
}