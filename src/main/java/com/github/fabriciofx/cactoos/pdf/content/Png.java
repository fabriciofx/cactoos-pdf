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
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Definition;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.png.Header;
import com.github.fabriciofx.cactoos.pdf.png.Palette;
import com.github.fabriciofx.cactoos.pdf.png.PngRaw;
import com.github.fabriciofx.cactoos.pdf.png.Raw;
import com.github.fabriciofx.cactoos.pdf.png.SafePngRaw;
import com.github.fabriciofx.cactoos.pdf.text.Indirect;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.Bytes;

/**
 * PNG.
 *
 * @since 0.0.1
 */
public final class Png implements Content {
    /**
     * Raw image.
     */
    private final Raw raw;

    /**
     * Ctor.
     *
     * @param filename Image file name
     */
    public Png(final String filename) {
        this(
            () -> Files.readAllBytes(new File(filename).toPath())
        );
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a PNG image
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Png(final Bytes bytes) {
        this.raw = new SafePngRaw(new PngRaw(bytes));
    }

    /**
     * Image width.
     *
     * @return Image width in pixels
     * @throws Exception if fails
     */
    public int width() throws Exception {
        return this.raw.header().width();
    }

    /**
     * Image height.
     *
     * @return Image height in pixels
     * @throws Exception if fails
     */
    public int height() throws Exception {
        return this.raw.header().height();
    }

    @Override
    public Definition definition(final Id id) throws Exception {
        final int num = id.increment();
        final Header header = this.raw.header();
        final Palette palette = this.raw.palette(id);
        final Definition pal = palette.definition(id);
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
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(new Indirect(num, 0).asBytes());
        baos.write(dictionary.asBytes());
        baos.write("\nendobj\n".getBytes());
        baos.write(pal.asBytes());
        return new Definition(num, 0, dictionary, baos.toByteArray());
    }

    @Override
    public byte[] asStream() throws Exception {
        return this.raw.body().asStream();
    }
}
