package dev.denischifer.cli;

import dev.denischifer.algorithms.SlidingWindowEntropy;
import dev.denischifer.concurrency.EngineConfig;
import dev.denischifer.concurrency.ExecutionEngine;
import dev.denischifer.core.EntropyFactory;
import dev.denischifer.core.EntropyResult;
import dev.denischifer.io.FileReaderService;
import dev.denischifer.util.MemoryUtils;
import dev.denischifer.util.Stopwatch;
import dev.denischifer.visualization.*;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.HashMap;
import java.util.concurrent.Callable;

@Command(name = "entropy")
public class EntropyCommand implements Callable<Integer> {
    @Mixin
    private CliOptions options;

    @Override
    public Integer call() throws Exception {
        Stopwatch sw = new Stopwatch();
        sw.start();

        FileReaderService reader = new FileReaderService(options.getFile().getAbsolutePath());

        if (options.getMode() == dev.denischifer.core.EntropyType.SLIDING_WINDOW) {
            SlidingWindowEntropy swe = new SlidingWindowEntropy(reader);
            var results = swe.analyze(options.getFile().length(), 1024, 512);
            new HeatmapRenderer().render(results);
            if (options.getCsvPath() != null) new ExportService().toCsv(options.getCsvPath(), results);
        } else {
            EngineConfig config = EngineConfig.builder()
                    .threadCount(options.getThreads())
                    .chunkSize(options.getChunkSize())
                    .queueCapacity(options.getThreads() * 2)
                    .build();

            ExecutionEngine engine = new ExecutionEngine(reader, config);
            var model = engine.execute(options.getFile().length());

            var calc = EntropyFactory.get(options.getMode(), new HashMap<>());
            double val = calc.calculate(model);

            new ConsoleReport().print(EntropyResult.builder()
                    .type(options.getMode())
                    .value(val)
                    .fileName(options.getFile().getName())
                    .executionTimeMs(sw.stop())
                    .build());

            if (options.isShowHistogram()) {
                new HistogramRenderer().render(model.getFrequencies());
            }
        }

        System.out.println("Memory: " + MemoryUtils.getUsedMemory() / 1024 / 1024 + " MB");
        return 0;
    }
}