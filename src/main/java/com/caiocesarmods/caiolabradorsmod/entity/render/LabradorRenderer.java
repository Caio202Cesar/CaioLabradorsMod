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
        return entity.getVariant().getTexture();
    }
}