package com.github.qe7.hephaestus.core;

import com.github.qe7.hephaestus.commands.CommandManager;
import com.github.qe7.hephaestus.config.ConfigManager;
import com.github.qe7.hephaestus.events.EventManager;
import com.github.qe7.hephaestus.modules.ModuleManager;

public class Globals {
    public static final String NAME = "Hephaestus";
    public static final String VERSION = "2.0.0";
    public static final String VERSION_FULL = String.format("%s (%s.%s)", VERSION, BuildConstants.GIT_COMMIT, BuildConstants.GIT_REVISION);

    public static final EventManager EVENT_MANAGER = new EventManager();
    public static final ModuleManager MODULE_MANAGER = new ModuleManager();
    public static final CommandManager COMMAND_MANAGER = new CommandManager();
    public static final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public static void init() {
        MODULE_MANAGER.init();
        COMMAND_MANAGER.init();
        CONFIG_MANAGER.load();
    }
}
