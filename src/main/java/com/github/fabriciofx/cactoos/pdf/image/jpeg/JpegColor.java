/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.jpeg;

import com.github.fabriciofx.cactoos.pdf.image.Color;

/**
 * JPEG Color.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public final class JpegColor implements Color {
    /**
     * Color type.
     */
    private final int type;

    /**
     * Ctor.
     *
     * @param type Color type
     */
    public JpegColor(final int type) {
        this.type = type;
    }

    @Override
    public int type() {
        return this.type;
    }

    @Override
    public String space() {
        final String space;
        switch (this.type) {
            case 3:
                space = "DeviceRGB";
                break;
            case 4:
                space = "DeviceCMYK";
                break;
            default:
                space = "DeviceGray";
                break;
        }
        return space;
    }

    @Override
    public int colors() {
        throw new UnsupportedOperationException(
            "Method JpegColor::colors() not implemented"
        );
    }
}
