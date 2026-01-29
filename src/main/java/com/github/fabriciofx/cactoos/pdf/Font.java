/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

/**
 * Font.
 *
 * @since 0.0.1
 */
public interface Font extends Resource {
    /**
     * Font name.
     *
     * @return The font name
     */
    String name();

    /**
     * Font size.
     *
     * @return The font size in points
     */
    int size();

    /**
     * Char width in a font.
     *
     * @param chr The character
     * @return The size of the char in a font
     */
    int width(char chr);
}
