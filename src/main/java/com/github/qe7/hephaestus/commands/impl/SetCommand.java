package com.github.qe7.hephaestus.commands.impl;

import com.github.qe7.hephaestus.commands.AbstractCommand;
import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.settings.AbstractSetting;
import com.github.qe7.hephaestus.settings.impl.ModeSetting;
import com.github.qe7.hephaestus.settings.impl.NumberSetting;
import com.github.qe7.hephaestus.utils.ChatUtil;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public final class SetCommand extends AbstractCommand {

    public SetCommand() {
        super("set", "<module> [setting] [value]");

        this.description = "Shows or changes a module's settings.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1 || args.length > 3) throw new IllegalArgumentException();

        final AbstractModule module = Globals.MODULE_MANAGER.getModule(args[0]);
        if (module == null) throw new IllegalArgumentException(String.format("Unknown module \"%s\".", args[0]));

        if (args.length == 1) {
            if (module.getSettings().isEmpty()) {
                ChatUtil.addMessage(String.format("%s has no settings.", module.getName()));
                return;
            }
            for (AbstractSetting<?> setting : module.getSettings()) {
                ChatUtil.addMessage(describe(setting));
            }
            return;
        }

        final AbstractSetting<?> setting = module.getSetting(args[1]);
        if (setting == null) {
            throw new IllegalArgumentException(String.format("Unknown setting \"%s\" for %s.", args[1], module.getName()));
        }

        if (args.length == 3) setting.parse(args[2]);

        ChatUtil.addMessage(describe(setting));
    }

    private static String describe(AbstractSetting<?> setting) {
        final StringBuilder text = new StringBuilder(setting.getName()).append(": ").append(format(setting.getValue()));

        if (setting instanceof NumberSetting) {
            final NumberSetting<?> number = (NumberSetting<?>) setting;
            text.append(String.format(" (%s - %s)", number.getMin(), number.getMax()));
        }
        if (setting instanceof ModeSetting) {
            final String modes = Arrays.stream(((ModeSetting<?>) setting).getModes()).map(SetCommand::format).collect(Collectors.joining(", "));
            text.append(String.format(" [%s]", modes));
        }
        return text.toString();
    }

    private static String format(Object value) {
        return value instanceof Enum ? ((Enum<?>) value).name().toLowerCase(Locale.ROOT) : String.valueOf(value);
    }
}
