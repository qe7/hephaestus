package net.minecraft.world.chunk;

import net.minecraft.util.IProgressUpdate;

public interface IChunkProvider {
    boolean chunkExists(int i, int j);

    Chunk provideChunk(int i, int j);

    Chunk prepareChunk(int i, int j);

    void populate(IChunkProvider ichunkprovider, int i, int j);

    boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate);

    boolean unload100OldestChunks();

    boolean canSave();

    String makeString();
}
