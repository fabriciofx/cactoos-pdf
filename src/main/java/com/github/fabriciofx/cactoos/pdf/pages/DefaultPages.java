/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
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
import java.util.List;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;

/**
 * Pages.
 *
 * @since 0.0.1
 */
public final class DefaultPages extends ListEnvelope<Page> implements Pages {
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
     * Ctor.
     *
     * @param id Id number
     * @param kids Some Pages
     */
    public DefaultPages(
        final Id id,
        final Page... kids
    ) {
        this(id.increment(), 0, Format.A4, new ListOf<>(kids));
    }

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
        this(id.increment(), 0, format, new ListOf<>(kids));
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param format Page's size
     * @param kids Some Pages
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultPages(
        final int number,
        final int generation,
        final Format format,
        final List<Page> kids
    ) {
        super(kids);
        this.number = number;
        this.generation = generation;
        this.fmt = format;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        Array kds = new Array();
        for (final Page page : this) {
            final Indirect indirect = page.indirect(this.number);
            kds = kds.add(new Text(indirect.reference().asString()));
        }
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("Pages"))
            .add("Kids", kds)
            .add("Count", new Int(this.size()))
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
            dictionary
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
        for (final Page page : this) {
            page.print(indirects, this.number);
        }
    }

    @Override
    public Format format() {
        return this.fmt;
    }
}
