/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import org.cactoos.Scalar;

/**
 * Id.
 *
 * @since 0.0.1
 */
public interface Id extends Scalar<Integer> {
    /**
     * Return the last object id and increment it.
     *
     * @return The last object id integer
     */
    int increment();
}
