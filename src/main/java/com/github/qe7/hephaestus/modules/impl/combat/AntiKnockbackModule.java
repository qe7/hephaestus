package com.github.qe7.hephaestus.modules.impl.combat;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.network.PacketEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet28EntityVelocity;

public final class AntiKnockbackModule extends AbstractModule {

    public AntiKnockbackModule() {
        super("AntiKnockback", ModuleCategory.COMBAT);

        this.description = "Stops you from taking knockback.";
    }

    @EventSubscriber
    private final Listener<PacketEvent.Receive> receiveListener = event -> {
        if (!(event.getPacket() instanceof Packet28EntityVelocity)) return;

        final Minecraft mc = Minecraft.get();
        if (mc.thePlayer != null && ((Packet28EntityVelocity) event.getPacket()).entityId == mc.thePlayer.entityId) {
            event.setCancelled(true);
        }
    };
}
