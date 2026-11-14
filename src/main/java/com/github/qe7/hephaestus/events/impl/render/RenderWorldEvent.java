package com.github.qe7.hephaestus.events.impl.render;

import com.github.qe7.hephaestus.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class RenderWorldEvent implements Event {
    private final float partialTicks;
}
