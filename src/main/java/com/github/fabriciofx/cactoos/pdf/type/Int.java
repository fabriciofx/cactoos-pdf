/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;

/**
 * Int.
 *
 * @since 0.0.1
 */
public final class Int implements Type<Integer> {
    /**
     * Value.
     */
    private final int num;

    /**
     * Ctor.
     *
     * @param num Number
     */
    public Int(final int num) {
        this.num = num;
    }

    @Override
    public Integer value() throws Exception {
        return this.num;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final byte[] bytes = new byte[4];
        bytes[0] = (byte) (this.num >> 24);
        bytes[1] = (byte) (this.num >> 16);
        bytes[2] = (byte) (this.num >> 8);
        bytes[3] = (byte) this.num;
        return bytes;
    }

    @Override
    public String asString() throws Exception {
        return Integer.toString(this.num);
    }
}
