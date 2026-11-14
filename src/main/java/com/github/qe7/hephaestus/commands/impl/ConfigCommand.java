package com.github.qe7.hephaestus.commands.impl;

import com.github.qe7.hephaestus.commands.AbstractCommand;
import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.utils.ChatUtil;

public final class ConfigCommand extends AbstractCommand {

    public ConfigCommand() {
        super("config", "<save|load>");

        this.description = "Saves or reloads the config file.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();

        if (args[0].equalsIgnoreCase("save")) {
            ChatUtil.addMessage(Globals.CONFIG_MANAGER.save() ? "Saved config." : "Failed to save config, check the log.");
        } else if (args[0].equalsIgnoreCase("load")) {
            ChatUtil.addMessage(Globals.CONFIG_MANAGER.load() ? "Loaded config." : "No readable config found, check the log.");
        } else {
            throw new IllegalArgumentException();
        }
    }
}
