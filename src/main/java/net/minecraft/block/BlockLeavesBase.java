// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;


// Referenced classes of package net.minecraft.src:
//            Block, IBlockAccess, Material

public class BlockLeavesBase extends Block
{

    public BlockLeavesBase(int i, int j, Material material, boolean flag)
    {
        super(i, j, material);
        graphicsLevel = flag;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        int i1 = iblockaccess.getBlockId(i, j, k);
        if(!graphicsLevel && i1 == blockID)
        {
            return false;
        } else
        {
            return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
        }
    }

    public boolean graphicsLevel;
}
