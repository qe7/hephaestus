package com.github.qe7.hephaestus.events.impl.render;

import com.github.qe7.hephaestus.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public final class FovEvent implements Event {
    private float fov;
}
