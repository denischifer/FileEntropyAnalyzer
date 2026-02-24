package dev.denischifer.visualization;

import java.io.PrintWriter;
import java.util.List;

public class ExportService {
    public void toCsv(String path, List<Double> data) {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.println("index,entropy");
            for (int i = 0; i < data.size(); i++) {
                writer.printf("%d,%.6f%n", i, data.get(i));
            }
        } catch (Exception e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }
}