/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Page;

/**
 * Rotate90.
 *
 * @since 0.0.1
 */
public final class Rotate90 extends RotateEnvelope {
    /**
     * Ctor.
     *
     * @param page The Page
     */
    public Rotate90(final Page page) {
        super(page, 90);
    }
}
