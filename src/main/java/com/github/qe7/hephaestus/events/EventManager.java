package com.github.qe7.hephaestus.events;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventManager {
    private final List<RegisteredListener> listeners = new CopyOnWriteArrayList<>();

    @SuppressWarnings("unchecked")
    public <T extends Event> T publishEvent(final T event) {
        for (final RegisteredListener registered : this.listeners) {
            if (registered.eventClass.isInstance(event) && registered.handler.listening()) {
                ((Listener<T>) registered.listener).call(event);
            }
        }
        return event;
    }

    public synchronized void registerHandler(final EventHandler handler) {
        for (final Field field : handler.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(EventSubscriber.class)) continue;

            try {
                field.setAccessible(true);

                final Class<?> eventClass = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                final EventPriority priority = field.getAnnotation(EventSubscriber.class).value();

                this.insert(new RegisteredListener(handler, (Listener<?>) field.get(handler), eventClass, priority));
            } catch (Exception e) {
                throw new RuntimeException("Failed to register listener " + field.getName(), e);
            }
        }
    }

    private void insert(final RegisteredListener listener) {
        int index = 0;
        while (index < this.listeners.size() && this.listeners.get(index).priority.getValue() >= listener.priority.getValue()) {
            index++;
        }
        this.listeners.add(index, listener);
    }

    @RequiredArgsConstructor
    private static final class RegisteredListener {
        private final EventHandler handler;
        private final Listener<?> listener;
        private final Class<?> eventClass;
        private final EventPriority priority;
    }
}
