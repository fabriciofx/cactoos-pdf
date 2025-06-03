/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource.font;

import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.indirect.NoReferenceIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.util.List;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * FontEnvelope.
 *
 * @since 0.0.1
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
public abstract class FontEnvelope implements Font {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Family.
     */
    private final FontFamily family;

    /**
     * Size.
     */
    private final int points;

    /**
     * Ctor.
     *
     * @param number Object number
     * @param family Font family
     * @param size Font size
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public FontEnvelope(
        final int number,
        final FontFamily family,
        final int size
    ) {
        this.number = number;
        this.family = family;
        this.points = size;
    }

    @Override
    public String name() {
        return new UncheckedText(
            new FormattedText("F%d", this.number)
        ).asString();
    }

    @Override
    public int size() {
        return this.points;
    }

    @Override
    public int width(final char chr) {
        return 1;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final Indirect indirect = this.family.indirect();
        final Dictionary dictionary = new Dictionary()
            .add(
                "Font",
                new Dictionary().add(
                    this.name(),
                    new Text(indirect.reference().asString())
                )
            );
        return new NoReferenceIndirect(dictionary);
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        this.family.print(indirects);
    }
}
