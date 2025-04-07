package jagm.jagmkiwis;

import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class KiwiRenderer extends AgeableMobRenderer<KiwiEntity, KiwiRenderState, KiwiModel> {

	private static final ResourceLocation NORMAL_KIWI = ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, "textures/entity/kiwi.png");
	private static final ResourceLocation LASER_KIWI = ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, "textures/entity/laser_kiwi.png");

	public KiwiRenderer(Context context) {
		super(context, new KiwiModel(context.bakeLayer(KiwiModel.KIWI_LAYER)), new KiwiModel(context.bakeLayer(KiwiModel.BABY_KIWI_LAYER)), 0.3F);
	}

	@Override
	public KiwiRenderState createRenderState() {
		return new KiwiRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(KiwiRenderState renderState) {
		return renderState.texture;
	}

	@Override
	public void extractRenderState(KiwiEntity kiwi, KiwiRenderState renderState, float partialTicks){
		renderState.headEatPositionScale = 17.5F + kiwi.getHeadEatPositionScale(partialTicks);
		renderState.headEatAngleScale = kiwi.getHeadEatAngleScale(partialTicks);
		renderState.texture = kiwi.getVariant() == KiwiEntity.Variant.LASER ? LASER_KIWI : NORMAL_KIWI;
		super.extractRenderState(kiwi, renderState, partialTicks);
	}

}
