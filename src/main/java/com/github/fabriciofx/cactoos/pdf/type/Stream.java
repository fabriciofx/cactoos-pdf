/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.cactoos.Bytes;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.FormattedText;

/**
 * Stream.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.StringInstantiation")
public final class Stream implements Type<byte[]> {
    /**
     * Value.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param bytes An array of bytes
     */
    public Stream(final byte[] bytes) {
        this(new BytesOf(bytes));
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes
     */
    public Stream(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] value() throws Exception {
        return this.bytes.asBytes();
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("\nstream\n".getBytes(StandardCharsets.UTF_8));
        baos.write(this.bytes.asBytes());
        baos.write("\nendstream".getBytes(StandardCharsets.UTF_8));
        return baos.toByteArray();
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText(
            "\nstream\n%s\nendstream",
            Locale.ENGLISH,
            new String(this.bytes.asBytes())
        ).asString();
    }

    /**
     * Stream's length.
     *
     * @return Amount of stream bytes
     * @throws Exception if fails
     */
    public int length() throws Exception {
        return this.bytes.asBytes().length;
    }
}
