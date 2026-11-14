package com.github.qe7.hephaestus.modules.impl.movement;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.TickEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public final class FlightModule extends AbstractModule {
    private static final double VERTICAL_SPEED = 0.4;

    public FlightModule() {
        super("Flight", ModuleCategory.MOVEMENT);

        this.description = "Lets you fly up with jump and down with sneak.";
    }

    @EventSubscriber
    private final Listener<TickEvent> tickListener = event -> {
        final EntityPlayerSP player = Minecraft.get().thePlayer;

        player.motionY = player.movementInput.jump ? VERTICAL_SPEED : player.movementInput.sneak ? -VERTICAL_SPEED : 0.0;
        player.onGround = true;
        player.fallDistance = 0.0F;
    };
}
