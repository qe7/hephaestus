package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public interface IRecipe {
    boolean matches(InventoryCrafting inventorycrafting);

    ItemStack getCraftingResult(InventoryCrafting inventorycrafting);

    int getRecipeSize();

    ItemStack getRecipeOutput();
}
