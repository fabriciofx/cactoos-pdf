/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023 Fabrício Barros Cabral
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
package com.github.fabriciofx.cactoos.pdf.resource;

import org.cactoos.Bytes;
import org.cactoos.text.FormattedText;

/**
 * Font Family.
 *
 * @since 0.0.1
 */
public final class FontFamily implements Bytes {
    /**
     * Font base.
     */
    private final String base;

    /**
     * Font subtype.
     */
    private final String subtype;

    /**
     * Ctor.
     *
     * @param base Font base
     * @param subtype Font subtype
     */
    public FontFamily(final String base, final String subtype) {
        this.base = base;
        this.subtype = subtype;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return new FormattedText(
            "<< /Type /Font /BaseFont /%s /Subtype /%s >>",
            this.base,
            this.subtype
        ).asString().getBytes();
    }
}
