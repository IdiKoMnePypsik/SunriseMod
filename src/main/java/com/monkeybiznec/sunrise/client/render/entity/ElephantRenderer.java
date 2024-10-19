package com.monkeybiznec.sunrise.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.monkeybiznec.sunrise.client.model.SunriseModelLayers;
import com.monkeybiznec.sunrise.client.model.entity.ElephantModel;
import com.monkeybiznec.sunrise.client.render.ICustomPlayerRidePos;
import com.monkeybiznec.sunrise.client.render.entity.layer.ModPassengerLayer;
import com.monkeybiznec.sunrise.common.entity.Elephant;
import com.monkeybiznec.sunrise.util.ResourceLocationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ElephantRenderer extends MobRenderer<Elephant, ElephantModel> implements ICustomPlayerRidePos {
    private static final ResourceLocation ELEPHANT_LOCATION = ResourceLocationUtils.entityPath("elephant");

    public ElephantRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ElephantModel(pContext.bakeLayer(SunriseModelLayers.get("elephant"))), 1.85F);
        this.addLayer(new ModPassengerLayer<>(this));
    }

    @Override
    public <T extends LivingEntity> void applyRiderPose(@NotNull LivingEntity pVehicle, HumanoidModel<T> pHumanoidModel, @NotNull T pRider) {

    }

    @Override
    public <T extends Entity> void applyRiderMatrixStack(@NotNull T pVehicle, PoseStack pMatrixStack) {
        this.getModel().setMatrixStack(pMatrixStack);
        pMatrixStack.translate(0.0F, 2.75F - pVehicle.getBbHeight(), 0.0F);
        pMatrixStack.mulPose(Axis.YN.rotationDegrees(180.0F));
        pMatrixStack.mulPose(Axis.XN.rotationDegrees(180.0F));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Elephant pEntity) {
        return ELEPHANT_LOCATION;
    }
}