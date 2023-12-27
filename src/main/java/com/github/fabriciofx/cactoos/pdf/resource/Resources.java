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

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Object;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;

/**
 * Resources.
 *
 * @since 0.0.1
 */
public final class Resources extends ListEnvelope<Resource>
    implements Object, Resource {
    /**
     * Object id.
     */
    private final int id;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * Ctor.
     *
     * @param id Object id
     * @param objects An array of objects
     */
    public Resources(final Id id, final Resource... objects) {
        this(id.increment(), 0, new ListOf<>(objects));
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param generation Object generation
     * @param list A list of objects
     */
    public Resources(
        final int id,
        final int generation,
        final List<Resource> list
    ) {
        super(list);
        this.id = id;
        this.generation = generation;
    }

    @Override
    public Reference reference() {
        return new Reference(this.id, this.generation);
    }

    @Override
    public Dictionary dictionary() throws Exception {
        Dictionary main = this.get(0).dictionary();
        for (int idx = 1; idx < this.size(); ++idx) {
            main = main.merge(this.get(idx).dictionary());
        }
        return main;
    }

    @Override
    public byte[] definition() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                this.id,
                this.generation
            ).asString().getBytes()
        );
        baos.write(this.dictionary().asBytes());
        baos.write("\nendobj\n".getBytes());
        return baos.toByteArray();
    }
}
