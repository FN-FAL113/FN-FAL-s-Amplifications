package ne.fnfal113.fnamplifications.machines.implementation;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

public enum DiscDurationsEnum {

    MUSIC_DISC_13(180),
    MUSIC_DISC_CAT(185),
    MUSIC_DISC_BLOCKS(545),
    MUSIC_DISC_CHIRP(185),
    MUSIC_DISC_FAR(174),
    MUSIC_DISC_MALL(197),
    MUSIC_DISC_MELLOHI(96),
    MUSIC_DISC_STAL(150),
    MUSIC_DISC_STRAD(188),
    MUSIC_DISC_WARD(251),
    MUSIC_DISC_11(71),
    MUSIC_DISC_WAIT(235),
    MUSIC_DISC_OTHERSIDE(180),
    MUSIC_DISC_PIGSTEP(148),
    MUSIC_DISC_5(178),
    MUSIC_DISC_RELIC(216),
    ;

    private final int duration;

    DiscDurationsEnum(int durationInSeconds) {
        this.duration = durationInSeconds * (int) 20.0 / Slimefun.getTickerTask().getTickRate();
    }

    public int getDuration() {
        return duration;
    }

}