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
     * Ctor.
     *
     * @param objects An array of objects
     */
    public Resources(final Resource... objects) {
        this(new ListOf<>(objects));
    }

    /**
     * Ctor.
     *
     * @param list A list of objects
     */
    public Resources(final List<Resource> list) {
        super(list);
    }

    @Override
    public Definition definition(final Id id) throws Exception {
        final int num = id.increment();
        Dictionary dictionary = this.get(0).definition(id).dictionary();
        for (int idx = 1; idx < this.size(); ++idx) {
            dictionary = dictionary.merge(
                this.get(idx).definition(id).dictionary()
            );
        }
        return new Definition(num, 0, dictionary);
    }
}
