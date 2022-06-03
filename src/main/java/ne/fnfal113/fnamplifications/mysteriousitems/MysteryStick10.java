package ne.fnfal113.fnamplifications.mysteriousitems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.mysteriousitems.abstracts.AbstractStick;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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
import java.util.Objects;

public class MysteryStick10 extends AbstractStick {

    @Getter
    private final Material material;

    @ParametersAreNonnullByDefault
    public MysteryStick10(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material material) {
        super(itemGroup, item, recipeType, recipe, Keys.STICK_10_EXP_LEVELS, Keys.STICK_10_DAMAGE, 4, 25);

        this.material = material;
    }

    @Override
    public Map<Enchantment, Integer> enchantments(){
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SWEEPING_EDGE, 10);
        enchantments.put(Enchantment.DAMAGE_ALL, 8);
        enchantments.put(Enchantment.FIRE_ASPECT, 7);
        enchantments.put(Enchantment.DAMAGE_ARTHROPODS, 7);
        enchantments.put(Enchantment.DAMAGE_UNDEAD, 8);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "Why is this stick too good";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "Deadly or creepy stick";
    }

    @Override
    public Material getStickMaterial() {
        return getMaterial();
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event){
        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != getMaterial()){
            return;
        } // if item material is not a weapon, don't continue further

        if(getStickTask().onSwing(item, player, event.getDamage(), 13, 4)) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 2, false, true, false));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 2, false, true, false));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 2, false, true, false));

            int playerDefaultHealth = (int) Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
            if(player.getHealth() < playerDefaultHealth - 2)  {
                player.setHealth(player.getHealth() + 2);
                victim.setHealth(victim.getHealth() < 2 ? victim.getHealth() + (victim.getHealth() * (-1)) : victim.getHealth() - 2);
            } else {
                player.sendMessage(ChatColor.RED + "Make sure your hearts are not full for Lifesteal to proc!");
            }
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }
    }
}