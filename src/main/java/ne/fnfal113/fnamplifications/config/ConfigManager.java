package ne.fnfal113.fnamplifications.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Main config manager class for FN Amplifications
 * @author FN_FAL113
 */
@NoArgsConstructor
public class ConfigManager {

    @Getter
    private final Map<String, FileConfiguration> fileConfigurationMap = new HashMap<>();

    /**
     *
     * @param <T> this method uses generics for parameter 'val'
     * @param itemNameSection the config section that will be created if not exist
     * @param settings the settings that will be created or added to section if not exist
     * @param val generic value for the settings
     * @param fileName the name of the config file
     */
    public <T> void initializeConfig(String itemNameSection, String settings, T val, String fileName)  {
        FileConfiguration customConfig = getCustomConfig(fileName);

        try{
            // skip creating existing config sections and settings if necessary to prevent being overridden
            if (!customConfig.isConfigurationSection(itemNameSection)) {
                // create a config section if not exist
                customConfig.createSection(itemNameSection).set(settings, val);
                // save only for new section or setting
                customConfig.save(new File(FNAmplifications.getInstance().getDataFolder(), fileName + ".yml"));
            } else if (!customConfig.getConfigurationSection(itemNameSection).contains(settings)) {
                // create settings if config section exist
                customConfig.getConfigurationSection(itemNameSection).set(settings, val);
                // save only for new section or setting
                customConfig.save(new File(FNAmplifications.getInstance().getDataFolder(), fileName + ".yml"));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param jsonName the name of the json file that will be saved
     */
    @SneakyThrows
    public JsonObject loadJson(String jsonName) {
        try {
            // get the json in the resource folder as input stream
            // and deserialize the contents to the temporary file line by line
            InputStream resource = FNAmplifications.class.getResourceAsStream("/json/" + jsonName + ".json");

            JsonParser parser = new JsonParser();
            return parser.parse(new InputStreamReader(resource)).getAsJsonObject();
        } catch (JsonParseException | NullPointerException e) {
            e.printStackTrace();
            return new JsonObject();
        }
    }

    /**
     * @param fileName the config file name
     * @return the loaded config file
     */
    public FileConfiguration getCustomConfig(String fileName) {
        // if a custom config exist in the map with the given fileName key then re-use it
        if(getFileConfigurationMap().containsKey(fileName)){
            return getFileConfigurationMap().get(fileName);
        }

        File customConfigFile = new File(FNAmplifications.getInstance().getDataFolder(), fileName + ".yml");
        FileConfiguration customConfig = new YamlConfiguration();

        try {
            if (!customConfigFile.exists()) {
                customConfigFile.getParentFile().mkdirs();
                FNAmplifications.getInstance().saveResource(fileName + ".yml", false);
            }

            customConfig.load(customConfigFile);

            // cache custom config object instance
            getFileConfigurationMap().put(fileName, customConfig);

            return customConfig;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();

            return customConfig;
        }

    }

}