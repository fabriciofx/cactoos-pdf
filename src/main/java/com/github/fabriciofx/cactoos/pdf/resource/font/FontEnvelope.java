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
package com.github.fabriciofx.cactoos.pdf.resource.font;

import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.indirect.NoReferenceIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.util.List;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * FontEnvelope.
 *
 * @since 0.0.1
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
public abstract class FontEnvelope implements Font {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Family.
     */
    private final FontFamily family;

    /**
     * Size.
     */
    private final int points;

    /**
     * Ctor.
     *
     * @param number Object number
     * @param family Font family
     * @param size Font size
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public FontEnvelope(
        final int number,
        final FontFamily family,
        final int size
    ) {
        this.number = number;
        this.family = family;
        this.points = size;
    }

    @Override
    public String name() {
        return new UncheckedText(
            new FormattedText("F%d", this.number)
        ).asString();
    }

    @Override
    public int size() {
        return this.points;
    }

    @Override
    public int width(final char chr) {
        return 1;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final Indirect indirect = this.family.indirect();
        final Dictionary dictionary = new Dictionary()
            .add(
                "Font",
                new Dictionary().add(
                    this.name(),
                    new Text(indirect.reference().asString())
                )
            );
        return new NoReferenceIndirect(dictionary);
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
        this.family.print(indirects);
    }
}
