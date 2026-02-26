package dev.denischifer.cli;

import dev.denischifer.algorithms.SlidingWindowEntropy;
import dev.denischifer.concurrency.EngineConfig;
import dev.denischifer.concurrency.ExecutionEngine;
import dev.denischifer.core.EntropyFactory;
import dev.denischifer.core.EntropyResult;
import dev.denischifer.core.EntropyType;
import dev.denischifer.io.FileReaderService;
import dev.denischifer.math.ByteSequence;
import dev.denischifer.util.MemoryUtils;
import dev.denischifer.util.Stopwatch;
import dev.denischifer.visualization.*;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.Map;
import java.util.concurrent.Callable;

import static dev.denischifer.core.EntropyType.*;

@Command(name = "entropy", mixinStandardHelpOptions = true, version = "1.0")
public class EntropyCommand implements Callable<Integer> {
    @Mixin
    private CliOptions options;

    @Override
    public Integer call() throws Exception {
        Stopwatch sw = new Stopwatch();
        sw.start();

        FileReaderService reader = new FileReaderService(options.getFile().getAbsolutePath());
        EntropyType mode = options.getMode();

        if (mode == SLIDING_WINDOW) {
            SlidingWindowEntropy swe = new SlidingWindowEntropy(reader);
            var results = swe.analyze(options.getFile().length(), options.getWindowSize(), options.getWindowStep());
            new HeatmapRenderer().render(results);
            if (options.getCsvPath() != null) {
                new ExportService().toCsv(options.getCsvPath(), results);
            }
        } else {
            int nSize = (mode == CONDITIONAL) ? 2 : options.getNGramSize();

            EngineConfig config = EngineConfig.builder()
                    .threadCount(options.getThreads())
                    .chunkSize(options.getChunkSize())
                    .queueCapacity(options.getThreads() * 2)
                    .build();

            ExecutionEngine engine = new ExecutionEngine(reader, config);
            var model = engine.execute(options.getFile().length(), nSize);

            var calc = EntropyFactory.get(mode, Map.of("blockSize", nSize));
            double val = calc.calculate(model);

            new ConsoleReport().print(EntropyResult.builder()
                    .type(mode)
                    .value(val)
                    .fileName(options.getFile().getName())
                    .executionTimeMs(sw.stop())
                    .build());

            @SuppressWarnings("unchecked")
            Map<ByteSequence, Long> freqs = model.getFrequencies();

            if (options.isShowHistogram() && nSize == 1) {
                new HistogramRenderer().render(freqs);
            }

            if (options.getCsvPath() != null) {
                new ExportService().toCsv(options.getCsvPath(), freqs);
            }
        }

        System.out.println("Memory: " + MemoryUtils.getUsedMemory() / 1024 / 1024 + " MB");
        return 0;
    }
}