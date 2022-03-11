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
     * id and bool is stored in a map for later retrieval
     * @param id the string that will be used as the path for the value
     * @param bool the boolean value that will be used by the path
     * @param fileName the string that will be assigned as the file name
     */
    public void setBooleanValues(String id, boolean bool, String fileName) throws IOException {
        if(createCustomConfig(fileName)) {
            if(!getCustomConfig().contains(id)) {
                getCustomConfig().set(id, bool);
            }
            getCustomConfig().save(customConfigFile);
            boolean value = getCustomConfig().getBoolean(id, false);
            this.booleanValues.put(id, value);
        }
    }

    /**
     * id and val is stored in a map for later retrieval
     * @param id the string that will be used as the path for the value
     * @param val the integer value that will be used by the path
     * @param fileName the string that will be assigned as the file name
     */
    public void setIntegerValues(String id, Integer val, String fileName) throws IOException {
        if(createCustomConfig(fileName)) {
            if(!getCustomConfig().contains(id)) {
                getCustomConfig().set(id, val);
            }
            getCustomConfig().save(customConfigFile);
            int value = getCustomConfig().getInt(id, 0);
            this.integerValues.put(id, value);
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
     * @param id the config path as string
     * @return the assigned value from the given id or path
     */
    public int getValueById(String id){
        return getIntegerValues().get(id);
    }

    /**
     *
     * @param id the config path as string
     * @return
     */
    public boolean getBoolById(String id){
        return getBooleanValues().get(id);
    }

}