package com.github.qe7.hephaestus.settings;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractSetting<T> {
    @NonNull
    private final String name;

    @NonNull
    @Setter
    protected T value;

    public final void parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("No value given for %s.", name));
        }
        setValue(convert(input.trim()));
    }

    protected abstract T convert(String input);
}
