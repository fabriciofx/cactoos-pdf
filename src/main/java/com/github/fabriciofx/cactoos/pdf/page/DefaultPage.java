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

import com.github.fabriciofx.cactoos.pdf.Count;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.Pages;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.io.ByteArrayOutputStream;
import org.cactoos.text.FormattedText;

/**
 * PageDefault.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public final class DefaultPage implements Page {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Object generation.
     */
    private final int generation;

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
     * @param count Counter
     * @param resources List of resources
     * @param contents Page contents
     */
    public DefaultPage(
        final Count count,
        final Resources resources,
        final Contents contents
    ) {
        this(count.increment(), 0, resources, contents);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Object generation
     * @param resources List of resources
     * @param contents Page contents
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultPage(
        final int number,
        final int generation,
        final Resources resources,
        final Contents contents
    ) {
        this.number = number;
        this.generation = generation;
        this.resources = resources;
        this.contents = contents;
    }

    @Override
    public Reference reference() {
        return new Reference(this.number, this.generation);
    }

    @Override
    public Dictionary dictionary(final Pages parent) throws Exception {
        return new Dictionary()
            .add("Type", new Name("Page"))
            .add("Resources", new Text(this.resources.reference().asString()))
            .add("Contents", new Text(this.contents.reference().asString()))
            .add("Parent", new Text(parent.reference().asString()));
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
    public byte[] with(final Pages parent) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                this.number,
                this.generation
            ).asString().getBytes()
        );
        baos.write(this.dictionary(parent).asBytes());
        baos.write("\nendobj\n".getBytes());
        baos.write(this.resources.asBytes());
        baos.write(this.contents.asBytes());
        return baos.toByteArray();
    }
}
