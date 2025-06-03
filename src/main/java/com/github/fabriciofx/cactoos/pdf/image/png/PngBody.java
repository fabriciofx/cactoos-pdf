/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Body;
import com.github.fabriciofx.cactoos.pdf.image.Flow;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;

/**
 * PngBody: Represents a PNG image body.
 *
 * @since 0.0.1
 */
public final class PngBody implements Body {
    /**
     * PNG image body.
     */
    private final Scalar<byte[]> bytes;

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a PNG image body
     */
    public PngBody(final Bytes bytes) {
        this(
            new Sticky<>(
                () -> {
                    final Flow flow = new Flow(bytes.asBytes());
                    final ByteArrayOutputStream body =
                        new ByteArrayOutputStream();
                    int len;
                    flow.skip(33);
                    do {
                        len = flow.asInt();
                        final String type = flow.asString(4);
                        if ("IDAT".equals(type)) {
                            body.write(flow.asBytes(len));
                            flow.skip(4);
                        } else if ("IEND".equals(type)) {
                            break;
                        } else {
                            flow.skip(len + 4);
                        }
                    } while (len > 0);
                    return body.toByteArray();
                }
            )
        );
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a PNG image body
     */
    public PngBody(final Scalar<byte[]> bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] asStream() throws Exception {
        return this.bytes.value();
    }

    @Override
    public List<Resource> resource() {
        return new ListOf<>();
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        throw new UnsupportedOperationException(
            "There is no indirect into PNG body"
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        // Empty of purpose.
    }
}
