package com.github.qe7.hephaestus.commands.impl;

import com.github.qe7.hephaestus.commands.AbstractCommand;
import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.KeyTrigger;
import com.github.qe7.hephaestus.utils.ChatUtil;
import org.lwjgl.input.Keyboard;

public final class BindCommand extends AbstractCommand {

    public BindCommand() {
        super("bind", "<module> <key|none> [press|hold|release]");

        this.description = "Binds a module to a key or mouse button.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2 || args.length > 3) throw new IllegalArgumentException();

        final AbstractModule module = Globals.MODULE_MANAGER.getModule(args[0]);
        if (module == null) throw new IllegalArgumentException(String.format("Unknown module \"%s\".", args[0]));

        final int key = parseKey(args[1]);
        final KeyTrigger trigger = args.length == 3 ? parseTrigger(args[2]) : KeyTrigger.PRESS;

        module.setKey(key);
        module.setTrigger(trigger);

        if (key == Keyboard.KEY_NONE) {
            ChatUtil.addMessage(String.format("Unbound %s.", module.getName()));
        } else {
            ChatUtil.addMessage(String.format("Bound %s to %s (%s).", module.getName(), keyName(key), trigger.name().toLowerCase()));
        }
    }

    private static int parseKey(String name) {
        final String upper = name.toUpperCase();

        if (upper.matches("MOUSE\\d")) return Integer.parseInt(upper.substring(5)) - 100;

        final int key = Keyboard.getKeyIndex(upper);
        if (key == Keyboard.KEY_NONE && !upper.equals("NONE")) {
            throw new IllegalArgumentException(String.format("Unknown key \"%s\".", name));
        }
        return key;
    }

    private static KeyTrigger parseTrigger(String name) {
        for (KeyTrigger trigger : KeyTrigger.values()) {
            if (trigger.name().equalsIgnoreCase(name)) return trigger;
        }
        throw new IllegalArgumentException(String.format("Unknown trigger \"%s\".", name));
    }

    private static String keyName(int key) {
        return key < 0 ? "MOUSE" + (key + 100) : Keyboard.getKeyName(key);
    }
}
