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
import com.github.fabriciofx.cactoos.pdf.Count;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.png.Header;
import com.github.fabriciofx.cactoos.pdf.png.Palette;
import com.github.fabriciofx.cactoos.pdf.png.PngRaw;
import com.github.fabriciofx.cactoos.pdf.png.Raw;
import com.github.fabriciofx.cactoos.pdf.png.SafePngRaw;
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
import org.cactoos.text.FormattedText;

/**
 * PNG.
 *
 * @since 0.0.1
 */
public final class Png implements Content {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * Raw image.
     */
    private final Raw raw;

    /**
     * Ctor.
     *
     * @param count Object count
     * @param filename Image file name
     */
    public Png(final Count count, final String filename) {
        this(
            count.increment(),
            0,
            count,
            () -> Files.readAllBytes(new File(filename).toPath())
        );
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Object generation
     * @param count Object count
     * @param bytes Bytes that represents a PNG image
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Png(
        final int number,
        final int generation,
        final Count count,
        final Bytes bytes
    ) {
        this.number = number;
        this.generation = generation;
        this.raw = new SafePngRaw(new PngRaw(count, bytes));
    }

    @Override
    public Reference reference() {
        return new Reference(this.number, this.generation);
    }

    @Override
    public byte[] asBytes() throws Exception {
        final Palette palette = this.raw.palette();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                this.number,
                this.generation
            ).asString().getBytes()
        );
        baos.write(this.dictionary().asBytes());
        baos.write("\nendobj\n".getBytes());
        baos.write(palette.asBytes());
        return baos.toByteArray();
    }

    @Override
    public byte[] stream() throws Exception {
        return this.raw.body().stream();
    }

    @Override
    public Dictionary dictionary() throws Exception {
        final Header header = this.raw.header();
        final Palette palette = this.raw.palette();
        final byte[] stream = this.stream();
        return new Dictionary()
            .add("Type", new Name("XObject"))
            .add("Subtype", new Name("Image"))
            .add("Width", new Int(header.width()))
            .add("Height", new Int(header.height()))
            .add(
                "ColorSpace",
                new Array(
                    new Name(header.color().space()),
                    new Name("DeviceRGB"),
                    new Int(palette.stream().length / 3 - 1),
                    new Text(palette.reference().asString())
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
    }
}
