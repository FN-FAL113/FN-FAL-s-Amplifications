package ne.fnfal113.fnamplifications.ConfigValues;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import ne.fnfal113.fnamplifications.FNAmplifications;
import org.bukkit.configuration.file.FileConfiguration;

public class ReturnConfValue {

    private final SlimefunAddon plugin = FNAmplifications.getInstance();


    FileConfiguration config = plugin.getJavaPlugin().getConfig();

    public int rank1capacity()  { return config.getInt("FN-Xpansion-Rank1-Capacity"); }

    public int rank1dayrate()
    {
        return config.getInt("FN-Xpansion-Rank1-Dayrate");
    }

    public int rank1nightrate()
    {
        return config.getInt("FN-Xpansion-Rank1-Nightrate");
    }

    public int rank1output()
    {
        return config.getInt("FN-Xpansion-Rank1-Nightrate");
    }

    public int rank2capacity() { return config.getInt("FN-Xpansion-Rank2-Capacity"); }

    public int rank2dayrate() { return config.getInt("FN-Xpansion-Rank2-Dayrate"); }

    public int rank2nightrate() {return config.getInt("FN-Xpansion-Rank2-Nightrate"); }

    public int rank2output() { return config.getInt("FN-Xpansion-Rank2-Nightrate"); }

    public int rank3capacity() { return config.getInt("FN-Xpansion-Rank3-Capacity"); }

    public int rank3dayrate() { return config.getInt("FN-Xpansion-Rank3-Dayrate"); }

    public int rank3nightrate() {return config.getInt("FN-Xpansion-Rank3-Nightrate"); }

    public int rank3output() { return config.getInt("FN-Xpansion-Rank3-Nightrate"); }

    public int rank4capacity() { return config.getInt("FN-Xpansion-Rank4-Capacity"); }

    public int rank4dayrate() { return config.getInt("FN-Xpansion-Rank4-Dayrate"); }

    public int rank4nightrate() {return config.getInt("FN-Xpansion-Rank4-Nightrate"); }

    public int rank4output() { return config.getInt("FN-Xpansion-Rank4-Nightrate"); }

    public int rank5capacity() { return config.getInt("FN-Xpansion-Rank5-Capacity"); }

    public int rank5dayrate() { return config.getInt("FN-Xpansion-Rank5-Dayrate"); }

    public int rank5nightrate() {return config.getInt("FN-Xpansion-Rank5-Nightrate"); }

    public int rank5output() { return config.getInt("FN-Xpansion-Rank5-Nightrate"); }

    public int rank6capacity() { return config.getInt("FN-Xpansion-Rank6-Capacity"); }

    public int rank6dayrate() { return config.getInt("FN-Xpansion-Rank6-Dayrate"); }

    public int rank6nightrate() {return config.getInt("FN-Xpansion-Rank6-Nightrate"); }

    public int rank6output() { return config.getInt("FN-Xpansion-Rank6-Nightrate"); }

    public int rank7capacity() { return config.getInt("FN-Xpansion-Rank7-Capacity"); }

    public int rank7dayrate() { return config.getInt("FN-Xpansion-Rank7-Dayrate"); }

    public int rank7nightrate() {return config.getInt("FN-Xpansion-Rank7-Nightrate"); }

    public int rank7output() { return config.getInt("FN-Xpansion-Rank7-Nightrate"); }

    public int rank8capacity() { return config.getInt("FN-Xpansion-Rank8-Capacity"); }

    public int rank8dayrate() { return config.getInt("FN-Xpansion-Rank8-Dayrate"); }

    public int rank8nightrate() {return config.getInt("FN-Xpansion-Rank8-Nightrate"); }

    public int rank8output() { return config.getInt("FN-Xpansion-Rank8-Nightrate"); }

    public int rank9capacity() { return config.getInt("FN-Xpansion-Rank9-Capacity"); }

    public int rank9dayrate() { return config.getInt("FN-Xpansion-Rank9-Dayrate"); }

    public int rank9nightrate() {return config.getInt("FN-Xpansion-Rank9-Nightrate"); }

    public int rank9output() { return config.getInt("FN-Xpansion-Rank9-Nightrate"); }

    public int rank10capacity() { return config.getInt("FN-Xpansion-Rank10-Capacity"); }

    public int rank10dayrate() { return config.getInt("FN-Xpansion-Rank10-Dayrate"); }

    public int rank10nightrate() {return config.getInt("FN-Xpansion-Rank10-Nightrate"); }

    public int rank10output() { return config.getInt("FN-Xpansion-Rank10-Nightrate"); }

    public int rank11capacity() { return config.getInt("FN-Xpansion-Rank11-Capacity"); }

    public int rank11dayrate() { return config.getInt("FN-Xpansion-Rank11-Dayrate"); }

    public int rank11nightrate() {return config.getInt("FN-Xpansion-Rank11-Nightrate"); }

    public int rank11output() { return config.getInt("FN-Xpansion-Rank11-Nightrate"); }

    public int rank12capacity() { return config.getInt("FN-Xpansion-Rank12-Capacity"); }

    public int rank12dayrate() { return config.getInt("FN-Xpansion-Rank12-Dayrate"); }

    public int rank12nightrate() {return config.getInt("FN-Xpansion-Rank12-Nightrate"); }

    public int rank12output() { return config.getInt("FN-Xpansion-Rank12-Nightrate"); }


    public int clayTickrate() { return config.getInt("FN_CLAY-TickRate"); }
    public int warpedTickrate() { return config.getInt("FN_WARPED_NYLIUM-TickRate"); }
    public int terracottaTickrate() { return config.getInt("FN_TERRACOTTA-TickRate"); }
    public int boneTickrate() { return config.getInt("FN_BONE-TickRate"); }
    public int diamondTickrate() { return config.getInt("FN_DIAMOND-TickRate"); }
    public int emeraldTickrate() { return config.getInt("FN_EMERALD-TickRate"); }
    public int dirtTickrate() { return config.getInt("FN_Dirt-TickRate"); }
    public int honeycombTickrate() { return config.getInt("FN_HONEYCOMB-TickRate"); }


    public int tier1Power() { return config.getInt("Tier1-power1"); }
    public int tier1PowerNight() { return config.getInt("Tier1-power1-night"); }
    public int tier1Buffer() { return config.getInt("Buffer1"); }
    public String tier1Lore() { return config.getString("Tier1-Lore"); }

    public int tier2Power() { return config.getInt("Tier2-power2"); }
    public int tier2PowerNight() { return config.getInt("Tier2-power2-night"); }
    public int tier2Buffer() { return config.getInt("Buffer2"); }
    public String tier2Lore() { return config.getString("Tier2-Lore"); }

    public int tier3Power() { return config.getInt("Tier3-power3"); }
    public int tier3PowerNight() { return config.getInt("Tier3-power3-night"); }
    public int tier3Buffer() { return config.getInt("Buffer3"); }
    public String tier3Lore() { return config.getString("Tier3-Lore"); }

    public int tier4Power() { return config.getInt("Tier4-power4"); }
    public int tier4PowerNight() { return config.getInt("Tier4-power4-night"); }
    public int tier4Buffer() { return config.getInt("Buffer4"); }
    public String tier4Lore() { return config.getString("Tier4-Lore"); }

    public int tier5Power() { return config.getInt("Tier5-power5"); }
    public int tier5PowerNight() { return config.getInt("Tier5-power5-night"); }
    public int tier5Buffer() { return config.getInt("Buffer5"); }
    public String tier5Lore() { return config.getString("Tier5-Lore"); }

    public int tier6Power() { return config.getInt("Tier6-power6"); }
    public int tier6PowerNight() { return config.getInt("Tier6-power6-night"); }
    public int tier6Buffer() { return config.getInt("Buffer6"); }
    public String tier6Lore() { return config.getString("Tier6-Lore"); }

    public int tier7Power() { return config.getInt("Tier7-power7"); }
    public int tier7PowerNight() { return config.getInt("Tier7-power7-night"); }
    public int tier7Buffer() { return config.getInt("Buffer7"); }
    public String tier7Lore() { return config.getString("Tier7-Lore"); }

    public int tier8Power() { return config.getInt("Tier8-power8"); }
    public int tier8PowerNight() { return config.getInt("Tier8-power8-night"); }
    public int tier8Buffer() { return config.getInt("Buffer8"); }
    public String tier8Lore() { return config.getString("Tier8-Lore"); }



    public ReturnConfValue() {}
}
