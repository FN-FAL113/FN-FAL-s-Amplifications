package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.OnPlayerDeathHandler;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.*;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class AvengeGem extends AbstractGem implements OnPlayerDeathHandler {

    @Getter
    private final int chance;

    public AvengeGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 13);

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
            if (WeaponArmorEnum.BOOTS.isTagged(currentItem.getType())) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on boots only"));
            }
        }
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event) {

        if(ThreadLocalRandom.current().nextInt(100) < getChance()){
            Player player = event.getEntity();
            Location loc = player.getLocation();
            Creeper creeper = player.getWorld().spawn(loc.clone(), Creeper.class);
            creeper.setPowered(true); // big boomer creeper
            creeper.setExplosionRadius(3);
        }
    }

}