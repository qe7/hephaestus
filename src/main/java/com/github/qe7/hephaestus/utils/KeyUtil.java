package com.github.qe7.hephaestus.utils;

import org.lwjgl.input.Keyboard;

import java.util.Locale;

public final class KeyUtil {
    private static final int MOUSE_OFFSET = -100;
    private static final int MOUSE_BUTTONS = 10;

    private KeyUtil() {
    }

    public static int parseKey(String name) {
        final String upper = name.trim().toUpperCase(Locale.ROOT);

        if (upper.matches("MOUSE[0-9]")) return Integer.parseInt(upper.substring(5)) + MOUSE_OFFSET;

        final int key = Keyboard.getKeyIndex(upper);
        if (key == Keyboard.KEY_NONE && !upper.equals("NONE")) {
            throw new IllegalArgumentException(String.format("Unknown key \"%s\".", name));
        }
        return key;
    }

    public static String keyName(int key) {
        if (key >= MOUSE_OFFSET && key < MOUSE_OFFSET + MOUSE_BUTTONS) return "MOUSE" + (key - MOUSE_OFFSET);

        final String name = key >= 0 && key < Keyboard.KEYBOARD_SIZE ? Keyboard.getKeyName(key) : null;
        return name != null ? name : "NONE";
    }
}
