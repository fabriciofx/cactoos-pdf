/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import java.util.List;

/**
 * Content.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.ExtendsObject")
public interface Content extends Object {
    /**
     * Stream of a content.
     *
     * @return The stream content
     * @throws Exception if fails
     */
    byte[] asStream() throws Exception;

    /**
     * Resource content.
     *
     * @return The resource if there is one
     */
    List<Resource> resource();
}
