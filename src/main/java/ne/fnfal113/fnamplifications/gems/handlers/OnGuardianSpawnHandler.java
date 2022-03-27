package ne.fnfal113.fnamplifications.gems.handlers;

import ne.fnfal113.fnamplifications.gems.events.GuardianSpawnEvent;

public interface OnGuardianSpawnHandler extends GemHandler {

    /**
     *
     * @param event the guardian spawn event that is being listened for
     */
    void onGuardianSpawn(GuardianSpawnEvent event);
}
