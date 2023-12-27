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
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.FormattedText;

/**
 * FlateEncode.
 *
 * @since 0.0.1
 */
public final class FlateEncode implements Content {
    /**
     * The content.
     */
    private final Content origin;

    /**
     * Ctor.
     *
     * @param content The content
     */
    public FlateEncode(final Content content) {
        this.origin = content;
    }

    @Override
    public byte[] stream() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final Deflater deflater = new Deflater();
        try (
            DeflaterOutputStream out = new DeflaterOutputStream(
                baos,
                deflater
            )
        ) {
            new LengthOf(
                new TeeInput(
                    this.origin.stream(),
                    new OutputTo(out)
                )
            ).value();
            deflater.end();
        }
        return baos.toByteArray();
    }

    @Override
    public Dictionary dictionary() throws Exception {
        final byte[] stream = this.stream();
        return new Dictionary()
            .add("Length", new Int(stream.length))
            .add("Filter", new Name("FlateDecode"))
            .with(new Stream(stream));
    }

    @Override
    public Reference reference() {
        return this.origin.reference();
    }

    @Override
    public byte[] definition() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                this.reference().id(),
                this.reference().generation()
            ).asString().getBytes()
        );
        baos.write(this.dictionary().asBytes());
        baos.write("\nendobj\n".getBytes());
        return baos.toByteArray();
    }
}
