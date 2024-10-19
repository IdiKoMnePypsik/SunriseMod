package com.monkeybiznec.sunrise.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class MiscUtils {
    public MiscUtils() {
    }

    public static ItemStack createPotion(RegistryObject<Potion> pPotion){
        return PotionUtils.setPotion(new ItemStack(Items.POTION), pPotion.get());
    }

    public static ItemStack createPotion(Potion pPotion){
        return PotionUtils.setPotion(new ItemStack(Items.POTION), pPotion);
    }

    public static void setAttributeValue(@Nullable LivingEntity pLivingEntity, Attribute pAttribute, float pValue) {
        if (pLivingEntity != null) {
            AttributeInstance attribute = pLivingEntity.getAttribute(pAttribute);
            if (attribute != null) {
                attribute.setBaseValue(pValue);
            }
        }
    }

    @Nullable
    public static <T extends Entity> List<T> getEntitiesAroundSelf(Class<T> pEntityClass, @javax.annotation.Nullable Entity pEntity, float pRadius, boolean pMustSee) {
        if (pEntity != null) {
            AABB aabb = new AABB(pEntity.position(), pEntity.position()).inflate(pRadius);
            List<T> entityList = pEntity.level().getEntitiesOfClass(pEntityClass, aabb, EntitySelector.ENTITY_STILL_ALIVE);
            if (!entityList.isEmpty()) {
                for (T entity : entityList) {
                    if (pEntity instanceof LivingEntity living) {
                        if (pMustSee) {
                            if (living.hasLineOfSight(entity)) {
                                return entityList;
                            }
                        } else {
                            return entityList;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean isEntityMoving(Entity pEntity, float pMinChange) {
        Vec3 deltaMovement = pEntity.getDeltaMovement();
        return !(deltaMovement.length() > -pMinChange && deltaMovement.length() < pMinChange);
    }

    public static boolean checkItemsInHands(@Nullable LivingEntity pEntity, Predicate<Item> pCondition) {
        if (pEntity == null) {
            return false;
        }
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemInHand = pEntity.getItemInHand(hand);
            if (pCondition.test(itemInHand.getItem())) {
                return true;
            }
        }
        return false;
    }
}
