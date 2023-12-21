package com.github.fabriciofx.cactoos.pdf.png;

import org.cactoos.Bytes;
import org.cactoos.Scalar;

public final class BytesAsInteger implements Scalar<Integer> {
    private final Bytes bytes;

    public BytesAsInteger(final byte[] bytes) {
        this(() -> bytes);
    }

    public BytesAsInteger(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public Integer value() throws Exception {
        final byte[] raw = this.bytes.asBytes();
        return ((raw[0] & 0xFF) << 24) |
            ((raw[1] & 0xFF) << 16) |
            ((raw[2] & 0xFF) << 8) |
            (raw[3] & 0xFF);
    }
}
