package ne.fnfal113.fnamplifications.MysteriousItems.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public abstract class AbstractStick extends SlimefunItem {

    public AbstractStick(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     *
     * @return the assigned enchantments as key along with its level a value
     */
    public abstract Map<Enchantment, Integer> enchantments();

    /**
     *
     * @return the lore when the weapon is summoned
     */
    public abstract String weaponLore();

    /**
     *
     * @return the default lore of the stick
     */
    public abstract String stickLore();

    /**
     *
     * @param e the interact event specifically uses right-click action
     *          to summon the weapon
     */
    public abstract void interact(PlayerInteractEvent e);

    /**
     *
     * @param event the event for damaging another entity and
     *              has a chance to consume levels from the player
     */
    public abstract void onSwing(EntityDamageByEntityEvent event);

    /**
     *
     * @param event the level change event where the player level
     *              changes and gets assigned to the weapon lore
     */
    public abstract void LevelChange(PlayerLevelChangeEvent event);

    public void blindPlayer(Player p, int levelReq){
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2, false, false));
        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than " + levelReq);
        p.sendTitle(ChatColor.DARK_RED + " Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 15, 40, 45);
    }

}