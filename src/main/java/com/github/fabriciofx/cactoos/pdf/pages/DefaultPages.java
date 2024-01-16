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
package com.github.fabriciofx.cactoos.pdf.pages;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.Pages;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.page.Format;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Pages.
 *
 * @since 0.0.1
 */
public final class DefaultPages implements Pages {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Pages size.
     */
    private final Format fmt;

    /**
     * Pages.
     */
    private final List<Page> kids;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param format Page's size
     * @param kids Some Page
     */
    public DefaultPages(
        final Id id,
        final Format format,
        final Page... kids
    ) {
        this(id.increment(), 0, format, kids);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param format Page's size
     * @param kids Some Page
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultPages(
        final int number,
        final int generation,
        final Format format,
        final Page... kids
    ) {
        this.number = number;
        this.generation = generation;
        this.fmt = format;
        this.kids = new ListOf<>(kids);
    }

    @Override
    public Indirect indirect() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Array kds = new Array();
        for (final Page page : this.kids) {
            final Indirect indirect = page.indirect(this.number);
            kds = kds.add(new Text(indirect.reference().asString()));
            baos.write(indirect.asBytes());
        }
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("Pages"))
            .add("Kids", kds)
            .add("Count", new Int(this.kids.size()))
            .add(
                "MediaBox",
                new Array(
                    new Int(0),
                    new Int(0),
                    new Text(this.fmt.asString())
                )
            );
        return new DefaultIndirect(
            this.number,
            this.generation,
            dictionary,
            baos::toByteArray
        );
    }

    @Override
    public void add(final Page page) {
        this.kids.add(page);
    }

    @Override
    public Format format() {
        return this.fmt;
    }
}
