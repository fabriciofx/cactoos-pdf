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

import com.github.fabriciofx.cactoos.pdf.Count;
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
     * Header.
     */
    private final Header hdr;

    /**
     * Body.
     */
    private final Body bdy;

    /**
     * Palette.
     */
    private final Palette pal;

    /**
     * Ctor.
     * @param count Object count
     * @param filename File name of a PNG image
     */
    public PngRaw(final Count count, final String filename) {
        this(count, () -> Files.readAllBytes(new File(filename).toPath()));
    }

    /**
     * Ctor.
     *
     * @param count Object count
     * @param bytes Bytes that represents a PNG image
     */
    public PngRaw(final Count count, final Bytes bytes) {
        this.hdr = new PngHeader(bytes);
        this.bdy = new PngBody(count, bytes);
        this.pal = new PngPalette(count, bytes);
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