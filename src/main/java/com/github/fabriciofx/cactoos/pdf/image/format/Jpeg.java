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
package com.github.fabriciofx.cactoos.pdf.image.format;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Format;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import com.github.fabriciofx.cactoos.pdf.image.jpeg.JpegRaw;
import com.github.fabriciofx.cactoos.pdf.image.jpeg.Safe;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * PNG.
 *
 * @since 0.0.1
 */
public final class Jpeg implements Format {
    /**
     * Id.
     */
    private final Id id;

    /**
     * Raw image.
     */
    private final Raw raw;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param filename Image file name
     */
    public Jpeg(final Id id, final String filename) {
        this(id, () -> Files.readAllBytes(new File(filename).toPath()));
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param bytes Bytes that represents a PNG image
     */
    public Jpeg(final Id id, final Bytes bytes) {
        this.id = id;
        this.raw = new Safe(new JpegRaw(this.id, bytes));
    }

    @Override
    public int width() throws Exception {
        return this.raw.header().width();
    }

    @Override
    public int height() throws Exception {
        return this.raw.header().height();
    }

    @Override
    public Indirect indirect() throws Exception {
        final Header header = this.raw.header();
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("XObject"))
            .add("Subtype", new Name("Image"))
            .add("Width", new Int(header.width()))
            .add("Height", new Int(header.height()))
            .add("ColorSpace", new Name("DeviceRGB"))
            .add("BitsPerComponent", new Int(header.depth()))
            .add("Filter", new Name("DCTDecode"))
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
        return new DefaultIndirect(
            this.id.increment(),
            0,
            dictionary
        );
    }

    @Override
    public byte[] asStream() throws Exception {
        return this.raw.body().asStream();
    }

    @Override
    public List<Resource> resource() {
        return new ListOf<>();
    }
}
