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

import org.cactoos.Bytes;
import org.cactoos.Scalar;

/**
 * BytesAsInteger.
 *
 * Convert an array of bytes (4) in an integer.
 *
 * @since 0.0.1
 * @checkstyle BooleanExpressionComplexityCheck (100 lines)
 */
public final class BytesAsInteger implements Scalar<Integer> {
    /**
     * Bytes.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param bytes An array of bytes that represents an integer
     */
    public BytesAsInteger(final byte[] bytes) {
        this(() -> bytes);
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes that represents an integer
     */
    public BytesAsInteger(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public Integer value() throws Exception {
        final byte[] octets = this.bytes.asBytes();
        return ((octets[0] & 0xFF) << 24)
            | ((octets[1] & 0xFF) << 16)
            | ((octets[2] & 0xFF) << 8)
            | (octets[3] & 0xFF);
    }
}
