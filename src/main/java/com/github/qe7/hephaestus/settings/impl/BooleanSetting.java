package com.github.qe7.hephaestus.settings.impl;

import com.github.qe7.hephaestus.settings.AbstractSetting;

public final class BooleanSetting extends AbstractSetting<Boolean> {

    public BooleanSetting(String name, boolean value) {
        super(name, value);
    }
}
