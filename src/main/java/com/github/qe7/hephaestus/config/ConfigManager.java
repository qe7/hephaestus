package com.github.qe7.hephaestus.config;

import com.github.qe7.hephaestus.core.Globals;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.KeyTrigger;
import com.github.qe7.hephaestus.settings.AbstractSetting;
import com.github.qe7.hephaestus.utils.KeyUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.function.Consumer;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path path = Minecraft.getMinecraftDir().toPath().resolve(Globals.NAME.toLowerCase(Locale.ROOT)).resolve("config.json");

    private JsonObject storedModules = new JsonObject();
    private boolean safeToSave;

    public boolean load() {
        safeToSave = true;

        if (!Files.exists(path)) return false;

        final JsonObject root;
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            root = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            moveBrokenConfig(e);
            return false;
        }

        final JsonObject modules = object(root, "modules");
        if (modules == null) return true;

        storedModules = modules;

        for (AbstractModule module : Globals.MODULE_MANAGER.getModules().values()) {
            final JsonObject json = object(modules, module.getName());
            if (json != null) loadModule(module, json);
        }
        return true;
    }

    public boolean save() {
        if (!safeToSave) return false;

        final Path temp = path.resolveSibling(path.getFileName() + ".tmp");
        try {
            for (AbstractModule module : Globals.MODULE_MANAGER.getModules().values()) {
                storedModules.add(module.getName(), saveModule(module));
            }

            final JsonObject root = new JsonObject();
            root.add("modules", storedModules);

            Files.createDirectories(path.getParent());
            Files.write(temp, GSON.toJson(root).getBytes(StandardCharsets.UTF_8));
            try {
                Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (Exception e) {
            log("Failed to save config: " + e);
            return false;
        }
    }

    private void loadModule(AbstractModule module, JsonObject json) {
        read(module, json, "key", value -> module.setKey(KeyUtil.parseKey(value)));
        read(module, json, "trigger", value -> module.setTrigger(KeyTrigger.parse(value)));

        final JsonObject settings = object(json, "settings");
        if (settings != null) {
            for (AbstractSetting<?> setting : module.getSettings()) {
                read(module, settings, setting.getName(), setting::parse);
            }
        }

        read(module, json, "enabled", value -> {
            if (!value.equals("true") && !value.equals("false")) {
                throw new IllegalArgumentException(String.format("Expected true or false, got \"%s\".", value));
            }
            module.setEnabled(value.equals("true"));
        });
    }

    private static JsonObject saveModule(AbstractModule module) {
        final JsonObject settings = new JsonObject();
        for (AbstractSetting<?> setting : module.getSettings()) {
            final Object value = setting.getValue();
            settings.add(setting.getName(), value instanceof Number || value instanceof Boolean ? GSON.toJsonTree(value) : new JsonPrimitive(setting.format()));
        }

        final JsonObject json = new JsonObject();
        json.addProperty("enabled", module.isEnabled());
        json.addProperty("key", KeyUtil.keyName(module.getKey()));
        json.addProperty("trigger", module.getTrigger().name());
        json.add("settings", settings);
        return json;
    }

    private static void read(AbstractModule module, JsonObject json, String key, Consumer<String> apply) {
        final JsonElement element = json.get(key);
        if (element == null) return;

        try {
            if (!element.isJsonPrimitive()) throw new IllegalArgumentException("Expected a value.");
            apply.accept(element.getAsString());
        } catch (Exception e) {
            log(String.format("Skipped %s.%s: %s", module.getName(), key, e.getMessage()));
        }
    }

    private static JsonObject object(JsonObject json, String key) {
        final JsonElement element = json.get(key);
        return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    private void moveBrokenConfig(Exception cause) {
        final Path broken = path.resolveSibling(path.getFileName() + ".broken");
        try {
            Files.move(path, broken, StandardCopyOption.REPLACE_EXISTING);
            log(String.format("Config was unreadable (%s), moved it to %s.", cause.getMessage(), broken));
        } catch (IOException e) {
            safeToSave = false;
            log(String.format("Config was unreadable (%s) and could not be moved, saving is disabled to protect it.", cause.getMessage()));
        }
    }

    private static void log(String message) {
        System.err.printf("[%s] %s%n", Globals.NAME, message);
    }
}
