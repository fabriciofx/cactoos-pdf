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
package com.github.fabriciofx.cactoos.pdf.text;

import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;

/**
 * Reference.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.ShortMethodName")
public final class Reference implements Text {
    /**
     * Object number.
     */
    private final int num;

    /**
     * Generation number.
     */
    private final int gen;

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     */
    public Reference(final int number, final int generation) {
        this.num = number;
        this.gen = generation;
    }

    /**
     * Object number.
     *
     * @return Object number
     */
    public int number() {
        return this.num;
    }

    /**
     * Object generation.
     *
     * @return Object generation
     */
    public int generation() {
        return this.gen;
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText(
            "%d %d R",
            Locale.ENGLISH,
            this.num,
            this.gen
        ).asString();
    }
}
