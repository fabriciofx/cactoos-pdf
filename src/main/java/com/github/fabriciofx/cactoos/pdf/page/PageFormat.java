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
package com.github.fabriciofx.cactoos.pdf.page;

import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * PageSize.
 *
 * @since 0.0.1
 */
public enum PageFormat implements Text {
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
    PageFormat(final double width, final double height) {
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
