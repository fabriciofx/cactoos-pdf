/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import java.util.List;

/**
 * Represent any PDF object.
 *
 * @since 0.0.1
 */
public interface Object {
    /**
     * Object indirect.
     *
     * @param parent Parent number, if there is one
     * @return An object indirect
     * @throws Exception if fails
     */
    Indirect indirect(int... parent) throws Exception;

    /**
     * Print object.
     *
     * @param indirects List of indirects to print
     * @param parent Parent number, if there is one
     * @throws Exception if fails
     */
    void print(List<Indirect> indirects, int... parent) throws Exception;
}
