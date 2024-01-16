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
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Format;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.XObject;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Image.
 *
 * @since 0.0.1
 */
public final class Image implements Content {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Id.
     */
    private final Id id;

    /**
     * Image Format.
     */
    private final Format fmt;

    /**
     * Position X.
     */
    private final double posx;

    /**
     * Position Y.
     */
    private final double posy;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param format Raw image format
     * @param posx Position X
     * @param posy Position Y
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final Id id,
        final Format format,
        final double posx,
        final double posy
    ) {
        this(
            id.increment(),
            0,
            id,
            format,
            posx,
            posy
        );
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param id Id number
     * @param format Raw image format
     * @param posx Position X
     * @param posy Position Y
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final int number,
        final int generation,
        final Id id,
        final Format format,
        final double posx,
        final double posy
    ) {
        this.number = number;
        this.generation = generation;
        this.id = id;
        this.fmt = format;
        this.posx = posx;
        this.posy = posy;
    }

    /**
     * Image name.
     *
     * @return The image name
     */
    public String name() {
        return new UncheckedText(
            new FormattedText("I%d", this.number)
        ).asString();
    }

    @Override
    public byte[] asStream() throws Exception {
        return new FormattedText(
            "q %d 0 0 %d %.2f %.2f cm /%s Do Q",
            Locale.ENGLISH,
            this.fmt.width(),
            this.fmt.height(),
            this.posx,
            this.posy,
            this.name()
        ).asString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public List<Resource> resource() {
        return new ListOf<>(new XObject(this.id, this));
    }

    @Override
    public Indirect indirect() throws Exception {
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
        return new DefaultIndirect(this.number, this.generation, dictionary);
    }

    /**
     * Image format.
     *
     * @return The image format
     */
    public Format format() {
        return this.fmt;
    }
}
