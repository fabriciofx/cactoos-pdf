/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import org.cactoos.text.FormattedText;

/**
 * Flow.
 *
 * @since 0.0.1
 * @checkstyle BooleanExpressionComplexityCheck (150 lines)
 */
@SuppressWarnings("PMD.AvoidUsingShortType")
public final class Flow {
    /**
     * A stream of bytes.
     */
    private final ByteArrayInputStream stream;

    /**
     * Ctor.
     *
     * @param bytes An array of bytes
     */
    public Flow(final byte[] bytes) {
        this(new ByteArrayInputStream(bytes));
    }

    /**
     * Ctor.
     *
     * @param stream A stream of bytes
     */
    public Flow(final ByteArrayInputStream stream) {
        this.stream = stream;
    }

    /**
     * Read length bytes and return a String contains these bytes.
     *
     * @param length Amount of bytes to read
     * @return The String
     * @throws Exception if fails
     */
    public String asString(final int length) throws Exception {
        return new String(readNBytes(this.stream, length));
    }

    /**
     * Read length bytes and return an array of bytes.
     *
     * @param length Amount of bytes to read
     * @return An array of bytes
     * @throws Exception if fails
     */
    public byte[] asBytes(final int length) throws Exception {
        return readNBytes(this.stream, length);
    }

    /**
     * Read 4 bytes and return an integer that represents these bytes.
     *
     * @return An integer number
     * @throws Exception if fails
     */
    public int asInt() throws Exception {
        final byte[] bytes = this.asBytes(4);
        return ((bytes[0] & 0xFF) << 24)
            | ((bytes[1] & 0xFF) << 16)
            | ((bytes[2] & 0xFF) << 8)
            | (bytes[3] & 0xFF);
    }

    /**
     * Read 2 bytes and return a short that represents these bytes.
     *
     * @return A short number
     * @throws Exception if fails
     */
    public short asShort() throws Exception {
        final byte[] bytes = this.asBytes(2);
        return ByteBuffer.wrap(bytes).getShort();
    }

    /**
     * Read a unique byte.
     *
     * @return A byte
     * @throws Exception if fails
     */
    public byte asByte() throws Exception {
        return this.asBytes(1)[0];
    }

    /**
     * Skip length bytes.
     *
     * @param length Amount of bytes to skip
     * @return Amount of skipped bytes
     * @throws Exception if fails
     */
    public long skip(final int length) throws Exception {
        return this.stream.skip(length);
    }

    /**
     * Search for a sequence of bytes.
     *
     * @param marker Bytes to search for
     * @throws Exception if fails
     */
    public void search(final byte[] marker) throws Exception {
        while (true) {
            final byte[] bytes = readNBytes(this.stream, marker.length);
            if (Arrays.equals(bytes, marker)) {
                break;
            }
        }
    }

    /**
     * Read n bytes from a ByteArrayInputStream.
     *
     * @param bais A ByteArrayInputStream
     * @param length Amount of bytes to read
     * @return Bytes read
     * @throws Exception if fails
     */
    private static byte[] readNBytes(
        final ByteArrayInputStream bais,
        final int length
    ) throws Exception {
        final byte[] buffer = new byte[length];
        final int read = bais.read(buffer);
        if (read != length) {
            throw new IOException(
                new FormattedText(
                    "Amount of bytes read (%d) is different of specified (%d)",
                    Locale.ENGLISH,
                    read,
                    length
                ).asString()
            );
        }
        return buffer;
    }
}
