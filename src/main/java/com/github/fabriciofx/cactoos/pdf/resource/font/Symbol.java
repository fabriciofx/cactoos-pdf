/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource.font;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;

/**
 * Symbol.
 *
 * @since 0.0.1
 */
public final class Symbol extends FontEnvelope {
    /**
     * Widths.
     */
    private static final int[] WIDTHS = {
        250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
        250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
        250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
        250, 250, 250, 333, 713, 500, 549, 833, 778, 439,
        333, 333, 500, 549, 250, 549, 250, 278, 500, 500,
        500, 500, 500, 500, 500, 500, 500, 500, 278, 278,
        549, 549, 549, 444, 549, 722, 667, 722, 612, 611,
        763, 603, 722, 333, 631, 722, 686, 889, 722, 722,
        768, 741, 556, 592, 611, 690, 439, 768, 645, 795,
        611, 333, 863, 333, 658, 500, 500, 631, 549, 549,
        494, 439, 521, 411, 603, 329, 603, 549, 549, 576,
        521, 549, 549, 521, 549, 603, 439, 576, 713, 686,
        493, 686, 494, 480, 200, 480, 549, 0,   0,   0,
        0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
        0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
        0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
        750, 620, 247, 549, 167, 713, 500, 753, 753, 753,
        753, 1042, 987, 603, 987, 603, 400, 549, 411, 549,
        549, 713, 494, 460, 549, 549, 549, 549, 1000, 603,
        1000, 658, 823, 686, 795, 987, 768, 768, 823, 768,
        768, 713, 713, 713, 713, 713, 713, 713, 768, 713,
        790, 790, 890, 823, 549, 250, 713, 603, 603, 1042,
        987, 603, 987, 603, 494, 329, 790, 790, 786, 713,
        384, 384, 384, 384, 384, 384, 494, 494, 494, 494,
        0, 329, 274, 686, 686, 686, 384, 384, 384, 384,
        384, 384, 494, 494, 494, 0,
    };

    /**
     * Ctor.
     *
     * @param id Id number
     * @param size Font size in points
     */
    public Symbol(final Id id, final int size) {
        this(id.increment(), 0, size);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param size Font size in points
     */
    public Symbol(final int number, final int generation, final int size) {
        super(
            number,
            new FontFamily(number, generation, "Symbol", "Type1"),
            size
        );
    }

    @Override
    public int width(final char chr) {
        return Symbol.WIDTHS[chr];
    }
}
