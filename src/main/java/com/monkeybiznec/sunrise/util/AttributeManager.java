package com.monkeybiznec.sunrise.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AttributeManager {
    public static void setAttributeValue(@Nullable LivingEntity pLivingEntity, Attribute pAttribute, float pValue) {
        if (pLivingEntity != null) {
            AttributeInstance attribute = pLivingEntity.getAttribute(pAttribute);
            if (attribute != null) {
                attribute.setBaseValue(pValue);
            }
        }
    }

    public static class ModifierGenerator {
        @Nullable
        private final LivingEntity entity;
        private final Attribute attribute;
        private final UUID uuid;
        private final String name;
        private double amount;
        private AttributeModifier.Operation operation = AttributeModifier.Operation.ADDITION;

        public ModifierGenerator(@Nullable LivingEntity pLivingEntity, Attribute pAttribute, String pName) {
            this.entity = pLivingEntity;
            this.attribute = pAttribute;
            this.uuid = UUID.randomUUID();
            this.name = pName;
        }

        public @Nullable LivingEntity getEntity() {
            return this.entity;
        }

        public Attribute getAttribute() {
            return this.attribute;
        }

        public UUID getUUID() {
            return this.uuid;
        }

        public String getName() {
            return this.name;
        }

        public double getAmount() {
            return this.amount;
        }

        public AttributeModifier.Operation getOperation() {
            return this.operation;
        }

        public ModifierGenerator(@Nullable LivingEntity pEntity, Attribute pAttribute, UUID pUUID, String pName) {
            this.entity = pEntity;
            this.attribute = pAttribute;
            this.uuid = pUUID != null ? pUUID : UUID.randomUUID();
            this.name = pName;
        }

        public ModifierGenerator setAmount(double pAmount) {
            this.amount = pAmount;
            return this;
        }

        public ModifierGenerator setOperation(AttributeModifier.Operation pOperation) {
            this.operation = pOperation;
            return this;
        }

        public void apply() {
            if (this.getEntity() != null) {
                AttributeInstance attributeInstance = this.getEntity().getAttribute(this.getAttribute());
                if (attributeInstance != null) {
                    AttributeModifier existingModifier = attributeInstance.getModifier(this.getUUID());
                    if (existingModifier != null) {
                        attributeInstance.removeModifier(existingModifier);
                    }
                    AttributeModifier modifier = new AttributeModifier(this.getUUID(), this.getName(), this.getAmount(), this.getOperation());
                    attributeInstance.addTransientModifier(modifier);
                }
            }
        }

        public void remove() {
            if (this.getEntity() != null) {
                AttributeInstance attributeInstance = this.getEntity().getAttribute(this.getAttribute());
                if (attributeInstance != null) {
                    AttributeModifier existingModifier = attributeInstance.getModifier(this.getUUID());
                    if (existingModifier != null) {
                        attributeInstance.removeModifier(existingModifier);
                    }
                }
            }
        }
    }
}
