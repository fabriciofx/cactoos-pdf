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
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.io.ByteArrayOutputStream;
import org.cactoos.Bytes;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.FormattedText;

/**
 * Stream.
 *
 * @since 0.0.1
 */
public final class Stream implements Type<byte[]> {
    /**
     * Value.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param bytes An array of bytes
     */
    public Stream(final byte[] bytes) {
        this(new BytesOf(bytes));
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes
     */
    public Stream(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] value() throws Exception {
        return this.bytes.asBytes();
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("stream\n".getBytes());
        baos.write(this.bytes.asBytes());
        baos.write("\nendstream".getBytes());
        return baos.toByteArray();
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText(
            "\nstream\n%s\nendstream",
            new String(this.bytes.asBytes())
        ).asString();
    }

    /**
     * Stream's length.
     *
     * @return Amount of stream bytes
     * @throws Exception if fails
     */
    public int length() throws Exception {
        return this.bytes.asBytes().length;
    }
}
