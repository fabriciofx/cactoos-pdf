/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.format;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Format;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import com.github.fabriciofx.cactoos.pdf.image.Palette;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import com.github.fabriciofx.cactoos.pdf.image.png.PngRaw;
import com.github.fabriciofx.cactoos.pdf.image.png.Safe;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import com.github.fabriciofx.cactoos.pdf.type.Text;
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
public final class Png implements Format {
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
     * @param id Id number
     * @param filename Image file name
     */
    public Png(final Id id, final String filename) {
        this(id, () -> Files.readAllBytes(new File(filename).toPath()));
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param bytes Bytes that represents a PNG image
     */
    public Png(final Id id, final Bytes bytes) {
        this(id.increment(), 0, id, bytes);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Object generation
     * @param id Object id
     * @param bytes Bytes that represents a PNG image
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Png(
        final int number,
        final int generation,
        final Id id,
        final Bytes bytes
    ) {
        this.number = number;
        this.generation = generation;
        this.raw = new Safe(new PngRaw(id, bytes));
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
    public Indirect indirect(final int... parent) throws Exception {
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
            this.number,
            this.generation,
            dictionary
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
        this.raw.palette().print(indirects);
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
