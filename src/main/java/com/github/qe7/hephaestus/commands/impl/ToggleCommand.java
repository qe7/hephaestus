package com.github.qe7.hephaestus.commands.impl;

import com.github.qe7.hephaestus.commands.AbstractCommand;
import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.utils.ChatUtil;

public final class ToggleCommand extends AbstractCommand {

    public ToggleCommand() {
        super("toggle", "<module>");

        this.description = "Turns a module on or off.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();

        final AbstractModule module = Globals.MODULE_MANAGER.getModule(args[0]);
        if (module == null) throw new IllegalArgumentException(String.format("Unknown module \"%s\".", args[0]));

        module.setEnabled(!module.isEnabled());

        ChatUtil.addMessage(String.format("%s %s.", module.isEnabled() ? "Enabled" : "Disabled", module.getName()));
    }
}
