/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.id;

import com.github.fabriciofx.cactoos.pdf.Id;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serial.
 *
 * @since 0.0.1
 */
public final class Serial implements Id {
    /**
     * Seed.
     */
    private final AtomicInteger seed;

    /**
     * Ctor.
     */
    public Serial() {
        this(1);
    }

    /**
     * Ctor.
     *
     * @param seed Seed to start counting
     */
    public Serial(final int seed) {
        this(new AtomicInteger(seed));
    }

    /**
     * Ctor.
     *
     * @param seed Seed to start counting
     */
    public Serial(final AtomicInteger seed) {
        this.seed = seed;
    }

    @Override
    public Integer value() {
        return this.seed.get();
    }

    @Override
    public int increment() {
        return this.seed.getAndIncrement();
    }
}
