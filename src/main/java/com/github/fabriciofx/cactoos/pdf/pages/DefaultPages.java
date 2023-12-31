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
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.list.ListOf;
import org.cactoos.text.UncheckedText;

/**
 * Pages.
 *
 * @since 0.0.1
 */
public final class DefaultPages implements Pages {
    /**
     * Pages size.
     */
    private final PageFormat fmt;

    /**
     * Pages.
     */
    private final List<Page> kids;

    /**
     * Ctor.
     *
     * @param format Page's size
     * @param kids Some Page
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultPages(final PageFormat format, final Page... kids) {
        this.fmt = format;
        this.kids = new ListOf<>(kids);
    }

    @Override
    public Indirect indirect(final Id id) throws Exception {
        final int num = id.increment();
        final List<Indirect> indirects = new ListOf<>();
        for (final Page page : this.kids) {
            indirects.add(page.indirect(id, num));
        }
        final String kds = indirects.stream()
            .map(def -> new UncheckedText(def.reference()).asString())
            .collect(Collectors.joining(" "));
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("Pages"))
            .add("Kids", new Array(new Text(kds)))
            .add("Count", new Int(this.kids.size()))
            .add(
                "MediaBox",
                new Array(
                    new Int(0),
                    new Int(0),
                    new Text(this.fmt.asString())
                )
            );
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (final Indirect indirect : indirects) {
            baos.write(indirect.asBytes());
        }
        return new DefaultIndirect(num, 0, dictionary, baos::toByteArray);
    }

    @Override
    public void add(final Page page) {
        this.kids.add(page);
    }

    @Override
    public PageFormat format() {
        return this.fmt;
    }
}
