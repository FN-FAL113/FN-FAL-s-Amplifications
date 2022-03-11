package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.implementation.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnGemAltar;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class InfernoGem extends AbstractGem implements OnDamageHandler {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    @Getter
    private final int chance;

    public InfernoGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 16);

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
            if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType())) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on swords only"));
            }
        }

    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.isCancelled()){
            return;
        }

        LivingEntity livingEntity = (LivingEntity) event.getEntity();

        int random = ThreadLocalRandom.current().nextInt(100);

        if(random < getChance()){
            livingEntity.setFireTicks(60);
        } // set the attacked entity on fire

        for(Entity entity : livingEntity.getNearbyEntities(7, 4,7)){
            if(random < getChance()){
                if(entity.getUniqueId() != event.getDamager().getUniqueId()){
                    entity.setFireTicks(60);
                } // make sure entity is not the attacker
            }
        } // loop nearby entities in a 7 block bounding box and set them on fire

    }

    public static void setup(){
        new InfernoGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_INFERNO, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.TALISMAN_FIRE, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), SlimefunItems.TALISMAN_FIRE,
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                SlimefunItems.TALISMAN_FIRE, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 2), SlimefunItems.TALISMAN_FIRE})
                .register(plugin);
    }
}