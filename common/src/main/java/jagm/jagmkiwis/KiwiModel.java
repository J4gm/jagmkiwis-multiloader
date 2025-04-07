package jagm.jagmkiwis;

import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Set;

public class KiwiModel extends EntityModel<KiwiRenderState> {

	public static final ModelLayerLocation KIWI_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, "kiwi"), "main");
	public static final ModelLayerLocation BABY_KIWI_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(KiwiMod.MOD_ID, "baby_kiwi"), "main");

	public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(Set.of("head"));
	private final ModelPart head;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart body;

	public KiwiModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 10)
				.addBox(-2.0F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(12, 10)
				.addBox(-1.0F, -0.5F, -7.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.5F, 17.5F, -1.0F)
		);
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
				.texOffs(15, 0)
				.addBox(-1.5F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(23, 0)
				.addBox(-1.0F, 1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(21, 3)
				.addBox(-2.0F, 3.5F, -2.5F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.0F, 20.5F, 2.0F)
		);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
				.texOffs(15, 0)
				.addBox(-1.5F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(23, 0)
				.addBox(-1.0F, 1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(21, 3)
				.addBox(-2.0F, 3.5F, -2.5F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 20.5F, 2.0F)
		);
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-3.0F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.5F, 19.5F, 1.5F)
		);

		return LayerDefinition.create(meshdefinition, 32, 32);

	}

	@Override
	public void setupAnim(KiwiRenderState renderState) {
		super.setupAnim(renderState);
		this.head.y = renderState.headEatPositionScale + (renderState.isBaby ? 2.0F : 0.0F);
		this.head.xRot = renderState.headEatAngleScale;
		this.head.yRot = renderState.yRot * ((float) Math.PI / 180F);
		this.rightLeg.xRot = Mth.cos(renderState.walkAnimationPos * 0.6662F) * 1.4F * renderState.walkAnimationSpeed;
		this.leftLeg.xRot = Mth.cos(renderState.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * renderState.walkAnimationSpeed;
	}

}
