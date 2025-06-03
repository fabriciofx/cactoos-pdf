/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image;

import org.cactoos.Bytes;
import org.cactoos.Text;

/**
 * Header.
 *
 * @since 0.0.1
 */
public interface Header extends Text, Bytes {
    /**
     * Length.
     *
     * @return The length
     * @throws Exception if fails
     */
    int length() throws Exception;

    /**
     * Width.
     * @return Image width
     * @throws Exception if fails
     */
    int width() throws Exception;

    /**
     * Height.
     *
     * @return Image height
     * @throws Exception if fails
     */
    int height() throws Exception;

    /**
     * Bit Depth.
     *
     * @return Amount of image bit depth
     * @throws Exception if fails
     */
    int depth() throws Exception;

    /**
     * Color space.
     *
     * @return Color type and space
     * @throws Exception if fails
     */
    Color color() throws Exception;

    /**
     * Image compression level.
     *
     * @return Image compression level
     * @throws Exception if fails
     */
    int compression() throws Exception;

    /**
     * Image filter type.
     *
     * @return Image filter type
     * @throws Exception if fails
     */
    int filter() throws Exception;

    /**
     * Image interlacing.
     *
     * @return Image interlacing
     * @throws Exception if fails
     */
    int interlacing() throws Exception;
}
