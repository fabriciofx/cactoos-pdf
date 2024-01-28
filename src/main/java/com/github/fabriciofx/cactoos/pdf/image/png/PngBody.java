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
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Body;
import com.github.fabriciofx.cactoos.pdf.image.Flow;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;

/**
 * PngBody: Represents a PNG image body.
 *
 * @since 0.0.1
 */
public final class PngBody implements Body {
    /**
     * PNG image body.
     */
    private final Scalar<byte[]> bytes;

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a PNG image body
     */
    public PngBody(final Bytes bytes) {
        this(
            new Sticky<>(
                () -> {
                    final Flow flow = new Flow(bytes.asBytes());
                    final ByteArrayOutputStream body =
                        new ByteArrayOutputStream();
                    int len;
                    flow.skip(33);
                    do {
                        len = flow.asInt();
                        final String type = flow.asString(4);
                        if (type.equals("IDAT")) {
                            body.write(flow.asBytes(len));
                            flow.skip(4);
                        } else if (type.equals("IEND")) {
                            break;
                        } else {
                            flow.skip(len + 4);
                        }
                    } while (len > 0);
                    return body.toByteArray();
                }
            )
        );
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents a PNG image body
     */
    public PngBody(final Scalar<byte[]> bytes) {
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
        throw new UnsupportedOperationException(
            "There is no indirect into PNG body"
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        // Empty of purpose.
    }
}
