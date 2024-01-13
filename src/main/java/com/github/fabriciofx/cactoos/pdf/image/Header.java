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
package com.github.fabriciofx.cactoos.pdf.image;

import org.cactoos.Bytes;
import org.cactoos.Text;

/**
 * Header.
 *
 * @since 0.0.1
 */
public interface Header extends Text, Bytes {
    /**
     * Length.
     *
     * @return The length
     * @throws Exception if fails
     */
    int length() throws Exception;

    /**
     * Width.
     * @return Image width
     * @throws Exception if fails
     */
    int width() throws Exception;

    /**
     * Height.
     *
     * @return Image height
     * @throws Exception if fails
     */
    int height() throws Exception;

    /**
     * Bit Depth.
     *
     * @return Amount of image bit depth
     * @throws Exception if fails
     */
    int depth() throws Exception;

    /**
     * Color space.
     *
     * @return Color type and space
     * @throws Exception if fails
     */
    Color color() throws Exception;

    /**
     * Image compression level.
     *
     * @return Image compression level
     * @throws Exception if fails
     */
    int compression() throws Exception;

    /**
     * Image filter type.
     *
     * @return Image filter type
     * @throws Exception if fails
     */
    int filter() throws Exception;

    /**
     * Image interlacing.
     *
     * @return Image interlacing
     * @throws Exception if fails
     */
    int interlacing() throws Exception;
}
