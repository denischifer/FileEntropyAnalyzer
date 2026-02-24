package dev.denischifer.algorithms;

import dev.denischifer.math.ProbabilityModel;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class EntropyAlgorithmTest {

    @Test
    void testShannonZeroEntropy() {
        Map<Integer, Long> freqs = new HashMap<>();
        freqs.put((int) 'A', 100L);
        ProbabilityModel<Integer> model = new ProbabilityModel<>(freqs);
        ShannonEntropy shannon = new ShannonEntropy();
        assertEquals(0.0, shannon.calculate(model), 1e-9);
    }

    @Test
    void testShannonMaxEntropy() {
        Map<Integer, Long> freqs = new HashMap<>();
        for (int i = 0; i < 256; i++) freqs.put(i, 1L);
        ProbabilityModel<Integer> model = new ProbabilityModel<>(freqs);
        ShannonEntropy shannon = new ShannonEntropy();
        assertEquals(8.0, shannon.calculate(model), 1e-9);
    }

    @Test
    void testMinEntropy() {
        Map<Integer, Long> freqs = new HashMap<>();
        freqs.put(1, 1L);
        freqs.put(2, 3L);
        ProbabilityModel<Integer> model = new ProbabilityModel<>(freqs);
        MinEntropy minEntropy = new MinEntropy();
        double expected = -(Math.log(0.75) / Math.log(2));
        assertEquals(expected, minEntropy.calculate(model), 1e-9);
    }

    @Test
    void testBlockEntropyConsistency() {
        byte[] data = "AAAAABBBBB".getBytes();
        Map<java.nio.ByteBuffer, Long> freqs = new HashMap<>();
        freqs.put(java.nio.ByteBuffer.wrap(new byte[]{'A'}), 5L);
        freqs.put(java.nio.ByteBuffer.wrap(new byte[]{'B'}), 5L);

        BlockEntropy blockEntropy = new BlockEntropy(1);
        double result = blockEntropy.calculate(new ProbabilityModel<>(freqs));
        assertEquals(1.0, result, 1e-9);
    }
}