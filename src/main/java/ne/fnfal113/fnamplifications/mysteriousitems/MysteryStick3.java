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
import java.util.HashMap;
import java.util.Map;

public class MysteryStick3 extends AbstractStick {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    public final MainStick mainStick;

    @ParametersAreNonnullByDefault
    public MysteryStick3(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.mainStick = new MainStick(Keys.STICK_3_EXP_LEVELS, Keys.STICK_3_DAMAGE, enchantments(), weaponLore(), stickLore(), 1, 5);
    }

    @Override
    public Map<Enchantment, Integer> enchantments(){
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.ARROW_DAMAGE, 2);
        enchantments.put(Enchantment.ARROW_INFINITE, 1);

        return enchantments;
    }

    @Override
    public String weaponLore(){
        return ChatColor.GOLD + "I knew it was something about shooting arrows";
    }

    @Override
    public String stickLore(){
        return ChatColor.WHITE + "I feel coordinated when holding this stick";
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

        if(mainStick.onSwing(item, FNAmpItems.FN_STICK_9, player, event.getDamage(), 27, 1)) {
            LivingEntity victim = (LivingEntity) event.getEntity();
            victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0, false, false));
            player.sendMessage(Utils.colorTranslator("&cMystery effects was applied to your enemy"));
        }

    }

    public static void setup(){
        new MysteryStick3(FNAmpItems.MYSTERY_STICKS, FNAmpItems.FN_STICK_3, FnMysteryStickAltar.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1), new ItemStack(Material.LEATHER), new SlimefunItemStack(SlimefunItems.BLANK_RUNE, 1),
                new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 8), new ItemStack(Material.STICK), new SlimefunItemStack(SlimefunItems.MAGIC_LUMP_2, 8),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 6), new ItemStack(Material.LEAD), new SlimefunItemStack(SlimefunItems.ENDER_LUMP_1, 6)})
                .register(plugin);
    }
}