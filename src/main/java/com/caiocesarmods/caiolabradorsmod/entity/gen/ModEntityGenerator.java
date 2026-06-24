package com.caiocesarmods.caiolabradorsmod.entity.gen;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import com.caiocesarmods.caiolabradorsmod.entity.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = CaioLabradorsMod.MOD_ID)
public class ModEntityGenerator {

    @SubscribeEvent
    public static void onEntitySpawn(final BiomeLoadingEvent event) {
        addEntityToAllOverworldBiomes(event, ModEntityTypes.LABRADOR_ENTITY.get(),
                27, 2, 4);
    }

    private static void addEntityToAllBiomesExceptThese(BiomeLoadingEvent event, EntityType<?> type,
                                                        int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(RegistryKey::getLocation)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(!isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToSpecificBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
        boolean isBiomeSelected = Arrays.stream(biomes).map(RegistryKey::getLocation)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if(isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllOverworldBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                      int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.Category.THEEND) && !event.getCategory().equals(Biome.Category.NETHER)) {
            List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
            base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomesNoNether(BiomeLoadingEvent event, EntityType<?> type,
                                                     int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.Category.NETHER)) {
            List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
            base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomesNoEnd(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount) {
        if(!event.getCategory().equals(Biome.Category.THEEND)) {
            List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
            base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
        }
    }

    private static void addEntityToAllBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                             int weight, int minCount, int maxCount) {
        List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
        base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
    }

}
