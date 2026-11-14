package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IInventory {
    int getSizeInventory();

    ItemStack getStackInSlot(int i);

    ItemStack decrStackSize(int i, int j);

    void setInventorySlotContents(int i, ItemStack itemstack);

    String getInvName();

    int getInventoryStackLimit();

    void onInventoryChanged();

    boolean canInteractWith(EntityPlayer entityplayer);
}
