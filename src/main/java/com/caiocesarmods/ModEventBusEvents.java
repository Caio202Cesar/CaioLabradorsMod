package com.caiocesarmods;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import com.caiocesarmods.caiolabradorsmod.entity.ModEntityTypes;
import com.caiocesarmods.caiolabradorsmod.entity.custom.LabradorEntity;
import com.caiocesarmods.caiolabradorsmod.item.ModSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CaioLabradorsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.LABRADOR_ENTITY.get(), LabradorEntity.setCustomAttributes().create());

    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initSpawnEggs();
    }
}
