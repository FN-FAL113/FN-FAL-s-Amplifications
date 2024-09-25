package ne.fnfal113.fnamplifications.mysteriousitems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.compatibility.VersionedEnchantment;

import ne.fnfal113.fnamplifications.mysteriousitems.abstracts.AbstractStick;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

public class MysteryStick4 extends AbstractStick {

    private final Material material;

    @ParametersAreNonnullByDefault
    public MysteryStick4(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material material) {
        super(itemGroup, item, recipeType, recipe, Keys.STICK_4_EXP_LEVELS, Keys.STICK_4_DAMAGE, 2, 15);

        this.material = material;
    }

    @Override
    public Map<Enchantment, Integer> enchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SWEEPING_EDGE, 3);
        enchantments.put(VersionedEnchantment.SHARPNESS, 3);
        enchantments.put(Enchantment.FIRE_ASPECT, 3);

        return enchantments;
    }

    @Override
    public String weaponLore() {
        return ChatColor.GOLD + "It was indeed a magical improvement";
    }

    @Override
    public String stickLore() {
        return ChatColor.WHITE + "Did I use this before or maybe not";
    }

    @Override
    public Material getStickMaterial() {
        return getMaterial();
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != getMaterial()) return;

        if(getStickTask().onSwing(item, player, event.getDamage(), 24, 2))  {
            LivingEntity victim = (LivingEntity) event.getEntity();
            
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 100, 1, false, false, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 40, 0, false, false, false));
            
            Utils.sendMessage("Mystery effects was applied to your enemy", player);
        }

    }

    public Material getMaterial() {
        return material;
    }

}