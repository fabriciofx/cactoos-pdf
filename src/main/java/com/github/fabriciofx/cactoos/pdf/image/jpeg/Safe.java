/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.jpeg;

import com.github.fabriciofx.cactoos.pdf.image.Body;
import com.github.fabriciofx.cactoos.pdf.image.Flow;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import com.github.fabriciofx.cactoos.pdf.image.InvalidFormatException;
import com.github.fabriciofx.cactoos.pdf.image.Palette;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import java.util.Arrays;

/**
 * SafeJpegRaw: Safe decorator for a JPEG image raw.
 *
 * @since 0.0.1
 */
public final class Safe implements Raw {
    /**
     * JPEG file signature (part 1).
     */
    private static final byte[] SIGNATURE_PART1 = {
        (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0,
    };

    /**
     * JPEG file signature (part 2).
     */
    private static final byte[] SIGNATURE_PART2 = {
        (byte) 'J', (byte) 'F', (byte) 'I', (byte) 'F', (byte) 0x00,
    };

    /**
     * JPEG origin.
     */
    private final JpegRaw origin;

    /**
     * Ctor.
     *
     * @param raw Image raw
     */
    public Safe(final JpegRaw raw) {
        this.origin = raw;
    }

    @Override
    public Header header() throws Exception {
        final Header header = this.origin.header();
        final Flow flow = new Flow(header.asBytes());
        if (!Arrays.equals(Safe.SIGNATURE_PART1, flow.asBytes(4))) {
            throw new InvalidFormatException("Not a valid JPEG image file");
        }
        flow.skip(2);
        if (!Arrays.equals(Safe.SIGNATURE_PART2, flow.asBytes(5))) {
            throw new InvalidFormatException("Not a valid JPEG image file");
        }
        return header;
    }

    @Override
    public Body body() throws Exception {
        this.header();
        return this.origin.body();
    }

    @Override
    public Palette palette() throws Exception {
        this.header();
        return this.origin.palette();
    }
}
