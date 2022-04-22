package ne.fnfal113.fnamplifications.gems.handlers;

import ne.fnfal113.fnamplifications.gems.events.GuardianSpawnEvent;
import org.bukkit.inventory.ItemStack;

public interface OnGuardianSpawnHandler extends GemHandler {

    /**
     *
     * @param event the guardian spawn event that is being listened for
     * @param itemStack the item where the gem is bound at
     */
    void onGuardianSpawn(GuardianSpawnEvent event, ItemStack itemStack);
}
