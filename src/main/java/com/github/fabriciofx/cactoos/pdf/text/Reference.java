/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.text;

import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;

/**
 * Reference.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.ShortMethodName")
public final class Reference implements Text {
    /**
     * Object number.
     */
    private final int num;

    /**
     * Generation number.
     */
    private final int gen;

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     */
    public Reference(final int number, final int generation) {
        this.num = number;
        this.gen = generation;
    }

    /**
     * Object number.
     *
     * @return Object number
     */
    public int number() {
        return this.num;
    }

    /**
     * Object generation.
     *
     * @return Object generation
     */
    public int generation() {
        return this.gen;
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText(
            "%d %d R",
            Locale.ENGLISH,
            this.num,
            this.gen
        ).asString();
    }
}
