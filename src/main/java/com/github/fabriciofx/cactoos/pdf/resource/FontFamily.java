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
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import java.util.List;

/**
 * Font Family.
 *
 * @since 0.0.1
 */
public final class FontFamily implements Resource {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

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
     * @param id Id number
     * @param base Font base
     * @param subtype Font subtype
     */
    public FontFamily(
        final Id id,
        final String base,
        final String subtype
    ) {
        this(id.increment(), 0, base, subtype);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param base Font base
     * @param subtype Font subtype
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public FontFamily(
        final int number,
        final int generation,
        final String base,
        final String subtype
    ) {
        this.number = number;
        this.generation = generation;
        this.base = base;
        this.subtype = subtype;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("Font"))
            .add("BaseFont", new Name(this.base))
            .add("Subtype", new Name(this.subtype));
        return new DefaultIndirect(this.number, this.generation, dictionary);
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        final Indirect indirect = this.indirect();
        if (!indirects.contains(indirect)) {
            indirects.add(indirect);
        }
    }
}
