package dev.denischifer.algorithms;

import dev.denischifer.core.EntropyCalculator;
import dev.denischifer.math.ByteSequence;
import dev.denischifer.math.LogMath;
import dev.denischifer.math.ProbabilityModel;
import java.util.HashMap;
import java.util.Map;

public class ConditionalEntropy implements EntropyCalculator {
    @Override
    public double calculate(ProbabilityModel<?> model) {
        Map<ByteSequence, Long> frequencies = (Map<ByteSequence, Long>) model.getFrequencies();
        Map<ByteSequence, Long> prefixFrequencies = new HashMap<>();

        for (var entry : frequencies.entrySet()) {
            byte[] full = entry.getKey().data();
            ByteSequence prefix = new ByteSequence(new byte[]{full[0]});
            prefixFrequencies.merge(prefix, entry.getValue(), Long::sum);
        }

        long totalJoint = model.getTotalCount();
        double conditionalEntropy = 0.0;

        for (var entry : frequencies.entrySet()) {
            double pJoint = (double) entry.getValue() / totalJoint;
            ByteSequence prefix = new ByteSequence(new byte[]{entry.getKey().data()[0]});
            double pCondition = (double) prefixFrequencies.get(prefix) / totalJoint;

            conditionalEntropy -= pJoint * LogMath.log2(pJoint / pCondition);
        }

        return conditionalEntropy;
    }
}