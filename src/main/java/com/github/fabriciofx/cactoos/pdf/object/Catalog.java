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
package com.github.fabriciofx.cactoos.pdf.object;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Object;
import com.github.fabriciofx.cactoos.pdf.Pages;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;

/**
 * Catalog.
 *
 * @since 0.0.1
 */
public final class Catalog implements Object {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Pages.
     */
    private final Pages pages;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param pages Pages
     */
    public Catalog(final Id id, final Pages pages) {
        this(id.increment(), 0, pages);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param pages Pages
     */
    public Catalog(final int number, final int generation, final Pages pages) {
        this.number = number;
        this.generation = generation;
        this.pages = pages;
    }

    @Override
    public Indirect indirect() throws Exception {
        final Indirect indirect = this.pages.indirect();
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("Catalog"))
            .add("Pages", new Text(indirect.reference().asString()));
        return new DefaultIndirect(
            this.number,
            this.generation,
            dictionary,
            indirect
        );
    }
}
