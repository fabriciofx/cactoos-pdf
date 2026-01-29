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
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
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
        this(id.increment(), 0, bytes);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param bytes Bytes that represent a JPEG image
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Jpeg(
        final int number,
        final int generation,
        final Bytes bytes
    ) {
        this.number = number;
        this.generation = generation;
        this.raw = new Safe(new JpegRaw(bytes));
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
        final byte[] stream = this.asStream();
        return new DefaultIndirect(
            this.number,
            this.generation,
            new Dictionary()
                .add("Type", new Name("XObject"))
                .add("Subtype", new Name("Image"))
                .add("Width", new Int(header.width()))
                .add("Height", new Int(header.height()))
                .add("ColorSpace", new Name("DeviceRGB"))
                .add("BitsPerComponent", new Int(header.depth()))
                .add("Filter", new Name("DCTDecode"))
                .add("Length", new Int(stream.length))
                .with(new Stream(stream))
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
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
