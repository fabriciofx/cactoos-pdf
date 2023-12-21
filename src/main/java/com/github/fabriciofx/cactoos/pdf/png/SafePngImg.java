/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023 Fabrício Barros Cabral
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
import java.util.Arrays;

public final class SafePngImg implements Img {
    private static final byte[] SIGNATURE = {
        (byte) 137, 'P', 'N', 'G', '\r', '\n', 26, '\n'
    };
    private final PngImg origin;

    public SafePngImg(final PngImg img) {
        this.origin = img;
    }

    @Override
    public Header header() throws Exception {
        final Header header = this.origin.header();
        final Flow flow = new Flow(header.asBytes());
        if (!Arrays.equals(SafePngImg.SIGNATURE, flow.asBytes(8))) {
            throw new Exception("Not a PNG image file");
        }
        flow.skip(4);
        if (!flow.asString(4).equals("IHDR")) {
            throw new Exception("Incorrect PNG image file");
        }
        if (header.depth() > 8) {
            throw new Exception("16-bit depth in PNG file not supported");
        }
        if (header.color().space().equals("Unknown")) {
            throw new Exception("Unknown color type");
        }
        if (header.compression() != 0) {
            throw new Exception("Unknown compression method");
        }
        if (header.filter() != 0) {
            throw new Exception("Unknown filter method");
        }
        if (header.interlacing() != 0) {
            throw new Exception("Interlacing not supported");
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
        if (header.color().space().equals("Indexed")
            && this.origin.palette().asBytes().length == 0) {
            throw new Exception("Missing palette in PNG file");
        }
        return this.origin.palette();
    }
}
