package com.github.qe7.hephaestus.modules.impl.render;

import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.render.RenderOverlayEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public final class HUDModule extends AbstractModule {

    public HUDModule() {
        super("HUD", ModuleCategory.RENDER);

        this.description = "Displays information related to the client/player.";

        this.setEnabled(true);
    }

    @EventSubscriber
    private final Listener<RenderOverlayEvent> renderOverlayListener = event -> {
        final FontRenderer font = Minecraft.get().fontRenderer;

        font.drawStringWithShadow(String.format("%s %s", Globals.NAME, Globals.VERSION_FULL), 2, 2, -1);

        for (AbstractModule module : Globals.MODULE_MANAGER.getEnabledModules()) {
            final String moduleName = module.getName();
            final int x = 2;
            final int y = 2 + (Globals.MODULE_MANAGER.getEnabledModules().indexOf(module) + 1) * (8 + 2);
            font.drawStringWithShadow(moduleName, x, y, -1);
        }
    };
}
