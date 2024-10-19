package com.monkeybiznec.sunrise.client.event;

import com.monkeybiznec.sunrise.SunriseMod;
import com.monkeybiznec.sunrise.client.SunriseClientProxy;
import com.monkeybiznec.sunrise.client.input.InputKey;
import com.monkeybiznec.sunrise.client.input.InputStateManager;
import com.monkeybiznec.sunrise.client.render.ICustomPlayerRidePos;
import com.monkeybiznec.sunrise.client.render.entity.ElephantRenderer;
import com.monkeybiznec.sunrise.common.entity.Elephant;
import com.monkeybiznec.sunrise.common.entity.ai.behavior.IDynamicCamera;
import com.monkeybiznec.sunrise.common.entity.ai.behavior.IMouseInput;
import com.monkeybiznec.sunrise.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = SunriseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SunriseClientEvents {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent pEvent) {
        InputStateManager.getInstance().update();
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key pEvent) {
        InputKey inputKey = InputKey.fromKeyCode(pEvent.getKey());
        if (inputKey != null) {
            InputStateManager.getInstance().updateKeyState(inputKey, pEvent.getAction() != 0);
        }
    }

    private static int getIdForAction(InputEvent.InteractionKeyMappingTriggered pEvent) {
        if (pEvent.isUseItem()) {
            return 0;
        } else if (pEvent.isAttack()) {
            return 1;
        } else {
            return -1;
        }
    }

    @SubscribeEvent
    public static void onInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered pEvent) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            if (localPlayer.getVehicle() instanceof IMouseInput mouseInput) {
                if (mouseInput.isActionDenied(getIdForAction(pEvent))) {
                    pEvent.setSwingHand(false);
                    pEvent.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMouseButton(InputEvent.MouseButton pEvent) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null && Minecraft.getInstance().screen == null) {
            if (localPlayer.getVehicle() instanceof IMouseInput mouseInput) {
                mouseInput.onMouseClick(pEvent.getButton());
            }
        }
    }

    @SubscribeEvent
    public static void onComputeCameraAngle(ViewportEvent.ComputeCameraAngles pEvent) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.isPassenger()) {
                Entity vehicle = player.getRootVehicle();
                if (vehicle instanceof Mob mob) {
                    if (vehicle instanceof Elephant) {
                        EntityRenderer<? extends Entity> entityRenderer = RenderUtil.getEntityRenderer(vehicle);
                        if (entityRenderer instanceof ElephantRenderer elephantRenderer) {
                            float targetRoll = elephantRenderer.getModel().body.yRot * (180 / (float) Math.PI);
                            pEvent.setRoll(Mth.lerp(pEvent.getRoll(), targetRoll, 0.1F));
                        }
                    }
                    if (vehicle instanceof IDynamicCamera dynamicCamera) {
                        float targetRoll = Math.max(-dynamicCamera.getMaxCameraTilt(), Math.min(dynamicCamera.getMaxCameraTilt(), (mob.yRotO - vehicle.getYRot()) * dynamicCamera.getCameraTiltSpeed()));
                        SunriseClientProxy.currentRoll = SunriseClientProxy.currentRoll + (targetRoll - SunriseClientProxy.currentRoll) * 0.1F;
                        pEvent.setRoll(pEvent.getRoll() - SunriseClientProxy.currentRoll);
                    } else {
                        SunriseClientProxy.currentRoll = SunriseClientProxy.currentRoll + (0.0F - SunriseClientProxy.currentRoll) * 0.1F;
                        pEvent.setRoll(pEvent.getRoll() - SunriseClientProxy.currentRoll);
                    }
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static <T extends LivingEntity> void onPlayerPose(HumanoidModelEvent<T> pEvent) {
        T entity = pEvent.getEntity();
        if (entity instanceof Player) {
            T vehicle = (T) entity.getVehicle();
            if (RenderUtil.getEntityRenderer(vehicle) instanceof ICustomPlayerRidePos customRidePos) {
                customRidePos.applyRiderPose(vehicle, pEvent.getHumanoidModel(), entity);
            }
        }
    }

    @SubscribeEvent
    public static <T extends LivingEntity> void onModelRotation(ModelRotationEvent<T> pEvent) {
        T entity = pEvent.getEntity();
        if (entity instanceof Player) {
            pEvent.setCanceled(RenderUtil.getEntityRenderer(entity.getVehicle()) instanceof ICustomPlayerRidePos);
        }
    }

    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void onRenderLivingPre(RenderLivingEvent.Pre<T, M> pEvent) {
        LivingEntity entity = pEvent.getEntity();
        RenderLivingEvent.Post<T, M> event = new RenderLivingEvent.Post<>(entity, pEvent.getRenderer(), pEvent.getPartialTick(), pEvent.getPoseStack(), pEvent.getMultiBufferSource(), pEvent.getPackedLight());
        synchronized (RenderUtil.hiddenEntities) {
            if (RenderUtil.hiddenEntities.remove(entity.getUUID()) && RenderUtil.shouldSkipRendering(false, Minecraft.getInstance().getCameraEntity())) {
                MinecraftForge.EVENT_BUS.post(event);
                pEvent.setCanceled(true);
            }
        }
    }
}
