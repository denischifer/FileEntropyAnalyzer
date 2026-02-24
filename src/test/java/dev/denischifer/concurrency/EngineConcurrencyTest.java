package dev.denischifer.concurrency;

import dev.denischifer.io.FileReaderService;
import dev.denischifer.math.ProbabilityModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class EngineConcurrencyTest {

    @TempDir
    Path tempDir;

    @Test
    void testMultiThreadConsistency() throws Exception {
        File file = tempDir.resolve("concurrency.bin").toFile();
        byte[] data = new byte[1024 * 1024];
        new Random().nextBytes(data);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }

        FileReaderService reader = new FileReaderService(file.getAbsolutePath());

        EngineConfig singleConfig = EngineConfig.builder()
                .threadCount(1).chunkSize(1024).queueCapacity(10).build();
        ExecutionEngine singleEngine = new ExecutionEngine(reader, singleConfig);
        ProbabilityModel<Integer> singleModel = singleEngine.execute(file.length());

        EngineConfig multiConfig = EngineConfig.builder()
                .threadCount(8).chunkSize(1024).queueCapacity(10).build();
        ExecutionEngine multiEngine = new ExecutionEngine(reader, multiConfig);
        ProbabilityModel<Integer> multiModel = multiEngine.execute(file.length());

        assertEquals(singleModel.getTotalCount(), multiModel.getTotalCount());
        for (int i = 0; i < 256; i++) {
            assertEquals(singleModel.getFrequencies().get(i), multiModel.getFrequencies().get(i));
        }
    }
}