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
import java.nio.ByteBuffer;

/**
 * Real.
 *
 * @since 0.0.1
 */
public final class Real implements Type<Double> {
    /**
     * Value.
     */
    private final double num;

    /**
     * Ctor.
     *
     * @param num Number
     */
    public Real(final double num) {
        this.num = num;
    }

    @Override
    public Double value() throws Exception {
        return this.num;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(this.num);
        return buffer.array();
    }

    @Override
    public String asString() throws Exception {
        return Double.toString(this.num);
    }
}
