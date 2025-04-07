package jagm.jagmkiwis;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class LaserBeamRenderer extends ArrowRenderer<LaserBeamEntity>{

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, "textures/entity/laser_beam.png");

    public LaserBeamRenderer(Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(LaserBeamEntity laserBeam) {
        return TEXTURE;
    }

}
