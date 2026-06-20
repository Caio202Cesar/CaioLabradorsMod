package com.caiocesarmods.caiolabradorsmod.entity;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import com.caiocesarmods.caiolabradorsmod.entity.custom.LabradorEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, CaioLabradorsMod.MOD_ID);

    public static final RegistryObject<EntityType<LabradorEntity>> LABRADOR_ENTITY =
            ENTITY_TYPES.register("labrador",
                    () -> EntityType.Builder.create(LabradorEntity::new,
                                    EntityClassification.CREATURE).size(0.6F, 0.9F)
                            .build(new ResourceLocation(CaioLabradorsMod.MOD_ID, "labrador").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
