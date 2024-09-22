package ne.fnfal113.fnamplifications.mysteriousitems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.compatibility.VersionedEnchantment;
import ne.fnfal113.fnamplifications.mysteriousitems.abstracts.AbstractStick;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.compatibility.VersionedEnchantmentPlus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

public class MysteryStick7 extends AbstractStick {

    private final Material material;

    @ParametersAreNonnullByDefault
    public MysteryStick7(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material material) {
        super(itemGroup, item, recipeType, recipe, Keys.STICK_7_EXP_LEVELS, Keys.STICK_7_DAMAGE, 3, 20);

        this.material = material;
    }

    @Override
    public Map<Enchantment, Integer> enchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SWEEPING_EDGE, 7);
        enchantments.put(VersionedEnchantment.SHARPNESS, 6);
        enchantments.put(Enchantment.FIRE_ASPECT, 6);
        enchantments.put(VersionedEnchantmentPlus.BANE_OF_ARTHROPODS, 5);
        enchantments.put(VersionedEnchantmentPlus.SMITE, 6);

        return enchantments;
    }

    @Override
    public String weaponLore() {
        return ChatColor.GOLD + "Blood will spit across the land";
    }

    @Override
    public String stickLore() {
        return ChatColor.WHITE + "The aura on this stick is mesmerizing";
    }

    @Override
    public Material getStickMaterial() {
        return getMaterial();
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != getMaterial()) {
            return;
        }

        if(getStickTask().onSwing(item, player, event.getDamage(), 26, 3)) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 100, 1, false, false, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 80, 1, false, false, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 60, 0, false, false, false));
            
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }

    }

    public Material getMaterial() {
        return material;
    }

}