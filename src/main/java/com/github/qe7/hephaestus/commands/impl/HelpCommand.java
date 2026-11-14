package com.github.qe7.hephaestus.commands.impl;

import com.github.qe7.hephaestus.commands.AbstractCommand;
import com.github.qe7.hephaestus.commands.CommandManager;
import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.utils.ChatUtil;

public final class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "");

        this.description = "Lists all commands.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 0) throw new IllegalArgumentException();

        for (AbstractCommand command : Globals.COMMAND_MANAGER.getCommands().values()) {
            final String usage = command.getUsage().isEmpty() ? "" : " " + command.getUsage();
            final String description = command.getDescription() == null ? "" : " - " + command.getDescription();

            ChatUtil.addMessage(String.format("%s%s%s%s", CommandManager.PREFIX, command.getName(), usage, description));
        }
    }
}
