package com.github.qe7.hephaestus.events.impl.render;

import com.github.qe7.hephaestus.events.CancellableEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.block.Block;

@RequiredArgsConstructor
@Getter
public final class RenderBlockEvent extends CancellableEvent {
    private final Block block;
    private final int x;
    private final int y;
    private final int z;

    @Setter
    private boolean renderAllFaces;
}
