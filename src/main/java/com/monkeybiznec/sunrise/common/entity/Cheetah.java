package com.monkeybiznec.sunrise.common.entity;

import com.monkeybiznec.sunrise.common.entity.ai.ai_system.SensorType;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.Task;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.sensor.VisionSensor;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.task.TestTask;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.task.TestTaskTwo;
import com.monkeybiznec.sunrise.common.entity.ai.behavior.DietBuilder;
import com.monkeybiznec.sunrise.common.entity.ai.control.IJumpable;
import com.monkeybiznec.sunrise.common.entity.ai.control.SearchType;
import com.monkeybiznec.sunrise.common.entity.ai.goal.MultiAttackableTargetGoal;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.TaskManager;
import com.monkeybiznec.sunrise.common.entity.goal.cheetah.CheetahMeleeAttackGoal;
import com.monkeybiznec.sunrise.common.entity.goal.cheetah.CheetahRunAwayFromEnemyGoal;
import com.monkeybiznec.sunrise.common.entity.navigation.SmartGroundNavigation;
import com.monkeybiznec.sunrise.common.tag.SunriseTags;
import com.monkeybiznec.sunrise.util.MiscUtils;
import com.monkeybiznec.sunrise.util.compound.CompoundType;
import com.monkeybiznec.sunrise.util.compound.EntityNBTGenerator;
import com.monkeybiznec.sunrise.util.compound.NBTCollectionHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Cheetah extends Animal implements IJumpable {
    public static final EntityNBTGenerator<Integer> DATA_JUMP_COOLDOWN = new EntityNBTGenerator<>(Cheetah.class, "JumpCooldown", CompoundType.INTEGER, 0);
    public static final EntityNBTGenerator<Integer> DATA_RUNNING_TICK = new EntityNBTGenerator<>(Cheetah.class, "RunningTicks", CompoundType.INTEGER, 0);
    public static final EntityNBTGenerator<Boolean> DATA_JUMPING = new EntityNBTGenerator<>(Cheetah.class, "IsJumping", CompoundType.BOOLEAN, false);
    public static final EntityNBTGenerator<Integer> CALM = new EntityNBTGenerator<>(Cheetah.class, "CalmTicks", CompoundType.INTEGER, 0);
    public static final EntityNBTGenerator<Boolean> ANGRY = new EntityNBTGenerator<>(Cheetah.class, "Angry", CompoundType.BOOLEAN, false);
    private List<UUID> neutralForPlayers = new ArrayList<>();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState lookAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public int jumpAnimationTimeout = 0;
    public int lookAnimationTimeout = 0;
    public float tailRot;
    public float prevTailRot;

    private final TaskManager<Cheetah> taskManager;
    private final DietBuilder dietBuilder;


    public Cheetah(EntityType<? extends Animal> pAnimal, Level pLevel) {
        super(pAnimal, pLevel);
        this.setMaxUpStep(1.0F);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 2.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 3.0F);
        this.navigation = new SmartGroundNavigation(this, 1.0F);
        this.navigation.setCanFloat(true);
        this.xpReward = 5;

        this.dietBuilder = new DietBuilder();

        this.taskManager = new TaskManager<>(this);
        this.taskManager.actionScheduler.addAction(entity -> {
            if (entity.getTarget() == null) {
                this.level().broadcastEntityEvent(this, (byte) 100);
            }
        }, 35, this.getRandom().nextInt(250) + 350);

        /*
        this.taskManager.addSensor(SensorType.VISION, new VisionSensor<>(this).setVisionRange(10));
        this.taskManager.addTask(new TestTask(this.taskManager, this,3, Task.TaskImportance.HIGH));
        this.taskManager.addTask(new TestTaskTwo(this.taskManager, this, 1, Task.TaskImportance.LOW));

         */
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CheetahRunAwayFromEnemyGoal(this));
        this.goalSelector.addGoal(1, new CheetahMeleeAttackGoal<>(this, 10, 0.550F));
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return this.targetMob != null && !Cheetah.this.neutralForPlayers.contains(this.targetMob.getUUID());
            }
        });
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.3F,  Ingredient.of(Items.BEEF, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.RABBIT), false));
        this.goalSelector.addGoal(4, new BreedGoal(this, 0.2F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.3F, 8));
        this.targetSelector.addGoal(1, new MultiAttackableTargetGoal<>(this, true, SearchType.CUSTOM, List.of(Animal.class, Player.class)).setValidTargetCondition(target -> {
            if (target instanceof Player player) {
                return !this.neutralForPlayers.contains(player.getUUID());
            }
            return target.getType().is(SunriseTags.EntityTypes.TAG_CHEETAH_FOOD);
        }).setSearchRange(40).setStartCondition(mob -> CALM.get(mob) <= 0 && !mob.isBaby()));
        super.registerGoals();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 16.0F)
                .add(Attributes.FOLLOW_RANGE, 48.0F)
                .add(Attributes.ATTACK_DAMAGE, 4.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1F)
                .add(Attributes.ARMOR, 0.2F);
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return super.calculateFallDamage(pFallDistance, pDamageMultiplier) - 6;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        DATA_JUMP_COOLDOWN.define(this);
        DATA_RUNNING_TICK.define(this);
        DATA_JUMPING.define(this);
        CALM.define(this);
        ANGRY.define(this);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        DATA_JUMP_COOLDOWN.write(this, pCompound);
        DATA_RUNNING_TICK.write(this, pCompound);
        DATA_JUMPING.write(this, pCompound);
        CALM.write(this, pCompound);
        ANGRY.write(this, pCompound);
        NBTCollectionHandler.writeCollection(pCompound, "NeutralPlayers", this.neutralForPlayers, (entryTag, uuid) -> entryTag.putUUID("PlayerUUID", uuid));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        DATA_JUMP_COOLDOWN.read(this, pCompound);
        DATA_RUNNING_TICK.read(this, pCompound);
        DATA_JUMPING.read(this, pCompound);
        CALM.read(this, pCompound);
        ANGRY.read(this, pCompound);
        this.neutralForPlayers = NBTCollectionHandler.readCollection(pCompound, "NeutralPlayers", (entryTag, name) -> entryTag.getUUID("PlayerUUID"));
    }

    @Override
    protected void updateWalkAnimation(float pPartialTicks) {
        float speed = !this.isBaby() ? 6.0F : 3.5F;
        float f = this.getPose() == Pose.STANDING ? Math.min(pPartialTicks * speed, 1.0F) : 0.0F;
        this.walkAnimation.update(f, 0.5F);
    }

    private void setupAnimationStates() {
        if (this.level().isClientSide) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 105;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
            if (this.jumpAnimationTimeout <= 0) {
                this.jumpAnimationTimeout = 20;
                this.jumpAnimationState.start(this.tickCount);
            } else {
                --this.jumpAnimationTimeout;
            }
            if (!DATA_JUMPING.get(this)) {
                this.jumpAnimationState.stop();
            }
            if (!MiscUtils.isEntityMoving(this, 0.05F) && this.lookAnimationTimeout == 32) {
                this.lookAnimationState.start(this.tickCount);
            }
            if (this.lookAnimationTimeout > 0) {
                --this.lookAnimationTimeout;
            }
        }
    }

    @Contract("null->false")
    public boolean canAttackTarget(@Nullable LivingEntity pTarget) {
        return pTarget != null && !this.neutralForPlayers.contains(pTarget.getUUID()) && CALM.get(this) <= 0 && !this.playerHasMeatInHand(pTarget);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        switch (pId) {
            case 100:
                if (this.lookAnimationTimeout <= 0) {
                    this.lookAnimationTimeout = 32;
                }
                break;
            default:
                super.handleEntityEvent(pId);
        }
    }

    @Override
    public boolean canSprint() {
        return this.isSprinting() && !this.isSpectator() && !this.isCrouching() && !this.isInLava() && !this.isInFluidType();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFood(@NotNull ItemStack pItemStack) {
        if (!pItemStack.isEdible()) {
            return false;
        }
        FoodProperties foodProperties = pItemStack.getItem().getFoodProperties();
        return foodProperties != null && foodProperties.isMeat();
    }

    @Contract("null->false")
    public boolean playerHasMeatInHand(@Nullable LivingEntity pEntity) {
        if (pEntity instanceof Player player) {
            return MiscUtils.checkItemsInHands(player, item -> {
                return this.isFood(item.getDefaultInstance()) || this.dietBuilder.isFoodInDiet(item);
            });
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (this.isFood(itemInHand)) {
            if (this.canFallInLove()) {
                if (!this.level().isClientSide && this.getAge() == 0) {
                    this.neutralForPlayers.add(pPlayer.getUUID());
                    this.usePlayerItem(pPlayer, pHand, itemInHand);
                    this.setInLove(pPlayer);
                    return InteractionResult.SUCCESS;
                }
            }
            if (this.isBaby()) {
                this.usePlayerItem(pPlayer, pHand, itemInHand);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-this.getAge()), true);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.taskManager.update();
        if (DATA_JUMPING.get(this) && (this.onGround() || this.isInFluidType())) {
            DATA_JUMPING.set(this, false);
        }
        if (!this.level().isClientSide) {
            if (this.getTarget() != null) {
                ANGRY.set(this, true);
            } else {
                ANGRY.set(this, false);
            }
            MiscUtils.setAttributeValue(this, Attributes.MOVEMENT_SPEED, DATA_RUNNING_TICK.get(this) > 0 ? 0.8F : 0.6F);
            DATA_JUMP_COOLDOWN.set(this, Math.max(DATA_JUMP_COOLDOWN.get(this) - 1, 0));
            DATA_RUNNING_TICK.set(this, Math.max(DATA_RUNNING_TICK.get(this) - 1, 0));
            CALM.set(this, Math.max(CALM.get(this) - 1, 0));
        }
        this.setupAnimationStates();
        this.prevTailRot = this.tailRot;
        this.tailRot += (-( this.yBodyRot - this.yBodyRotO) - this.tailRot) * 0.9F;
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel pServerLevel, @NotNull LivingEntity pTarget) {
        if (!pServerLevel.isClientSide()) {
            CALM.set(this, 6000);
        }
        return super.killedEntity(pServerLevel, pTarget);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pMob) {
        return SunriseEntityTypes.CHEETAH.get().create(pLevel);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.OCELOT_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.OCELOT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.OCELOT_DEATH;
    }

    @Override
    public float getJumpPower() {
        return 1.7F;
    }
}
