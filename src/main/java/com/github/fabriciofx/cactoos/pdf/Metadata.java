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

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * PDF Metadata.
 *
 * @since 0.0.1
 */
public final class Metadata implements Object {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * PDF title.
     */
    private final String title;

    /**
     * Ctor.
     *
     * @param count Counter
     * @param title PDF title
     */
    public Metadata(final Count count, final String title) {
        this(count.increment(), 0, title);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Object generation
     * @param title PDF title
     */
    public Metadata(
        final int number,
        final int generation,
        final String title
    ) {
        this.number = number;
        this.generation = generation;
        this.title = title;
    }

    @Override
    public String reference() {
        return new UncheckedText(
            new FormattedText(
                "%d %d R",
                this.number,
                this.generation
            )
        ).asString();
    }

    @Override
    public byte[] with(final Object... objects) throws Exception {
        return new FormattedText(
            "%d %d obj\n<< /Title (%s) >>\nendobj\n",
            this.number,
            this.generation,
            this.title
        ).asString().getBytes();
    }
}
