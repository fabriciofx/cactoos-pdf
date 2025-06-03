/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.page;

import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Format.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public enum Format implements Text {
    /**
     * A1 page size.
     */
    A1(1683.78, 2383.94),

    /**
     * A2 page size.
     */
    A2(1190.55, 1683.78),

    /**
     * A3 page size.
     */
    A3(841.89, 1190.55),

    /**
     * A4 page size.
     */
    A4(595.28, 841.89),

    /**
     * A5 page size.
     */
    A5(420.94, 595.28),

    /**
     * A6 page size.
     */
    A6(297.64, 420.94),

    /**
     * Letter page size.
     */
    LETTER(612, 792),

    /**
     * Legal page size.
     */
    LEGAL(612, 1008),

    /**
     * Tabloid page size.
     */
    TABLOID(792, 1224);

    /**
     * Width.
     */
    private final double width;

    /**
     * Height.
     */
    private final double height;

    /**
     * Ctor.
     *
     * @param width Page's width
     * @param height Page's height
     */
    Format(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Page width.
     *
     * @return The width of page
     */
    public double width() {
        return this.width;
    }

    /**
     * Page height.
     *
     * @return The height of page
     */
    public double height() {
        return this.height;
    }

    @Override
    public String asString() throws Exception {
        return new UncheckedText(
            new FormattedText(
                "%.2f %.2f",
                Locale.ENGLISH,
                this.width,
                this.height
            )
        ).asString();
    }
}
