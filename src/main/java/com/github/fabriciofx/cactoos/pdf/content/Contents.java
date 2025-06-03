/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Object;
import java.util.List;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;

/**
 * Contents.
 *
 * @since 0.0.1
 */
public final class Contents extends ListEnvelope<Content> implements Object {
    /**
     * Ctor.
     *
     * @param elements An array of elements
     */
    public Contents(final Content... elements) {
        this(new ListOf<>(elements));
    }

    /**
     * Ctor.
     *
     * @param list A list of objects
     */
    public Contents(final List<Content> list) {
        super(list);
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        throw new UnsupportedOperationException(
            "there is no indirect to contents"
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        for (final Content content : this) {
            content.print(indirects);
        }
    }
}
