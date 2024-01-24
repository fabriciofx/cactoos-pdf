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
import java.io.OutputStream;
import java.util.List;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;

/**
 * Resources.
 *
 * @since 0.0.1
 */
public final class Resources extends ListEnvelope<Resource>
    implements Resource {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param elements An array de elements (Resource)
     */
    public Resources(
        final Id id,
        final Resource... elements
    ) {
        this(id.increment(), 0, elements);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param elements An array of elements (Resource)
     */
    public Resources(
        final int number,
        final int generation,
        final Resource... elements
    ) {
        this(number, generation, new ListOf<>(elements));
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param list A list of elements (Resource)
     */
    public Resources(
        final int number,
        final int generation,
        final List<Resource> list
    ) {
        super(list);
        this.number = number;
        this.generation = generation;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final List<Indirect> indirects = new ListOf<>();
        for (final Resource resource : this) {
            indirects.add(resource.indirect());
        }
        Dictionary dictionary = indirects.get(0).dictionary();
        for (int idx = 1; idx < indirects.size(); ++idx) {
            dictionary = dictionary.merge(indirects.get(idx).dictionary());
        }
        return new DefaultIndirect(
            this.number,
            this.generation,
            dictionary
        );
    }

    @Override
    public void print(
        final OutputStream output,
        final int... parent
    ) throws Exception {
        output.write(this.indirect().asBytes());
        for (final Resource resource : this) {
            resource.print(output);
        }
    }
}
