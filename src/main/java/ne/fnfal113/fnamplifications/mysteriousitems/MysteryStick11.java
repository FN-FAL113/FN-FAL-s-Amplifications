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
import org.bukkit.Location;
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

public class MysteryStick11 extends AbstractStick {

    private final Material material;

    @ParametersAreNonnullByDefault
    public MysteryStick11(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material material) {
        super(itemGroup, item, recipeType, recipe, Keys.STICK_11_EXP_LEVELS, Keys.STICK_11_DAMAGE, 4, 25);

        this.material = material;
    }

    @Override
    public Map<Enchantment, Integer> enchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(VersionedEnchantmentPlus.BANE_OF_ARTHROPODS, 10);
        enchantments.put(VersionedEnchantment.SHARPNESS, 7);
        enchantments.put(VersionedEnchantmentPlus.SMITE, 8);
        enchantments.put(Enchantment.KNOCKBACK, 2);

        return enchantments;
    }

    @Override
    public String weaponLore() {
        return ChatColor.GOLD + "Behind your enemies awaits danger";
    }

    @Override
    public String stickLore() {
        return ChatColor.WHITE + "The stick of the nords";
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

        if(getStickTask().onSwing(item, player, event.getDamage(), 28, 4))   {
            LivingEntity victim = (LivingEntity) event.getEntity();

            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 80, 2, false, true, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 80, 2, false, true, false));
            victim.addPotionEffect(new PotionEffect(getRandomPotionEffectType(), 100, 2, false, true, false));

            Location loc = victim.getLocation();
            
            loc.setYaw(loc.getYaw() + 180);
            
            victim.teleport(loc);
            victim.sendMessage(ChatColor.DARK_RED + "You have been disoriented! your opponent's mysterious stick is deadly");
            
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }

    }

    public Material getMaterial() {
        return material;
    }

}