package com.github.qe7.hephaestus.modules;

import com.github.qe7.hephaestus.events.EventHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

@RequiredArgsConstructor
@Getter
public abstract class AbstractModule implements EventHandler {
    protected final String name;
    protected final ModuleCategory category;

    protected String description;

    private boolean enabled;

    @Setter
    private int key = Keyboard.KEY_NONE;

    @Setter
    private KeyTrigger trigger = KeyTrigger.PRESS;

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;

        this.enabled = enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    @Override
    public boolean listening() {
        return enabled;
    }
}
