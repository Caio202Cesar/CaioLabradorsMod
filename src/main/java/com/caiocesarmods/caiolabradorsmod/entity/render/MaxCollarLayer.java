package com.caiocesarmods.caiolabradorsmod.entity.render;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import com.caiocesarmods.caiolabradorsmod.entity.custom.LabradorEntity;
import com.caiocesarmods.caiolabradorsmod.entity.model.LabradorModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MaxCollarLayer extends LayerRenderer<LabradorEntity,
        LabradorModel<LabradorEntity>> {

    private static final ResourceLocation COLLAR_TEXTURE =
            new ResourceLocation(
                    CaioLabradorsMod.MOD_ID,
                    "textures/entity/max_collar.png"
            );

    public MaxCollarLayer(
            IEntityRenderer<LabradorEntity, LabradorModel<LabradorEntity>> renderer) {

        super(renderer);
    }

    @Override
    public void render(MatrixStack matrixStack,
                       IRenderTypeBuffer buffer,
                       int packedLight,
                       LabradorEntity entity,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTicks,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {

        if (!entity.isMyDog()) {
            return;
        }

        IVertexBuilder builder =
                buffer.getBuffer(
                        RenderType.getEntityCutoutNoCull(COLLAR_TEXTURE)
                );

        this.getEntityModel().render(
                matrixStack,
                builder,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );
    }
}
