/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;

/**
 * PageDefault.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.ExtendsObject")
public interface Page extends Object {
    /**
     * Page Resources.
     *
     * @return Resources
     */
    Resources resources();

    /**
     * Page Contents.
     *
     * @return Contents
     */
    Contents contents();
}
