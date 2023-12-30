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

import com.github.fabriciofx.cactoos.pdf.Definition;
import com.github.fabriciofx.cactoos.pdf.Flow;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.text.Indirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import org.cactoos.Bytes;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;

/**
 * PngPalette: Represents a palette of a PNG image.
 *
 * @since 0.0.1
 */
public final class PngPalette implements Palette {
    /**
     * Bytes that represents a palette.
     */
    private final Scalar<byte[]> bytes;

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a palette
     */
    public PngPalette(final Bytes bytes) {
        this(
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
     * @param bytes Bytes that represents a palette.
     */
    public PngPalette(final Scalar<byte[]> bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] asStream() throws Exception {
        return this.bytes.value();
    }

    @Override
    public Definition definition(final Id id) throws Exception {
        final int num = id.increment();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
        baos.write(new Indirect(num, 0).asBytes());
        baos.write(dictionary.asBytes());
        baos.write("\nendobj\n".getBytes());
        return new Definition(num, 0, dictionary, baos.toByteArray());
    }
}
