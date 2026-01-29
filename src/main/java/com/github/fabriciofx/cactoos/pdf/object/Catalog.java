/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.object;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Object;
import com.github.fabriciofx.cactoos.pdf.Pages;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.util.List;

/**
 * Catalog.
 *
 * @since 0.0.1
 */
public final class Catalog implements Object {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Pages.
     */
    private final Pages pages;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param pages Pages
     */
    public Catalog(final Id id, final Pages pages) {
        this(id.increment(), 0, pages);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param pages Pages
     */
    public Catalog(final int number, final int generation, final Pages pages) {
        this.number = number;
        this.generation = generation;
        this.pages = pages;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final Indirect indirect = this.pages.indirect();
        return new DefaultIndirect(
            this.number,
            this.generation,
            new Dictionary()
                .add("Type", new Name("Catalog"))
                .add("Pages", new Text(indirect.reference().asString()))
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
        this.pages.print(indirects);
    }
}
