package ne.fnfal113.fnamplifications.mysteriousitems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.mysteriousitems.abstracts.AbstractStick;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.mysteriousitems.implementation.MainStick;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

public class MysteryStick6 extends AbstractStick {

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick6(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.mainStick = new MainStick(Keys.STICK_6_EXP_LEVELS, Keys.STICK_5_EXP_LEVELS, enchantments(), weaponLore(), stickLore(), 2, 15);
    }

    @Override
    public Map<Enchantment, Integer> enchantments(){
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.ARROW_DAMAGE, 4);
        enchantments.put(Enchantment.ARROW_INFINITE, 1);
        enchantments.put(Enchantment.ARROW_KNOCKBACK, 1);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "Make them take an arrow to the knee";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "May the force and accuracy be with you";
    }

    @Override
    public void interact(PlayerInteractEvent e) {
        mainStick.onInteract(e, Material.BOW);
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event){
        Arrow arrow = (Arrow) event.getDamager();
        Player player = ((Player) arrow.getShooter());
        if(player == null){
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.BOW) {
            return;
        }

        if(mainStick.onSwing(item, FNAmpItems.FN_STICK_6, player, event.getDamage(), 20, 2))  {
            LivingEntity victim = (LivingEntity) event.getEntity();
            victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, false, false, false));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 0, false, false, false));
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }

    }
}