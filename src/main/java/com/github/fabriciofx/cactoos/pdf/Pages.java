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

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.UncheckedText;

/**
 * Pages.
 *
 * @since 0.0.1
 */
public final class Pages implements Object {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * Pages size.
     */
    private final PageFormat size;

    /**
     * Pages.
     */
    private final List<Page> kids;

    /**
     * Ctor.
     *
     * @param count Counter
     * @param size Page's size
     * @param kids Pages
     */
    public Pages(final Count count, final PageFormat size, final Page... kids) {
        this(count.increment(), 0, size, kids);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Object generation
     * @param size Page's size
     * @param kids Pages
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Pages(
        final int number,
        final int generation,
        final PageFormat size,
        final Page... kids
    ) {
        this.number = number;
        this.generation = generation;
        this.size = size;
        this.kids = new ListOf<>(kids);
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
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String kds = this.kids.stream()
            .map(Page::reference)
            .collect(Collectors.joining(" "));
        baos.write(
            new FormattedText(
                new Joined(
                    " ",
                    "%d %d obj\n<< /Type /Pages /Kids [%s]",
                    "/Count %d /MediaBox [0 0 %s] >>\nendobj\n"
                ),
                this.number,
                this.generation,
                kds,
                this.kids.size(),
                this.size.asString()
            ).asString().getBytes()
        );
        for (final Page page : this.kids) {
            baos.write(page.with(this));
        }
        return baos.toByteArray();
    }
}
