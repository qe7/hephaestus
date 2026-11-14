package com.github.qe7.hephaestus.modules;

import com.github.qe7.hephaestus.events.EventHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbstractModule implements EventHandler {
    protected final String name;
    protected final ModuleCategory category;

    protected String description;

    private boolean enabled;

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
