package ne.fnfal113.fnamplifications.gems.implementation;

import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

public enum GemKeysEnum {

    GEM_KEYS;

    private final List<NamespacedKey> gemKeyList = new ArrayList<>();

    public List<NamespacedKey> getGemKeyList() {
        return gemKeyList;
    }

}