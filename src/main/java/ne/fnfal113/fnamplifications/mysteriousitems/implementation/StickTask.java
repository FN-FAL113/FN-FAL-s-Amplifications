package ne.fnfal113.fnamplifications.mysteriousitems.implementation;

import lombok.Getter;
import ne.fnfal113.fnamplifications.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ConstantConditions")
public class StickTask {

    @Getter
    public final NamespacedKey xpKey;
    @Getter
    public final NamespacedKey damageInflictedKey;
    @Getter
    public final Map<Enchantment, Integer> enchantmentMap;
    @Getter
    public final String weaponLore;
    @Getter
    public final String stickLore;
    @Getter
    public final int effectCount;
    @Getter
    public final int requiredLevel;

    @ParametersAreNonnullByDefault
    public StickTask(NamespacedKey key1, NamespacedKey key2, Map<Enchantment, Integer> enchantmentMap, String weaponLore, String stickLore){
        this(key1, key2, enchantmentMap, weaponLore, stickLore, 0, 0);
    }

    @ParametersAreNonnullByDefault
    public StickTask(NamespacedKey key1, NamespacedKey key2, Map<Enchantment, Integer> enchantmentMap, String weaponLore, String stickLore, int effectCount, int levelReq){
        this.xpKey = key1;
        this.damageInflictedKey = key2;
        this.enchantmentMap = enchantmentMap;
        this.weaponLore = weaponLore;
        this.stickLore = stickLore;
        this.effectCount = effectCount;
        this.requiredLevel = levelReq;
    }

    public int getPdc(PersistentDataContainer pdc, NamespacedKey key){
        return pdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    /**
     * transforms the stick to a weapon on right click if the player has the sufficient levels
     * @param e the interact event
     * @param material the material of the item that will be used for transformation
     */
    public void onInteract(PlayerInteractEvent e, Material material){
        if(!(e.getPlayer().getLevel() >= getRequiredLevel())) {
            darkenVision(e.getPlayer(), getRequiredLevel());
            return;
        }

        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemStack.getItemMeta();

        convertToWeapon(itemStack, meta, material, player);
    }

    public void convertToWeapon(ItemStack itemStack, ItemMeta meta ,Material material, Player player){
        if(itemStack.getType() == material) {
             return;
        } // convert the stick to a weapon

        meta.setUnbreakable(true);
        getEnchantmentMap().forEach((Key, Value) -> meta.addEnchant(Key, Value, true));
        itemMetaUpdate(itemStack, meta ,getWeaponLore(), 0, getPdc(meta.getPersistentDataContainer(),
                getXpKey()), false);
        itemStack.setType(material);
        player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
        player.getWorld().playEffect(player.getLocation().add(0.3, 0.4, 0.45), Effect.ENDER_SIGNAL, 1);
        player.getWorld().spawnParticle(Particle.FLASH, player.getLocation().add(0.3, 0.4, 0.45), 2, 0.1, 0.1, 0.1, 0.1);
    }

    public void convertToStick(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        lore.clear();
        lore.add(getStickLore());

        meta.setLore(lore);
        meta.getEnchants().forEach((enchant, value) -> meta.removeEnchant(enchant));

        item.setType(Material.STICK);
        item.setItemMeta(meta);
    }

    /**
     *
     * @param item the stick used
     * @param player the player involved
     * @param damageAmount the damage inflicted
     * @param chance the chance to deduct level from the owner
     * @param levelDeduction the amount of level to be deducted
     * @return true if xp level deduction is applied
     */
    public boolean onSwing(ItemStack item, Player player, double damageAmount, int chance, int levelDeduction){
        if (player.getLevel() >= getRequiredLevel()) {
            if(ThreadLocalRandom.current().nextInt(100) > chance){
                itemMetaUpdate(item, item.getItemMeta(), getWeaponLore(), (int) damageAmount, levelDeduction, false);
                return false;
            }

            player.setLevel(player.getLevel() - levelDeduction);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.
                    fromLegacyText(Utils.colorTranslator("&d" + levelDeduction + " xp levels has been consumed from you")));

            itemMetaUpdate(item, item.getItemMeta(), getWeaponLore(), (int) damageAmount, levelDeduction, true);

            return true;
        }

        convertToStick(item);
        darkenVision(player, getRequiredLevel());
        return false;
    }

    /**
     *
     * @param item the mystery stick used
     * @param loreType lore type can be the lore of the stick or as weapon
     * @param damageInflicted the damage amount inflicted to the enemy
     * @param xpLevelDeduction the amount of xp levels deducted from the owner
     */
    public void itemMetaUpdate(ItemStack item, ItemMeta meta, String loreType, int damageInflicted, int xpLevelDeduction, boolean isLevelDeducted){
        List<String> lore2 = meta.getLore();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        int xpAmount = getPdc(pdc, getXpKey());
        int damageAmount = damageInflicted + getPdc(pdc, getDamageInflictedKey());

        if(isLevelDeducted) { // if level is deducted from the owner when using the stick
            xpAmount = xpAmount + xpLevelDeduction;
        }

        pdc.set(getXpKey(), PersistentDataType.INTEGER, xpAmount);
        pdc.set(getDamageInflictedKey(), PersistentDataType.INTEGER, damageAmount);

        lore2.set(0, loreType);
        try {
            lore2.set(1, ChatColor.YELLOW + "Total Exp Levels Consumed: " + ChatColor.WHITE + xpAmount);
            lore2.set(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + damageAmount);
        } catch (IndexOutOfBoundsException e){
            lore2.add(1, ChatColor.YELLOW + "Total Exp Levels Consumed: " + ChatColor.WHITE + xpAmount);
            lore2.add(2, ChatColor.YELLOW + "Total Damage inflicted: " + ChatColor.WHITE + damageAmount);
        }

        if(!(lore2.size() >= 4)){ // add the mystery effect lore
            lore2.add("");
            lore2.add(Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4&lEffects &f|◥◣◥◣◥◣◥◣"));
            lore2.add(ChatColor.BLUE + "◆ "  + getEffectCount() + " Mystery effect/s");
            lore2.add(Utils.colorTranslator("&c◢◤◢◤◢◤◢◤| &4◢◤◤◥◤◥◤◥◤◥◥◣ &f|◥◣◥◣◥◣◥◣"));
        }

        meta.setLore(lore2);
        item.setItemMeta(meta);
    }

    @ParametersAreNonnullByDefault
    public void darkenVision(Player player, int level){
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2, false, false));
        player.sendTitle(ChatColor.DARK_RED + "Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 45, 120, 135);
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than " + level);
    }

}