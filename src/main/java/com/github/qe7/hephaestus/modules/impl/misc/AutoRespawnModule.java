package com.github.qe7.hephaestus.modules.impl.misc;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.TickEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;

public final class AutoRespawnModule extends AbstractModule {

    public AutoRespawnModule() {
        super("AutoRespawn", ModuleCategory.MISC);

        this.description = "Respawns you as soon as you die.";
    }

    private EntityPlayerSP respawned;

    @EventSubscriber
    private final Listener<TickEvent> tickListener = event -> {
        final Minecraft mc = Minecraft.get();
        if (!(mc.currentScreen instanceof GuiGameOver) || mc.thePlayer == respawned) return;

        respawned = mc.thePlayer;
        mc.thePlayer.respawnPlayer();
        mc.displayGuiScreen(null);
    };
}
