package dev.denischifer.algorithms;

import dev.denischifer.io.FileReaderService;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SlidingWindowEntropy {
    private final FileReaderService reader;

    public List<Double> analyze(long fileSize, int windowSize, int step) throws Exception {
        List<Double> results = new ArrayList<>();
        ShannonEntropy shannon = new ShannonEntropy();
        for (long i = 0; i <= fileSize - windowSize; i += step) {
            var chunk = reader.readChunk(i, windowSize);
            java.util.Map<Integer, Long> freqs = new java.util.HashMap<>();
            for (byte b : chunk.getData()) freqs.merge(b & 0xFF, 1L, Long::sum);
            results.add(shannon.calculate(new dev.denischifer.math.ProbabilityModel<>(freqs)));
        }
        return results;
    }
}