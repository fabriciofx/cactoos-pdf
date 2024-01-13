/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023-2024 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
public final class SafeJpegRaw implements Raw {
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
    public SafeJpegRaw(final JpegRaw raw) {
        this.origin = raw;
    }

    @Override
    public Header header() throws Exception {
        final Header header = this.origin.header();
        final Flow flow = new Flow(header.asBytes());
        if (!Arrays.equals(SafeJpegRaw.SIGNATURE_PART1, flow.asBytes(4))) {
            throw new InvalidFormatException("Not a valid JPEG image file");
        }
        flow.skip(2);
        if (!Arrays.equals(SafeJpegRaw.SIGNATURE_PART2, flow.asBytes(5))) {
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
