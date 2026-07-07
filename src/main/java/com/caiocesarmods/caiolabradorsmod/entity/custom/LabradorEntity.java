package com.caiocesarmods.caiolabradorsmod.entity.custom;

import com.caiocesarmods.caiolabradorsmod.Util.ModSoundEvents;
import com.caiocesarmods.caiolabradorsmod.entity.LabradorVariant;
import com.caiocesarmods.caiolabradorsmod.entity.LabradorWorldData;
import com.caiocesarmods.caiolabradorsmod.entity.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class LabradorEntity extends WolfEntity {
    private static final DataParameter<Integer> VARIANT =
            EntityDataManager.createKey(LabradorEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_MY_DOG =
            EntityDataManager.createKey(LabradorEntity.class, DataSerializers.BOOLEAN);

    public LabradorEntity(EntityType<? extends WolfEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTamed(false);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(IS_MY_DOG, false);

    }

    public boolean isMyDog() {
        return this.dataManager.get(IS_MY_DOG);
    }

    public void setMyDog(boolean value) {
        this.dataManager.set(IS_MY_DOG, value);
    }

    public LabradorVariant getVariant() {
        return LabradorVariant.values()[this.dataManager.get(VARIANT)];
    }

    public void setVariant(LabradorVariant variant) {
        this.dataManager.set(VARIANT, variant.ordinal());
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 24.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.30D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.getVariant().ordinal());

    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0D);
            this.setHealth(40.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24.0D);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, true));
        this.goalSelector.addGoal(7, new FetchItemGoal(this));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FollowPackLeaderGoal(this));
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
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractFishEntity.class, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, false));
        this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
    }

    @Override
    public boolean canMateWith(AnimalEntity other) {

        if (other == this)
            return false;

        if (!(other instanceof LabradorEntity))
            return false;

        LabradorEntity dog = (LabradorEntity) other;

        return this.isTamed()
                && dog.isTamed()
                && !this.isQueuedToSit()
                && !dog.isQueuedToSit()
                && !isMyDog()
                && this.isInLove()
                && dog.isInLove();
    }

    @Override
    public void onAddedToWorld() {

        super.onAddedToWorld();

        if (!world.isRemote) {

            LabradorWorldData data = getWorldData();

            if (data != null) {

                this.setMyDog(
                        this.getUniqueID().equals(data.getMyDogUUID())
                );
            }
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("Variant")) {
            int variant = compound.getInt("Variant");

            if (variant >= 0 && variant < LabradorVariant.values().length) {
                this.setVariant(LabradorVariant.values()[variant]);
            } else {
                this.setVariant(LabradorVariant.BROWN);
            }
        }

        if (this.isMyDog()) {
            this.setCustomName(
                    new StringTextComponent("Max")
                            .mergeStyle(TextFormatting.GOLD)
            );
            this.setCustomNameVisible(true);
        }
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItem(hand);

        // Prevent renaming Max
        if (this.isMyDog() && stack.getItem() == Items.NAME_TAG) {
            return ActionResultType.SUCCESS;
        }

        if (stack.getItem() == Items.BREAD
                && this.getVariant() == LabradorVariant.BROWN
                && isAdult()
                && !this.isMyDog()
                && this.canBecomeMyDog()) {

            LabradorWorldData data = getWorldData();

            if (data != null) {
                data.setMyDogUUID(this.getUniqueID());
            }

            this.setMyDog(true);

            this.setCustomName(
                    new StringTextComponent("Max")
                            .mergeStyle(TextFormatting.GOLD)
            );

            this.setCustomNameVisible(true);

            if (!this.world.isRemote) {

                this.world.playSound(
                        null,
                        this.getPosX(),
                        this.getPosY(),
                        this.getPosZ(),
                        ModSoundEvents.LABRADOR_ANGRY.get(),
                        this.getSoundCategory(),
                        1.0F,
                        1.0F
                );

                ServerWorld server = (ServerWorld) this.world;

                server.spawnParticle(ParticleTypes.HEART,
                        getPosX(), getPosYEye(), getPosZ(),
                        8, 0.4, 0.4, 0.4, 0.02);

                server.spawnParticle(ParticleTypes.FIREWORK,
                        getPosX(), getPosYEye(), getPosZ(),
                        15, 0.5, 0.5, 0.5, 0.05);
            }

            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }

            return ActionResultType.SUCCESS;
        }

        System.out.println(this.isInLove());

        if (stack.getItem() == Items.COD
                || stack.getItem() == Items.SALMON
                || stack.getItem() == Items.BEEF
                || stack.getItem() == Items.MUTTON
                || stack.getItem() == Items.CHICKEN
                || stack.getItem() == Items.APPLE
                || stack.getItem() == Items.TROPICAL_FISH
                || stack.getItem() == Items.PORKCHOP
                || stack.getItem() == Items.SWEET_BERRIES
                || stack.getItem() == Items.RABBIT
                || stack.getItem() == Items.GOLDEN_APPLE
                || stack.getItem() == Items.MELON_SLICE) {

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamedBy(player);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.world.setEntityState(this, (byte)6);
                }
            }

            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }

            return ActionResultType.SUCCESS;
        }

        if (stack.getItem() == DEADLY_ITEM) {
            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }

            this.addPotionEffect(new EffectInstance(Effects.POISON, 900));
            if (player.isCreative() || !this.isInvulnerable()) {
                this.attackEntityFrom(DamageSource.causePlayerDamage(player), Float.MAX_VALUE);
            }

            return ActionResultType.func_233537_a_(this.world.isRemote);
        }

        return super.getEntityInteractionResult(player, hand);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isRemote && this.ticksExisted % 40 == 0) {
            System.out.println("----------------");
            System.out.println("Tamed: " + this.isTamed());
            System.out.println("Owner: " + this.getOwnerId());
            System.out.println("Sitting: " + this.isQueuedToSit());
            System.out.println("In love: " + this.isInLove());

        }
    }

    @Override
    public ILivingEntityData onInitialSpawn(
            IServerWorld world,
            DifficultyInstance difficulty,
            SpawnReason reason,
            ILivingEntityData spawnData,
            CompoundNBT dataTag) {

        int roll = this.rand.nextInt(100);

        if (roll < 25)
            setVariant(LabradorVariant.YELLOW);

        else if (roll < 50)
            setVariant(LabradorVariant.BLACK);

        else if (roll < 75)
            setVariant(LabradorVariant.BROWN);

        else
            setVariant(LabradorVariant.WHITE);

        return super.onInitialSpawn(
                world,
                difficulty,
                reason,
                spawnData,
                dataTag
        );
    }

    private LabradorWorldData getWorldData() {

        if (!(world instanceof ServerWorld))
            return null;

        return LabradorWorldData.get((ServerWorld) world);
    }

    public boolean canBecomeMyDog() {

        LabradorWorldData data = getWorldData();

        return data != null
                && data.getMyDogUUID() == null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {

        System.out.println("Breeding item: " + stack.getItem());

        return stack.getItem() == Items.COD
                || stack.getItem() == Items.SALMON
                || stack.getItem() == Items.BEEF
                || stack.getItem() == Items.MUTTON
                || stack.getItem() == Items.CHICKEN
                || stack.getItem() == Items.APPLE
                || stack.getItem() == Items.TROPICAL_FISH
                || stack.getItem() == Items.PORKCHOP
                || stack.getItem() == Items.SWEET_BERRIES
                || stack.getItem() == Items.RABBIT
                || stack.getItem() == Items.GOLDEN_APPLE
                || stack.getItem() == Items.MELON_SLICE;
    }

    private static final Item DEADLY_ITEM = Items.COOKIE;

    @Override
    public boolean canSwim() {
        return true;
    }

    @Override
    public void onDeath(DamageSource source) {

        if (!world.isRemote && this.isMyDog()) {

            LabradorWorldData data = getWorldData();

            if (data != null)
                data.clearMyDogUUID();
        }

        super.onDeath(source);
    }

    @Override
    public LabradorEntity createChild(ServerWorld world, AgeableEntity mate) {
        System.out.println("Labrador createChild() called!");

        LabradorEntity puppy = ModEntityTypes.LABRADOR_ENTITY.get().create(world);

        if (puppy != null) {

            LabradorVariant variant;

            if (mate instanceof LabradorEntity) {
                LabradorEntity other = (LabradorEntity) mate;

                // 5% de chance de mutação
                if (this.rand.nextFloat() < 0.05F) {

                    variant = LabradorVariant.values()[
                            this.rand.nextInt(LabradorVariant.values().length)
                            ];

                } else {

                    // Herda a cor de um dos pais
                    variant = this.rand.nextBoolean()
                            ? this.getVariant()
                            : other.getVariant();
                }

            } else {

                variant = this.getVariant();

            }

            puppy.setVariant(variant);
        }

        return puppy;
    }

    @Override
    protected SoundEvent getAmbientSound() {

        if (this.isAngry()) {

            return this.rand.nextBoolean()
                    ? ModSoundEvents.LABRADOR_ANGRY.get()
                    : ModSoundEvents.LABRADOR_BARK2.get();
        }

        switch (this.rand.nextInt(4)) {

            case 0:
                return ModSoundEvents.LABRADOR_BARK2.get();

            case 1:
                return ModSoundEvents.LABRADOR_BARK_AMBIENT.get();

            case 2:
                return ModSoundEvents.LABRADOR_BREATHING.get();

            default:
                return ModSoundEvents.LABRADOR_PURR.get();
        }
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_HURT, 1.0F, 1.7F);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(ModSoundEvents.LABRADOR_PURR.get(), 0.7F, 2.0F);
        return null;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getBlockState(pos).getMaterial() == Material.WATER) {
            return 10.0F;
        }
        return super.getBlockPathWeight(pos, worldIn);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false; // keep realistic
    }

    @Override
    public int getMaxAir() {
        return 1200; // stays underwater longer
    }
}
