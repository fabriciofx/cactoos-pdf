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
import com.github.fabriciofx.cactoos.pdf.Flow;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import org.cactoos.Bytes;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;
import org.cactoos.text.FormattedText;

/**
 * PngPalette: Represents a palette of a PNG image.
 *
 * @since 0.0.1
 */
public final class PngPalette implements Palette {
    /**
     * Number.
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
     * @param count Object count
     * @param bytes Bytes that represents a palette
     */
    public PngPalette(final Count count, final Bytes bytes) {
        this(
            count.increment(),
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
                        if (type.equals("PLTE")) {
                            palette.write(flow.asBytes(len));
                            flow.skip(4);
                        } else if (type.equals("IEND")) {
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
     * @param number Number
     * @param generation Generation number
     * @param bytes Bytes that represents a palette.
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
    public Reference reference() {
        return new Reference(this.number, this.generation);
    }

    @Override
    public byte[] stream() throws Exception {
        return this.bytes.value();
    }

    @Override
    public Dictionary dictionary() throws Exception {
        final byte[] stream = this.stream();
        return new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
    }

    @Override
    public byte[] asBytes() throws Exception {
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
        return baos.toByteArray();
    }
}
