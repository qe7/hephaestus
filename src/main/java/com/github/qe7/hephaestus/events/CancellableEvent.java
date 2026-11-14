package com.github.qe7.hephaestus.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CancellableEvent implements Event {
    private boolean cancelled;
}
