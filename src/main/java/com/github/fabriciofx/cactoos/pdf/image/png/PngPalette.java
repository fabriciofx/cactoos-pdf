/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Flow;
import com.github.fabriciofx.cactoos.pdf.image.Palette;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;

/**
 * PngPalette: Represents a palette of a PNG image.
 *
 * @since 0.0.1
 */
public final class PngPalette implements Palette {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Bytes that represents a palette.
     */
    private final Scalar<byte[]> bytes;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param bytes Bytes that represents a palette
     */
    public PngPalette(final Id id, final Bytes bytes) {
        this(
            id.increment(),
            0,
            new Sticky<>(
                () -> {
                    final Flow flow = new Flow(bytes.asBytes());
                    int len;
                    final ByteArrayOutputStream palette = new ByteArrayOutputStream();
                    flow.skip(33);
                    do {
                        len = flow.asInt();
                        final String type = flow.asString(4);
                        if ("PLTE".equals(type)) {
                            palette.write(flow.asBytes(len));
                            flow.skip(4);
                        } else if ("IEND".equals(type)) {
                            break;
                        } else {
                            flow.skip(len + 4);
                        }
                    } while (len > 0);
                    return palette.toByteArray();
                }
            )
        );
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param bytes Bytes that represents a palette
     */
    public PngPalette(
        final int number,
        final int generation,
        final Scalar<byte[]> bytes
    ) {
        this.number = number;
        this.generation = generation;
        this.bytes = bytes;
    }

    @Override
    public byte[] asStream() throws Exception {
        return this.bytes.value();
    }

    @Override
    public List<Resource> resource() {
        return new ListOf<>();
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
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
    }
}
