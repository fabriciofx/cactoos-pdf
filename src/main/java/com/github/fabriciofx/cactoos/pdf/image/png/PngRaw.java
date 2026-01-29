/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.image.Body;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import com.github.fabriciofx.cactoos.pdf.image.Palette;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.Bytes;

/**
 * PngRaw: Raw for a PNG image.
 *
 * @since 0.0.1
 */
public final class PngRaw implements Raw {
    /**
     * PNG Header.
     */
    private final Header hdr;

    /**
     * PNG Body.
     */
    private final Body bdy;

    /**
     * PNG Palette.
     */
    private final Palette pal;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param filename File name of a PNG image
     */
    public PngRaw(final Id id, final String filename) {
        this(id, () -> Files.readAllBytes(new File(filename).toPath()));
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param bytes Bytes that represents a PNG image
     */
    public PngRaw(final Id id, final Bytes bytes) {
        this.hdr = new PngHeader(bytes);
        this.bdy = new PngBody(bytes);
        this.pal = new PngPalette(id, bytes);
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
        return this.pal;
    }
}
