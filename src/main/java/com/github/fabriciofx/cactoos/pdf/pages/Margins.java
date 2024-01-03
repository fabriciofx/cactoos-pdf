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
package com.github.fabriciofx.cactoos.pdf.pages;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.Pages;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.FormattedText;

/**
 * Margins.
 *
 * @since 0.0.1
 */
@SuppressWarnings(
    {
        "PMD.UnusedPrivateField",
        "PMD.SingularField",
        "PMD.UseUnderscoresInNumericLiterals"
    }
)
public final class Margins implements Pages {
    /**
     * One centimeter in points.
     *
     * ONE_CM = 72 (points in 1 inch) / 2.54 (1 inch in cm)
     */
    private static final double ONE_CM = 28.346_456_7;

    /**
     * Top margin.
     */
    private final double top;

    /**
     * Right margin.
     */
    private final double right;

    /**
     * Bottom margin.
     */
    private final double bottom;

    /**
     * Left margin.
     */
    private final double left;

    /**
     * Pages.
     */
    private final Pages origin;

    /**
     * Ctor.
     *
     * @param pages Pages
     */
    public Margins(final Pages pages) {
        this(1.0, 1.0, 1.0, 1.0, pages);
    }

    /**
     * Ctor.
     *
     * @param top Top margin
     * @param right Right margin
     * @param bottom Bottom margin
     * @param left Left margin
     * @param pages Pages to be decorated
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Margins(
        final double top,
        final double right,
        final double bottom,
        final double left,
        final Pages pages
    ) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.origin = pages;
    }

    @Override
    public Indirect indirect(final Id id) throws Exception {
        final Indirect indirect = this.origin.indirect(id);
        final Pattern pattern = Pattern.compile("BT.*TL");
        final Matcher matcher = pattern.matcher(
            new String(indirect.asBytes())
        );
        final StringBuffer stream = new StringBuffer();
        while (matcher.find()) {
            final String[] parts = matcher.group().split("\\s+");
            final String fontname = parts[1];
            final String fontsize = parts[2];
            final String leading = parts[7];
            matcher.appendReplacement(
                stream,
                new FormattedText(
                    "BT %s %s Tf %.2f %.2f Td %s TL",
                    Locale.ENGLISH,
                    fontname,
                    fontsize,
                    this.left * Margins.ONE_CM,
                    this.format().height() - this.top * Margins.ONE_CM,
                    leading
                ).asString()
            );
        }
        matcher.appendTail(stream);
        return new Indirect(
            indirect.reference().id(),
            indirect.reference().generation(),
            new Dictionary(),
            new BytesOf(stream)
        );
    }

    @Override
    public void add(final Page page) {
        this.origin.add(page);
    }

    @Override
    public PageFormat format() {
        return this.origin.format();
    }
}
