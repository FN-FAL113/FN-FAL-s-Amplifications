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
public class FnBoots extends AbstractGears {

    private final NamespacedKey defaultUsageKey;
    private final NamespacedKey defaultUsageKey2;
    private final NamespacedKey defaultUsageKey3;

    private final MainGears mainGears;

    @ParametersAreNonnullByDefault
    @SneakyThrows
    public FnBoots(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = Keys.FN_GEAR_BOOTS;
        this.defaultUsageKey2 = Keys.FN_GEAR_BOOTS_LEVEL;
        this.defaultUsageKey3 = Keys.FN_GEAR_BOOTS_FINAL;
        this.mainGears = new MainGears(getStorageKey(), getStorageKey2(), getStorageKey3(), defaultLore(), item, 25, 100);
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
        lore.add(2, ChatColor.WHITE + "Soldiers from FN's army only wants to posses");
        lore.add(3, ChatColor.WHITE + "this historical boots but it was kept");
        lore.add(4, ChatColor.WHITE + "hidden under the hands of the zion people");
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

        ItemStack itemStack = p.getInventory().getBoots();

        if(mainGears.onHit(event, p, itemStack)){
            upgradeArmor(itemStack, mainGears.getLevel(), p);
        }

    }

    @Override
    public void upgradeArmor(ItemStack armor, int level, Player p){
        ItemMeta meta = armor.getItemMeta();

        if(level == 1){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 2){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 3){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 4){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 5){
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
            meta.removeAttributeModifier(EquipmentSlot.FEET);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.helmet", 4.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.helmet", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.helmet", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            armor.setItemMeta(meta);
        } else if(level == 6){
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 7){
            meta.addEnchant(Enchantment.PROTECTION_FALL, 3, true);
            armor.setItemMeta(meta);
        } else if(level == 8){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 9){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 10){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 6, true);
            meta.removeAttributeModifier(EquipmentSlot.FEET);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.helmet", 4.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.helmet", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.helmet", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Boots attributes has been increased!");
        } else if(level == 11){
            meta.addEnchant(Enchantment.THORNS, 5, true);
            armor.setItemMeta(meta);
        } else if(level == 12){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 13){
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 14){
            meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
            armor.setItemMeta(meta);
        } else if(level == 15){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 10, true);
            meta.removeAttributeModifier(EquipmentSlot.FEET);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.helmet", 4.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.helmet", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.helmet", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Boots attributes has been increased!");
        } else if(level == 16){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 12, true);
            armor.setItemMeta(meta);
        } else if(level == 17){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
            armor.setItemMeta(meta);
        } else if(level == 18){
            meta.addEnchant(Enchantment.THORNS, 10, true);
            armor.setItemMeta(meta);
        } else if(level == 19){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 10, true);
            armor.setItemMeta(meta);
        } else if(level == 20){
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 12, true);
            meta.removeAttributeModifier(EquipmentSlot.FEET);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.helmet", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.helmet", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.helmet", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed.helmet", 0.06, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Boots attributes has been increased!");
        } else if (level == 21){
            meta.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
            armor.setItemMeta(meta);
        } else if (level == 22){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 15, true);
            armor.setItemMeta(meta);
        } else if (level == 23){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 15, true);
            armor.setItemMeta(meta);
        } else if (level == 24){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 15, true);
            armor.setItemMeta(meta);
        } else if (level == 25){
            meta.addEnchant(Enchantment.THORNS, 16, true);
            meta.removeAttributeModifier(EquipmentSlot.FEET);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.helmet", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.helmet", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.helmet", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed.helmet", 0.07, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Boots attributes has been increased!");
        } else if (level == 26){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 15, true);
            armor.setItemMeta(meta);
        } else if (level == 27){
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 14, true);
            armor.setItemMeta(meta);
        } else if (level == 28){
            meta.addEnchant(Enchantment.PROTECTION_FALL, 15, true);
            armor.setItemMeta(meta);
        } else if (level == 29){
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 30){
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
            meta.removeAttributeModifier(EquipmentSlot.FEET);
            armor.setItemMeta(meta);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armorBonus.helmet", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorTough.helmet", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance.helmet", 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed.helmet", 0.08, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            armor.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Boots attributes has been increased!");
        } else if (level == 31){
            meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 32){
            meta.addEnchant(Enchantment.THORNS, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 33){
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 20, true);
            armor.setItemMeta(meta);
        } else if (level == 34){
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 18, true);
            armor.setItemMeta(meta);
        } else if (level == 35){
            meta.addEnchant(Enchantment.PROTECTION_FALL, 20, true);
            armor.setItemMeta(meta);
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