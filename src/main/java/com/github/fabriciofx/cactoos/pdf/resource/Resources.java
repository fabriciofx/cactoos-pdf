/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.util.List;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;

/**
 * Resources.
 *
 * @since 0.0.1
 */
public final class Resources extends ListEnvelope<Resource>
    implements Resource {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param elements An array de elements (Resource)
     */
    public Resources(
        final Id id,
        final Resource... elements
    ) {
        this(id.increment(), 0, elements);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param elements An array of elements (Resource)
     */
    public Resources(
        final int number,
        final int generation,
        final Resource... elements
    ) {
        this(number, generation, new ListOf<>(elements));
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param list A list of elements (Resource)
     */
    public Resources(
        final int number,
        final int generation,
        final List<Resource> list
    ) {
        super(list);
        this.number = number;
        this.generation = generation;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final List<Indirect> indirects = new ListOf<>();
        for (final Resource resource : this) {
            indirects.add(resource.indirect());
        }
        Dictionary dictionary = indirects.get(0).dictionary();
        for (int idx = 1; idx < indirects.size(); ++idx) {
            dictionary = dictionary.merge(indirects.get(idx).dictionary());
        }
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
        for (final Resource resource : this) {
            resource.print(indirects);
        }
    }
}
