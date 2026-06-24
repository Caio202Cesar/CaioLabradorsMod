package com.caiocesarmods.caiolabradorsmod;

import com.caiocesarmods.caiolabradorsmod.entity.gen.ModEntityGenerator;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CaioLabradorsMod.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModEntityGenerator.onEntitySpawn(event);
    }
}
