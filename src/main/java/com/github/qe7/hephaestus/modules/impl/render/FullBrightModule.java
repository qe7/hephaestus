package com.github.qe7.hephaestus.modules.impl.render;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.TickEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import net.minecraft.client.Minecraft;

import java.util.Arrays;

public final class FullBrightModule extends AbstractModule {

    public FullBrightModule() {
        super("FullBright", ModuleCategory.RENDER);

        this.description = "Lights up your world.";
    }

    @EventSubscriber
    private final Listener<TickEvent> tickListener = event -> {
        final Minecraft mc = Minecraft.get();
        if (mc.theWorld == null) return;

        final float[] table = mc.theWorld.worldProvider.lightBrightnessTable;
        if (table[0] == 1.0F) return;

        Arrays.fill(table, 1.0F);
        mc.renderGlobal.loadRenderers();
    };

    @Override
    protected void onDisable() {
        final Minecraft mc = Minecraft.get();
        if (mc == null || mc.theWorld == null) return;

        mc.theWorld.worldProvider.generateLightBrightnessTable();
        mc.renderGlobal.loadRenderers();
    }
}
