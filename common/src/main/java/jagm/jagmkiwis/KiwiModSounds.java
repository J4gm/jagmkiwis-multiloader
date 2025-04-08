package jagm.jagmkiwis;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;

public class KiwiModSounds {

    public static final HashMap<String, Supplier<SoundEvent>> SOUNDS_COMMON = new HashMap<>();

    public static final Supplier<SoundEvent> KIWI_AMBIENT = createSoundEventSupplier("kiwi_ambient");
    public static final Supplier<SoundEvent> KIWI_HURT = createSoundEventSupplier("kiwi_hurt");
    public static final Supplier<SoundEvent> KIWI_DEATH = createSoundEventSupplier("kiwi_death");
    public static final Supplier<SoundEvent> KIWI_DIG = createSoundEventSupplier("kiwi_dig");
    public static final Supplier<SoundEvent> KIWI_LAY_EGG = createSoundEventSupplier("kiwi_lay_egg");
    public static final Supplier<SoundEvent> LASER_SHOOT = createSoundEventSupplier("laser_shoot");

    private static Supplier<SoundEvent> createSoundEventSupplier (String name) {
        Supplier<SoundEvent> soundEventSupplier = Suppliers.memoize(() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(KiwiMod.MOD_ID, name)));
        SOUNDS_COMMON.put(name, soundEventSupplier);
        return soundEventSupplier;
    }

}
