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
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;

/**
 * Image.
 *
 * @since 0.0.1
 */
public final class Image implements Content {
    /**
     * Object id.
     */
    private final int id;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * Image name.
     */
    private final String label;

    /**
     * Image PNG.
     */
    private final Png png;

    /**
     * Ctor.
     *
     * @param id Object id
     * @param name Image name
     * @param png Raw PNG
     */
    public Image(final Id id, final String name, final Png png) {
        this(id.increment(), 0, name, png);
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param generation Object generation
     * @param name Image name
     * @param png Raw PNG
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final int id,
        final int generation,
        final String name,
        final Png png
    ) {
        this.id = id;
        this.generation = generation;
        this.label = name;
        this.png = png;
    }

    /**
     * Image name.
     *
     * @return The image name
     */
    public String name() {
        return this.label;
    }

    @Override
    public byte[] stream() throws Exception {
        return new FormattedText(
            new Joined(
                "\n",
                "2 J",
                "0.57 w",
                "q 85.04 0 0 58.06 28.35 766.83 cm /%s Do Q"
            ),
            this.label
        ).asString().getBytes();
    }

    @Override
    public Dictionary dictionary() throws Exception {
        final byte[] stream = this.stream();
        return new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
    }

    @Override
    public Reference reference() {
        return new Reference(this.id, this.generation);
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
        baos.write("endobj\n".getBytes());
        baos.write(this.png.definition());
        return baos.toByteArray();
    }

    /**
     * PNG.
     * @return The PNG
     */
    public Png content() {
        return this.png;
    }
}
