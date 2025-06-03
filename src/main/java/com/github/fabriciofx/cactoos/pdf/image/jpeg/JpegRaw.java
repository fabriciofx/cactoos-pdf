/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.jpeg;

import com.github.fabriciofx.cactoos.pdf.image.Body;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import com.github.fabriciofx.cactoos.pdf.image.Palette;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.Bytes;

/**
 * JpegRaw.
 *
 * @since 0.0.1
 */
public final class JpegRaw implements Raw {
    /**
     * JPEG header.
     */
    private final Header hdr;

    /**
     * JPEG body.
     */
    private final Body bdy;

    /**
     * Ctor.
     *
     * @param filename File name of a JPEG image
     */
    public JpegRaw(final String filename) {
        this(() -> Files.readAllBytes(new File(filename).toPath()));
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a JPEG image
     */
    public JpegRaw(final Bytes bytes) {
        this.hdr = new JpegHeader(bytes);
        this.bdy = new JpegBody(bytes);
    }

    @Override
    public Header header() throws Exception {
        return this.hdr;
    }

    @Override
    public Body body() throws Exception {
        return this.bdy;
    }

    @Override
    public Palette palette() throws Exception {
        throw new IllegalStateException("JPEG image has not a palette");
    }
}
