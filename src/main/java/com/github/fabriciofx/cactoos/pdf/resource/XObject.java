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
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import org.cactoos.bytes.BytesOf;

/**
 * XObject.
 *
 * @since 0.0.1
 */
public final class XObject implements Resource {
    /**
     * Image.
     */
    private final Image image;

    /**
     * Ctor.
     *
     * @param image Image
     */
    public XObject(final Image image) {
        this.image = image;
    }

    @Override
    public Indirect indirect(final Id id) throws Exception {
        final int num = id.increment();
        final Indirect indirect = this.image.content().indirect(id);
        final Dictionary dictionary = new Dictionary()
            .add(
                "XObject",
                new Dictionary().add(
                    this.image.name(),
                    new Text(indirect.reference().asString())
                )
            );
        return new Indirect(
            num,
            0,
            dictionary,
            new BytesOf(indirect.asBytes())
        );
    }
}
