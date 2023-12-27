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
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Definition;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.Serial;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.text.Indirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import java.io.ByteArrayOutputStream;

/**
 * Rotate a Page in a angle.
 *
 * @since 0.0.1
 */
public final class Rotate implements Page {
    /**
     * The Page.
     */
    private final Page origin;

    /**
     * Angle.
     */
    private final int angle;

    /**
     * Ctor.
     *
     * @param page The Page
     * @param angle The angle
     */
    public Rotate(final Page page, final int angle) {
        this.origin = page;
        this.angle = angle;
    }

    @Override
    public Resources resources() {
        return this.origin.resources();
    }

    @Override
    public Contents contents() {
        return this.origin.contents();
    }

    @Override
    public Definition definition(final Id id, final int parent)
        throws Exception {
        final Definition definition = this.origin.definition(id, parent);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final Dictionary dictionary = definition.dictionary().add(
            "Rotate",
            new Int(this.angle)
        );
        baos.write(
            new Indirect(
                definition.reference().id(),
                definition.reference().generation()
            ).asBytes()
        );
        baos.write(dictionary.asBytes());
        baos.write("\nendobj\n".getBytes());
        final Id reset = new Serial(id.value() - 2);
        baos.write(this.resources().definition(reset).asBytes());
        baos.write(this.contents().definition(reset).asBytes());
        return new Definition(
            definition.reference().id(),
            definition.reference().generation(),
            dictionary,
            baos.toByteArray()
        );
    }
}
