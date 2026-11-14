package com.github.qe7.hephaestus.utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class RenderUtil {
    private RenderUtil() {
    }

    public static void drawOutlinedBox(AxisAlignedBB box, int argb) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_LINES);
        tessellator.setColorRGBA_I(argb & 0xFFFFFF, argb >>> 24);

        line(tessellator, box.minX, box.minY, box.minZ, box.maxX, box.minY, box.minZ);
        line(tessellator, box.maxX, box.minY, box.minZ, box.maxX, box.minY, box.maxZ);
        line(tessellator, box.maxX, box.minY, box.maxZ, box.minX, box.minY, box.maxZ);
        line(tessellator, box.minX, box.minY, box.maxZ, box.minX, box.minY, box.minZ);

        line(tessellator, box.minX, box.maxY, box.minZ, box.maxX, box.maxY, box.minZ);
        line(tessellator, box.maxX, box.maxY, box.minZ, box.maxX, box.maxY, box.maxZ);
        line(tessellator, box.maxX, box.maxY, box.maxZ, box.minX, box.maxY, box.maxZ);
        line(tessellator, box.minX, box.maxY, box.maxZ, box.minX, box.maxY, box.minZ);

        line(tessellator, box.minX, box.minY, box.minZ, box.minX, box.maxY, box.minZ);
        line(tessellator, box.maxX, box.minY, box.minZ, box.maxX, box.maxY, box.minZ);
        line(tessellator, box.maxX, box.minY, box.maxZ, box.maxX, box.maxY, box.maxZ);
        line(tessellator, box.minX, box.minY, box.maxZ, box.minX, box.maxY, box.maxZ);

        tessellator.draw();
    }

    public static void drawFilledBox(AxisAlignedBB box, int argb) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(argb & 0xFFFFFF, argb >>> 24);

        quad(tessellator, box.minX, box.minY, box.minZ, box.maxX, box.minY, box.minZ, box.maxX, box.minY, box.maxZ, box.minX, box.minY, box.maxZ);
        quad(tessellator, box.minX, box.maxY, box.minZ, box.minX, box.maxY, box.maxZ, box.maxX, box.maxY, box.maxZ, box.maxX, box.maxY, box.minZ);
        quad(tessellator, box.minX, box.minY, box.minZ, box.minX, box.maxY, box.minZ, box.maxX, box.maxY, box.minZ, box.maxX, box.minY, box.minZ);
        quad(tessellator, box.minX, box.minY, box.maxZ, box.maxX, box.minY, box.maxZ, box.maxX, box.maxY, box.maxZ, box.minX, box.maxY, box.maxZ);
        quad(tessellator, box.minX, box.minY, box.minZ, box.minX, box.minY, box.maxZ, box.minX, box.maxY, box.maxZ, box.minX, box.maxY, box.minZ);
        quad(tessellator, box.maxX, box.minY, box.minZ, box.maxX, box.maxY, box.minZ, box.maxX, box.maxY, box.maxZ, box.maxX, box.minY, box.maxZ);

        tessellator.draw();
    }

    private static void line(Tessellator tessellator, double x1, double y1, double z1, double x2, double y2, double z2) {
        tessellator.addVertex(x1, y1, z1);
        tessellator.addVertex(x2, y2, z2);
    }

    private static void quad(Tessellator tessellator, double x1, double y1, double z1, double x2, double y2, double z2,
                             double x3, double y3, double z3, double x4, double y4, double z4) {
        tessellator.addVertex(x1, y1, z1);
        tessellator.addVertex(x2, y2, z2);
        tessellator.addVertex(x3, y3, z3);
        tessellator.addVertex(x4, y4, z4);
    }
}
