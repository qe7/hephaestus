package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block {

    public BlockContainer(int i, Material material) {
        super(i, material);
        isBlockContainer[i] = true;
    }

    public BlockContainer(int i, int j, Material material) {
        super(i, j, material);
        isBlockContainer[i] = true;
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        world.setBlockTileEntity(i, j, k, getBlockEntity());
    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        super.onBlockRemoval(world, i, j, k);
        world.removeBlockTileEntity(i, j, k);
    }

    public abstract TileEntity getBlockEntity();
}
