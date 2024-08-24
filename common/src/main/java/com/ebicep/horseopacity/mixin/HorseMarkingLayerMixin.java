package com.ebicep.horseopacity.mixin;

import com.ebicep.horseopacity.config.Config;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;
import java.util.Objects;

@Mixin(HorseMarkingLayer.class)
public class HorseMarkingLayerMixin {

    @Redirect(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/HorseModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"
            )
    )
    public void renderToBuffer(HorseModel<Horse> instance, PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, @Local(argsOnly = true) Horse horse) {
        if (!Config.INSTANCE.getValues().getEnabled()) {
            instance.renderToBuffer(poseStack, vertexConsumer, i, j);
        }
        if (Minecraft.getInstance().player == null) {
            instance.renderToBuffer(poseStack, vertexConsumer, i, j);
        }
        if (!Objects.equals(Minecraft.getInstance().player.getVehicle(), horse)) {
            instance.renderToBuffer(poseStack, vertexConsumer, i, j);
        }
        instance.renderToBuffer(poseStack, vertexConsumer, i, j, Config.INSTANCE.getValues().getOpacityColor());
    }

}
