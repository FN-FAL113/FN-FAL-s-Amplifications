package ne.fnfal113.fnamplifications.mysteriousitems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.mysteriousitems.abstracts.AbstractStick;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.compatibility.VersionedEnchantmentPlus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

public class MysteryStick6 extends AbstractStick {

    private final Material material;

    @ParametersAreNonnullByDefault
    public MysteryStick6(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material material) {
        super(itemGroup, item, recipeType, recipe, Keys.STICK_6_EXP_LEVELS, Keys.STICK_6_DAMAGE, 2, 15);

        this.material = material;
    }

    @Override
    public Map<Enchantment, Integer> enchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(VersionedEnchantmentPlus.POWER, 4);
        enchantments.put(VersionedEnchantmentPlus.INFINITY, 1);
        enchantments.put(VersionedEnchantmentPlus.PUNCH, 1);

        return enchantments;
    }

    @Override
    public String weaponLore() {
        return ChatColor.GOLD + "Make them take an arrow to the knee";
    }

    @Override
    public String stickLore() {
        return ChatColor.WHITE + "May the force and accuracy be with you";
    }

    @Override
    public Material getStickMaterial() {
        return getMaterial();
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getDamager();
        Player player = ((Player) arrow.getShooter());
        
        if(player == null) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != getMaterial()) {
            return;
        }

        if(getStickTask().onSwing(item, player, event.getDamage(), 24, 2)) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 100, 1, false, false, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 40, 0, false, false, false));
            
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }

    }

    public Material getMaterial() {
        return material;
    }

}