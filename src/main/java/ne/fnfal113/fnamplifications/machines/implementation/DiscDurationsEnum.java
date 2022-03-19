package ne.fnfal113.fnamplifications.machines.implementation;

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
    ;

    private final int durationInSec;

    DiscDurationsEnum(int durationInSec) {
        this.durationInSec = durationInSec;
    }

    public int getDurationInSec(){
        return durationInSec;
    }

}