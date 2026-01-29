/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.nio.charset.StandardCharsets;

/**
 * Text.
 *
 * @since 0.0.1
 */
public final class Text implements Type<String> {
    /**
     * Value.
     */
    private final String txt;

    /**
     * Ctor.
     *
     * @param num Double number
     */
    public Text(final double num) {
        this(Double.toString(num));
    }

    /**
     * Ctor.
     *
     * @param num Integer number
     */
    public Text(final int num) {
        this(Integer.toString(num));
    }

    /**
     * Ctor.
     *
     * @param txt String text
     */
    public Text(final String txt) {
        this.txt = txt;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.txt.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String value() throws Exception {
        return this.txt;
    }

    @Override
    public String asString() throws Exception {
        return this.txt;
    }
}
