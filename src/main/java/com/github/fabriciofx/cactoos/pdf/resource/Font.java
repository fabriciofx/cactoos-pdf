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

import com.github.fabriciofx.cactoos.pdf.Definition;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;

/**
 * Font.
 *
 * @since 0.0.1
 */
public final class Font implements Resource {
    /**
     * Font family.
     */
    private final FontFamily family;

    /**
     * Font name.
     */
    private final String label;

    /**
     * Ctor.
     *
     * @param family Font family
     * @param name Font name
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Font(final FontFamily family, final String name) {
        this.family = family;
        this.label = name;
    }

    /**
     * The font name.
     *
     * @return The font name
     */
    public String name() {
        return this.label;
    }

    @Override
    public Definition definition(final Id id) throws Exception {
        final Definition definition = this.family.definition(id);
        final Dictionary dictionary = new Dictionary()
            .add(
                "Font",
                new Dictionary().add(this.label, definition.dictionary())
            );
        return new Definition(dictionary, dictionary.asBytes());
    }
}
