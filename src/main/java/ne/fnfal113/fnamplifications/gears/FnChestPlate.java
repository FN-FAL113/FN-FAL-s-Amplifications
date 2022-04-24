package ne.fnfal113.fnamplifications.gears;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.gears.abstracts.AbstractGears;
import ne.fnfal113.fnamplifications.gears.implementation.MainGears;
import ne.fnfal113.fnamplifications.utils.Keys;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class FnChestPlate extends AbstractGears {

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;
    private final NamespacedKey defaultUsageKey3;

    private final MainGears mainGears;

    @ParametersAreNonnullByDefault
    @SneakyThrows
    public FnChestPlate(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = Keys.FN_GEAR_CHEST;
        this.defaultUsageKey2 = Keys.FN_GEAR_CHEST_LEVEL;
        this.defaultUsageKey3 = Keys.FN_GEAR_CHEST_FINAL;
        this.mainGears = new MainGears(getStorageKey(), getStorageKey2(), getStorageKey3(), defaultLore(), item, 30, 120);
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

    @Override
    public List<String> defaultLore(){
        List<String> lore = new ArrayList<>();
        lore.add(0, ChatColor.RED + "◬◬◬◬◬◬| "+ ChatColor.LIGHT_PURPLE + ""
                + ChatColor.BOLD + "Lore " + ChatColor.GOLD + "|◬◬◬◬◬◬");
        lore.add(1, "");
        lore.add(2, ChatColor.WHITE + "The armor from the past brought to life");
        lore.add(3, ChatColor.WHITE + "once again. It becomes more powerful during");
        lore.add(4, ChatColor.WHITE + "times of war and conflict");
        lore.add(5, "");
        lore.add(6, ChatColor.RED + "◬◬◬◬◬◬| "+ ChatColor.LIGHT_PURPLE + ""
                + ChatColor.BOLD + "Stats " + ChatColor.GOLD + "|◬◬◬◬◬◬");

        return lore;
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player p = ((Player) event.getEntity()).getPlayer();

        if(p == null){
            return;
        }

        ItemStack itemStack = p.getInventory().getChestplate();

        if(mainGears.onHit(event, p, itemStack)){
            upgradeArmor(itemStack, mainGears.getLevel(), p);
        }

    }

    @Override
    public void upgradeArmor(ItemStack armor, int level, Player p){
        ItemMeta meta = armor.getItemMeta();

        if(level == 1){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 2){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 3){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 4){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 5){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
            meta.removeAttributeModifier(EquipmentSlot.CHEST);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.chest", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.chest", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.chest", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Chestplate attributes has been increased!");
        } else if(level == 6){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 7){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 8){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 9){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 10, true);
            armor.setItemMeta(meta);
        } else if(level == 10){
            meta.addEnchant(Enchantment.THORNS, 3, true);
            meta.removeAttributeModifier(EquipmentSlot.CHEST);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.chest", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.chest", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.chest", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Chestplate attributes has been increased!");
        } else if(level == 11){
            meta.addEnchant(Enchantment.THORNS, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 12){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 10, true);
            armor.setItemMeta(meta);
        } else if(level == 13){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
            armor.setItemMeta(meta);
        } else if(level == 14){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 10, true);
            armor.setItemMeta(meta);
        } else if(level == 15){
            meta.addEnchant(Enchantment.THORNS, 10, true);
            meta.removeAttributeModifier(EquipmentSlot.CHEST);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.chest", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.chest", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.chest", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Chestplate attributes has been increased!");
        } else if(level == 16){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 15, true);
            armor.setItemMeta(meta);
        } else if(level == 17){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 14, true);
            armor.setItemMeta(meta);
        } else if(level == 18){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 16, true);
            armor.setItemMeta(meta);
        } else if(level == 19){
            meta.addEnchant(Enchantment.THORNS, 15, true);
            armor.setItemMeta(meta);
        } else if(level == 20){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 15, true);
            meta.removeAttributeModifier(EquipmentSlot.CHEST);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.chest", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.chest", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.chest", 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Chestplate attributes has been increased!");
        } else if (level == 21){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 22){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 23){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 24){
            meta.addEnchant(Enchantment.THORNS, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 25){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 20, true);
            meta.removeAttributeModifier(EquipmentSlot.CHEST);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.chest", 12.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.chest", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.chest", 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Chestplate attributes has been increased!");
        }

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

}