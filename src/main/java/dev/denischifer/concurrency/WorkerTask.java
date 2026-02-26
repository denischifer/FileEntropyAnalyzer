package dev.denischifer.concurrency;

import dev.denischifer.io.FileChunk;
import dev.denischifer.math.ByteSequence;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@RequiredArgsConstructor
public class WorkerTask implements Runnable {
    private final FileChunk chunk;
    private final ResultAggregator<ByteSequence> aggregator;
    private final int nGramSize;

    @Override
    public void run() {
        byte[] data = chunk.getData();
        for (int i = 0; i <= chunk.getLength() - nGramSize; i++) {
            byte[] nGram = Arrays.copyOfRange(data, i, i + nGramSize);
            aggregator.add(new ByteSequence(nGram));
        }
    }
}