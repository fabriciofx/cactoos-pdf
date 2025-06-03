/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.jpeg;

import com.github.fabriciofx.cactoos.pdf.image.Color;

/**
 * JPEG Color.
 *
 * @since 0.0.1
 */
public final class JpegColor implements Color {
    /**
     * Color type.
     */
    private final int tpe;

    /**
     * Ctor.
     *
     * @param type Color type
     */
    public JpegColor(final int type) {
        this.tpe = type;
    }

    @Override
    public int type() {
        return this.tpe;
    }

    @Override
    public String space() {
        final String space;
        switch (this.tpe) {
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
