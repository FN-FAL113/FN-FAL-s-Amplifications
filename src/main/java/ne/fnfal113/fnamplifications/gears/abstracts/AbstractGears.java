package ne.fnfal113.fnamplifications.gears.abstracts;

import com.google.gson.JsonObject;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.config.ConfigManager;
import ne.fnfal113.fnamplifications.gears.implementation.GearTask;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.UUID;

public abstract class AbstractGears extends SlimefunItem {

    @Getter
    private final NamespacedKey defaultUsageKey;
    @Getter
    private final NamespacedKey defaultUsageKey2;
    @Getter
    private final NamespacedKey defaultUsageKey3;
    @Getter
    private final int startingProgress;
    @Getter
    private final int incrementingProgress;
    @Getter
    private final int maxLevel;
    @Getter
    private final int maxAttributes;
    @Getter
    private final EquipmentSlot equipmentSlot;
    @Getter
    private final GearTask gearTask;

    @Getter
    private final ConfigManager configManager = FNAmplifications.getInstance().getConfigManager();

    public AbstractGears(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         NamespacedKey defaultUsageKey, NamespacedKey defaultUsageKey2, NamespacedKey defaultUsageKey3,
                         int startingProgress, int incrementingProgress, int maxLevel, int maxAttributes, EquipmentSlot equipmentSlot) {
        super(itemGroup, item, recipeType, recipe);

        initializeSettings(maxLevel, maxAttributes);
        this.defaultUsageKey = defaultUsageKey;
        this.defaultUsageKey2 = defaultUsageKey2;
        this.defaultUsageKey3 = defaultUsageKey3;
        this.startingProgress = startingProgress;
        this.incrementingProgress = incrementingProgress;
        this.maxLevel = configManager.getValueById(this.getId(), "max-level");
        this.maxAttributes = configManager.getValueById(this.getId(), "max-attributes");
        this.equipmentSlot = equipmentSlot;
        this.gearTask = new GearTask(getDefaultUsageKey(), getDefaultUsageKey2(), getDefaultUsageKey3(), item, startingProgress, incrementingProgress, getMaxLevel());
    }

    public void initializeSettings(int maxLevel, int maxAttributes){
        try {
            configManager.setConfigBooleanValues(this.getId(), "unbreakable", false, "fn-gear-unbreakable-settings", true);
            configManager.setConfigIntegerValues(this.getId(), "max-level", maxLevel, "fn-gear-level-settings", false);
            configManager.setConfigIntegerValues(this.getId(), "max-attributes", maxAttributes, "fn-gear-level-settings", false);

            JsonObject jsonObject = (JsonObject) configManager.loadJson(this.getId().toLowerCase() + "_default_ench");

            int configMaxLevel = configManager.getValueById(this.getId(), "max-level");

            for(int i = 1; i <= Math.max(configMaxLevel, maxLevel); i++) {
                String section = this.getId() + "." + "level-" + i;

                initializeEnchants(section, i, jsonObject);

                if(i % 5 == 0){
                    initializeAttributes(section, maxAttributes, i, jsonObject);
                }
            }

            setUnbreakable();
        } catch (IOException | NullPointerException | IllegalArgumentException e){
            FNAmplifications.getInstance().getLogger().info("An error has occurred upon initializing gear config settings! Please report on github with logs!");
            e.printStackTrace();
        }
    }

    public void initializeEnchants(String section, int i, JsonObject jsonObject){
        try{
            String settingEnchant = "enchantment-name";
            String settingEnchantLevel = "enchantment-level";

            // initialize default enchant and levels under the default max level from the json resource stream
            if(jsonObject.has("level-" + i) && jsonObject.getAsJsonObject("level-" + i).has(settingEnchant)
                    && jsonObject.getAsJsonObject("level-" + i).has(settingEnchantLevel)) {
                configManager.setConfigStringValues(section, settingEnchant, jsonObject.getAsJsonObject("level-" + i).get(settingEnchant).getAsString(), "fn-gear-level-settings", false);
                configManager.setConfigIntegerValues(section, settingEnchantLevel, jsonObject.getAsJsonObject("level-" + i).get(settingEnchantLevel).getAsInt(), "fn-gear-level-settings", false);
            }

            // read and add to map the added config section and values over the config max level
            if(configManager.getCustomConfig().isConfigurationSection(section)) {
                String enchantment = configManager.getCustomConfig().getConfigurationSection(section).getString(settingEnchant, "null");
                int enchantLevel = configManager.getCustomConfig().getConfigurationSection(section).getInt(settingEnchantLevel, 0);

                configManager.setMapStringValue(section, settingEnchant, enchantment);
                configManager.setMapIntValue(section, settingEnchantLevel, enchantLevel);
            }
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            FNAmplifications.getInstance().getLogger().info("An error has occurred upon initializing gear enchants setting! Please report on github with logs!");
            e.printStackTrace();
        }
    }

    public void initializeAttributes(String section, int maxAttributes, int i, JsonObject jsonObject){
        try {
            for (int x = 1; x <= maxAttributes; x++) {
                String attributeSection = section + "." + "bonus-attributes" + "." + "attribute-" + x;
                String settingAttribute = "attribute-name";
                String settingAttributeValue = "attribute-value";

                // initialize default attribute and values under the default max level from the json resource stream
                if (jsonObject.has("level-" + i) && jsonObject.getAsJsonObject("level-" + i).has("attributes")
                        && jsonObject.getAsJsonObject("level-" + i).getAsJsonObject("attributes").has("attribute-" + x)) {
                    configManager.setConfigStringValues(attributeSection, settingAttribute, jsonObject.getAsJsonObject("level-" + i).getAsJsonObject("attributes").get("attribute-" + x).getAsJsonObject().get(settingAttribute).getAsString(), "fn-gear-level-settings", false);
                    configManager.setConfigDoubleValues(attributeSection, settingAttributeValue, jsonObject.getAsJsonObject("level-" + i).getAsJsonObject("attributes").get("attribute-" + x).getAsJsonObject().get(settingAttributeValue).getAsDouble(), "fn-gear-level-settings", false);
                }

                // read and add to map the added config section and values over the config max level
                if (configManager.getCustomConfig().isConfigurationSection(attributeSection)) {
                    String attribute = configManager.getCustomConfig().getConfigurationSection(attributeSection).getString(settingAttribute, "null");
                    double attributeValue = configManager.getCustomConfig().getConfigurationSection(attributeSection).getDouble(settingAttributeValue, 0.0);

                    configManager.setMapStringValue(attributeSection, settingAttribute, attribute);
                    configManager.setMapDoubleValue(attributeSection, settingAttributeValue, attributeValue);
                }
            }
        } catch (IOException | NullPointerException | IllegalArgumentException e){
            FNAmplifications.getInstance().getLogger().info("An error has occurred upon initializing gear bonus attributes setting! Please report on github with logs!");
            e.printStackTrace();
        }
    }

    public final void setUnbreakable() {
        ItemMeta meta = this.getItem().getItemMeta();
        meta.setUnbreakable(configManager.getBoolById(this.getId(), "unbreakable"));
        this.getItem().setItemMeta(meta);
    }

    public void onHit(EntityDamageByEntityEvent event, ItemStack armour){
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player p = ((Player) event.getEntity()).getPlayer();

        if(p == null){
            return;
        }

        if(gearTask.onHit(event, p, armour)){
            upgradeArmor(armour, gearTask.getLevel(), p, getEquipmentSlot());
        }

    }

    /**
     * When the armor levels up, add or upgrade any enchants/attributes
     * @param armor the armor that leveled up
     * @param level the new level of the armor
     * @param p the player who wore the armor
     */
    public void upgradeArmor(ItemStack armor, int level, Player p, EquipmentSlot slot){
        ItemMeta meta = armor.getItemMeta();
        String section = this.getId() + "." + "level-" + level;
        String enchant = getConfigManager().getStringById(section, "enchantment-name");
        int enchantLevel = getConfigManager().getValueById(section, "enchantment-level");

        try {
            if (!enchant.equalsIgnoreCase("null") && enchantLevel != 0) {
                if(EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchant)) != null) {
                    meta.addEnchant(EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchant)), enchantLevel, true);
                }
            }
        } catch (NullPointerException | IllegalArgumentException e){
            p.sendMessage(Utils.colorTranslator("&cAn error has occurred upon adding new armor enchants, please ask the admin to check the console for errors and report it on github"));
            e.printStackTrace();
        }

        if (level % 5 == 0) {
            try {
                for (int i = 1; i <= getMaxAttributes(); i++) {
                    String attribute = getConfigManager().getStringById(section + "." + "bonus-attributes" + "." + "attribute-" + i, "attribute-name");
                    double attributeValue = getConfigManager().getDoubleValueById(section + "." + "bonus-attributes" + "." + "attribute-" + i, "attribute-value");

                    if (!attribute.equalsIgnoreCase("null") && attributeValue != 0.0) {
                        if(meta.getAttributeModifiers(getEquipmentSlot()).asMap().containsKey(Attribute.valueOf(attribute))) {
                            meta.removeAttributeModifier(Attribute.valueOf(attribute));
                        }
                        meta.addAttributeModifier(Attribute.valueOf(attribute), new AttributeModifier(
                                UUID.randomUUID(),
                                "generic." + attribute.toLowerCase() + "." + armor.getType().toString().toLowerCase(),
                                attributeValue,
                                AttributeModifier.Operation.ADD_NUMBER, slot));
                    }

                }

                p.sendMessage(Utils.colorTranslator("&6FN Gear attributes has been increased!"));
            } catch (NullPointerException | IllegalArgumentException e){
                p.sendMessage(Utils.colorTranslator("&cAn error has occurred upon adding bonus armor attributes, please ask the admin to check the console for errors and report it on github"));
                e.printStackTrace();
            }

        }

        armor.setItemMeta(meta);
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