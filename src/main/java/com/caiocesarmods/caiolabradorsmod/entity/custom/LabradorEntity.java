package com.caiocesarmods.caiolabradorsmod.entity.custom;

import com.google.common.collect.Maps;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.Map;

public class LabradorEntity extends WolfEntity {
    private static final DataParameter<Integer> VARIANT =
            EntityDataManager.createKey(LabradorEntity.class, DataSerializers.VARINT);
    public static final Map<Integer, ResourceLocation> TEXTURE_BY_ID = Util.make(Maps.newHashMap(), (p_213410_0_) -> {
        p_213410_0_.put(0, new ResourceLocation("textures/entity/yellow_lab.png"));
        p_213410_0_.put(1, new ResourceLocation("textures/entity/white_lab.png"));
        p_213410_0_.put(2, new ResourceLocation("textures/entity/black_lab.png"));
        p_213410_0_.put(3, new ResourceLocation("textures/entity/brown_lab.png"));
    });

    public LabradorEntity(EntityType<? extends WolfEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LlamaEntity.class, 24.0F, 1.5D, 1.5D));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, TARGET_ENTITIES));
        this.targetSelector.addGoal(6, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
        this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
    }

}
