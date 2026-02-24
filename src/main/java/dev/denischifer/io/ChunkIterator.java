package dev.denischifer.io;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChunkIterator implements Iterator<Long> {
    private final long fileSize;
    private final int chunkSize;
    private long currentOffset = 0;

    public ChunkIterator(long fileSize, int chunkSize) {
        this.fileSize = fileSize;
        this.chunkSize = chunkSize;
    }

    @Override
    public boolean hasNext() {
        return currentOffset < fileSize;
    }

    @Override
    public Long next() {
        if (!hasNext()) throw new NoSuchElementException();
        long offset = currentOffset;
        currentOffset += chunkSize;
        return offset;
    }
}