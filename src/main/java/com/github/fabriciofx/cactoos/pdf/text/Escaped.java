/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.text;

import org.cactoos.Text;
import org.cactoos.text.TextEnvelope;

/**
 * Escape a text with special characters: '(', ')', '\' and '\r'.
 *
 * @since 0.0.1
 */
public final class Escaped extends TextEnvelope implements Text {
    /**
     * Ctor.
     *
     * @param text Text to be escaped.
     */
    public Escaped(final String text) {
        this(() -> text);
    }

    /**
     * Ctor.
     *
     * @param text Text to be escaped.
     */
    public Escaped(final Text text) {
        super(
            () -> text.asString()
                .replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\r", "\\\\r")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)")
        );
    }
}
