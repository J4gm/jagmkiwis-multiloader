package jagm.jagmkiwis;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;

public class KiwiModSounds {

    public static final HashMap<String, SoundEvent> SOUNDS_COMMON = new HashMap<>();

    public static final SoundEvent KIWI_AMBIENT = createSoundEvent("kiwi_ambient");
    public static final SoundEvent KIWI_HURT = createSoundEvent("kiwi_hurt");
    public static final SoundEvent KIWI_DEATH = createSoundEvent("kiwi_death");
    public static final SoundEvent KIWI_DIG = createSoundEvent("kiwi_dig");
    public static final SoundEvent KIWI_LAY_EGG = createSoundEvent("kiwi_lay_egg");
    public static final SoundEvent LASER_SHOOT = createSoundEvent("laser_shoot");

    private static SoundEvent createSoundEvent (String name) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(KiwiMod.MOD_ID, name));
        SOUNDS_COMMON.put(name, soundEvent);
        return soundEvent;
    }

}
