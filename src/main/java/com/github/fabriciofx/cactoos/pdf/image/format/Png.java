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
import com.github.fabriciofx.cactoos.pdf.image.Format;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import com.github.fabriciofx.cactoos.pdf.image.Palette;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import com.github.fabriciofx.cactoos.pdf.image.png.PngRaw;
import com.github.fabriciofx.cactoos.pdf.image.png.SafePngRaw;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.Bytes;

/**
 * PNG.
 *
 * @since 0.0.1
 */
public final class Png implements Format {
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
    public Png(final Id id, final String filename) {
        this(id, () -> Files.readAllBytes(new File(filename).toPath()));
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param bytes Bytes that represents a PNG image
     */
    public Png(final Id id, final Bytes bytes) {
        this.id = id;
        this.raw = new SafePngRaw(new PngRaw(this.id, bytes));
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
        final Palette palette = this.raw.palette();
        final Indirect pal = palette.indirect();
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("XObject"))
            .add("Subtype", new Name("Image"))
            .add("Width", new Int(header.width()))
            .add("Height", new Int(header.height()))
            .add(
                "ColorSpace",
                new Array(
                    new Name(header.color().space()),
                    new Name("DeviceRGB"),
                    new Int(palette.asStream().length / 3 - 1),
                    new Text(pal.reference().asString())
                )
            )
            .add("BitsPerComponent", new Int(header.depth()))
            .add("Filter", new Name("FlateDecode"))
            .add(
                "DecodeParms",
                new Dictionary()
                    .add("Predictor", new Int(15))
                    .add(
                        "Colors",
                        new Int(header.color().colors())
                    )
                    .add("BitsPerComponent", new Int(header.depth()))
                    .add("Columns", new Int(header.width()))
            )
            .add("Mask", new Array(new Int(0), new Int(0)))
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
        return new DefaultIndirect(
            this.id.increment(),
            0,
            dictionary,
            pal
        );
    }

    @Override
    public byte[] asStream() throws Exception {
        return this.raw.body().asStream();
    }
}
