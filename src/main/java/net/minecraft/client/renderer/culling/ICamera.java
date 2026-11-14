package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public interface ICamera {
    boolean isBoundingBoxInFrustum(AxisAlignedBB axisalignedbb);

    void setPosition(double d, double d1, double d2);
}
