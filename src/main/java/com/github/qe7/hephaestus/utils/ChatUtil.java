package com.github.qe7.hephaestus.utils;

import com.github.qe7.hephaestus.core.Globals;
import net.minecraft.client.Minecraft;

public final class ChatUtil {
    private ChatUtil() {
    }

    public static void addMessage(String message) {
        Minecraft.get().ingameGUI.addChatMessage(String.format("\u00a78[\u00a76%s\u00a78]\u00a7f %s", Globals.NAME, message));
    }
}
