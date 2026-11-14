package com.github.qe7.hephaestus.settings.impl;

import com.github.qe7.hephaestus.settings.AbstractSetting;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public final class NumberSetting<T extends Number> extends AbstractSetting<T> {
    private static final int MAX_SCALE = 64;

    private final T min;
    private final T max;
    private final T step;

    public NumberSetting(String name, T value, T min, T max, T step) {
        super(name, value);

        this.min = min;
        this.max = max;
        this.step = step;

        if (decimal(min).compareTo(decimal(max)) > 0) {
            throw new IllegalArgumentException(String.format("Min is greater than max for %s.", name));
        }
        if (decimal(step).signum() <= 0) {
            throw new IllegalArgumentException(String.format("Step must be positive for %s.", name));
        }

        setValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(fit(decimal(value)));
    }

    @Override
    protected T convert(String input) {
        final BigDecimal number;
        try {
            number = new BigDecimal(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("\"%s\" is not a number.", input));
        }

        if (Math.abs(number.scale()) > MAX_SCALE) {
            throw new IllegalArgumentException(String.format("\"%s\" is out of range for %s.", input, getName()));
        }
        return fit(number);
    }

    private T fit(BigDecimal number) {
        final BigDecimal min = decimal(this.min);
        final BigDecimal max = decimal(this.max);
        final BigDecimal step = decimal(this.step);

        final BigDecimal steps = number.max(min).min(max).subtract(min).divide(step, 0, RoundingMode.HALF_UP);

        return toType(min.add(steps.multiply(step)).min(max));
    }

    private static BigDecimal decimal(Number number) {
        return new BigDecimal(number.toString());
    }

    @SuppressWarnings("unchecked")
    private T toType(BigDecimal number) {
        if (min instanceof Integer) return (T) Integer.valueOf(number.intValue());
        if (min instanceof Long) return (T) Long.valueOf(number.longValue());
        if (min instanceof Float) return (T) Float.valueOf(number.floatValue());
        if (min instanceof Double) return (T) Double.valueOf(number.doubleValue());

        throw new IllegalArgumentException("Unsupported number type " + min.getClass().getSimpleName());
    }
}
