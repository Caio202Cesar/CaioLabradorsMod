package com.caiocesarmods.caiolabradorsmod.entity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import java.util.UUID;

public class LabradorWorldData extends WorldSavedData {

    public static final String NAME = "labrador_data";

    private UUID myDogUUID;

    public LabradorWorldData() {
        super(NAME);
    }

    public LabradorWorldData(String name) {
        super(name);
    }

    public static LabradorWorldData get(ServerWorld world) {
        return world.getSavedData().getOrCreate(
                LabradorWorldData::new,
                NAME
        );
    }

    public UUID getMyDogUUID() {
        return myDogUUID;
    }

    public void setMyDogUUID(UUID uuid) {
        this.myDogUUID = uuid;
        markDirty();
    }

    public void clearMyDogUUID() {
        this.myDogUUID = null;
        markDirty();
    }

    @Override
    public void read(CompoundNBT nbt) {

        if (nbt.hasUniqueId("MyDog")) {
            myDogUUID = nbt.getUniqueId("MyDog");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        if (myDogUUID != null) {
            nbt.putUniqueId("MyDog", myDogUUID);
        }

        return nbt;
    }
}