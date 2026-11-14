package com.github.qe7.hephaestus.settings.impl;

import com.github.qe7.hephaestus.settings.AbstractSetting;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public final class ModeSetting<E extends Enum<E>> extends AbstractSetting<E> {

    public ModeSetting(String name, E value) {
        super(name, value);
    }

    public E[] getModes() {
        return value.getDeclaringClass().getEnumConstants();
    }

    @Override
    public String format() {
        return value.name().toLowerCase(Locale.ROOT);
    }

    @Override
    protected E convert(String input) {
        for (E mode : getModes()) {
            if (mode.name().equalsIgnoreCase(input)) return mode;
        }

        final String valid = Arrays.stream(getModes()).map(mode -> mode.name().toLowerCase(Locale.ROOT)).collect(Collectors.joining(", "));
        throw new IllegalArgumentException(String.format("Unknown mode \"%s\" for %s. Valid: %s.", input, getName(), valid));
    }
}
