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
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.nio.charset.StandardCharsets;

/**
 * Text.
 *
 * @since 0.0.1
 */
public final class Text implements Type<String> {
    /**
     * Value.
     */
    private final String txt;

    /**
     * Ctor.
     *
     * @param num Double number
     */
    public Text(final double num) {
        this(Double.toString(num));
    }

    /**
     * Ctor.
     *
     * @param num Integer number
     */
    public Text(final int num) {
        this(Integer.toString(num));
    }

    /**
     * Ctor.
     *
     * @param txt String text
     */
    public Text(final String txt) {
        this.txt = txt;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.txt.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String value() throws Exception {
        return this.txt;
    }

    @Override
    public String asString() throws Exception {
        return this.txt;
    }
}
