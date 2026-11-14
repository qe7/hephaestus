package com.github.qe7.hephaestus.settings.impl;

import com.github.qe7.hephaestus.settings.AbstractSetting;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public final class NumberSetting<T extends Number> extends AbstractSetting<T> {
    private final T min;
    private final T max;
    private final T step;

    public NumberSetting(String name, T value, T min, T max, T step) {
        super(name, value);

        this.min = min;
        this.max = max;
        this.step = step;

        setValue(value);
    }

    @Override
    public void setValue(T value) {
        final BigDecimal min = new BigDecimal(this.min.toString());
        final BigDecimal max = new BigDecimal(this.max.toString());
        final BigDecimal step = new BigDecimal(this.step.toString());

        final BigDecimal steps = new BigDecimal(value.toString()).subtract(min).divide(step, 0, RoundingMode.HALF_UP);

        super.setValue(convert(min.add(steps.multiply(step)).max(min).min(max)));
    }

    @SuppressWarnings("unchecked")
    private T convert(BigDecimal number) {
        if (min instanceof Integer) return (T) Integer.valueOf(number.intValue());
        if (min instanceof Long) return (T) Long.valueOf(number.longValue());
        if (min instanceof Float) return (T) Float.valueOf(number.floatValue());
        if (min instanceof Double) return (T) Double.valueOf(number.doubleValue());

        throw new IllegalArgumentException("Unsupported number type " + min.getClass().getSimpleName());
    }
}
