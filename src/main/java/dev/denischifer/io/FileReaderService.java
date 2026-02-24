package dev.denischifer.io;

import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.io.RandomAccessFile;

@RequiredArgsConstructor
public class FileReaderService {
    private final String filePath;

    public FileChunk readChunk(long offset, int size) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek(offset);
            long remaining = file.length() - offset;
            int bytesToRead = (int) Math.min(size, remaining);
            byte[] buffer = new byte[bytesToRead];
            int read = file.read(buffer);
            return new FileChunk(offset, buffer, read);
        }
    }
}