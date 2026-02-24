package dev.denischifer.math;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StatisticsUtils {
    public static <T> double calculateShannon(ProbabilityModel<T> model) {
        if (model.getTotalCount() == 0) return 0.0;
        return model.getFrequencies().keySet().stream()
                .mapToDouble(symbol -> {
                    double p = model.getProbability(symbol);
                    return p > 0 ? -p * LogMath.log2(p) : 0;
                })
                .sum();
    }
}