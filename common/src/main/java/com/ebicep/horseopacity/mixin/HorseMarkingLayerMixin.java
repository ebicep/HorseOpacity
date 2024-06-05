package com.ebicep.horseopacity.mixin;

import com.ebicep.horseopacity.config.Config;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;

@Mixin(HorseMarkingLayer.class)
public class HorseMarkingLayerMixin {

    @ModifyArg(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/HorseModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
            ),
            index = 7
    )
    public float render(float opacity, @Local(argsOnly = true) Horse horse) {
        if (!Config.INSTANCE.getValues().getEnabled()) {
            return opacity;
        }
        if (Minecraft.getInstance().player == null) {
            return opacity;
        }
        if (!Objects.equals(Minecraft.getInstance().player.getVehicle(), horse)) {
            return opacity;
        }
        return Config.INSTANCE.getValues().getOpacity();
    }

}
