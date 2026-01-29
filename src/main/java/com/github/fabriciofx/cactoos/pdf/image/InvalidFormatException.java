/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image;

/**
 * InvalidFormatException.
 *
 * Throws this exception when image format is invalid.
 *
 * @since 0.0.1
 */
public class InvalidFormatException extends RuntimeException {
    private static final long serialVersionUID = -4456180910126746406L;

    /**
     * Ctor.
     */
    public InvalidFormatException() {
        super();
    }

    /**
     * Ctor.
     *
     * @param msg Exception message
     */
    public InvalidFormatException(final String msg) {
        super(msg);
    }
}
