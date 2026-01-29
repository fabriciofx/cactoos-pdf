/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.jpeg;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Body;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * JpegBody.
 *
 * @since 0.0.1
 */
public final class JpegBody implements Body {
    /**
     * JPEG image body.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a JPEG image (all content)
     */
    public JpegBody(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] asStream() throws Exception {
        return this.bytes.asBytes();
    }

    @Override
    public List<Resource> resource() {
        return new ListOf<>();
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        throw new UnsupportedOperationException(
            "there is not indirect in jpeg body"
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
