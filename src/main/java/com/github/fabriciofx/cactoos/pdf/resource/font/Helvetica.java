/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
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
     * @param number Object number
     * @param generation Generation number
     * @param size Font size in points
     */
    public Helvetica(final int number, final int generation, final int size) {
        super(
            number,
            new FontFamily(number, generation, "Helvetica", "Type1"),
            size
        );
    }

    @Override
    public int width(final char chr) {
        return Helvetica.WIDTHS[chr];
    }
}
