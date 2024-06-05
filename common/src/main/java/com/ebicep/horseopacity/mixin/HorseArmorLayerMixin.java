package com.ebicep.horseopacity.mixin;

import com.ebicep.horseopacity.config.Config;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.HorseArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;

@Mixin(HorseArmorLayer.class)
public class HorseArmorLayerMixin {

    @ModifyArg(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/HorseModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
            ),
            index = 1
    )
    public VertexConsumer renderToBuffer(
            VertexConsumer vertexConsumer,
            @Local(argsOnly = true) MultiBufferSource multiBufferSource,
            @Local(argsOnly = true) Horse horse,
            @Local HorseArmorItem horseArmorItem,
            @Share("horseopacity$isHorse") LocalBooleanRef isHorse
    ) {
        if (!Config.INSTANCE.getValues().getEnabled()) {
            return vertexConsumer;
        }
        if (Minecraft.getInstance().player == null) {
            return vertexConsumer;
        }
        isHorse.set(Objects.equals(Minecraft.getInstance().player.getVehicle(), horse));
        if (!isHorse.get()) {
            return vertexConsumer;
        }
        return multiBufferSource.getBuffer(RenderType.entityTranslucent(horseArmorItem.getTexture()));
    }

    @ModifyArg(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/HorseModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
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
