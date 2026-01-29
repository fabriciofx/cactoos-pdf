/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource.font;

import com.github.fabriciofx.cactoos.pdf.Id;
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
     * @param id Id number
     * @param size Font size in points
     */
    public TimesRoman(final Id id, final int size) {
        this(id.increment(), 0, size);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param size Font size in points
     */
    public TimesRoman(final int number, final int generation, final int size) {
        super(
            number,
            new FontFamily(number, generation, "Times-Roman", "Type1"),
            size
        );
    }

    @Override
    public int width(final char chr) {
        return TimesRoman.WIDTHS[chr];
    }
}
