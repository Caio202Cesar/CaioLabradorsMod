package com.caiocesarmods.caiolabradorsmod.entity.render;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import com.caiocesarmods.caiolabradorsmod.entity.custom.LabradorEntity;
import com.caiocesarmods.caiolabradorsmod.entity.model.LabradorModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class LabradorRenderer extends MobRenderer<LabradorEntity, LabradorModel<LabradorEntity>> {

    public LabradorRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new LabradorModel<>(), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(LabradorEntity entity) {
        if (entity.isMyDog()) {
            return MY_DOG;
        }

        return entity.getVariant().getTexture();
    }

    @Override
    public ResourceLocation getEntityTexture(LabradorEntity entity) {

        if (entity.isMyDog()) {
            return MY_DOG_TEXTURE;
        }

        return entity.getVariant().getTexture();
    }

    private static final ResourceLocation MY_DOG =
            new ResourceLocation(
                    CaioLabradorsMod.MOD_ID,
                    "textures/entity/my_dog.png");
}