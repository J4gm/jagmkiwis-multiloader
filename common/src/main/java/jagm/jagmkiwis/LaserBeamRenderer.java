package jagm.jagmkiwis;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class LaserBeamRenderer extends ArrowRenderer<LaserBeamEntity, ArrowRenderState>{

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(KiwiMod.MOD_ID, "textures/entity/laser_beam.png");

    public LaserBeamRenderer(Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState arrowRenderState) {
        return TEXTURE;
    }

}
