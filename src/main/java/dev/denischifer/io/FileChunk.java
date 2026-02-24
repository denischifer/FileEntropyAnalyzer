package dev.denischifer.io;

import lombok.Value;

@Value
public class FileChunk {
    long offset;
    byte[] data;
    int length;
}