package dev.denischifer.io;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TextAnalysisService {
    public List<Integer> toCodePoints(byte[] data) {
        String text = new String(data, StandardCharsets.UTF_8);
        return text.codePoints().boxed().toList();
    }
}