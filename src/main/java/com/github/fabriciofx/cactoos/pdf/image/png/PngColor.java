/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.image.Color;

/**
 * PNG Color.
 *
 * @since 0.0.1
 */
public final class PngColor implements Color {
    /**
     * Color type.
     */
    private final int tpe;

    /**
     * Ctor.
     *
     * @param type Color type
     */
    public PngColor(final int type) {
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
            case 0:
            case 4:
                space = "DeviceGray";
                break;
            case 2:
            case 6:
                space = "DeviceRGB";
                break;
            case 3:
                space = "Indexed";
                break;
            default:
                space = "Unknown";
                break;
        }
        return space;
    }

    @Override
    public int colors() {
        final int clrs;
        if ("DeviceRGB".equals(this.space())) {
            clrs = 3;
        } else {
            clrs = 1;
        }
        return clrs;
    }
}
