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

import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.Pages;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import java.io.ByteArrayOutputStream;
import org.cactoos.text.FormattedText;

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
    public Reference reference() {
        return this.origin.reference();
    }

    @Override
    public Dictionary dictionary(final Pages parent) throws Exception {
        return this.origin.dictionary(parent)
            .add("Rotate", new Int(this.angle));
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
    public byte[] with(final Pages parent) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                this.origin.reference().number(),
                this.origin.reference().generation()
            ).asString().getBytes()
        );
        baos.write(this.dictionary(parent).asBytes());
        baos.write("\nendobj\n".getBytes());
        baos.write(this.resources().asBytes());
        baos.write(this.contents().asBytes());
        return baos.toByteArray();
    }
}