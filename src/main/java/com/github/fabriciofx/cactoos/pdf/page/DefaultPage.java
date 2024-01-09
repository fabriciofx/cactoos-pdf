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

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;

/**
 * PageDefault.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public final class DefaultPage implements Page {
    /**
     * Resources.
     */
    private final Resources resources;

    /**
     * Page contents.
     */
    private final Contents contents;

    /**
     * Ctor.
     *
     * @param resources List of resources
     * @param contents Page contents
     */
    public DefaultPage(final Resources resources, final Contents contents) {
        this.resources = resources;
        this.contents = contents;
    }

    @Override
    public Resources resources() {
        return this.resources;
    }

    @Override
    public Contents contents() {
        return this.contents;
    }

    @Override
    public Indirect indirect(final Id id, final int parent) throws Exception {
        final int num = id.increment();
        final Indirect resrcs = this.resources.indirect(id);
        final Indirect conts = this.contents.indirect(id);
        final Dictionary dictionary = new Dictionary()
            .add("Type", new Name("Page"))
            .add("Resources", new Text(resrcs.reference().asString()))
            .add("Contents", new Text(conts.reference().asString()))
            .add("Parent", new Text(new Reference(parent, 0).asString()));
        return new DefaultIndirect(num, 0, dictionary, resrcs, conts);
    }
}
