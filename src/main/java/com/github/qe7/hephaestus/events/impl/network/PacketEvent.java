package com.github.qe7.hephaestus.events.impl.network;

import com.github.qe7.hephaestus.events.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.Packet;

@AllArgsConstructor
@Getter
public abstract class PacketEvent extends CancellableEvent {
    private final Packet packet;

    public static final class Send extends PacketEvent {
        public Send(Packet packet) {
            super(packet);
        }
    }

    public static final class Receive extends PacketEvent {
        public Receive(Packet packet) {
            super(packet);
        }
    }
}
