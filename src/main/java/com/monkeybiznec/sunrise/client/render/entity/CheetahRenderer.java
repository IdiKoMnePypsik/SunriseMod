package com.monkeybiznec.sunrise.client.render.entity;

import com.monkeybiznec.sunrise.SunriseMod;
import com.monkeybiznec.sunrise.client.model.SunriseModelLayers;
import com.monkeybiznec.sunrise.client.model.entity.CheetahModel;
import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.mojang.blaze3d.vertex.PoseStack;
import com.monkeybiznec.sunrise.util.ResourceLocationUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CheetahRenderer extends MobRenderer<Cheetah, CheetahModel> {
    private static final ResourceLocation CHEETAH_LOCATION = ResourceLocationUtils.entityPath("cheetah");
    private static final ResourceLocation CHEETAH_ANGRY_LOCATION = ResourceLocationUtils.entityPath("cheetah_angry");

    public CheetahRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CheetahModel(pContext.bakeLayer(SunriseModelLayers.get("cheetah"))), 0.65F);
    }

    @Override
    public void render(@NotNull Cheetah pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferIn, int pPackedLightIn) {
        this.shadowRadius = pEntity.isBaby() ? 1.1F : 0.6F;
        if (!pEntity.isBaby()) {
            pPoseStack.scale(1.0F, 1.0F, 1.0F);
        } else {
            pPoseStack.scale(0.6F, 0.6F, 0.6F);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBufferIn, pPackedLightIn);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cheetah pEntity) {
        return !Cheetah.ANGRY.get(pEntity) ? CHEETAH_LOCATION : CHEETAH_ANGRY_LOCATION;
    }
}