/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image;

import com.github.fabriciofx.cactoos.pdf.Content;

/**
 * Image Format.
 *
 * @since 0.0.1
 */
public interface Format extends Content {
    /**
     * Image width.
     *
     * @return Image width in pixels
     * @throws Exception if fails
     */
    int width() throws Exception;

    /**
     * Image height.
     *
     * @return Image height in pixels
     * @throws Exception if fails
     */
    int height() throws Exception;
}
