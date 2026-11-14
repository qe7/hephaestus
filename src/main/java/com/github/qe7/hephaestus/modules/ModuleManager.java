package com.github.qe7.hephaestus.modules;

import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.events.EventHandler;
import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.TickEvent;
import com.github.qe7.hephaestus.events.impl.input.KeyEvent;
import com.github.qe7.hephaestus.modules.impl.render.HUDModule;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class ModuleManager implements EventHandler {
    @Getter
    private final LinkedHashMap<Class<? extends AbstractModule>, AbstractModule> modules = new LinkedHashMap<>();

    public void init() {
        registerModule(new HUDModule());

        Globals.EVENT_MANAGER.registerHandler(this);
    }

    @EventSubscriber
    private final Listener<KeyEvent> keyListener = event -> {
        if (event.getKeyCode() == Keyboard.KEY_NONE || Minecraft.get().currentScreen != null) return;

        final KeyTrigger trigger = event.isPressed() ? KeyTrigger.PRESS : KeyTrigger.RELEASE;

        for (AbstractModule module : modules.values()) {
            if (module.getKey() == event.getKeyCode() && module.getTrigger() == trigger) {
                module.setEnabled(!module.isEnabled());
            }
        }
    };

    @EventSubscriber
    private final Listener<TickEvent> tickListener = event -> {
        for (AbstractModule module : modules.values()) {
            if (module.getTrigger() == KeyTrigger.HOLD && module.getKey() != Keyboard.KEY_NONE) {
                module.setEnabled(Minecraft.get().currentScreen == null && isDown(module.getKey()));
            }
        }
    };

    private static boolean isDown(int keyCode) {
        return keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
    }

    private void registerModule(AbstractModule module) {
        modules.put(module.getClass(), module);
        Globals.EVENT_MANAGER.registerHandler(module);
    }

    public <T extends AbstractModule> T getModule(Class<T> moduleClass) {
        return moduleClass.cast(modules.get(moduleClass));
    }

    public AbstractModule getModule(String name) {
        return modules.values().stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<AbstractModule> getEnabledModules() {
        return modules.values().stream().filter(AbstractModule::isEnabled).collect(Collectors.toList());
    }

    public List<AbstractModule> getModulesForCategory(ModuleCategory category) {
        return modules.values().stream().filter(module -> module.getCategory() == category).collect(Collectors.toList());
    }
}
