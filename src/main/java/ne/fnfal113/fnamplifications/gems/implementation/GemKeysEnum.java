package ne.fnfal113.fnamplifications.gems.implementation;

import lombok.Getter;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

public enum GemKeysEnum {

    GEM_KEYS;

    @Getter
    private final List<NamespacedKey> gemKeyList = new ArrayList<>();

}