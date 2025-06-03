/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image;

/**
 * Image Color.
 *
 * @since 0.0.1
 */
public interface Color {
    /**
     * Color type.
     *
     * @return Color type
     */
    int type();

    /**
     * Color space.
     *
     * @return Color space
     */
    String space();

    /**
     * Number of colors according to color space.
     *
     * @return Number of colors
     */
    int colors();
}
