package ne.fnfal113.fnamplifications.config;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serves as the main config manager for FN Amplifications
 * it can retrieve values from a json or yml file in order
 * to create a new yaml config file and values are cached
 * in a map for easy retrieval
 * @author FN_FAL113
 */
public class ConfigManager {

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Getter
    private final Map<String, Integer> integerValues = new HashMap<>();
    @Getter
    private final Map<String, Double> doubleValues = new HashMap<>();
    @Getter
    private final Map<String, Boolean> booleanValues = new HashMap<>();
    @Getter
    private final Map<String, String> stringValues = new HashMap<>();

    private final List<String> autoUpdateFile = new ArrayList<>();

    public ConfigManager(){}

    /**
     * id and val is stored in a map for later retrieval
     * @param itemNameSection the string that will be used as the section for the settings
     * @param settings the string that will be used as a key for the itemNameSection
     * @param bool the boolean value that will be used by the key or settings
     * @param fileName the string that will be assigned as the file name
     */
    public void setConfigBooleanValues(String itemNameSection, String settings, boolean bool, String fileName, boolean autoUpdate) throws IOException {
        if(initializeConfig(itemNameSection, settings, bool, fileName, autoUpdate)) {
            getCustomConfig().save(customConfigFile);
            if(getCustomConfig().isConfigurationSection(itemNameSection)) {
                boolean value = getCustomConfig().getConfigurationSection(itemNameSection).getBoolean(settings, false);
                this.booleanValues.put(itemNameSection + "." + settings, value);
            }
        }
    }

    /**
     * id and val is stored in a map for later retrieval
     * @param itemNameSection the string that will be used as the section for the settings
     * @param settings the string that will be used as a key for the itemNameSection
     * @param val the integer value that will be used by the key or settings
     * @param fileName the string that will be assigned as the file name
     */
    public void setConfigIntegerValues(String itemNameSection, String settings, int val, String fileName, boolean autoUpdate) throws IOException {
        if(initializeConfig(itemNameSection, settings, val, fileName, autoUpdate)) {
            getCustomConfig().save(customConfigFile);
            if(getCustomConfig().isConfigurationSection(itemNameSection)) {
                int value = getCustomConfig().getConfigurationSection(itemNameSection).getInt(settings, 0);
                this.integerValues.put(itemNameSection + "." + settings, value);
            }
        }
    }

    /**
     * id and val is stored in a map for later retrieval
     * @param itemNameSection the string that will be used as the section for the settings
     * @param settings the string that will be used as a key for the itemNameSection
     * @param val the double value that will be used by the key or settings
     * @param fileName the string that will be assigned as the file name
     */
    public void setConfigDoubleValues(String itemNameSection, String settings, double val, String fileName, boolean autoUpdate) throws IOException {
        if(initializeConfig(itemNameSection, settings, val, fileName, autoUpdate)) {
            getCustomConfig().save(customConfigFile);
            if(getCustomConfig().isConfigurationSection(itemNameSection)) {
                double value = getCustomConfig().getConfigurationSection(itemNameSection).getDouble(settings, 0);
                this.doubleValues.put(itemNameSection + "." + settings, value);
            }
        }
    }

    /**
     * id and val is stored in a map for later retrieval
     * @param itemNameSection the string that will be used as the section for the settings
     * @param settings the string that will be used as a key for the itemNameSection
     * @param val the string value that will be used by the key or settings
     * @param fileName the string that will be assigned as the file name
     */
    public void setConfigStringValues(String itemNameSection, String settings, String val, String fileName, boolean autoUpdate) throws IOException {
        if(initializeConfig(itemNameSection, settings, val, fileName, autoUpdate)) {
            getCustomConfig().save(customConfigFile);
            if(getCustomConfig().isConfigurationSection(itemNameSection)) {
                String value = getCustomConfig().getConfigurationSection(itemNameSection).getString(settings, "null");
                this.stringValues.put(itemNameSection + "." + settings, value);
            }
        }
    }

    /**
     *
     * @param itemNameSection the config section that will be created if not exist
     * @param settings the settings that will be created or added to section if not exist
     * @param val generic value for the settings
     * @param fileName the name of the config file
     * @param autoUpdate auto update the file if there are missing config section or settings
     * @param <T> this method uses generics for parameter 'val'
     * @return true if the config is loaded successfully
     */
    public <T> boolean initializeConfig(String itemNameSection, String settings, T val, String fileName, boolean autoUpdate){
        if(!loadOrCreateCustomConfig(fileName, autoUpdate)) {
            return false;
        }

        if(autoUpdate){
            // add newly created or missing settings and sections to the config
            // does not run by default unless auto update is set to true then it will add missing entries
            if(!getCustomConfig().isConfigurationSection(itemNameSection)){
                getCustomConfig().createSection(itemNameSection).set(settings, val);
            } else if (!getCustomConfig().getConfigurationSection(itemNameSection).getKeys(false).contains(settings)) {
                getCustomConfig().getConfigurationSection(itemNameSection).set(settings, val);
            }

            autoUpdateFile.remove(fileName);
        } else if (!getCustomConfig().isConfigurationSection(itemNameSection) && autoUpdateFile.contains(fileName)) {
            // create a config section if not exist and file name is in the auto update list
            // runs by default if the file is newly created and will add entries
            getCustomConfig().createSection(itemNameSection).set(settings, val);
        } else if (getCustomConfig().isConfigurationSection(itemNameSection) && autoUpdateFile.contains(fileName)) {
            // create settings if the config section exist and file name is in the auto update list
            // runs by default if the file is newly created and will add entries
            if (!getCustomConfig().getConfigurationSection(itemNameSection).getKeys(false).contains(settings)) {
                getCustomConfig().getConfigurationSection(itemNameSection).set(settings, val);
            }
        }

        return true;
    }

    /**
     *
     * @param fileName this will be used as the file name
     * @return true if the IO operation has no errors and successfully created or loaded the config file
     */
    private boolean loadOrCreateCustomConfig(String fileName, boolean autoUpdate) {
        customConfigFile = new File(FNAmplifications.getInstance().getDataFolder(),  fileName + ".yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            FNAmplifications.getInstance().saveResource(fileName + ".yml", false);

            autoUpdateFile.add(fileName);
        }
        if(autoUpdate){
            autoUpdateFile.add(fileName);
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
     * @param jsonName the name of the json file that will be saved
     */
    @SneakyThrows
    public Object loadJson(String jsonName) {
        try {
            // create temporary file
            File file = File.createTempFile(jsonName, ".json", new File(System.getProperty("java.io.tmpdir")));

            // get the json in the resource folder as input stream
            // and deserialize the contents to the temporary file line by line
            InputStream inputStream = FNAmplifications.class.getResourceAsStream("/" + jsonName + ".json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            PrintWriter writer = new PrintWriter(file);

            while ((line = bufferedReader.readLine()) != null) {
                writer.append(line).append(System.getProperty("line.separator"));
            }

            // on server close, temporary file gets deleted from the system temp folder
            file.deleteOnExit();

            // close io streams to prevent leaks
            bufferedReader.close();
            writer.close();

            // return the temporary file with the json contents
            JsonParser parser = new JsonParser();
            return parser.parse(new FileReader(file));
        } catch (IOException | JsonParseException | NullPointerException e) {
            e.printStackTrace();
            return new FileReader("test");
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
    public int getIntValueById(String itemSection, String setting){
        return getIntegerValues().getOrDefault(itemSection + "." + setting, 0);
    }

    /**
     *
     * @param itemSection the config section
     * @param setting the config section key
     * @return the assigned double value from the given section and key
     */
    public double getDoubleValueById(String itemSection, String setting){
        return getDoubleValues().getOrDefault(itemSection + "." + setting, 0.0);
    }

    /**
     *
     * @param itemSection the config section
     * @param setting the config section key
     * @return the assigned string value from the given section and key
     */
    public String getStringById(String itemSection, String setting){
        return getStringValues().getOrDefault(itemSection + "." + setting, "null");
    }

    /**
     *
     * @param itemSection the config section
     * @param setting the config section key
     * @return the assigned boolean value from the given section and key
     */
    public boolean getBoolById(String itemSection, String setting){
        return getBooleanValues().getOrDefault(itemSection + "." + setting, false);
    }

    public void setMapIntValue(String itemSection, String setting, int value){
       getIntegerValues().put(itemSection + "." + setting, value);
    }

    public void setMapDoubleValue(String itemSection, String setting, double value){
        getDoubleValues().put(itemSection + "." + setting, value);
    }

    public void setMapStringValue(String itemSection, String setting, String value){
        getStringValues().put(itemSection + "." + setting, value);
    }

    public void setMapBooleanValue(String itemSection, String setting, boolean value){
        getBooleanValues().put(itemSection + "." + setting, value);
    }

}