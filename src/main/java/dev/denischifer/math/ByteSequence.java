package dev.denischifer.math;

import java.util.Arrays;

public record ByteSequence(byte[] data) implements Comparable<ByteSequence> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.equals(data, ((ByteSequence) o).data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public int compareTo(ByteSequence other) {
        return Arrays.compare(this.data, other.data);
    }
}