package jagm.jagmkiwis;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class BabyKiwiModel extends EntityModel<KiwiRenderState> {

    public static final ModelLayerLocation BABY_KIWI_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(KiwiMod.MOD_ID, "baby_kiwi"), "main");

    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public BabyKiwiModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 10)
                        .addBox(-2.0F, 0.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 10)
                        .addBox(-1.0F, 1.5F, -7.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, 17.5F, 1.5F)
        );
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(23, 0)
                        .addBox(-1.0F, 0.5F, -0.5F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(27, 0)
                        .addBox(-1.0F, 3.5F, -1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.5F, 20.5F, 1.0F)
        );
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(23, 0)
                        .addBox(-1.0F, 0.5F, -0.5F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(27, 0)
                        .addBox(-1.0F, 3.5F, -1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 20.5F, 1.0F)
        );
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);

    }

    @Override
    public void setupAnim(KiwiRenderState renderState) {
        super.setupAnim(renderState);
        this.body.yRot = renderState.yRot * ((float) Math.PI / 180F);
        this.rightLeg.xRot = Mth.cos(renderState.walkAnimationPos * 0.6662F) * 1.4F * renderState.walkAnimationSpeed;
        this.leftLeg.xRot = Mth.cos(renderState.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * renderState.walkAnimationSpeed;
    }

}
