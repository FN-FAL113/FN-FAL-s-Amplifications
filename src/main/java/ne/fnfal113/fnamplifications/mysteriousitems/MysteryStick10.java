package ne.fnfal113.fnamplifications.mysteriousitems;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.items.FNAmpItems;
import ne.fnfal113.fnamplifications.multiblocks.FnMysteryStickAltar;
import ne.fnfal113.fnamplifications.mysteriousitems.abstracts.AbstractStick;
import ne.fnfal113.fnamplifications.mysteriousitems.implementation.MainStick;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

public class MysteryStick10 extends AbstractStick {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick10(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.mainStick = new MainStick(Keys.STICK_10_EXP_LEVELS, Keys.STICK_10_DAMAGE, enchantments(), weaponLore(), stickLore(), 4, 25);
    }

    @Override
    public Map<Enchantment, Integer> enchantments(){
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SWEEPING_EDGE, 10);
        enchantments.put(Enchantment.DAMAGE_ALL, 9);
        enchantments.put(Enchantment.FIRE_ASPECT, 8);
        enchantments.put(Enchantment.DAMAGE_ARTHROPODS, 8);
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
    public void interact(PlayerInteractEvent e) {
        mainStick.onInteract(e, Material.DIAMOND_SWORD);
    }

    @Override
    public void onSwing(EntityDamageByEntityEvent event){
        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.DIAMOND_SWORD){
            return;
        } // if item material is not a weapon, don't continue further

        if(mainStick.onSwing(item, FNAmpItems.FN_STICK_10, player, event.getDamage(), 13, 4)) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 3, false, true, false));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 3, false, true, false));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 2, false, true, false));

            int playerDefaultHealth = (int) Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
            if(player.getHealth() < playerDefaultHealth - 2)  {
                player.setHealth(player.getHealth() + 2);
                victim.setHealth(victim.getHealth() - 2);
            } else {
                player.sendMessage(ChatColor.RED + "Make sure your hearts are not full for Lifesteal to proc!");
            }
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }
    }

    public static void setup(){
        new MysteryStick10(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_10, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2), FNAmpItems.FN_STICK_4, new SlimefunItemStack(SlimefunItems.ENCHANTMENT_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.FIRE_RUNE, 3), FNAmpItems.FN_STICK_7, new SlimefunItemStack(SlimefunItems.AIR_RUNE, 3),
                new SlimefunItemStack(SlimefunItems.LIGHTNING_RUNE, 3), FNAmpItems.FN_STICK, new SlimefunItemStack(SlimefunItems.ESSENCE_OF_AFTERLIFE, 2)})
                .register(plugin);
    }
}