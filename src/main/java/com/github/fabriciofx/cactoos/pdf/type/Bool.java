/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;

/**
 * Bool.
 *
 * @since 0.0.1
 */
public final class Bool implements Type<Boolean> {
    /**
     * Value.
     */
    private final boolean val;

    /**
     * Ctor.
     *
     * @param val Value
     */
    public Bool(final boolean val) {
        this.val = val;
    }

    @Override
    public Boolean value() throws Exception {
        return this.val;
    }

    @Override
    public String asString() throws Exception {
        final String text;
        if (this.val) {
            text = "true";
        } else {
            text = "false";
        }
        return text;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final byte[] stream = new byte[1];
        if (this.val) {
            stream[0] = 1;
        } else {
            stream[0] = 0;
        }
        return stream;
    }
}
