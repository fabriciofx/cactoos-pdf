/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource.font;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;

/**
 * Courier.
 *
 * @since 0.0.1
 */
public final class Courier extends FontEnvelope {
    /**
     * Ctor.
     *
     * @param id Id number
     * @param size Font size in points
     */
    public Courier(final Id id, final int size) {
        this(id.increment(), 0, size);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param size Font size in points
     */
    public Courier(final int number, final int generation, final int size) {
        super(
            number,
            new FontFamily(number, generation, "Courier", "Type1"),
            size
        );
    }

    @Override
    public int width(final char chr) {
        return 600;
    }
}
