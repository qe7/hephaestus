package com.github.qe7.hephaestus.modules;

import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.events.EventHandler;
import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.TickEvent;
import com.github.qe7.hephaestus.events.impl.input.KeyEvent;
import com.github.qe7.hephaestus.modules.impl.combat.AntiKnockbackModule;
import com.github.qe7.hephaestus.modules.impl.misc.AutoRespawnModule;
import com.github.qe7.hephaestus.modules.impl.misc.NoFallModule;
import com.github.qe7.hephaestus.modules.impl.render.ESPModule;
import com.github.qe7.hephaestus.modules.impl.render.FullBrightModule;
import com.github.qe7.hephaestus.modules.impl.render.HUDModule;
import com.github.qe7.hephaestus.modules.impl.render.XrayModule;
import com.github.qe7.hephaestus.modules.impl.render.ZoomModule;
import com.github.qe7.hephaestus.settings.AbstractSetting;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class ModuleManager implements EventHandler {
    @Getter
    private final LinkedHashMap<Class<? extends AbstractModule>, AbstractModule> modules = new LinkedHashMap<>();

    public void init() {
        registerModule(new HUDModule());
        registerModule(new FullBrightModule());
        registerModule(new NoFallModule());
        registerModule(new AntiKnockbackModule());
        registerModule(new AutoRespawnModule());
        registerModule(new ZoomModule());
        registerModule(new XrayModule());
        registerModule(new ESPModule());

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
        for (Field field : module.getClass().getDeclaredFields()) {
            if (!AbstractSetting.class.isAssignableFrom(field.getType())) continue;

            try {
                field.setAccessible(true);
                module.getSettings().add((AbstractSetting<?>) field.get(module));
            } catch (Exception e) {
                throw new RuntimeException("Failed to register setting " + field.getName(), e);
            }
        }

        modules.put(module.getClass(), module);
        Globals.EVENT_MANAGER.registerHandler(module);
    }

    public <T extends AbstractModule> T getModule(Class<T> moduleClass) {
        return moduleClass.cast(modules.get(moduleClass));
    }

    public AbstractModule getModule(String name) {
        return modules.values().stream().filter(module -> module.getName().replace(" ", "").equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<AbstractModule> getEnabledModules() {
        return modules.values().stream().filter(AbstractModule::isEnabled).collect(Collectors.toList());
    }

    public List<AbstractModule> getModulesForCategory(ModuleCategory category) {
        return modules.values().stream().filter(module -> module.getCategory() == category).collect(Collectors.toList());
    }
}
