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
package com.github.fabriciofx.cactoos.pdf.resource.font;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;

/**
 * Helvetica.
 *
 * @since 0.0.1
 */
public final class Helvetica extends FontEnvelope {
    /**
     * Widths.
     */
    private static final int[] WIDTHS = {
        278, 278, 278, 278, 278, 278, 278, 278, 278, 278,
        278, 278, 278, 278, 278, 278, 278, 278, 278, 278,
        278, 278, 278, 278, 278, 278, 278, 278, 278, 278,
        278, 278, 278, 278, 355, 556, 556, 889, 667, 191,
        333, 333, 389, 584, 278, 333, 278, 278, 556, 556,
        556, 556, 556, 556, 556, 556, 556, 556, 278, 278,
        584, 584, 584, 556, 1015, 667, 667, 722, 722, 667,
        611, 778, 722, 278, 500, 667, 556, 833, 722, 778,
        667, 778, 722, 667, 611, 722, 667, 944, 667, 667,
        611, 278, 278, 278, 469, 556, 333, 556, 556, 500,
        556, 556, 278, 556, 556, 222, 222, 500, 222, 833,
        556, 556, 556, 556, 333, 500, 278, 556, 500, 722,
        500, 500, 500, 334, 260, 334, 584, 350, 556, 350,
        222, 556, 333, 1000, 556, 556, 333, 1000, 667, 333,
        1000, 350, 611, 350, 350, 222, 222, 333, 333, 350,
        556, 1000, 333, 1000, 500, 333, 944, 350, 500, 667,
        278, 333, 556, 556, 556, 556, 260, 556, 333, 737,
        370, 556, 584, 333, 737, 333, 400, 584, 333, 333,
        333, 556, 537, 278, 333, 333, 365, 556, 834, 834,
        834, 611, 667, 667, 667, 667, 667, 667, 1000, 722,
        667, 667, 667, 667, 278, 278, 278, 278, 722, 722,
        778, 778, 778, 778, 778, 584, 778, 722, 722, 722,
        722, 667, 667, 611, 556, 556, 556, 556, 556, 556,
        889, 500, 556, 556, 556, 556, 278, 278, 278, 278,
        556, 556, 556, 556, 556, 556, 556, 584, 611, 556,
        556, 556, 556, 500, 556, 500,
    };

    /**
     * Ctor.
     *
     * @param id Id number
     * @param size Font size in points
     */
    public Helvetica(final Id id, final int size) {
        this(id.increment(), 0, size);
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param generation Generation number
     * @param size Font size in points
     */
    public Helvetica(final int id, final int generation, final int size) {
        super(
            id,
            new FontFamily(id, generation, "Helvetica", "Type1"),
            size
        );
    }

    @Override
    public int width(final char chr) {
        return Helvetica.WIDTHS[chr];
    }
}
