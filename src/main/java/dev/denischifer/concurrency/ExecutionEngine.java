package dev.denischifer.concurrency;

import dev.denischifer.io.ChunkIterator;
import dev.denischifer.io.FileReaderService;
import dev.denischifer.math.ProbabilityModel;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.*;

@RequiredArgsConstructor
public class ExecutionEngine {
    private final FileReaderService readerService;
    private final EngineConfig config;

    public ProbabilityModel<Integer> execute(long fileSize) throws InterruptedException {
        ResultAggregator<Integer> aggregator = new ResultAggregator<>();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(config.getQueueCapacity());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                config.getThreadCount(), config.getThreadCount(), 0L, TimeUnit.MILLISECONDS,
                queue, new ThreadPoolExecutor.CallerRunsPolicy()
        );

        ChunkIterator iterator = new ChunkIterator(fileSize, config.getChunkSize());
        try {
            while (iterator.hasNext()) {
                var chunk = readerService.readChunk(iterator.next(), config.getChunkSize());
                executor.execute(new WorkerTask(chunk, aggregator));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);
        }
        return new ProbabilityModel<>(aggregator.build());
    }
}