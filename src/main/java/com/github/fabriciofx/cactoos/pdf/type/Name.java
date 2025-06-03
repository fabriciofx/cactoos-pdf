/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.cactoos.text.FormattedText;

/**
 * Name.
 *
 * @since 0.0.1
 */
public final class Name implements Type<String> {
    /**
     * Value.
     */
    private final String text;

    /**
     * Ctor.
     *
     * @param text The name
     */
    public Name(final String text) {
        this.text = text;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.asString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String value() throws Exception {
        return this.text;
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText("/%s", Locale.ENGLISH, this.text).asString();
    }

    @Override
    public boolean equals(final Object name) {
        return name instanceof Name
            && Name.class.cast(name).text.equals(this.text);
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }
}
