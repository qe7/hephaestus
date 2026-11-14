package com.github.qe7.hephaestus.settings.impl;

import com.github.qe7.hephaestus.settings.AbstractSetting;

public final class ModeSetting<E extends Enum<E>> extends AbstractSetting<E> {

    public ModeSetting(String name, E value) {
        super(name, value);
    }

    public E[] getModes() {
        return value.getDeclaringClass().getEnumConstants();
    }
}
