package dev.denischifer.algorithms;

import dev.denischifer.core.EntropyCalculator;
import dev.denischifer.math.LogMath;
import dev.denischifer.math.ProbabilityModel;

public class MinEntropy implements EntropyCalculator {
    @Override
    public double calculate(ProbabilityModel<?> model) {
        double maxP = model.getFrequencies().values().stream()
                .mapToDouble(count -> (double) count / model.getTotalCount())
                .max().orElse(0.0);
        return maxP > 0 ? -LogMath.log2(maxP) : 0.0;
    }
}