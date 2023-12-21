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

public final class PngImg implements Img {
    private final Count count;
    private final Bytes bytes;

    public PngImg(final Count count, final String filename) {
        this(count, () -> Files.readAllBytes(new File(filename).toPath()));
    }

    public PngImg(final Count count, final Bytes bytes) {
        this.count = count;
        this.bytes = bytes;
    }

    @Override
    public Header header() throws Exception {
        return new PngHeader(this.bytes.asBytes());
    }

    @Override
    public Body body() throws Exception {
        return new PngBody(this.count, this.bytes.asBytes());
    }

    @Override
    public Palette palette() throws Exception {
        return new PngPalette(this.count, this.bytes.asBytes());
    }
}
