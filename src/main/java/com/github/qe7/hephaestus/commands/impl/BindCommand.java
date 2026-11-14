package com.github.qe7.hephaestus.commands.impl;

import com.github.qe7.hephaestus.commands.AbstractCommand;
import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.KeyTrigger;
import com.github.qe7.hephaestus.utils.ChatUtil;
import com.github.qe7.hephaestus.utils.KeyUtil;
import org.lwjgl.input.Keyboard;

import java.util.Locale;

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

        final int key = KeyUtil.parseKey(args[1]);
        final KeyTrigger trigger = args.length == 3 ? KeyTrigger.parse(args[2]) : KeyTrigger.PRESS;

        module.setKey(key);
        module.setTrigger(trigger);

        if (key == Keyboard.KEY_NONE) {
            ChatUtil.addMessage(String.format("Unbound %s.", module.getName()));
        } else {
            ChatUtil.addMessage(String.format("Bound %s to %s (%s).", module.getName(), KeyUtil.keyName(key), trigger.name().toLowerCase(Locale.ROOT)));
        }
    }
}
