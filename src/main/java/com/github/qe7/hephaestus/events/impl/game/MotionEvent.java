package com.github.qe7.hephaestus.events.impl.game;

import com.github.qe7.hephaestus.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public abstract class MotionEvent implements Event {

    @AllArgsConstructor
    @Getter
    @Setter
    public static final class Pre extends MotionEvent {
        private float yaw;
        private float pitch;
        private boolean onGround;
    }

    public static final class Post extends MotionEvent {
    }
}
