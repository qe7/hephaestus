package com.github.qe7.hephaestus.modules.impl.render;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.render.FovEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.KeyTrigger;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import com.github.qe7.hephaestus.settings.impl.BooleanSetting;
import com.github.qe7.hephaestus.settings.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public final class ZoomModule extends AbstractModule {
    private final NumberSetting<Float> factor = new NumberSetting<>("Factor", 4.0F, 1.5F, 10.0F, 0.5F);
    private final BooleanSetting smoothCamera = new BooleanSetting("Smooth Camera", true);

    private boolean previousSmoothCamera;

    public ZoomModule() {
        super("Zoom", ModuleCategory.RENDER);

        this.description = "Zooms in while the key is held.";

        this.setKey(Keyboard.KEY_C);
        this.setTrigger(KeyTrigger.HOLD);
    }

    @EventSubscriber
    private final Listener<FovEvent> fovListener = event -> event.setFov(event.getFov() / factor.getValue());

    @Override
    protected void onEnable() {
        final Minecraft mc = Minecraft.get();
        if (mc == null) return;

        previousSmoothCamera = mc.gameSettings.smoothCamera;
        if (smoothCamera.getValue()) mc.gameSettings.smoothCamera = true;
    }

    @Override
    protected void onDisable() {
        final Minecraft mc = Minecraft.get();
        if (mc == null) return;

        mc.gameSettings.smoothCamera = previousSmoothCamera;
    }
}
