package dev.denischifer.visualization;

import dev.denischifer.math.ByteSequence;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExportService {

    public void toCsv(String path, List<Double> data) {
        Path targetPath = resolvePath(path);
        try (PrintWriter writer = new PrintWriter(targetPath.toFile())) {
            writer.println("index,entropy");
            for (int i = 0; i < data.size(); i++) {
                writer.printf("%d,%.6f%n", i, data.get(i));
            }
            System.out.println("Results exported to: " + targetPath.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }

    public void toCsv(String path, Map<ByteSequence, Long> data) {
        Path targetPath = resolvePath(path);
        try (PrintWriter writer = new PrintWriter(targetPath.toFile())) {
            writer.println("sequence,frequency");
            data.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        byte[] bytes = entry.getKey().data();
                        String label = (bytes.length == 1)
                                ? String.format("0x%02X", bytes[0])
                                : Arrays.toString(bytes).replace(",", "");
                        writer.printf("%s,%d%n", label, entry.getValue());
                    });
            System.out.println("Frequencies exported to: " + targetPath.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }

    private Path resolvePath(String path) {
        try {
            Path targetPath = Paths.get(path);
            if (Files.isDirectory(targetPath) || path.endsWith("/") || path.endsWith("\\") || path.equals(".")) {
                targetPath = targetPath.resolve("entropy_report.csv");
            }
            Path parent = targetPath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            return targetPath;
        } catch (Exception e) {
            throw new RuntimeException("Invalid path: " + path, e);
        }
    }
}