/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.indirect;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;

/**
 * DefaultIndirect.
 *
 * @since 0.0.1
 */
public final class NoReferenceIndirect implements Indirect {
    /**
     * Dictionary.
     */
    private final Dictionary dict;

    /**
     * Ctor.
     *
     * @param dictionary Dictionary
     */
    public NoReferenceIndirect(final Dictionary dictionary) {
        this.dict = dictionary;
    }

    @Override
    public Reference reference() {
        throw new UnsupportedOperationException(
            "This indirect has not reference"
        );
    }

    @Override
    public Dictionary dictionary() {
        return this.dict;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return new byte[0];
    }
}
