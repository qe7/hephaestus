package com.github.qe7.hephaestus.settings.impl;

import com.github.qe7.hephaestus.settings.AbstractSetting;

public final class BooleanSetting extends AbstractSetting<Boolean> {

    public BooleanSetting(String name, boolean value) {
        super(name, value);
    }

    @Override
    protected Boolean convert(String input) {
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("on")) return true;
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("off")) return false;

        throw new IllegalArgumentException(String.format("Expected true/false or on/off for %s, got \"%s\".", getName(), input));
    }
}
