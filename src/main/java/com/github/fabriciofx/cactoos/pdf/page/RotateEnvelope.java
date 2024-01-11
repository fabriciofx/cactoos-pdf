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
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;

/**
 * Rotate envelope.
 *
 * @since 0.0.1
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
public abstract class RotateEnvelope implements Page {
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
    public RotateEnvelope(final Page page, final int angle) {
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
    public Indirect indirect(final int parent) throws Exception {
        final Indirect indirect = this.origin.indirect(parent);
        final Dictionary dictionary = indirect.dictionary().add(
            "Rotate",
            new Int(this.angle)
        );
        return new DefaultIndirect(
            indirect.reference().id(),
            indirect.reference().generation(),
            dictionary,
            this.resources().indirect(),
            this.contents().indirect()
        );
    }
}
