package com.github.qe7.hephaestus.commands;

import com.github.qe7.hephaestus.commands.impl.BindCommand;
import com.github.qe7.hephaestus.commands.impl.ConfigCommand;
import com.github.qe7.hephaestus.utils.ChatUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;

public final class CommandManager {
    public static final String PREFIX = ".";

    @Getter
    private final LinkedHashMap<String, AbstractCommand> commands = new LinkedHashMap<>();

    public void init() {
        registerCommand(new BindCommand());
        registerCommand(new ConfigCommand());
    }

    private void registerCommand(AbstractCommand command) {
        commands.put(command.getName().toLowerCase(Locale.ROOT), command);
    }

    public boolean handle(String line) {
        if (!line.startsWith(PREFIX)) return false;

        final String[] parts = line.substring(PREFIX.length()).trim().split("\\s+");
        final AbstractCommand command = commands.get(parts[0].toLowerCase(Locale.ROOT));

        if (command == null) {
            ChatUtil.addMessage(String.format("Unknown command \"%s\".", parts[0]));
            return true;
        }

        try {
            command.execute(Arrays.copyOfRange(parts, 1, parts.length));
        } catch (IllegalArgumentException e) {
            ChatUtil.addMessage(e.getMessage() != null ? e.getMessage() : String.format("Usage: %s%s %s", PREFIX, command.getName(), command.getUsage()));
        }
        return true;
    }
}
