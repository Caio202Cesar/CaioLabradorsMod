package com.caiocesarmods.caiolabradorsmod.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;

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

        head.setTextureOffset(0, 0)
                .addBox(-2.0F, -3.0F, -4.0F, 6.0F, 6.0F, 6.0F);

        head.setTextureOffset(1, 11)
                .addBox(-0.5F, -0.02F, -7.0F, 3.0F, 3.0F, 3.0F);

        head.setTextureOffset(47, 18)
                .addBox(-3.0F, -2.0F, -2.0F, 1.0F, 4.0F, 3.0F);

        head.setTextureOffset(47, 25)
                .addBox(4.0F, -2.0F, -2.0F, 1.0F, 4.0F, 3.0F);

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
        tail.setTextureOffset(11, 20)
                .addBox(0.5F, 0.0F, -1.0F, 1.0F, 10.0F, 1.0F);
    }

    private float animationTime;

    @Override
    public void setRotationAngles(T entityIn,
                                  float limbSwing,
                                  float limbSwingAmount,
                                  float ageInTicks,
                                  float netHeadYaw,
                                  float headPitch) {

        animationTime = ageInTicks;

        head.rotateAngleX = MathHelper.clamp(
                headPitch * 0.017453292F,
                -0.8F,
                0.8F
        );

        head.rotateAngleY = MathHelper.clamp(
                netHeadYaw * 0.017453292F,
                -1.2F,
                1.2F
        );
    }

    @Override
    public void setLivingAnimations(T entityIn,
                                    float limbSwing,
                                    float limbSwingAmount,
                                    float partialTick) {

        // Reset de rotações

        head.rotateAngleZ = 0.0F;

        mane.rotateAngleX = 1.5708F;
        mane.rotateAngleY = 0.0F;
        mane.rotateAngleZ = 0.0F;

        body.rotateAngleY = 0.0F;
        body.rotateAngleZ = 0.0F;

        tail.rotateAngleX = 0.0F;
        tail.rotateAngleY = 0.0F;
        tail.rotateAngleZ = 0.0F;

        leg1.rotateAngleY = 0.0F;
        leg2.rotateAngleY = 0.0F;
        leg3.rotateAngleY = 0.0F;
        leg4.rotateAngleY = 0.0F;

        leg1.rotateAngleZ = 0.0F;
        leg2.rotateAngleZ = 0.0F;
        leg3.rotateAngleZ = 0.0F;
        leg4.rotateAngleZ = 0.0F;

        if (entityIn.isQueuedToSit()) {

            mane.setRotationPoint(-1.0F, 16.0F, -3.0F);

            body.setRotationPoint(0.0F, 18.0F, 0.0F);
            body.rotateAngleX = ((float)Math.PI * 2F / 5F);

            tail.setRotationPoint(-1.0F, 20.0F, 7.0F);
            tail.rotateAngleX = (float)Math.PI / 2F;

            leg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
            leg2.setRotationPoint(0.5F, 22.0F, 2.0F);

            leg1.rotateAngleX = (float)Math.PI * 3F / 2F;
            leg2.rotateAngleX = (float)Math.PI * 3F / 2F;

            leg3.setRotationPoint(-2.5F, 17.0F, -4.0F);
            leg4.setRotationPoint(0.5F, 17.0F, -4.0F);

            leg3.rotateAngleX = 5.811947F;
            leg4.rotateAngleX = 5.811947F;

        } else {

            mane.setRotationPoint(-1.0F, 14.0F, -3.0F);

            body.setRotationPoint(0.0F, 14.0F, 2.0F);
            body.rotateAngleX = ((float)Math.PI / 2F);

            tail.setRotationPoint(-1.0F, 12.0F, 10.0F);

            leg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
            leg2.setRotationPoint(0.5F, 16.0F, 7.0F);

            leg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
            leg4.setRotationPoint(0.5F, 16.0F, -4.0F);

            leg1.rotateAngleX =
                    MathHelper.cos(limbSwing * 0.6662F)
                            * 1.2F
                            * limbSwingAmount;

            leg2.rotateAngleX =
                    MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI)
                            * 1.2F
                            * limbSwingAmount;

            leg3.rotateAngleX =
                    MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI)
                            * 1.2F
                            * limbSwingAmount;

            leg4.rotateAngleX =
                    MathHelper.cos(limbSwing * 0.6662F)
                            * 1.2F
                            * limbSwingAmount;
        }

        if (entityIn.isAngry()) {

            tail.rotateAngleY = 0.0F;

        } else {

            // Labrador abana o rabo quase sempre
            tail.rotateAngleY =
                    MathHelper.cos(animationTime * 0.18F)
                            * 0.25F;
        }

        // Shake ao sair da água

        head.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, 0.0F);

        mane.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, -0.08F);

        body.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, -0.16F);

        tail.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, -0.20F);

        leg1.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, -0.24F);

        leg2.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, -0.24F);

        leg3.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, -0.32F);

        leg4.rotateAngleZ =
                entityIn.getShakeAngle(partialTick, -0.32F);
    }

    private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(MatrixStack matrixStack,
                       IVertexBuilder buffer,
                       int packedLight,
                       int packedOverlay,
                       float red,
                       float green,
                       float blue,
                       float alpha) {

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