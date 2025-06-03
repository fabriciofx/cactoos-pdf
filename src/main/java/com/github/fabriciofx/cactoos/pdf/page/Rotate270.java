/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Page;

/**
 * Rotate270.
 *
 * @since 0.0.1
 */
public final class Rotate270 extends RotateEnvelope {
    /**
     * Ctor.
     *
     * @param page The Page
     */
    public Rotate270(final Page page) {
        super(page, 270);
    }
}
