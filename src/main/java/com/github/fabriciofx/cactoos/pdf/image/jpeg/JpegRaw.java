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

import com.github.fabriciofx.cactoos.pdf.Id;
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
     * Id.
     */
    private final Id id;

    /**
     * Bytes.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param filename File name of a JPEG image
     */
    public JpegRaw(final Id id, final String filename) {
        this(id, () -> Files.readAllBytes(new File(filename).toPath()));
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param bytes Bytes that represents a JPEG image
     */
    public JpegRaw(final Id id, final Bytes bytes) {
        this.id = id;
        this.bytes = bytes;
    }

    @Override
    public Header header() throws Exception {
        return new JpegHeader(this.bytes);
    }

    @Override
    public Body body() throws Exception {
        return new JpegBody(this.id, this.bytes);
    }

    @Override
    public Palette palette() throws Exception {
        throw new IllegalStateException("JPEG image has not a palette");
    }
}