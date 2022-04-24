package ne.fnfal113.fnamplifications.config;

import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Serves as the main config manager for FN Amplifications
 * it can set and retrieve values or create a new yaml config file
 * @author FN_FAL113
 */
public class ConfigManager {

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Getter
    private final Map<String, Integer> integerValues = new HashMap<>();
    @Getter
    private final Map<String, Boolean> booleanValues = new HashMap<>();

    public ConfigManager(){}

    /**
     * id and val is stored in a map for later retrieval
     * @param itemNameSection the string that will be used as the section for the settings
     * @param settings the string that will be used as a key for the itemNameSection
     * @param bool the boolean value that will be used by the key or settings
     * @param fileName the string that will be assigned as the file name
     */
    public void setBooleanValues(String itemNameSection, String settings, boolean bool, String fileName) throws IOException {
        if(createCustomConfig(fileName)) {
            if(!getCustomConfig().isConfigurationSection(itemNameSection)) {
                getCustomConfig().createSection(itemNameSection).set(settings, bool);
            } else if(getCustomConfig().isConfigurationSection(itemNameSection)) {
                if(!getCustomConfig().getConfigurationSection(itemNameSection).getKeys(false).contains(settings)){
                    getCustomConfig().getConfigurationSection(itemNameSection).set(settings, bool);
                }
            }
            getCustomConfig().save(customConfigFile);
            boolean value = getCustomConfig().getConfigurationSection(itemNameSection).getBoolean(settings, false);
            this.booleanValues.put(itemNameSection + "." + settings, value);
        }
    }

    /**
     * id and val is stored in a map for later retrieval
     * @param itemNameSection the string that will be used as the section for the settings
     * @param settings the string that will be used as a key for the itemNameSection
     * @param val the integer value that will be used by the key or settings
     * @param fileName the string that will be assigned as the file name
     */
    public void setIntegerValues(String itemNameSection, String settings, Integer val, String fileName) throws IOException {
        if(createCustomConfig(fileName)) {
            if(!getCustomConfig().isConfigurationSection(itemNameSection)) {
                getCustomConfig().createSection(itemNameSection).set(settings, val);
            } else if(getCustomConfig().isConfigurationSection(itemNameSection)) {
                if(!getCustomConfig().getConfigurationSection(itemNameSection).getKeys(false).contains(settings)){
                    getCustomConfig().getConfigurationSection(itemNameSection).set(settings, val);
                }
            }
            getCustomConfig().save(customConfigFile);
            int value = getCustomConfig().getConfigurationSection(itemNameSection).getInt(settings, 0);
            this.integerValues.put(itemNameSection + "." + settings, value);
        }
    }

    /**
     *
     * @param fileName this will be used as the file name
     * @return true if the IO operation has no errors and successfully created or loaded the config file
     */
    private boolean createCustomConfig(String fileName) {
        customConfigFile = new File(FNAmplifications.getInstance().getDataFolder(),  fileName + ".yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            FNAmplifications.getInstance().saveResource(fileName + ".yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
            return true;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return the config file from the current instance
     */
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    /**
     *
     * @param itemSection the config section
     * @param setting the config section key
     * @return the assigned integer value from the given section and key
     */
    public int getValueById(String itemSection, String setting){
        return getIntegerValues().get(itemSection + "." + setting);
    }

    /**
     *
     * @param itemSection the config section
     * @param setting the config section key
     * @return the assigned boolean value from the given section and key
     */
    public boolean getBoolById(String itemSection, String setting){
        return getBooleanValues().get(itemSection + "." + setting);
    }

}