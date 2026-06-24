package com.caiocesarmods.caiolabradorsmod.entity;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import net.minecraft.util.ResourceLocation;

public enum LabradorVariant {

    BROWN("brown_lab.png"),
    YELLOW("yellow_lab.png"),
    WHITE("white_lab.png"),
    BLACK("black_lab.png");

    private final ResourceLocation texture;

    LabradorVariant(String textureName) {
        this.texture = new ResourceLocation(
                CaioLabradorsMod.MOD_ID,
                "textures/entity/" + textureName
        );
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
