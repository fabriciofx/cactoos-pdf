/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.nio.ByteBuffer;

/**
 * Real.
 *
 * @since 0.0.1
 */
public final class Real implements Type<Double> {
    /**
     * Value.
     */
    private final double num;

    /**
     * Ctor.
     *
     * @param num Number
     */
    public Real(final double num) {
        this.num = num;
    }

    @Override
    public Double value() throws Exception {
        return this.num;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(this.num);
        return buffer.array();
    }

    @Override
    public String asString() throws Exception {
        return Double.toString(this.num);
    }
}
