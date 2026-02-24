package dev.denischifer.math;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogMath {
    public static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }
}