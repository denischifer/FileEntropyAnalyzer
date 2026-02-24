package dev.denischifer.concurrency;

import dev.denischifer.io.FileChunk;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerTask implements Runnable {
    private final FileChunk chunk;
    private final ResultAggregator<Integer> aggregator;

    @Override
    public void run() {
        byte[] data = chunk.getData();
        for (int i = 0; i < chunk.getLength(); i++) {
            aggregator.add(data[i] & 0xFF);
        }
    }
}