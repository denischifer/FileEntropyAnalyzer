package dev.denischifer.cli;

import dev.denischifer.core.EntropyType;
import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;

@Getter
public class CliOptions {
    @Parameters(index = "0", description = "Target file for analysis")
    private File file;

    @Option(names = {"-m", "--mode"}, defaultValue = "SHANNON", description = "Modes: SHANNON, BLOCK, CONDITIONAL, MIN_ENTROPY, SLIDING_WINDOW")
    private EntropyType mode;

    @Option(names = {"-t", "--threads"}, defaultValue = "4")
    private int threads;

    @Option(names = {"-s", "--size"}, defaultValue = "1048576", description = "Chunk size for parallel processing")
    private int chunkSize;

    @Option(names = {"-n", "--n-gram"}, defaultValue = "1", description = "N-gram size for block or conditional entropy")
    private int nGramSize;

    @Option(names = {"--step"}, defaultValue = "512", description = "Step for sliding window analysis")
    private int windowStep;

    @Option(names = {"--window-size"}, defaultValue = "1024")
    private int windowSize;

    @Option(names = {"--histogram"}, description = "Show byte distribution")
    private boolean showHistogram;

    @Option(names = {"--csv"}, description = "Export results to CSV file")
    private String csvPath;
}