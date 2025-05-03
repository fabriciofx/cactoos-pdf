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
