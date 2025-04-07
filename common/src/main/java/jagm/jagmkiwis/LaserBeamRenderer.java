package jagm.jagmkiwis;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;

public class LaserBeamRenderer extends ArrowRenderer<LaserBeamEntity, ArrowRenderState>{

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, "textures/entity/laser_beam.png");

    public LaserBeamRenderer(Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected ResourceLocation getTextureLocation(ArrowRenderState arrowRenderState) {
        return TEXTURE;
    }

}
