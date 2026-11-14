package com.github.qe7.hephaestus.events.impl.input;

import com.github.qe7.hephaestus.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class KeyEvent implements Event {
    private final int keyCode;
    private final boolean pressed;
}
