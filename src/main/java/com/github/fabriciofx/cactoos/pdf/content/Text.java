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
import com.github.fabriciofx.cactoos.pdf.Definition;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.text.Escaped;
import com.github.fabriciofx.cactoos.pdf.text.Indirect;
import com.github.fabriciofx.cactoos.pdf.text.Multiline;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import org.cactoos.text.FormattedText;

/**
 * Text.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UseStringBufferForStringAppends")
public final class Text implements Content {
    /**
     * Font size.
     */
    private final int size;

    /**
     * Position X.
     */
    private final int posx;

    /**
     * Position Y.
     */
    private final int posy;

    /**
     * Max line length.
     */
    private final int max;

    /**
     * Space between lines.
     */
    private final int leading;

    /**
     * Text content.
     */
    private final org.cactoos.Text content;

    /**
     * Ctor.
     *
     * @param size Font size
     * @param posx Position X
     * @param posy Position Y
     * @param content Text content
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Text(
        final int size,
        final int posx,
        final int posy,
        final org.cactoos.Text content
    ) {
        this(size, posx, posy, 80, 20, content);
    }

    /**
     * Ctor.
     *
     * @param size Font size
     * @param posx Position X
     * @param posy Position Y
     * @param max Max line length
     * @param leading Space between lines
     * @param content Text content
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Text(
        final int size,
        final int posx,
        final int posy,
        final int max,
        final int leading,
        final org.cactoos.Text content
    ) {
        this.size = size;
        this.posx = posx;
        this.posy = posy;
        this.max = max;
        this.leading = leading;
        this.content = new Escaped(content);
    }

    @Override
    public byte[] stream() throws Exception {
        final StringBuilder out = new StringBuilder();
        for (final org.cactoos.Text line : new Multiline(this.content, this.max)) {
            out.append(new FormattedText("(%s) Tj T*\n", line).asString());
        }
        return new FormattedText(
            "BT /F1 %d Tf %d %d Td %d TL\n%sET",
            this.size,
            this.posx,
            this.posy,
            this.leading,
            out.toString()
        ).asString().getBytes();
    }

    @Override
    public Definition definition(final Id id) throws Exception {
        final int num = id.value();
        final byte[] stream = this.stream();
        final Dictionary dictionary = new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(new Indirect(num, 0).asBytes());
        baos.write(dictionary.asBytes());
        baos.write("\nendobj\n".getBytes());
        return new Definition(num, 0, dictionary, baos.toByteArray());
    }
}
