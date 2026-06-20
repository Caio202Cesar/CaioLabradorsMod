package com.caiocesarmods.caiolabradorsmod.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.WolfEntity;

public class LabradorModel<T extends WolfEntity> extends WolfModel<T> {

    private final ModelRenderer head;
    private final ModelRenderer mane;
    private final ModelRenderer body;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer tail;

    public LabradorModel() {
        textureWidth = 64;
        textureHeight = 32;

        head = new ModelRenderer(this);
        head.setRotationPoint(-1.0F, 13.5F, -7.0F);

        head.setTextureOffset(-2, -2)
                .addBox(-2.0F, -3.0F, -4.0F,
                        6.0F, 6.0F, 6.0F,
                        0.0F);

        head.setTextureOffset(1, 11)
                .addBox(-0.5F, -0.02F, -7.0F,
                        3.0F, 3.0F, 3.0F,
                        0.0F);

        head.setTextureOffset(47, 18)
                .addBox(-3.0F, -2.0F, -2.0F,
                        1.0F, 4.0F, 3.0F,
                        0.0F);

        head.setTextureOffset(47, 25)
                .addBox(4.0F, -2.0F, -2.0F,
                        1.0F, 4.0F, 3.0F,
                        0.0F);

        mane = new ModelRenderer(this);
        mane.setRotationPoint(-1.0F, 14.0F, -3.0F);
        setRotationAngle(mane, 1.5708F, 0.0F, 0.0F);

        mane.setTextureOffset(23, 1)
                .addBox(-2.0F, -3.0F, -3.0F,
                        6.0F, 6.0F, 6.0F,
                        0.0F);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 14.0F, 2.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);

        body.setTextureOffset(18, 14)
                .addBox(-3.0F, -2.0F, -3.0F,
                        6.0F, 9.0F, 6.0F,
                        0.0F);

        leg1 = new ModelRenderer(this);
        leg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
        leg1.setTextureOffset(0, 18)
                .addBox(0.0F, 0.0F, -1.0F,
                        2.0F, 8.0F, 2.0F,
                        0.0F);

        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(0.5F, 16.0F, 7.0F);
        leg2.setTextureOffset(0, 18)
                .addBox(0.0F, 0.0F, -1.0F,
                        2.0F, 8.0F, 2.0F,
                        0.0F);

        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
        leg3.setTextureOffset(0, 18)
                .addBox(0.0F, 0.0F, -1.0F,
                        2.0F, 8.0F, 2.0F,
                        0.0F);

        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(0.5F, 16.0F, -4.0F);
        leg4.setTextureOffset(0, 18)
                .addBox(0.0F, 0.0F, -1.0F,
                        2.0F, 8.0F, 2.0F,
                        0.0F);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(-1.0F, 12.0F, 10.0F);
        tail.setTextureOffset(9, 18)
                .addBox(0.0F, 0.0F, -1.0F,
                        2.0F, 10.0F, 2.0F,
                        0.0F);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
    }


    private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        mane.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay);
        leg3.render(matrixStack, buffer, packedLight, packedOverlay);
        leg4.render(matrixStack, buffer, packedLight, packedOverlay);
        tail.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}