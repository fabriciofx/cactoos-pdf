/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.cactoos.text.FormattedText;

/**
 * Literal.
 *
 * @since 0.0.1
 */
public final class Literal implements Type<String> {
    /**
     * Value.
     */
    private final String text;

    /**
     * Ctor.
     *
     * @param txt String
     */
    public Literal(final String txt) {
        this.text = txt;
    }

    @Override
    public String value() throws Exception {
        return this.text;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.asString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText("(%s)", Locale.ENGLISH, this.text).asString();
    }
}
