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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ThunderBoltGem extends AbstractGem implements OnDamageHandler {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    @Getter
    private final int chance;

    public ThunderBoltGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 10);

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
            if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.AXES.isTagged(currentItem.getType())) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on swords and axes only"));
            }
        }

    }

    public void onDamage(EntityDamageByEntityEvent event){
        if(event.isCancelled()){
            return;
        }

        Player player = (Player) event.getDamager();
        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        if(ThreadLocalRandom.current().nextInt(100) < getChance()){
            livingEntity.getWorld().strikeLightning(livingEntity.getLocation());
            player.setNoDamageTicks(20);
        } // if below the chance, strike lightning at the victim and set no damage for the attacker for 1 second
    }

    public static void setup(){
        new ThunderBoltGem(FNAmpItems.FN_GEMS, FNAmpItems.FN_GEM_THUNDER, FnGemAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3), new ItemStack(Material.FIRE_CHARGE), new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1), new ItemStack(Material.EMERALD), new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 1),
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3), new ItemStack(Material.FIRE_CHARGE), new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3)})
                .register(plugin);
    }
}