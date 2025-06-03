/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.image.Body;
import com.github.fabriciofx.cactoos.pdf.image.Flow;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import com.github.fabriciofx.cactoos.pdf.image.InvalidFormatException;
import com.github.fabriciofx.cactoos.pdf.image.Palette;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import java.util.Arrays;

/**
 * SafePngRaw: Safe decorator for a PNG image raw.
 *
 * @since 0.0.1
 */
public final class Safe implements Raw {
    /**
     * PNG file signature.
     */
    private static final byte[] SIGNATURE = {
        (byte) 137, 'P', 'N', 'G', '\r', '\n', 26, '\n',
    };

    /**
     * Raw origin.
     */
    private final PngRaw origin;

    /**
     * Ctor.
     *
     * @param raw Image raw
     */
    public Safe(final PngRaw raw) {
        this.origin = raw;
    }

    @Override
    public Header header() throws Exception {
        final Header header = this.origin.header();
        final Flow flow = new Flow(header.asBytes());
        if (!Arrays.equals(Safe.SIGNATURE, flow.asBytes(8))) {
            throw new InvalidFormatException("Not a PNG image file");
        }
        flow.skip(4);
        if (!"IHDR".equals(flow.asString(4))) {
            throw new InvalidFormatException("Incorrect PNG image file");
        }
        if (header.depth() > 8) {
            throw new InvalidFormatException(
                "16-bit depth in PNG file not supported"
            );
        }
        if ("Unknown".equals(header.color().space())) {
            throw new InvalidFormatException("Unknown color type");
        }
        if (header.compression() != 0) {
            throw new InvalidFormatException("Unknown compression method");
        }
        if (header.filter() != 0) {
            throw new InvalidFormatException("Unknown filter method");
        }
        if (header.interlacing() != 0) {
            throw new InvalidFormatException("Interlacing not supported");
        }
        return this.origin.header();
    }

    @Override
    public Body body() throws Exception {
        this.header();
        return this.origin.body();
    }

    @Override
    public Palette palette() throws Exception {
        final Header header = this.header();
        final Indirect indirect = this.origin.palette().indirect();
        if ("Indexed".equals(header.color().space())
            && indirect.asBytes().length == 0) {
            throw new InvalidFormatException("Missing palette in PNG file");
        }
        return this.origin.palette();
    }
}
