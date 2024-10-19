package com.monkeybiznec.sunrise.client.model.entity;

import com.monkeybiznec.sunrise.client.animation.entity.CheetahAnimation;
import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.util.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class CheetahModel extends HierarchicalModel<Cheetah> {
    private final ModelPart root;
    private final ModelPart cheetah;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart tailend;
    private final ModelPart head;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart eyes;
    private final ModelPart leftLeg1;
    private final ModelPart rightLeg1;
    private final ModelPart rightLeg2;
    private final ModelPart leftLeg2;

    public CheetahModel(ModelPart pRoot) {
        this.root = pRoot.getChild("root");
        this.cheetah = this.root.getChild("cheetah");
        this.body = this.cheetah.getChild("body");
        this.tail = this.body.getChild("tail");
        this.tailend = this.tail.getChild("tailend");
        this.head = this.body.getChild("head");
        this.leftEar = this.head.getChild("leftEar");
        this.rightEar = this.head.getChild("rightEar");
        this.eyes = this.head.getChild("eyes");
        this.leftLeg1 = this.cheetah.getChild("leftLeg1");
        this.rightLeg1 = this.cheetah.getChild("rightLeg1");
        this.rightLeg2 = this.cheetah.getChild("rightLeg2");
        this.leftLeg2 = this.cheetah.getChild("leftLeg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition cheetah = root.addOrReplaceChild("cheetah", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition body = cheetah.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -4.0F, -8.0F, 6.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(19, 0).addBox(-3.0F, -4.0F, 1.0F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -1.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 41).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, -0.6109F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tailend", CubeListBuilder.create().texOffs(34, 35).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 10.0F, 0.3927F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(29, 14).addBox(-3.5F, -3.0F, -4.0F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 17).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -8.0F));
        head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(19, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -3.0F, -0.5F));
        head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(7, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -3.0F, -0.5F));
        head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(48, 18).addBox(0.5F, -0.5F, -0.005F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(48, 17).addBox(-3.5F, -0.5F, -0.005F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -4.0F));
        cheetah.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(10, 34).addBox(-1.25F, -2.0F, -1.5F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -10.0F, -6.5F));
        cheetah.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -2.0F, -1.5F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, -6.5F));
        cheetah.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(0, 34).addBox(-0.75F, -2.0F, -1.5F, 2.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, 6.5F));
        cheetah.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(30, 25).addBox(-1.25F, -2.0F, -1.5F, 2.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, 6.5F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Cheetah pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(pNetHeadYaw, pHeadPitch);
        this.dynamicTail(pEntity);
        if (this.young) {
            this.head.offsetScale(new Vector3f(0.3F, 0.3F, 0.3F));
        }
        if (!MiscUtils.isEntityMoving(pEntity, 0.1F)) {
            this.animate(pEntity.idleAnimationState, CheetahAnimation.CHEETAH_IDLE, pAgeInTicks, 1.0F);
        }
        this.animate(pEntity.jumpAnimationState, CheetahAnimation.JUMP, pAgeInTicks, 1.0F);
        if (!Cheetah.DATA_JUMPING.get(pEntity)) {
            if (Cheetah.DATA_RUNNING_TICK.get(pEntity) <= 0) {
                this.animateWalk(CheetahAnimation.CHEETAH_WALK, pLimbSwing, pLimbSwingAmount, 2.0F, 2.4F);
            } else {
                this.animateWalk(CheetahAnimation.CHEETAH_RUN, pLimbSwing, pLimbSwingAmount, 1.0F, 2.4F);
            }
        }
        this.animate(pEntity.lookAnimationState, CheetahAnimation.CHEETAH_LOOK, pAgeInTicks, 1.0F);
        this.root.yRot = pNetHeadYaw * (Mth.PI / 360F);
    }

    public void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 45.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -10.0F, 10.0F);
        this.head.xRot = pHeadPitch * (Mth.PI / 180F);
        this.head.yRot = pNetHeadYaw * (Mth.PI / 180F);
    }

    public void dynamicTail(@NotNull Cheetah pEntity) {
        float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
    //    this.tail.yRot = Mth.lerp(0.010F, this.tail.yRot, targetYaw);
     //   this.tailend.yRot = Mth.lerp(0.010F, this.tail.yRot, targetYaw);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}