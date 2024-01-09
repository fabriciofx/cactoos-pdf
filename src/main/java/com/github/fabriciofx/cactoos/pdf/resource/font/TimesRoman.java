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

import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;

/**
 * TimesRoman.
 *
 * @since 0.0.1
 */
public final class TimesRoman extends FontEnvelope {
    /**
     * Widths.
     */
    private static final int[] WIDTHS = {
        250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
        250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
        250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
        250, 250, 250, 333, 408, 500, 500, 833, 778, 180,
        333, 333, 500, 564, 250, 333, 250, 278, 500, 500,
        500, 500, 500, 500, 500, 500, 500, 500, 278, 278,
        564, 564, 564, 444, 921, 722, 667, 667, 722, 611,
        556, 722, 722, 333, 389, 722, 611, 889, 722, 722,
        556, 722, 667, 556, 611, 722, 722, 944, 722, 722,
        611, 333, 278, 333, 469, 500, 333, 444, 500, 444,
        500, 444, 333, 500, 500, 278, 278, 500, 278, 778,
        500, 500, 500, 500, 333, 389, 278, 500, 500, 722,
        500, 500, 444, 480, 200, 480, 541, 350, 500, 350,
        333, 500, 444, 1000, 500, 500, 333, 1000, 556, 333,
        889, 350, 611, 350, 350, 333, 333, 444, 444, 350,
        500, 1000, 333, 980, 389, 333, 722, 350, 444, 722,
        250, 333, 500, 500, 500, 500, 200, 500, 333, 760,
        276, 500, 564, 333, 760, 333, 400, 564, 300, 300,
        333, 500, 453, 250, 333, 300, 310, 500, 750, 750,
        750, 444, 722, 722, 722, 722, 722, 722, 889, 667,
        611, 611, 611, 611, 333, 333, 333, 333, 722, 722,
        722, 722, 722, 722, 722, 564, 722, 722, 722, 722,
        722, 722, 556, 500, 444, 444, 444, 444, 444, 444,
        667, 444, 444, 444, 444, 444, 278, 278, 278, 278,
        500, 500, 500, 500, 500, 500, 500, 564, 500, 500,
        500, 500, 500, 500, 500, 500,
    };

    /**
     * Ctor.
     *
     * @param size Font size in points
     */
    public TimesRoman(final int size) {
        super(
            new FontFamily("Times-Roman", "Type1"),
            "F1",
            size
        );
    }

    @Override
    public int width(final char chr) {
        return TimesRoman.WIDTHS[chr];
    }
}
