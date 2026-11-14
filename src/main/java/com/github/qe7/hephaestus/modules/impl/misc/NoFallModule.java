package com.github.qe7.hephaestus.modules.impl.misc;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.TickEvent;
import com.github.qe7.hephaestus.events.impl.network.PacketEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet10Flying;

public final class NoFallModule extends AbstractModule {

    public NoFallModule() {
        super("NoFall", ModuleCategory.MISC);

        this.description = "Prevents fall damage.";
    }

    @EventSubscriber
    private final Listener<TickEvent> tickListener = event -> Minecraft.get().thePlayer.fallDistance = 0.0F;

    @EventSubscriber
    private final Listener<PacketEvent.Send> sendListener = event -> {
        if (event.getPacket() instanceof Packet10Flying) {
            ((Packet10Flying) event.getPacket()).onGround = true;
        }
    };
}
