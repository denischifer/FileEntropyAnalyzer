package dev.denischifer.visualization;

import java.util.List;

public class HeatmapRenderer {
    public void render(List<Double> values) {
        String chars = " .:-=+*#%@";
        for (double v : values) {
            int idx = (int) (Math.min(v / 8.0, 0.99) * chars.length());
            System.out.print(chars.charAt(idx));
        }
        System.out.println();
    }
}