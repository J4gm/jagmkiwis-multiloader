package jagm.jagmkiwis;

import net.minecraft.world.level.block.DispenserBlock;

public class KiwiMod {

    public static final String MOD_ID = "jagmkiwis";

    public static void addDispenserBehaviour() {
        DispenserBlock.registerProjectileBehavior(KiwiModItems.KIWI_EGG.get());
    }

}