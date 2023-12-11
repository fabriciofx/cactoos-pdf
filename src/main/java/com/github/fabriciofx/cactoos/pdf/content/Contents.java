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
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Object;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.Bytes;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;

/**
 * Contents.
 *
 * @since 0.0.1
 */
public final class Contents extends ListEnvelope<Content>
    implements Object, Bytes {
    /**
     * Ctor.
     *
     * @param objects An array of objects
     */
    public Contents(final Content... objects) {
        this(new ListOf<>(objects));
    }

    /**
     * Ctor.
     *
     * @param list A list of objects
     */
    public Contents(final List<Content> list) {
        super(list);
    }

    @Override
    public String reference() {
        return this.stream()
            .map(Content::reference)
            .collect(Collectors.joining(" "));
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (final Bytes obj : this) {
            baos.write(obj.asBytes());
        }
        return baos.toByteArray();
    }
}
