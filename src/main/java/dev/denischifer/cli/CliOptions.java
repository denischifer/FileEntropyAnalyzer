package dev.denischifer.cli;

import dev.denischifer.core.EntropyType;
import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;

@Getter
public class CliOptions {
    @Parameters(index = "0")
    private File file;

    @Option(names = {"-m", "--mode"}, defaultValue = "SHANNON")
    private EntropyType mode;

    @Option(names = {"-t", "--threads"}, defaultValue = "4")
    private int threads;

    @Option(names = {"-s", "--size"}, defaultValue = "1048576")
    private int chunkSize;

    @Option(names = {"--histogram"}, description = "Show byte distribution")
    private boolean showHistogram;

    @Option(names = {"--csv"}, description = "Export results to CSV file")
    private String csvPath;
}