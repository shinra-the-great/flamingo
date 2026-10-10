package com.littlemobs.flamingo.client;

import com.littlemobs.flamingo.FlamingoMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

public class FlamingoModel extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(FlamingoMod.id("flamingo"), "main");

    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public FlamingoModel(ModelPart root) {
        super(root);
        ModelPart body = root.getChild("body");
        this.neck = root.getChild("neck");
        this.head = neck.getChild("head");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 14.0F, 0.0F));

        PartDefinition neck = root.addOrReplaceChild("neck", CubeListBuilder.create()
                .texOffs(24, 0).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 10.0F, -2.0F));

        neck.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 8).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -12.0F, 0.0F));

        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 14.0F, 0.0F));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(8, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 14.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create()
                .texOffs(16, 16).addBox(0.0F, -2.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(26, 16).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(36, 16).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 3.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);

        this.head.xRot = state.xRot * ((float) Math.PI / 180F);
        this.neck.yRot = state.yRot * ((float) Math.PI / 180F);

        this.rightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.leftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;

        this.rightWing.zRot = Mth.cos(state.ageInTicks * 0.2F) * 0.1F;
        this.leftWing.zRot = -Mth.cos(state.ageInTicks * 0.2F) * 0.1F;

        this.neck.xRot = Mth.cos(state.ageInTicks * 0.1F) * 0.05F;
    }
}
