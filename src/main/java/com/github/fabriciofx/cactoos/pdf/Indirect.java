/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import org.cactoos.Bytes;

/**
 * Indirect.
 *
 * @since 0.0.1
 */
public interface Indirect extends Bytes {
    /**
     * Create an object reference.
     *
     * @return A reference
     */
    Reference reference();

    /**
     * Create a dictionary.
     *
     * @return A dictionary
     */
    Dictionary dictionary();
}
