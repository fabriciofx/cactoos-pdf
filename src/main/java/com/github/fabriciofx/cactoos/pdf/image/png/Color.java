/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023-2024 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

/**
 * Color.
 *
 * @since 0.0.1
 */
public final class Color {
    /**
     * Color type.
     */
    private final int tpe;

    /**
     * Ctor.
     *
     * @param type Color type
     */
    public Color(final int type) {
        this.tpe = type;
    }

    /**
     * Color type.
     *
     * @return Color type
     */
    public int type() {
        return this.tpe;
    }

    /**
     * Color space.
     *
     * @return Color space
     */
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

    /**
     * Number of colors according to color space.
     *
     * @return Number of colors
     */
    public int colors() {
        final int clrs;
        if (this.space().equals("DeviceRGB")) {
            clrs = 3;
        } else {
            clrs = 1;
        }
        return clrs;
    }
}
