package com.caiocesarmods.caiolabradorsmod.item;

import com.caiocesarmods.caiolabradorsmod.CaioLabradorsMod;
import com.caiocesarmods.caiolabradorsmod.entity.ModEntityTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CaioLabradorsMod.MOD_ID);

    public static final RegistryObject<ModSpawnEggItem> LABRADOR_SPAWN_EGG = ITEMS.register("labrador_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.LABRADOR_ENTITY, 0x342e28, 0x462e16,
                    new Item.Properties().group(ItemGroup.MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
