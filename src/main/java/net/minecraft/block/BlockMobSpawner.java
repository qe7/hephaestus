// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, TileEntityMobSpawner, TileEntity

public class BlockMobSpawner extends BlockContainer
{

    public BlockMobSpawner(int i, int j)
    {
        super(i, j, Material.rock);
    }

    public TileEntity getBlockEntity()
    {
        return new TileEntityMobSpawner();
    }

    public int idDropped(int i, Random random)
    {
        return 0;
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
}
