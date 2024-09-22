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

public class MysteryStick9 extends AbstractStick {

    private final Material material;

    @ParametersAreNonnullByDefault
    public MysteryStick9(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material material) {
        super(itemGroup, item, recipeType, recipe, Keys.STICK_9_EXP_LEVELS, Keys.STICK_9_DAMAGE, 3, 20);

        this.material = material;
    }

    @Override
    public Map<Enchantment, Integer> enchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(VersionedEnchantmentPlus.POWER, 5);
        enchantments.put(VersionedEnchantmentPlus.INFINITY, 1);
        enchantments.put(VersionedEnchantmentPlus.FLAME, 5);
        enchantments.put(VersionedEnchantmentPlus.PUNCH, 4);

        return enchantments;
    }

    @Override
    public String weaponLore() {
        return ChatColor.GOLD + "I wonder if Elves possess this relic";
    }

    @Override
    public String stickLore() {
        return ChatColor.WHITE + "You need more mana when using this";
    }

    @Override
    public Material getStickMaterial() {
        return getMaterial();
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event){
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

        if(getStickTask().onSwing(item, player, event.getDamage(), 26, 3)) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 60, 1, false, true, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 60, 0, false, true, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 80, 2, false, false));
            
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }

    }

    public Material getMaterial() {
        return material;
    }

}