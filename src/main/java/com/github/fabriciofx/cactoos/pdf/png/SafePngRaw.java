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
package com.github.fabriciofx.cactoos.pdf.png;

import com.github.fabriciofx.cactoos.pdf.Flow;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import java.util.Arrays;

/**
 * SafePngRaw: Safe decorator for a PNG image raw.
 *
 * @since 0.0.1
 */
public final class SafePngRaw implements Raw {
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
    public SafePngRaw(final PngRaw raw) {
        this.origin = raw;
    }

    @Override
    public Header header() throws Exception {
        final Header header = this.origin.header();
        final Flow flow = new Flow(header.asBytes());
        if (!Arrays.equals(SafePngRaw.SIGNATURE, flow.asBytes(8))) {
            throw new InvalidFormatException("Not a PNG image file");
        }
        flow.skip(4);
        if (!flow.asString(4).equals("IHDR")) {
            throw new InvalidFormatException("Incorrect PNG image file");
        }
        if (header.depth() > 8) {
            throw new InvalidFormatException(
                "16-bit depth in PNG file not supported"
            );
        }
        if (header.color().space().equals("Unknown")) {
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
    public Palette palette(final Id id) throws Exception {
        final Header header = this.header();
        final Indirect indirect = this.origin.palette(id).indirect(id);
        if (header.color().space().equals("Indexed")
            && indirect.asBytes().length == 0) {
            throw new InvalidFormatException("Missing palette in PNG file");
        }
        return this.origin.palette(id);
    }
}
