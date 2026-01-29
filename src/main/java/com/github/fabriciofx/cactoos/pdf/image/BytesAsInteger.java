/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image;

import org.cactoos.Bytes;
import org.cactoos.Scalar;

/**
 * BytesAsInteger.
 *
 * Convert an array of bytes (4) in an integer.
 *
 * @since 0.0.1
 * @checkstyle BooleanExpressionComplexityCheck (100 lines)
 */
public final class BytesAsInteger implements Scalar<Integer> {
    /**
     * Bytes.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param bytes An array of bytes that represents an integer
     */
    public BytesAsInteger(final byte[] bytes) {
        this(() -> bytes);
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents an integer
     */
    public BytesAsInteger(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public Integer value() throws Exception {
        final byte[] octets = this.bytes.asBytes();
        return ((octets[0] & 0xFF) << 24)
            | ((octets[1] & 0xFF) << 16)
            | ((octets[2] & 0xFF) << 8)
            | (octets[3] & 0xFF);
    }
}
