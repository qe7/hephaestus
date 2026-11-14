package com.github.qe7.hephaestus.settings;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractSetting<T> {
    private final String name;

    @Setter
    protected T value;
}
