package ne.fnfal113.fnamplifications.Gears;

import com.google.common.base.Strings;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import ne.fnfal113.fnamplifications.Multiblock.FnAssemblyStation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.ChatColor.stripColor;

public class FnLeggings extends SlimefunItem{

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;
    private final NamespacedKey defaultUsageKey3;


    @ParametersAreNonnullByDefault
    public FnLeggings(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "leggings");
        this.defaultUsageKey2 = new NamespacedKey(FNAmplifications.getInstance(), "leggingslevel");
        this.defaultUsageKey3 = new NamespacedKey(FNAmplifications.getInstance(), "leggingsfinal");
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    protected @Nonnull
    NamespacedKey getStorageKey2() {
        return defaultUsageKey2;
    }

    protected @Nonnull
    NamespacedKey getStorageKey3() {
        return defaultUsageKey3;
    }


    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player p = ((Player) event.getEntity()).getPlayer();
        ItemStack itemStack = p.getInventory().getLeggings();

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return;
        }

        NamespacedKey key = getStorageKey();
        NamespacedKey key2 = getStorageKey2();
        NamespacedKey key3 = getStorageKey3();
        PersistentDataContainer progress = meta.getPersistentDataContainer();
        PersistentDataContainer level = meta.getPersistentDataContainer();
        PersistentDataContainer max = meta.getPersistentDataContainer();
        int amount = progress.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int armorLevel = level.getOrDefault(key2, PersistentDataType.INTEGER, 0);
        int maxReq = max.getOrDefault(key3, PersistentDataType.INTEGER, 30);
        int total = amount + 1;
        progress.set(key, PersistentDataType.INTEGER, total);

        List<String> lore = new ArrayList<>();

        if (total >= 0) {
            lore.add(0, ChatColor.RED + "◬◬◬◬◬◬| "+ ChatColor.LIGHT_PURPLE + ""
                    + ChatColor.BOLD + "Lore " + ChatColor.GOLD + "|◬◬◬◬◬◬");
            lore.add(1, "");
            lore.add(2, ChatColor.WHITE + "Glorious leggings worn by FN during war");
            lore.add(3, ChatColor.WHITE + "and was glorified on every victory against");
            lore.add(4, ChatColor.WHITE + "his foes");
            lore.add(5, "");
            lore.add(6, ChatColor.RED + "◬◬◬◬◬◬| "+ ChatColor.LIGHT_PURPLE + ""
                    + ChatColor.BOLD + "Stats " + ChatColor.GOLD + "|◬◬◬◬◬◬");
            lore.add(7, ChatColor.YELLOW + "Leggings Level: " + armorLevel);
            lore.add(8, ChatColor.YELLOW + "Progress:");
            lore.add(9, ChatColor.GRAY + "[" + getProgressBar(total, maxReq, 10, '■', ChatColor.YELLOW, ChatColor.GRAY) + ChatColor.GRAY + "]");
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }

        if (total == maxReq) {
            progress.set(key, PersistentDataType.INTEGER, 0);
            int totalLevel = armorLevel + 1;
            level.set(key2, PersistentDataType.INTEGER, totalLevel);
            lore.set(0, ChatColor.RED + "◬◬◬◬◬◬| "+ ChatColor.LIGHT_PURPLE + ""
                    + ChatColor.BOLD + "Lore " + ChatColor.GOLD + "|◬◬◬◬◬◬");
            lore.set(1, "");
            lore.set(2, ChatColor.WHITE + "Glorious leggings worn by FN during war");
            lore.set(3, ChatColor.WHITE + "and was glorified on every victory against");
            lore.set(4, ChatColor.WHITE + "his foes");
            lore.set(5, "");
            lore.set(6, ChatColor.RED + "◬◬◬◬◬◬| "+ ChatColor.LIGHT_PURPLE + ""
                    + ChatColor.BOLD + "Stats " + ChatColor.GOLD + "|◬◬◬◬◬◬");
            lore.set(7, ChatColor.YELLOW + "Leggings Level: " + totalLevel);
            lore.set(8, ChatColor.YELLOW + "Progress:");
            lore.set(9, ChatColor.GRAY + "[" + getProgressBar(total, maxReq, 10, '■', ChatColor.YELLOW, ChatColor.GRAY) + ChatColor.GRAY + "]");
            max.set(key3, PersistentDataType.INTEGER, maxReq + 105);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            upgradeArmor(itemStack, totalLevel, p);
        }

    }

    public void upgradeArmor(ItemStack armor, int level, Player p) {
        ItemMeta meta = armor.getItemMeta();

        if (meta == null) {
            return;
        }

        if (level == 1) {
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 2) {
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 3) {
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 4) {
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 6, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 5) {
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 3, true);
            meta.removeAttributeModifier(EquipmentSlot.LEGS);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.leggings", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.leggings", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.leggings", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            armor.setItemMeta(meta);
            levelUp(p);
            p.sendMessage(ChatColor.GOLD + "Leggings attributes has been increased!");
        } else if (level == 6) {
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 6, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 7) {
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 8) {
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 6, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 9) {
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 10, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 10) {
            meta.addEnchant(Enchantment.THORNS, 3, true);
            meta.removeAttributeModifier(EquipmentSlot.LEGS);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.leggings", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.leggings", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.leggings", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            armor.setItemMeta(meta);
            levelUp(p);
            p.sendMessage(ChatColor.GOLD + "Leggings attributes has been increased!");
        } else if (level == 11) {
            meta.addEnchant(Enchantment.THORNS, 6, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 12) {
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 10, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 13) {
            meta.addEnchant(Enchantment.THORNS, 10, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 14) {
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 15) {
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 10, true);
            meta.removeAttributeModifier(EquipmentSlot.LEGS);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.leggings", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.leggings", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.leggings", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            armor.setItemMeta(meta);
            levelUp(p);
            p.sendMessage(ChatColor.GOLD + "Leggings attributes has been increased!");
        } else if (level == 16) {
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 15, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 17) {
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 14, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 18) {
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 16, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 19) {
            meta.addEnchant(Enchantment.THORNS, 15, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 20) {
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 15, true);
            meta.removeAttributeModifier(EquipmentSlot.LEGS);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.leggings", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.leggings", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.leggings", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            armor.setItemMeta(meta);
            levelUp(p);
            p.sendMessage(ChatColor.GOLD + "Leggings attributes has been increased!");
        } else if (level == 21) {
            meta.addEnchant(Enchantment.THORNS, 20, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 22) {
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 20, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 23) {
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
            armor.setItemMeta(meta);
            levelUp(p);
        } else if (level == 24) {
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 20, true);
            levelUp(p);
        } else if (level == 25) {
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 20, true);
            meta.removeAttributeModifier(EquipmentSlot.LEGS);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.leggings", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.leggings", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.leggings", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            armor.setItemMeta(meta);
            levelUp(p);
            p.sendMessage(ChatColor.GOLD + "Leggings attributes has been increased!");
        } else if (level > 25){
            levelUp(p);
        }


    }

    public void levelUp(Player p) {
        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications]> " + ChatColor.GOLD + "FN's Chausses of Eminence leveled up!");
        p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    @Override
    public boolean isEnchantable() {
        return true;
    }

    @Override
    public boolean isDisenchantable() {
        return false;
    }

    @Override
    public boolean isUseableInWorkbench() {
        return false;
    }

    public static void setup() {
        new FnLeggings(FNAmpItems.FN_GEARS, FNAmpItems.FN_GEAR_LEGGINGS, FnAssemblyStation.RECIPE_TYPE, new ItemStack[]{
                new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2), new ItemStack(Material.IRON_LEGGINGS), new SlimefunItemStack(SlimefunItems.REINFORCED_PLATE, 2),
                SlimefunItems.ENCHANTMENT_RUNE, new ItemStack(Material.NETHERITE_INGOT, 3), SlimefunItems.ENCHANTMENT_RUNE,
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 3), new ItemStack(Material.DIAMOND_LEGGINGS), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 3)})
                .register(plugin);
    }
}