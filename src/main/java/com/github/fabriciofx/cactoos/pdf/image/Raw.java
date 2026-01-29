/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image;

/**
 * Image Raw.
 *
 * @since 0.0.1
 */
public interface Raw {
    /**
     * Header.
     *
     * @return Image header
     * @throws Exception if fails
     */
    Header header() throws Exception;

    /**
     * Body.
     *
     * @return Image body
     * @throws Exception if fails
     */
    Body body() throws Exception;

    /**
     * Palette.
     *
     * @return Image palette if there is one
     * @throws Exception if fails
     */
    Palette palette() throws Exception;
}
