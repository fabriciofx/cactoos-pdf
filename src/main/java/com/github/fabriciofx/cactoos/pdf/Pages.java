/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import com.github.fabriciofx.cactoos.pdf.page.Format;

/**
 * Pages.
 *
 * @since 0.0.1
 */
@SuppressWarnings({"PMD.ExtendsObject", "PMD.ImplicitFunctionalInterface"})
public interface Pages extends Object {
    /**
     * Page format.
     *
     * @return Page format.
     */
    Format format();
}
