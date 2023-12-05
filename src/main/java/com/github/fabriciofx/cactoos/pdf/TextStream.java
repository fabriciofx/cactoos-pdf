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
package com.github.fabriciofx.cactoos.pdf;

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Represents a Text.
 *
 * @since 0.0.1
 */
public final class TextStream implements Object {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * Font size.
     */
    private final int size;

    /**
     * Text X position.
     */
    private final int posx;

    /**
     * Text Y position.
     */
    private final int posy;

    /**
     * Content.
     */
    private final String content;

    /**
     * Ctor.
     *
     * @param count Counter
     * @param size Font size
     * @param posx Text X position
     * @param posy Text Y position
     * @param content Text content
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public TextStream(
        final Count count,
        final int size,
        final int posx,
        final int posy,
        final String content
    ) {
        this(count.value(), 0, size, posx, posy, content);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Object generation
     * @param size Font size
     * @param posx Text X position
     * @param posy Text Y position
     * @param content Text content
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public TextStream(
        final int number,
        final int generation,
        final int size,
        final int posx,
        final int posy,
        final String content
    ) {
        this.number = number;
        this.generation = generation;
        this.size = size;
        this.posx = posx;
        this.posy = posy;
        this.content = content;
    }

    @Override
    public String reference() {
        return new UncheckedText(
            new FormattedText(
                "%d %d R",
                this.number,
                this.generation
            )
        ).asString();
    }

    @Override
    public byte[] asBytes() throws Exception {
        return new FormattedText(
            "%d %d obj\n<< /Length %d >>\nstream\nBT /F1 %d Tf %d %d Td (%s) Tj ET\nendstream\nendobj\n",
            this.number,
            this.generation,
            55,
            this.size,
            this.posx,
            this.posy,
            this.content
        ).asString().getBytes();
    }
}
