package com.caiocesarmods.caiolabradorsmod.Util;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CaioLabradorsMod.MOD_ID);

    public static final RegistryObject<SoundEvent> LABRADOR_ANGRY =
            registerSoundEvent("labrador_angry");

    public static final RegistryObject<SoundEvent> LABRADOR_BARK_AMBIENT =
            registerSoundEvent("labrador_bark_ambient");

    public static final RegistryObject<SoundEvent> LABRADOR_BARK2 =
            registerSoundEvent("labrador_bark2");

    public static final RegistryObject<SoundEvent> LABRADOR_BREATHING =
            registerSoundEvent("labrador_breathing");

    public static final RegistryObject<SoundEvent> LABRADOR_PURR =
            registerSoundEvent("labrador_purr");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(CaioLabradorsMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
