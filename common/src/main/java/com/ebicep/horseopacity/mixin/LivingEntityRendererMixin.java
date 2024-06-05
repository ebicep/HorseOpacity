package com.ebicep.horseopacity.mixin;

import com.ebicep.horseopacity.config.Config;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "HEAD")
    )
    public void horseCheck(
            LivingEntity livingEntity,
            float f,
            float g,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            CallbackInfo ci,
            @Share("horseopacity$isHorse") LocalBooleanRef isHorse
    ) {
        if (Minecraft.getInstance().player != null) {
            isHorse.set(Objects.equals(Minecraft.getInstance().player.getVehicle(), livingEntity));
        }
    }

    @ModifyArg(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"
            ),
            index = 2
    )
    public boolean setTranslucent(boolean bl2, @Share("horseopacity$isHorse") LocalBooleanRef isHorse) {
        if (!Config.INSTANCE.getValues().getEnabled()) {
            return bl2;
        }
        return bl2 || isHorse.get();
    }


    @ModifyArg(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
            ),
            index = 7
    )
    public float renderToBuffer(float opacity, @Share("horseopacity$isHorse") LocalBooleanRef isHorse) {
        if (!Config.INSTANCE.getValues().getEnabled()) {
            return opacity;
        }
        if (!isHorse.get()) {
            return opacity;
        }
        return Config.INSTANCE.getValues().getOpacity();
    }

}
