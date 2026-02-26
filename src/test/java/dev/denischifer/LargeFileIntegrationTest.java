package dev.denischifer;

import dev.denischifer.algorithms.ShannonEntropy;
import dev.denischifer.concurrency.EngineConfig;
import dev.denischifer.concurrency.ExecutionEngine;
import dev.denischifer.io.FileReaderService;
import dev.denischifer.math.ByteSequence;
import dev.denischifer.math.ProbabilityModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LargeFileIntegrationTest {

    @TempDir
    Path tempDir;

    @Test
    void testEndToEndAnalysis() throws Exception {
        File file = tempDir.resolve("large_file.dat").toFile();
        long size = 10 * 1024 * 1024 + 12345;
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.setLength(size);
            raf.seek(0);
            raf.write("START".getBytes());
            raf.seek(size - 3);
            raf.write("END".getBytes());
        }

        FileReaderService reader = new FileReaderService(file.getAbsolutePath());
        EngineConfig config = EngineConfig.builder()
                .threadCount(4)
                .chunkSize(512 * 1024)
                .queueCapacity(20)
                .build();

        ExecutionEngine engine = new ExecutionEngine(reader, config);
        int nGramSize = 1;
        ProbabilityModel<ByteSequence> model = engine.execute(file.length(), nGramSize);

        assertEquals(size, model.getTotalCount());

        ShannonEntropy shannon = new ShannonEntropy();
        double entropy = shannon.calculate(model);

        assertTrue(entropy >= 0 && entropy <= 8);

        ByteSequence startChar = new ByteSequence(new byte[]{(byte) 'S'});
        ByteSequence endChar = new ByteSequence(new byte[]{(byte) 'D'});

        assertTrue(model.getFrequencies().containsKey(startChar));
        assertTrue(model.getFrequencies().containsKey(endChar));
    }
}