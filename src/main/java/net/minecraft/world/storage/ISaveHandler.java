package net.minecraft.world.storage;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import java.io.File;
import java.util.List;

public interface ISaveHandler {
    WorldInfo loadWorldInfo();

    void func_22150_b();

    IChunkLoader getChunkLoader(WorldProvider worldprovider);

    void saveWorldInfoAndPlayer(WorldInfo worldinfo, List list);

    void saveWorldInfo(WorldInfo worldinfo);

    File func_28113_a(String s);
}
