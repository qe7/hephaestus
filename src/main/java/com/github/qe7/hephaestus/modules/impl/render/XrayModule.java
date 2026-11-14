package com.github.qe7.hephaestus.modules.impl.render;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.TickEvent;
import com.github.qe7.hephaestus.events.impl.render.RenderBlockEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import com.github.qe7.hephaestus.settings.impl.ListSetting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

import java.util.Arrays;
import java.util.Set;

public final class XrayModule extends AbstractModule {
    private final ListSetting<Block> blocks = ListSetting.blocks("Blocks",
            Block.oreCoal, Block.oreIron, Block.oreGold, Block.oreDiamond, Block.oreRedstone, Block.oreRedstoneGlowing,
            Block.oreLapis, Block.mobSpawner, Block.chest);

    private Set<Block> renderedBlocks;

    public XrayModule() {
        super("Xray", ModuleCategory.RENDER);

        this.description = "Only renders the chosen blocks.";
    }

    @EventSubscriber
    private final Listener<RenderBlockEvent> renderBlockListener = event -> {
        if (blocks.contains(event.getBlock())) {
            event.setRenderAllFaces(true);
        } else {
            event.setCancelled(true);
        }
    };

    @EventSubscriber
    private final Listener<TickEvent> tickListener = event -> {
        final Minecraft mc = Minecraft.get();
        if (mc.theWorld == null) return;

        final float[] table = mc.theWorld.worldProvider.lightBrightnessTable;
        if (table[0] == 1.0F && blocks.getValue() == renderedBlocks) return;

        Arrays.fill(table, 1.0F);
        renderedBlocks = blocks.getValue();
        mc.renderGlobal.loadRenderers();
    };

    @Override
    protected void onDisable() {
        renderedBlocks = null;

        final Minecraft mc = Minecraft.get();
        if (mc == null || mc.theWorld == null) return;

        mc.theWorld.worldProvider.generateLightBrightnessTable();
        mc.renderGlobal.loadRenderers();
    }
}
