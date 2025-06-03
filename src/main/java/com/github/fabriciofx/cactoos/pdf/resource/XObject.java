/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.util.List;

/**
 * XObject.
 *
 * @since 0.0.1
 */
public final class XObject implements Resource {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Image.
     */
    private final Image image;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param image Image
     */
    public XObject(final Id id, final Image image) {
        this(id.increment(), 0, image);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param image Image
     */
    public XObject(final int number, final int generation, final Image image) {
        this.number = number;
        this.generation = generation;
        this.image = image;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final Indirect indirect = this.image.format().indirect();
        final Dictionary dictionary = new Dictionary()
            .add(
                "XObject",
                new Dictionary().add(
                    this.image.name(),
                    new Text(indirect.reference().asString())
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
    }
}
