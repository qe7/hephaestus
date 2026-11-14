package com.github.qe7.hephaestus.modules.impl.render;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.render.RenderWorldEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import com.github.qe7.hephaestus.settings.impl.ListSetting;
import com.github.qe7.hephaestus.settings.impl.ModeSetting;
import com.github.qe7.hephaestus.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class ESPModule extends AbstractModule {
    private static final int COLOR = 0xFFFF4040;
    private static final int FILL_ALPHA = 0x40;

    private enum Mode {
        OUTLINE,
        FILLED
    }

    private final ListSetting<Class<? extends Entity>> targets = ListSetting.entities("Targets", EntityPlayer.class);
    private final ModeSetting<Mode> mode = new ModeSetting<>("Mode", Mode.OUTLINE);

    public ESPModule() {
        super("ESP", ModuleCategory.RENDER);

        this.description = "Draws boxes around the chosen entities.";
    }

    @EventSubscriber
    private final Listener<RenderWorldEvent> renderListener = event -> {
        final Minecraft mc = Minecraft.get();
        if (mc.theWorld == null) return;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.5F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        final float partialTicks = event.getPartialTicks();

        for (Object object : mc.theWorld.loadedEntityList) {
            final Entity entity = (Entity) object;
            if (entity == mc.thePlayer || entity.isDead || !isTarget(entity)) continue;

            final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - entity.posX - RenderManager.renderPosX;
            final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - entity.posY - RenderManager.renderPosY;
            final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - entity.posZ - RenderManager.renderPosZ;
            final AxisAlignedBB box = entity.boundingBox.getOffsetBoundingBox(x, y, z);

            if (mode.getValue() == Mode.FILLED) RenderUtil.drawFilledBox(box, (FILL_ALPHA << 24) | (COLOR & 0xFFFFFF));
            RenderUtil.drawOutlinedBox(box, COLOR);
        }
    };

    private boolean isTarget(Entity entity) {
        for (Class<? extends Entity> type : targets.getValue()) {
            if (type.isInstance(entity)) return true;
        }
        return false;
    }
}
