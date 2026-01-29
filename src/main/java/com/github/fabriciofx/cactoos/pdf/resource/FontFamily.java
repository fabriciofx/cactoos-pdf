/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import java.util.List;

/**
 * Font Family.
 *
 * @since 0.0.1
 */
public final class FontFamily implements Resource {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Font base.
     */
    private final String base;

    /**
     * Font subtype.
     */
    private final String subtype;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param base Font base
     * @param subtype Font subtype
     */
    public FontFamily(
        final Id id,
        final String base,
        final String subtype
    ) {
        this(id.increment(), 0, base, subtype);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param base Font base
     * @param subtype Font subtype
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public FontFamily(
        final int number,
        final int generation,
        final String base,
        final String subtype
    ) {
        this.number = number;
        this.generation = generation;
        this.base = base;
        this.subtype = subtype;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        return new DefaultIndirect(
            this.number,
            this.generation,
            new Dictionary()
                .add("Type", new Name("Font"))
                .add("BaseFont", new Name(this.base))
                .add("Subtype", new Name(this.subtype))
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        final Indirect indirect = this.indirect();
        if (!indirects.contains(indirect)) {
            indirects.add(indirect);
        }
    }
}
