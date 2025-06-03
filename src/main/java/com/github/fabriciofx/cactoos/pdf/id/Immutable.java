/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.id;

import com.github.fabriciofx.cactoos.pdf.Id;
import org.cactoos.scalar.Unchecked;

/**
 * Immutable.
 *
 * @since 0.0.1
 */
public final class Immutable implements Id {
    /**
     * Id.
     */
    private final Id origin;

    /**
     * Ctor.
     *
     * @param id The id
     */
    public Immutable(final Id id) {
        this.origin = id;
    }

    @Override
    public int increment() {
        return new Unchecked<>(this.origin).value();
    }

    @Override
    public Integer value() throws Exception {
        return this.origin.value();
    }
}
