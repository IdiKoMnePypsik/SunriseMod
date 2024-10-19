package com.monkeybiznec.sunrise.client.model.entity;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.monkeybiznec.sunrise.client.animation.entity.CheetahAnimation;
import com.monkeybiznec.sunrise.client.animation.entity.ElephantAnimation;
import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.Elephant;
import com.monkeybiznec.sunrise.util.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ElephantModel extends HierarchicalModel<Elephant> {
    private final ModelPart root;
    public final ModelPart body;
    public final ModelPart tail;
    private final ModelPart head;

    public ElephantModel(ModelPart pRoot) {
        this.root = pRoot.getChild("root");
        this.body = this.root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 119).addBox(-4.5F, -1.0F, -5.0F, 10.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -19.0F, -15.0F));

        PartDefinition leg4 = root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(40, 137).addBox(-5.5F, 0.0F, -5.0F, 10.0F, 25.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -25.0F, 20.0F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(130, 114).addBox(-4.5F, 0.0F, -5.0F, 10.0F, 25.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -25.0F, 20.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.5F, -18.0F, -22.0F, 29.0F, 32.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -33.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(150, 18).addBox(-3.0F, -2.2358F, 0.0365F, 6.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 23.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 77).addBox(-9.0F, -22.0F, -14.0F, 18.0F, 20.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -18.0F));

        PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(70, 77).addBox(0.0F, -7.0F, -24.0F, 5.0F, 5.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 2.0F, -11.0F));

        PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(70, 107).addBox(-2.0F, -7.0F, -24.0F, 5.0F, 5.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 2.0F, -11.0F));

        PartDefinition nose1 = head.addOrReplaceChild("nose1", CubeListBuilder.create().texOffs(112, 149).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -16.0F));

        PartDefinition nose2 = nose1.addOrReplaceChild("nose2", CubeListBuilder.create().texOffs(80, 137).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition nose3 = nose2.addOrReplaceChild("nose3", CubeListBuilder.create().texOffs(0, 149).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

        PartDefinition ear1 = head.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(144, 149).addBox(0.0F, -8.0F, -1.0F, 14.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -14.0F, -5.25F));

        PartDefinition earlobe1 = ear1.addOrReplaceChild("earlobe1", CubeListBuilder.create().texOffs(40, 112).addBox(-7.0F, -1.0F, 0.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 9.0F, -1.0F));

        PartDefinition ear2 = head.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(150, 0).addBox(-14.0F, -8.0F, -1.0F, 14.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -14.0F, -5.25F));

        PartDefinition earlobe2 = ear2.addOrReplaceChild("earlobe2", CubeListBuilder.create().texOffs(40, 118).addBox(-3.0F, -1.0F, 0.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 9.0F, -1.0F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(130, 84).addBox(-5.5F, -1.0F, -5.0F, 10.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -19.0F, -15.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 45.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -10.0F, 10.0F);
        this.head.xRot = pHeadPitch * (Mth.PI / 180F);
        this.head.yRot = pNetHeadYaw * (Mth.PI / 180F);
    }

    protected void dynamicTail(@NotNull Elephant pEntity) {
        float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
        this.tail.zRot = Mth.lerp(0.08F, this.tail.zRot, targetYaw);
    }

    @Override
    public void setupAnim(@NotNull Elephant pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(pNetHeadYaw, pHeadPitch);
        this.dynamicTail(pEntity);
        if (!MiscUtils.isEntityMoving(pEntity, 0.1F)) {
            this.animate(pEntity.idleAnimationState, ElephantAnimation.ELEPHANT_IDLE, pAgeInTicks, 1.0F);
        }
        this.animateWalk(ElephantAnimation.ELEPHANT_WALK, pLimbSwing, pLimbSwingAmount, 2.0F, 2.4F);
        this.animate(pEntity.frAnimationState, ElephantAnimation.ELEPHANT_FR, pAgeInTicks, 1.0F);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    public void setMatrixStack(@NotNull PoseStack pMatrixStack) {
        this.root.translateAndRotate(pMatrixStack);
        this.body.translateAndRotate(pMatrixStack);
    }
}