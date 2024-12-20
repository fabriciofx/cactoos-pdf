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
