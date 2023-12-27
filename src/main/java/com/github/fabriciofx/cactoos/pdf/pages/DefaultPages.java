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
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.Pages;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Pages.
 *
 * @since 0.0.1
 */
public final class DefaultPages implements Pages {
    /**
     * Object id.
     */
    private final int id;

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
     * @param id Object id
     * @param size Page's size
     * @param kids Pages
     */
    public DefaultPages(
        final Id id,
        final PageFormat size,
        final Page... kids
    ) {
        this(id.increment(), 0, size, kids);
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param generation Object generation
     * @param size Page's size
     * @param kids Pages
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultPages(
        final int id,
        final int generation,
        final PageFormat size,
        final Page... kids
    ) {
        this.id = id;
        this.generation = generation;
        this.size = size;
        this.kids = Arrays.asList(kids);
    }

    @Override
    public Reference reference() {
        return new Reference(this.id, this.generation);
    }

    @Override
    public byte[] definition() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                this.id,
                this.generation
            ).asString().getBytes()
        );
        baos.write(this.dictionary().asBytes());
        baos.write("\nendobj\n".getBytes());
        for (final Page page : this.kids) {
            baos.write(page.definition(this));
        }
        return baos.toByteArray();
    }

    @Override
    public void add(final Page page) {
        this.kids.add(page);
    }

    @Override
    public Dictionary dictionary() throws Exception {
        final String kds = this.kids.stream()
            .map(page -> new UncheckedText(page.reference()).asString())
            .collect(Collectors.joining(" "));
        return new Dictionary()
            .add("Type", new Name("Pages"))
            .add("Kids", new Array(new Text(kds)))
            .add("Count", new Int(this.kids.size()))
            .add(
                "MediaBox",
                new Array(
                    new Int(0),
                    new Int(0),
                    new Text(this.size.asString())
                )
            );
    }
}
