package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.WorldChunkManager;

public interface IBlockAccess {
    int getBlockId(int i, int j, int k);

    TileEntity getBlockTileEntity(int i, int j, int k);

    float getBrightness(int i, int j, int k, int l);

    float getLightBrightness(int i, int j, int k);

    int getBlockMetadata(int i, int j, int k);

    Material getBlockMaterial(int i, int j, int k);

    boolean isBlockOpaqueCube(int i, int j, int k);
    
    boolean isBlockNormalCube(int i, int j, int k);

    WorldChunkManager getWorldChunkManager();
}
