package com.monkeybiznec.sunrise.common.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import org.jetbrains.annotations.NotNull;

public class SunriseBrewingRecipe extends BrewingRecipe {
    private final Ingredient input;

    public SunriseBrewingRecipe(Ingredient pInput, Ingredient pIngredient, ItemStack pOutput) {
        super(pInput, pIngredient, pOutput);
        this.input = pInput;
    }

    @Override
    public boolean isInput(@NotNull ItemStack pItemStack) {
        ItemStack[] matchingStacks = this.input.getItems();
        if (matchingStacks.length == 0) {
            return pItemStack.isEmpty();
        } else {
            for (ItemStack itemStack : matchingStacks) {
                if (ItemStack.isSameItem(pItemStack, itemStack) && ItemStack.isSameItemSameTags(itemStack, pItemStack)) {
                    return true;
                }
            }
            return false;
        }
    }
}