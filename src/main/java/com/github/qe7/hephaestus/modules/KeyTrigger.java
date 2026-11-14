package com.github.qe7.hephaestus.modules;

public enum KeyTrigger {
    PRESS,
    HOLD,
    RELEASE;

    public static KeyTrigger parse(String name) {
        for (KeyTrigger trigger : values()) {
            if (trigger.name().equalsIgnoreCase(name)) return trigger;
        }
        throw new IllegalArgumentException(String.format("Unknown trigger \"%s\".", name));
    }
}
