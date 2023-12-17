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
package com.github.fabriciofx.cactoos.pdf;

import java.io.ByteArrayInputStream;

public final class Flow {
    private final ByteArrayInputStream stream;

    public Flow(final byte[] bytes) {
        this(new ByteArrayInputStream(bytes));
    }

    public Flow(final ByteArrayInputStream stream) {
        this.stream = stream;
    }

    public String asString(final int length) throws Exception {
        return new String(this.stream.readNBytes(length));
    }

    public byte[] asBytes(final int length) throws Exception {
        return this.stream.readNBytes(length);
    }

    public int asInt() throws Exception {
        final byte[] bytes = this.asBytes(4);
        return ((bytes[0] & 0xFF) << 24) |
            ((bytes[1] & 0xFF) << 16) |
            ((bytes[2] & 0xFF) << 8) |
            (bytes[3] & 0xFF);
    }

    public byte asByte() throws Exception {
        return this.asBytes(1)[0];
    }

    public long skip(final int length) throws Exception {
        return this.stream.skip(length);
    }
}
